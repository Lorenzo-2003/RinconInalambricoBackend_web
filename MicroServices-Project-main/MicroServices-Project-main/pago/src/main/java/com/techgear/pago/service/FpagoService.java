package com.techgear.pago.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.techgear.pago.dto.PedidoRequest;
import com.techgear.pago.model.Factura;
import com.techgear.pago.model.Fpago;
import com.techgear.pago.repository.FacturaRepository;
import com.techgear.pago.repository.FpagoRepository;
import java.sql.Date;






@Service
public class FpagoService {

    @Autowired
    private FpagoRepository fpagoRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    // =========== CRUD NORMAL DE FPAGO ===========

    public List<Fpago> getAllFpagos() {
        return fpagoRepository.findAll();
    }

    public Fpago getFpago(int id) {
        return fpagoRepository.findById(id).orElse(null);
    }

    public Fpago saveFpago(Fpago fpago) {
        return fpagoRepository.save(fpago);
    }

    public Fpago updateFpago(Fpago fpago) {
        Fpago updFpago = getFpago(fpago.getId());
        if (updFpago == null) {
            return null;
        }
        updFpago.setNombreFpago(fpago.getNombreFpago());
        return fpagoRepository.save(updFpago);
    }

    public void deleteFpago(Integer id) {
        fpagoRepository.deleteById(id);
    }

    // =========== PROCESAR PEDIDO COMPLETO ===========

    // =========== PROCESAR PEDIDO COMPLETO ===========
    public void procesarPedido(PedidoRequest pedido) {

    // 1. Buscar la forma de pago por ID
        Fpago formaPago = fpagoRepository.findById(pedido.getFormaPagoId())
            .orElseThrow(() -> new IllegalArgumentException(
                    "Forma de pago no válida: " + pedido.getFormaPagoId()
            ));

   
        Factura factura = new Factura();
        factura.setUsuarioId(pedido.getUsuarioId());
        factura.setCarroId(pedido.getCarroId());
        factura.setFormaPago(formaPago);
        factura.setFecha(Date.valueOf(LocalDate.now())); // java.sql.Date
        factura.setMonto(pedido.getMonto());            // total que te manda la app
  
    facturaRepository.save(factura);

    }

}