package com.indexia.TecnicosRegistrar.model.Entity;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
@Embeddable
public class BancosTecnicosID implements Serializable {
    @Column(name = "idbanco")
    private Integer idBanco;

    @Column(name = "idtecnico")
    private Integer idTecnico;

    // Constructor vacío

    public BancosTecnicosID() {
    }

    public BancosTecnicosID(Integer idBanco, Integer idTecnico) {
        this.idBanco = idBanco;
        this.idTecnico = idTecnico;
    }

    public Integer getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Integer idBanco) {
        this.idBanco = idBanco;
    }

    public Integer getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(Integer idTecnico) {
        this.idTecnico = idTecnico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BancosTecnicosID that = (BancosTecnicosID) o;
        return Objects.equals(idBanco, that.idBanco) && Objects.equals(idTecnico, that.idTecnico);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBanco, idTecnico);
    }
}
