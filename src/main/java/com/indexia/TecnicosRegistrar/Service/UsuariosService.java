package com.indexia.TecnicosRegistrar.Service;

import com.indexia.TecnicosRegistrar.model.Entity.Usuarios;

public interface UsuariosService {
	public boolean validarUsuario(String nombreUsuario, String credencial);
}
