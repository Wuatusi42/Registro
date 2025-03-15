package com.indexia.TecnicosRegistrar.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.indexia.TecnicosRegistrar.Service.CodigoService;
import com.indexia.TecnicosRegistrar.model.Repository.CodigoActivacionDAO;
import com.indexia.TecnicosRegistrar.model.utils.DetalleDeInfoDTO;

@Service
public class CodigosServiceImpl implements CodigoService {
	
	 @Autowired
	 private CodigoActivacionDAO codigoActivacionRepository;
	 
	@Override
	public DetalleDeInfoDTO detalleDeInfo(Integer idtecnico) {
		return codigoActivacionRepository.detalleDeInfo(idtecnico);
	}
	
}
