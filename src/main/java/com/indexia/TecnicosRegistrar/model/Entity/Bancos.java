package com.indexia.TecnicosRegistrar.model.Entity;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "bancos")
public class Bancos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idbanco", nullable = false, updatable = false)
    private Integer idBanco;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @Column(name = "contacto", length = 100)
    private String contacto;

    @Column(name = "urlinterface", length = 150)
    private String urlInterface;

    @Column(name = "fecharegistro")
    private LocalDateTime fechaRegistro;

    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(mappedBy = "bancos", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BancosTecnicos> bancosTecnicos;

    public Integer getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Integer idBanco) {
        this.idBanco = idBanco;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getUrlInterface() {
        return urlInterface;
    }

    public void setUrlInterface(String urlInterface) {
        this.urlInterface = urlInterface;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Set<BancosTecnicos> getBancosTecnicos() {
        return bancosTecnicos;
    }

    public void setBancosTecnicos(Set<BancosTecnicos> bancosTecnicos) {
        this.bancosTecnicos = bancosTecnicos;
    }
}
