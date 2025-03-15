package com.indexia.TecnicosRegistrar.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indexia.TecnicosRegistrar.Service.TecnicoBancoService;
import com.indexia.TecnicosRegistrar.model.Entity.Bancos;
import com.indexia.TecnicosRegistrar.model.Entity.Tecnico;
import com.indexia.TecnicosRegistrar.model.Repository.BancosTecnicosDAO;
import com.indexia.TecnicosRegistrar.model.utils.TecnicoDTO;

import antlr.collections.List;

@Service
public class BancoTecnicoServiceImpl implements TecnicoBancoService{
	@Autowired
    private BancosTecnicosDAO tecnicoBancoRepository;

	@Override
	public Bancos obtenerBancoPorTecnico(Integer idTecnico) {
		return tecnicoBancoRepository.findBancoByTecnicoId(idTecnico);
	}

}

