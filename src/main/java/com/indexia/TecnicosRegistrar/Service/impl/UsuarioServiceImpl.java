package com.indexia.TecnicosRegistrar.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.indexia.TecnicosRegistrar.Service.UsuariosService;
import com.indexia.TecnicosRegistrar.model.Entity.Usuarios;
import com.indexia.TecnicosRegistrar.model.Repository.UsuariosDAO;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuariosService {
	@Autowired
	private UsuariosDAO usuariosdao;


	@Override
	public boolean validarUsuario(String nombreUsuario, String credencial) {
		Usuarios usuario = usuariosdao.findByNombreUsuario(nombreUsuario);
		return usuario != null && usuario.getCredencial().equals(credencial);
	}
}
