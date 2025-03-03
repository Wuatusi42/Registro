package com.indexia.TecnicosRegistrar.model.mapper;

import com.indexia.TecnicosRegistrar.model.Entity.CodigosActivacion;
import com.indexia.TecnicosRegistrar.model.utils.CodigosActivacionDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CodigoActivacionMapper {
/*
    @Mapping(source = "tecnico.idTecnico", target = "idTecnico") // Extrae solo el ID del técnico
    CodigosActivacionDTO toDTO(CodigosActivacion codigosActivacion);

    @InheritInverseConfiguration
    @Mapping(target = "tecnico", ignore = true) // No se asigna directamente el técnico
    CodigosActivacion toEntity(CodigosActivacionDTO codigosActivacionDTO);*/
}
