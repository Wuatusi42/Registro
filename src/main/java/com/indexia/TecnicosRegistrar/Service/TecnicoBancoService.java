package com.indexia.TecnicosRegistrar.Service;

import com.indexia.TecnicosRegistrar.model.Entity.Bancos;
import com.indexia.TecnicosRegistrar.model.Entity.Tecnico;

public interface TecnicoBancoService {
	 public Bancos obtenerBancoPorTecnico(Integer idTecnico);
	 
}
