package com.indexia.TecnicosRegistrar.model.Repository;

import com.indexia.TecnicosRegistrar.model.Entity.CodigosActivacion;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CodigoActivacionDAO extends JpaRepository<CodigosActivacion, Integer> {

	CodigosActivacion findByTecnicoIdTecnico(Integer idtecnico);

	@Query(nativeQuery = true,
			value = 
			  "SELECT b.nombre, ca.codigo, ca.codigoresumido "
			+ "FROM bancostecnicos bt, codigosactivacion ca, bancos b "
			+ "WHERE b.idbanco = bt.idbanco " 
			+ "AND bt.idtecnico = :idtecnico "
			+ "AND ca.idtecnico = :idtecnico "
			+ "AND b.nombre = 'Banorte'")
	Optional<Object[]> detalleDeInfo(@Param("idtecnico") Integer idtecnico);

}
