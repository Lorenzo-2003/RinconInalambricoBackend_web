package com.techgear.pago.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequest {

    
    private Integer usuarioId;

    
    private Integer carroId;

    
    private Integer formaPagoId;


    private Double monto;
}