package com.indexia.TecnicosRegistrar.model.mapper;

import com.indexia.TecnicosRegistrar.model.Entity.Tecnico;
import com.indexia.TecnicosRegistrar.model.utils.TecnicoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Mapper
public interface TecnicoMapper {
	TecnicoMapper INSTANCE = Mappers.getMapper(TecnicoMapper.class);

	@Mapping(source = "nombre", target = "name")
	@Mapping(source = "apellidoPaterno", target = "apellidoPatern")
	@Mapping(source = "apellidoMaterno", target = "apellidoMatern")
	@Mapping(source = "fechaNacimiento", target = "dateBhirthday")
	@Mapping(source = "rfc", target = "RFC")
	@Mapping(source = "curp", target = "CURP")
	@Mapping(source = "fechaRegistro", target = "dateRegister")
	@Mapping(source = "email", target = "correo")
	TecnicoDTO tecnicoToTecnicoDTO(Tecnico tecnico);

	@Mapping(source = "name", target = "nombre")
	@Mapping(source = "apellidoPatern", target = "apellidoPaterno")
	@Mapping(source = "apellidoMatern", target = "apellidoMaterno")
	@Mapping(source = "dateBhirthday", target = "fechaNacimiento")
	@Mapping(source = "RFC", target = "rfc")
	@Mapping(source = "CURP", target = "curp")
	@Mapping(source = "dateRegister", target = "fechaRegistro")
	@Mapping(source = "correo", target = "email")
	@Mapping(target = "activo", constant = "true")
	Tecnico tecnicoDTOtoTecnico(TecnicoDTO tecnicoDTO);
}
