package com.indexia.TecnicosRegistrar.Service;

import com.indexia.TecnicosRegistrar.model.Entity.Tecnico;
import com.indexia.TecnicosRegistrar.model.utils.RespuestaServicio;
import com.indexia.TecnicosRegistrar.model.utils.TecnicoDTO;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface TecnicoService {
    public RespuestaServicio formTecnico(TecnicoDTO tecnicoDTO);
    //public void registerFormat(MultipartFile file) throws IOException;
    public RespuestaServicio registerFormat(InputStream file)throws IOException;
    public boolean existeEmail(String email);
    public boolean isValidEmail(String email);
    public boolean isValidNombre(String nombre);
    public boolean isValidApePaterno(String apellidoPaterno);
    public boolean isValidRFC(String rfc);
    public boolean isValidApeMaterno(String apellidoMaterno);
    public boolean isValidCURP(String curp);
    public List<Tecnico> obtenerTodosLosTecnicos();
    public TecnicoDTO obtenerTecnico(Integer id);
    public boolean existeEmailAct(String email, Integer idTecnico);
    public List<Tecnico> filtrarTecnicos(String nombre, String correo);

}
