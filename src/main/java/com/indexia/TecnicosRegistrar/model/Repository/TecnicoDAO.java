package com.indexia.TecnicosRegistrar.model.Repository;

import com.indexia.TecnicosRegistrar.model.Entity.Bancos;
import com.indexia.TecnicosRegistrar.model.Entity.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TecnicoDAO extends JpaRepository<Tecnico,Integer> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdTecnicoNot(String email, Integer idTecnico);
    List<Tecnico> findByNombre(String nombre);
    List<Tecnico> findByEmail(String email);
    List<Tecnico> findByNombreAndEmail(String nombre, String email);
    
}
