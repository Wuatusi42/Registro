package com.indexia.TecnicosRegistrar.model.utils;

import java.time.LocalDateTime;

public class CodigosActivacionDTO {
    private Integer idCodigo;
    private Integer idTecnico; // Solo el ID del técnico, no el objeto completo
    private String codigo;
    private String codigoResumido;
    private LocalDateTime fechaRegistro;
    private LocalDateTime caducidad;
    private Boolean utilizado;

    public Integer getIdCodigo() {
        return idCodigo;
    }

    public void setIdCodigo(Integer idCodigo) {
        this.idCodigo = idCodigo;
    }

    public Integer getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(Integer idTecnico) {
        this.idTecnico = idTecnico;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoResumido() {
        return codigoResumido;
    }

    public void setCodigoResumido(String codigoResumido) {
        this.codigoResumido = codigoResumido;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalDateTime getCaducidad() {
        return caducidad;
    }

    public void setCaducidad(LocalDateTime caducidad) {
        this.caducidad = caducidad;
    }

    public Boolean getUtilizado() {
        return utilizado;
    }

    public void setUtilizado(Boolean utilizado) {
        this.utilizado = utilizado;
    }
}
