package com.indexia.TecnicosRegistrar.model.Repository;

import com.indexia.TecnicosRegistrar.model.Entity.Bancos;
import com.indexia.TecnicosRegistrar.model.Entity.BancosTecnicos;
import com.indexia.TecnicosRegistrar.model.Entity.BancosTecnicosID;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BancosTecnicosDAO extends JpaRepository<BancosTecnicos, BancosTecnicosID> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO bancostecnicos (idbanco, idtecnico) VALUES (:idBanco, :idTecnico)", nativeQuery = true)
    void insertarBancosTecnicos(Integer idBanco, Integer idTecnico);
    @Query("SELECT b.bancos FROM BancosTecnicos b WHERE b.tecnico.idTecnico = :idTecnico")
    Bancos findBancoByTecnicoId(@Param("idTecnico") Integer idTecnico);
    @Query("SELECT bt.bancos FROM BancosTecnicos bt WHERE bt.tecnico.idTecnico = :idTecnico")
    List<Bancos> findBancosByTecnicoId(@Param("idTecnico") Integer idTecnico);
}
