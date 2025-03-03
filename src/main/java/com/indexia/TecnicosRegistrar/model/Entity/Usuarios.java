package com.indexia.TecnicosRegistrar.model.Entity;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuarios {
	@Id
    @Column(name = "nombreusuario", nullable = false, length = 35)
    private String nombreUsuario;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @Column(name = "apellidopaterno", length = 50)
    private String apellidoPaterno;

    @Column(name = "apellidomaterno", length = 50)
    private String apellidoMaterno;

    @Column(name = "credencial", length = 100)
    private String credencial;

    @Column(name = "fecharegistro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    @Column(name = "activo")
    private Boolean activo;
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCredencial() {
        return credencial;
    }

    public void setCredencial(String credencial) {
        this.credencial = credencial;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
