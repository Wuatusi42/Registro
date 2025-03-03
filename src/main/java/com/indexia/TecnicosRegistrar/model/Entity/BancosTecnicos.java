package com.indexia.TecnicosRegistrar.model.Entity;
import javax.persistence.*;

@Entity
@Table(name = "bancostecnicos")
public class BancosTecnicos {
    @EmbeddedId
    private BancosTecnicosID idBancosTecnicos;

    @ManyToOne
    @MapsId("idBanco")
    @JoinColumn(name = "idbanco", nullable = false)
    private Bancos bancos;

    @ManyToOne
    @MapsId("idTecnico")
    @JoinColumn(name = "idtecnico", nullable = false)
    private Tecnico tecnico;

    public Bancos getBancos() {
        return bancos;
    }

    public void setBancos(Bancos bancos) {
        this.bancos = bancos;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public BancosTecnicosID getIdBancosTecnicos() {
        return idBancosTecnicos;
    }

    public void setIdBancosTecnicos(BancosTecnicosID idBancosTecnicos) {
        this.idBancosTecnicos = idBancosTecnicos;
    }

    public BancosTecnicos(Bancos banco, Tecnico tecnico) {
        this.idBancosTecnicos = new BancosTecnicosID(banco.getIdBanco(), tecnico.getIdTecnico());
        this.bancos = banco;
        this.tecnico = tecnico;
    }

    public BancosTecnicos() {
    }
}
