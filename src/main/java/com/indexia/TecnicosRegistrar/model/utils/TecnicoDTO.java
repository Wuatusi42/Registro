package com.indexia.TecnicosRegistrar.model.utils;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class TecnicoDTO {
	private Integer idTecnicoDTO;
	private String name;
	private String apellidoPatern;
	private String apellidoMatern;
	private String correo;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateBhirthday;
	private String RFC;
	private String CURP;
	private Date dateRegister;
	private Boolean status;

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getApellidoPatern() {
		return apellidoPatern;
	}

	public void setApellidoPatern(String apellidoPatern) {
		this.apellidoPatern = apellidoPatern;
	}

	public String getApellidoMatern() {
		return apellidoMatern;
	}

	public void setApellidoMatern(String apellidoMatern) {
		this.apellidoMatern = apellidoMatern;
	}

	public String getRFC() {
		return RFC;
	}

	public void setRFC(String RFC) {
		this.RFC = RFC;
	}

	public String getCURP() {
		return CURP;
	}

	public void setCURP(String CURP) {
		this.CURP = CURP;
	}


	public Date getDateRegister() {
		return dateRegister;
	}

	public void setDateRegister(Date dateRegister) {
		this.dateRegister = dateRegister;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Integer getIdTecnicoDTO() {
		return idTecnicoDTO;
	}

	public void setIdTecnicoDTO(Integer idTecnicoDTO) {
		this.idTecnicoDTO = idTecnicoDTO;
	}
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDateBhirthday() {
		return dateBhirthday;
	}

	public void setDateBhirthday(Date dateBhirthday) {
		this.dateBhirthday = dateBhirthday;
	}
	
}
