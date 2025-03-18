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

	public DetalleDeInfoDTO detalleDeInfo(Integer idtecnico) {
		Object[] tmpArr = codigoActivacionRepository.detalleDeInfo(idtecnico).get();
		
		if(tmpArr.length == 0) {
			return new DetalleDeInfoDTO();
		}else {
			Object[] dataTmp = (Object[]) tmpArr[0];
			return new DetalleDeInfoDTO((String) dataTmp[0], // nombre
					(String) dataTmp[1], // codigo
					(String) dataTmp[2] // codigoResumido
			);
		}
	}
}
