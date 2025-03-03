package com.indexia.TecnicosRegistrar.model.Repository;

import com.indexia.TecnicosRegistrar.model.Entity.Bancos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BancosDAO extends JpaRepository<Bancos,Integer> {
    Optional<Bancos> findByNombre(String nombre);
}
