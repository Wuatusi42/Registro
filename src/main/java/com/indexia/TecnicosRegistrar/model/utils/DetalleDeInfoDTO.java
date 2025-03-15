package com.indexia.TecnicosRegistrar.model.utils;

public class DetalleDeInfoDTO {
	  private String nombreBanco;
	  private String codigoActivacion;
	  private String codigoResumido;
	  
	public String getNombreBanco() {
		return nombreBanco;
	}
	public void setNombreBanco(String nombreBanco) {
		this.nombreBanco = nombreBanco;
	}
	public String getCodigoActivacion() {
		return codigoActivacion;
	}
	public void setCodigoActivacion(String codigoActivacion) {
		this.codigoActivacion = codigoActivacion;
	}
	public String getCodigoResumido() {
		return codigoResumido;
	}
	public void setCodigoResumido(String codigoResumido) {
		this.codigoResumido = codigoResumido;
	}
	public DetalleDeInfoDTO(String nombreBanco, String codigoActivacion, String codigoResumido) {
		this.nombreBanco = nombreBanco;
		this.codigoActivacion = codigoActivacion;
		this.codigoResumido = codigoResumido;
	}
	
}
