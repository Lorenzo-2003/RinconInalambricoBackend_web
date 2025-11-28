package com.techgear.pago.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.techgear.pago.model.Factura;
import com.techgear.pago.repository.FacturaRepository;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${usuario.service.url}")
    private String usuarioServiceUrl;

    @Value("${carro.service.url}")
    private String carroServiceUrl;


    public List<Factura> getAllFacturas(){
        return facturaRepository.findAll();
    }

    @SuppressWarnings("unchecked")
    public List<Factura> getFacturasDetalles(){
        List<Factura> facturas = facturaRepository.findAll();
        for (Factura factura : facturas) {
            try {
                String usuarioUrl = usuarioServiceUrl+"/"+factura.getUsuarioId();
                Map<String, Object> usuarioDetalles = restTemplate.getForObject(usuarioUrl, Map.class);
                factura.setUsuarioDetalles(usuarioDetalles);
                String carroUrl = carroServiceUrl+"/"+factura.getCarroId();
                Map<String, Object> carroDetalles = restTemplate.getForObject(carroUrl, Map.class);
                factura.setCarroDetalles(carroDetalles);
            } catch (Exception e) {
                factura.setUsuarioDetalles(null);
                factura.setCarroDetalles(null);
            }
        }
        return facturas;
    }

    public Factura getFactura(int id){
        return facturaRepository.findById(id).orElse(null);
    }

    public Factura saveFactura(Factura factura){
        return facturaRepository.save(factura);
    }

    public Factura updateFactura(Factura factura){
        Factura updFact = getFactura(factura.getId());
        if (updFact==null) {
            return null;
        }
        updFact.setFormaPago(factura.getFormaPago());
        updFact.setMonto(factura.getMonto());
        updFact.setFecha(factura.getFecha());
        
        return facturaRepository.save(updFact);
    }

    public void deleteFactura(Integer id){
        facturaRepository.deleteById(id);
    }
}
