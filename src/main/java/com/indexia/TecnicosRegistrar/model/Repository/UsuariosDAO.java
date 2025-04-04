package com.indexia.TecnicosRegistrar.model.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.indexia.TecnicosRegistrar.model.Entity.Usuarios;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosDAO extends JpaRepository<Usuarios,String> {
	Usuarios findByNombreUsuario(String username);
}
