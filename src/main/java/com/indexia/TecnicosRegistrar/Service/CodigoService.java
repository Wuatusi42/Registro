package com.indexia.TecnicosRegistrar.Service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.indexia.TecnicosRegistrar.model.Entity.CodigosActivacion;
import com.indexia.TecnicosRegistrar.model.utils.DetalleDeInfoDTO;

public interface CodigoService {
	 public DetalleDeInfoDTO detalleDeInfo(Integer idtecnico);
}
