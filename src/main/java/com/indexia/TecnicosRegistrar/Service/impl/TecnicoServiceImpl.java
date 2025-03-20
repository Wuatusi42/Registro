package com.indexia.TecnicosRegistrar.Service.impl;

import com.indexia.TecnicosRegistrar.Service.TecnicoService;
import com.indexia.TecnicosRegistrar.model.Entity.Bancos;
import com.indexia.TecnicosRegistrar.model.Entity.BancosTecnicos;
import com.indexia.TecnicosRegistrar.model.Entity.CodigosActivacion;
import com.indexia.TecnicosRegistrar.model.Entity.Tecnico;
import com.indexia.TecnicosRegistrar.model.Repository.BancosDAO;
import com.indexia.TecnicosRegistrar.model.Repository.BancosTecnicosDAO;
import com.indexia.TecnicosRegistrar.model.Repository.CodigoActivacionDAO;
import com.indexia.TecnicosRegistrar.model.Repository.TecnicoDAO;
import com.indexia.TecnicosRegistrar.model.mapper.TecnicoMapper;
import com.indexia.TecnicosRegistrar.model.utils.RespuestaServicio;
import com.indexia.TecnicosRegistrar.model.utils.TecnicoDTO;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class TecnicoServiceImpl implements TecnicoService {
	Scanner sc = new Scanner(System.in);
	@Autowired
	private TecnicoDAO tecnicoDAO;
	private TecnicoMapper tecnicoMapper;
	@Autowired
	private CodigoActivacionDAO codigoActivacionDAO;
	@Autowired
	private BancosTecnicosDAO bancosTecnicosDAO;
	@Autowired
	private BancosDAO bancosDAO;

	@Override
	public RespuestaServicio formTecnico(TecnicoDTO tecnicoDTO) {
		RespuestaServicio respuestaServicio = new RespuestaServicio();

		try {
			Tecnico tecnico = new Tecnico();

			if (tecnicoDTO.getIdTecnicoDTO() != null) {
				// Actualización
				tecnico = tecnicoDAO.findById(tecnicoDTO.getIdTecnicoDTO())
						.orElseThrow(() -> new Exception("Técnico no encontrado"));
				respuestaServicio = validacionesActualizar(tecnicoDTO);
				if (respuestaServicio.getCodigoRespuesta().equals("500")) {
					return respuestaServicio;
				}
				tecnico.setNombre(tecnicoDTO.getName());
				tecnico.setApellidoPaterno(tecnicoDTO.getApellidoPatern());
				tecnico.setApellidoMaterno(tecnicoDTO.getApellidoMatern());
				tecnico.setEmail(tecnicoDTO.getCorreo());
				tecnico.setRfc(tecnicoDTO.getRFC());
				tecnico.setCurp(tecnicoDTO.getCURP());
				tecnico.setFechaNacimiento(tecnicoDTO.getDateBhirthday());
				tecnico.setTelefono(tecnicoDTO.getPhone());
				tecnico.setEstado(tecnicoDTO.getState());
				tecnico.setZona(tecnicoDTO.getZone());
				tecnico.setActivo(tecnicoDTO.getStatus());
				tecnicoDAO.save(tecnico);
				respuestaServicio.setMensajeRespuesta("Técnico actualizado correctamente.");
			} else {
				respuestaServicio = validaciones(tecnicoDTO);
				if (respuestaServicio.getCodigoRespuesta().equals("500")) {
					return respuestaServicio;
				}

				// Registro de un nuevo técnico
				Tecnico tecEntity = TecnicoMapper.INSTANCE.tecnicoDTOtoTecnico(tecnicoDTO);
				tecEntity.setFechaNacimiento(tecnicoDTO.getDateBhirthday());
				tecEntity.setEmail(tecEntity.getEmail().toLowerCase());
				tecEntity.setFechaRegistro(new Date());
				tecnicoDAO.save(tecEntity);

				// Insertar Código de Activación
				CodigosActivacion codActEntity = new CodigosActivacion();
				codActEntity.setTecnico(tecEntity);
				codActEntity.setCaducidad(LocalDateTime.now().plusYears(2));
				codActEntity.setCodigo("TPV123codigo");
				codActEntity.setUtilizado(false);
				codActEntity.setFechaRegistro(LocalDateTime.now());
				codActEntity.setCodigoResumido("TPV123");
				codigoActivacionDAO.save(codActEntity);

				// Insertar en BancosTecnicos
				Optional<Bancos> bancoEntity = bancosDAO.findByNombre("Banorte");
				if (bancoEntity.isPresent()) {
					BancosTecnicos bancTecEntity = new BancosTecnicos();
					bancTecEntity.setBancos(bancoEntity.get());
					bancTecEntity.setTecnico(tecEntity);
					bancosTecnicosDAO.insertarBancosTecnicos(bancoEntity.get().getIdBanco(), tecEntity.getIdTecnico());
				}

				respuestaServicio.setMensajeRespuesta("Técnico registrado correctamente.");
			}

		} catch (Exception e) {
			respuestaServicio.setMensajeRespuesta("Error al procesar el técnico: " + e.getMessage());
			e.printStackTrace();
			System.out.println("Exeption catched");
		}

		return respuestaServicio;
	}

	@Override
	public RespuestaServicio registerFormat(InputStream file) throws IOException {
		RespuestaServicio respuestaServicio = new RespuestaServicio();
		List<Tecnico> tecnicoList = new LinkedList<>();

		log("Iniciando el proceso de registro de formato.");

		try {
			Workbook workbook = WorkbookFactory.create(file);
			Sheet sheet = workbook.getSheetAt(0);

			Row rowTitulo = sheet.getRow(sheet.getFirstRowNum());
			if (rowTitulo == null || rowTitulo.getCell(0) == null
					|| rowTitulo.getCell(0).getStringCellValue().isEmpty()) {
				logError("El archivo no contiene títulos válidos en la primera fila.");
				return new RespuestaServicio("500", "El archivo no contiene títulos validos en la primera fila");
			}

			String[] titulosEsperados = { "Nombre (s)", "Apellido Paterno", "Apellido Materno", "email", "TELEFONO",
					"CURP", "RFC", "ESTADO", "POBLACIÓN O ZONA QUE ATIENDE" };
			for (int j = 0; j < titulosEsperados.length; j++) {
				if (rowTitulo.getCell(j) == null
						|| !rowTitulo.getCell(j).getStringCellValue().equalsIgnoreCase(titulosEsperados[j])) {
					logError("Los titulos de la primera fila no están en el orden esperado.");
					return new RespuestaServicio("500",
							"Los titulos de la primera fila no estan en el orden esperado, revise su formato.");
				}
			}

			Row rowDescripcion = sheet.getRow(1);
			if (rowDescripcion == null || rowDescripcion.getCell(0) == null
					|| rowDescripcion.getCell(0).getStringCellValue().isEmpty()) {
				logError("La segunda fila debe contener descripciones válidas.");
				return new RespuestaServicio("500",
						"La segunda fila debe contener descripciones validas, revise su formato");
			}

			for (int i = 2; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row != null) {
					String email = getCellValue(row, 3).trim();

					if (existeEmail(email)) {
						logError("El email '" + email + "' ya existe en la base de datos.");
						return new RespuestaServicio("500", "El email '" + email
								+ "' ya existe en la base de datos. No se inserto ningun registro.");
					}
					if (!isValidEmail(email)) {
						logError("El email '" + email + "' tiene un formato inválido.");
						return new RespuestaServicio("500",
								"El email '" + email + "' tiene un formato invalido. No se inserto ningun registro.");
					}
				}
			}

			for (int i = 2; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row != null) {
					Tecnico tecnico = new Tecnico();

					String nombre = getCellValue(row, 0);
					String apellidoPaterno = getCellValue(row, 1);
					String apellidoMaterno = getCellValue(row, 2);
					String email = getCellValue(row, 3).toLowerCase().trim();
					String telefono = getCellValue(row, 4).trim();
					String curp = getCellValue(row, 5);
					String rfc = getCellValue(row, 6);
					String estado = getCellValue(row, 7);
					String zona = getCellValue(row, 8);

					log("Telefono obtenido: " + telefono);

					if (nombre.isEmpty() && apellidoPaterno.isEmpty() && apellidoMaterno.isEmpty() && email.isEmpty()
							&& curp.isEmpty() && rfc.isEmpty() && telefono.isEmpty() && zona.isEmpty()
							&& estado.isEmpty()) {
						continue;
					}

					tecnico.setNombre(nombre);
					tecnico.setApellidoPaterno(apellidoPaterno);
					tecnico.setApellidoMaterno(apellidoMaterno);
					tecnico.setEmail(email);
					tecnico.setCurp(curp);
					tecnico.setRfc(rfc);
					tecnico.setTelefono(telefono.isEmpty() ? "SIN TELEFONO" : telefono);
					tecnico.setEstado(estado);
					tecnico.setZona(zona);
					tecnico.setActivo(true);
					tecnico.setFechaRegistro(new Date());

					tecnicoList.add(tecnico);
				}
			}

			if (!tecnicoList.isEmpty()) {
				tecnicoDAO.saveAll(tecnicoList);
			}

			for (Tecnico tecnico : tecnicoList) {
				CodigosActivacion codActEntity = new CodigosActivacion();
				codActEntity.setTecnico(tecnico);
				codActEntity.setCaducidad(LocalDateTime.now().plusYears(2));
				codActEntity.setCodigo("TPV123codigo");
				codActEntity.setUtilizado(false);
				codActEntity.setFechaRegistro(LocalDateTime.now());
				codActEntity.setCodigoResumido("TPV123");
				codigoActivacionDAO.save(codActEntity);

				BancosTecnicos bancTecEntity = new BancosTecnicos();
				Optional<Bancos> bancoEntity = bancosDAO.findByNombre("Banorte");
				if (bancoEntity.isPresent()) {
					bancTecEntity.setBancos(bancoEntity.get());
					bancTecEntity.setTecnico(tecnico);
					bancosTecnicosDAO.insertarBancosTecnicos(bancoEntity.get().getIdBanco(), tecnico.getIdTecnico());
				} else {
					logError("Banco no encontrado.");
					return new RespuestaServicio("500", "Banco no encontrado.");
				}
			}

			log("Formato registrado correctamente.");
			return new RespuestaServicio("200", "Formato registrado correctamente.");
		} catch (Exception e) {
			logError("Error al procesar el archivo de registro: " + e.getMessage());
			return new RespuestaServicio("500", "Hubo un error al procesar el archivo. Intente nuevamente.");
		}
	}

	private String getCellValue(Row row, int columnIndex) {
		if (row.getCell(columnIndex) == null) {
			return "";
		}

		Cell cell = row.getCell(columnIndex);
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue().trim();
		case NUMERIC:
			return String.valueOf((long) cell.getNumericCellValue());
		default:
			return "";
		}
	}

	public RespuestaServicio validacionesActualizar(TecnicoDTO tecnicoDTO) {
		RespuestaServicio respuestaServicio = new RespuestaServicio();
		respuestaServicio.setCodigoRespuesta("500");
		if (!isValidNombre(tecnicoDTO.getName())) {
			respuestaServicio.setMensajeRespuesta("Nombre(s) con la primera letra mayuscula y las demas minusculas");
			return respuestaServicio;
		}
		if (!isValidApePaterno(tecnicoDTO.getApellidoPatern())) {
			respuestaServicio.setMensajeRespuesta("Apellido paterno con la primera letra mayuscula");
			return respuestaServicio;
		}
		if (!isValidApeMaterno(tecnicoDTO.getApellidoMatern())) {
			respuestaServicio.setMensajeRespuesta("Apellido Materno con la primera letra mayuscula");
			return respuestaServicio;
		}
		if (existeEmailAct(tecnicoDTO.getCorreo(), tecnicoDTO.getIdTecnicoDTO())) {
			respuestaServicio.setMensajeRespuesta("Este correo ya esta registrado");
			return respuestaServicio;
		}
		if (!isValidEmail(tecnicoDTO.getCorreo())) {
			respuestaServicio.setMensajeRespuesta("Sintaxis del correo mal ejecutada");
			return respuestaServicio;
		}
		if (!isValidRFC(tecnicoDTO.getRFC())) {
			respuestaServicio.setMensajeRespuesta("El RFC en MAYUSCULAS y revisar que tengan 13 digitos");
			return respuestaServicio;
		}
		if (!isValidCURP(tecnicoDTO.getCURP())) {
			respuestaServicio.setMensajeRespuesta("El CURP en MAYUSCULAS y revisar que tenga 18 digitos");
		}
		return new RespuestaServicio("200", "validaciones exitosas");
	}

	public RespuestaServicio validacionesFormat(Tecnico tecnico) {
		RespuestaServicio respuestaServicio = new RespuestaServicio();
		respuestaServicio.setCodigoRespuesta("500");
		if (!isValidEmail(tecnico.getEmail())) {
			respuestaServicio.setMensajeRespuesta("Sintaxis del correo mal ejecutada");
			return respuestaServicio;
		}
		if (existeEmail(tecnico.getEmail())) {
			respuestaServicio.setMensajeRespuesta("Este correo ya esta registrado");
			return respuestaServicio;
		}
		return new RespuestaServicio("200", "validaciones exitosas");
	}

	public RespuestaServicio validaciones(TecnicoDTO tecnicoDTO) {
		RespuestaServicio respuestaServicio = new RespuestaServicio();
		respuestaServicio.setCodigoRespuesta("500");
		if (!isValidEmail(tecnicoDTO.getCorreo())) {
			respuestaServicio.setMensajeRespuesta("Sintaxis del correo mal ejecutada");
			return respuestaServicio;
		}
		if (existeEmail(tecnicoDTO.getCorreo())) {
			respuestaServicio.setMensajeRespuesta("Este correo ya esta registrado");
			return respuestaServicio;
		}
		if (!isValidNombre(tecnicoDTO.getName())) {
			respuestaServicio.setMensajeRespuesta("Nombre(s) con la primera letra mayuscula y las demas minusculas");
			return respuestaServicio;
		}
		if (!isValidApePaterno(tecnicoDTO.getApellidoPatern())) {
			respuestaServicio.setMensajeRespuesta("Apellido paterno con la primera letra mayuscula");
			return respuestaServicio;
		}
		if (!isValidApeMaterno(tecnicoDTO.getApellidoMatern())) {
			respuestaServicio.setMensajeRespuesta("Apellido Materno con la primera letra mayuscula");
			return respuestaServicio;
		}
		if (!isValidRFC(tecnicoDTO.getRFC())) {
			respuestaServicio.setMensajeRespuesta("El RFC en MAYUSCULAS y revisar que tengan 13 digitos");
			return respuestaServicio;
		}
		if (!isValidCURP(tecnicoDTO.getCURP())) {
			respuestaServicio.setMensajeRespuesta("El CURP en MAYUSCULAS y revisar que tenga 18 digitos");
		}
		return new RespuestaServicio("200", "validaciones exitosas");
	}

	@Override
	public boolean existeEmail(String email) {
		return tecnicoDAO.existsByEmail(email);
	}

	@Override
	public boolean isValidEmail(String email) {
		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
		return email != null && email.matches(emailRegex);
	}

	@Override
	public boolean isValidNombre(String nombre) {
		String regex = "^([A-ZÁÉÍÓÚ][a-záéíóú]+)(\\s[A-ZÁÉÍÓÚ][a-záéíóú]+)*$";
		return nombre != null && nombre.matches(regex);
	}

	@Override
	public boolean isValidApePaterno(String apellidoPaterno) {
		String regex = "^[A-ZÁÉÍÓÚ][a-záéíóú]+$";
		return apellidoPaterno != null && apellidoPaterno.matches(regex);
	}

	@Override
	public boolean isValidRFC(String rfc) {
		String regex = "^[A-Z]{4}[0-9]{6}[A-Z0-9]{3}$";
		return rfc != null && rfc.matches(regex);
	}

	@Override
	public boolean isValidApeMaterno(String apellidoMaterno) {
		return apellidoMaterno != null && apellidoMaterno.matches("^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+$");
	}

	@Override
	public boolean isValidCURP(String curp) {
		String regex = "^[A-Z]{4}[0-9]{6}[HM][A-Z]{2}[A-Z]{3}[0-9A-Z]{2}$";
		return curp != null && curp.matches(regex);
	}

	@Override
	public List<Tecnico> obtenerTodosLosTecnicos() {
		List<Tecnico> tecnicos = tecnicoDAO.findAll();
		return tecnicos;
	}

	@Override
	public TecnicoDTO obtenerTecnico(Integer id) {
		Tecnico tecnico = tecnicoDAO.findById(id).orElse(null);
		if (tecnico == null) {
			return null; // Manejo de error en el Controller
		}

		// Convertir a DTO (idealmente con MapStruct o un método utilitario)
		TecnicoDTO tecnicoDTO = new TecnicoDTO();
		tecnicoDTO.setIdTecnicoDTO(tecnico.getIdTecnico());
		tecnicoDTO.setName(tecnico.getNombre());
		tecnicoDTO.setApellidoPatern(tecnico.getApellidoPaterno());
		tecnicoDTO.setApellidoMatern(tecnico.getApellidoMaterno());
		tecnicoDTO.setCorreo(tecnico.getEmail());
		tecnicoDTO.setRFC(tecnico.getRfc());
		tecnicoDTO.setCURP(tecnico.getCurp());
		tecnicoDTO.setDateBhirthday(tecnico.getFechaNacimiento());
		tecnicoDTO.setDateRegister(tecnico.getFechaRegistro());
		tecnicoDTO.setPhone(tecnico.getTelefono());
		tecnicoDTO.setState(tecnico.getEstado());
		tecnicoDTO.setZone(tecnico.getZona());
		tecnicoDTO.setStatus(tecnico.getActivo());
		return tecnicoDTO;
	}

	@Override
	public boolean existeEmailAct(String email, Integer idTecnico) {
		return tecnicoDAO.existsByEmailAndIdTecnicoNot(email, idTecnico);
	}

	@Override
	public List<Tecnico> filtrarTecnicos(String nombre, String email) {
		if (nombre != null && !nombre.isEmpty() && email != null && !email.isEmpty()) {
			return tecnicoDAO.findByNombreAndEmail(nombre, email);
		} else if (nombre != null && !nombre.isEmpty()) {
			return tecnicoDAO.findByNombre(nombre);
		} else if (email != null && !email.isEmpty()) {
			return tecnicoDAO.findByEmail(email);
		}
		return tecnicoDAO.findAll();
	}

	private void log(String message) {
		System.out.println("[INFO] " + message);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("registro.log"), true))) {
			writer.write("[INFO] " + message + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Método para loguear errores
	private void logError(String message) {
		System.err.println("[ERROR] " + message);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("registro.log"), true))) {
			writer.write("[ERROR] " + message + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}