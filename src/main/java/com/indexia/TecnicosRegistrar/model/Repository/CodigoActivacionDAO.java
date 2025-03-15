package com.indexia.TecnicosRegistrar.model.Repository;

import com.indexia.TecnicosRegistrar.model.Entity.CodigosActivacion;
import com.indexia.TecnicosRegistrar.model.utils.DetalleDeInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CodigoActivacionDAO extends JpaRepository<CodigosActivacion, Integer> {

	CodigosActivacion findByTecnicoIdTecnico(Integer idtecnico);

	@Query(value = "SELECT new com.indexia.TecnicosRegistrar.model.utils.DetalleDeInfoDTO(b.nombre, ca.codigo, ca.codigoresumido) "
			+ "FROM BancosTecnicos bt " + "JOIN CodigosActivacion ca ON ca.idtecnico = bt.idtecnico "
			+ "JOIN Bancos b ON b.idbanco = bt.idbanco " + "WHERE bt.idtecnico = :idtecnico")
	DetalleDeInfoDTO detalleDeInfo(@Param("idtecnico") Integer idtecnico);

}
