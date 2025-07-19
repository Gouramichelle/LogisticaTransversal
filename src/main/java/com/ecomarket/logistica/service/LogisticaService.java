package com.ecomarket.logistica.service;

import com.ecomarket.logistica.exception.ResourceNotFoundException;
import com.ecomarket.logistica.model.FacturacionDTO;
import com.ecomarket.logistica.model.LogisticaEntity;
import com.ecomarket.logistica.model.UsuarioDTO;
import com.ecomarket.logistica.repository.LogisticaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class LogisticaService {
    @Autowired
     public LogisticaRepository logisticaRepository;
    @Autowired
    public FacturacionService facturacionService;
    @Autowired
    public UsuarioService usuarioService;

    public List<LogisticaEntity> obtenerTodasLogisticas() {
        return logisticaRepository.findAll();
    }
    public LogisticaEntity obtenerLogisticaPorId(Integer id) {
    return  logisticaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontro logistica con el ID: "+id));

    }
    public LogisticaEntity crearLogistica(LogisticaEntity logistica) {
        UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioId(logistica.getId());
        if (usuarioDTO == null) {
            throw new ResourceNotFoundException("No existe esta usuario");
        }

        return  logisticaRepository.save(logistica);

    }
    public LogisticaEntity actualizarLogistica(Integer id,LogisticaEntity logistica) {
        LogisticaEntity logisticaExistente =  logisticaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe esta logistica con el ID: "+id));

        UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioId(logistica.getId());
        if (usuarioDTO == null) {
            throw new ResourceNotFoundException("No existe esta usuario");

        }
        FacturacionDTO facturacionDTO = facturacionService.obtenerFacturacionPorId(logistica.getId());
        if (facturacionDTO == null) {
            throw new ResourceNotFoundException("No existe esta Facturacion");
        }

        logisticaExistente.setEstado(logisticaExistente.getEstado());
        logisticaRepository.save(logisticaExistente);
        return logisticaExistente;

    }
    public void eliminarLogistica(Integer id) {
        LogisticaEntity logistica=logisticaRepository.findById(id) .orElseThrow(() -> new ResourceNotFoundException("No existe esta logistica con el ID: "+id));

        logisticaRepository.deleteById(id);

    }

}
