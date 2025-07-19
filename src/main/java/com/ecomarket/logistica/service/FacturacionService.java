package com.ecomarket.logistica.service;

import com.ecomarket.logistica.model.FacturacionDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FacturacionService {

    private final RestTemplate restTemplate;

    @Value("${services.facturacion.url}") 
    public String facturacionUrl;

    public FacturacionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public FacturacionDTO obtenerFacturacionPorId(Integer id) {
        String url = facturacionUrl + "/" + id;
        return restTemplate.getForObject(url, FacturacionDTO.class);
    }
}
