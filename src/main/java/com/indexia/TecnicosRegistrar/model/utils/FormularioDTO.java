package com.indexia.TecnicosRegistrar.model.utils;

public class FormularioDTO {
	private TecnicoDTO tecnicoDTO;
	private DetalleDeInfoDTO infoDetail;
	public TecnicoDTO getTecnicoDTO() {
		return tecnicoDTO;
	}
	public void setTecnicoDTO(TecnicoDTO tecnicoDTO) {
		this.tecnicoDTO = tecnicoDTO;
	}
	public DetalleDeInfoDTO getInfoDetail() {
		return infoDetail;
	}
	public void setInfoDetail(DetalleDeInfoDTO infoDetail) {
		this.infoDetail = infoDetail;
	}
	
}
