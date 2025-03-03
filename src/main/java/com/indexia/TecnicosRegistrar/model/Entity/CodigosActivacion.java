package com.indexia.TecnicosRegistrar.model.Entity;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "codigosactivacion")
public class CodigosActivacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcodigo", nullable = false, updatable = false)
    private Integer idCodigo;

    @ManyToOne
    @JoinColumn(name = "idtecnico", nullable = false)
    private Tecnico tecnico;

    @Column(name = "codigo", length = 15)
    private String codigo;

    @Column(name = "codigoresumido", length = 15)
    private String codigoResumido;

    @Column(name = "fecharegistro")
    private LocalDateTime fechaRegistro;

    @Column(name = "caducidad")
    private LocalDateTime caducidad;

    @Column(name = "utilizado")
    private Boolean utilizado;

    public Integer getIdCodigo() {
        return idCodigo;
    }

    public void setIdCodigo(Integer idCodigo) {
        this.idCodigo = idCodigo;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
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