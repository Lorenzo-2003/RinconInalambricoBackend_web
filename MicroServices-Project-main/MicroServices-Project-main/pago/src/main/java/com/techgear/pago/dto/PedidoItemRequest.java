package com.techgear.pago.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoItemRequest {

    
    private Integer productoId;

    // cuántas unidades de ese producto
    private Integer cantidad;

    // precio unitario de ese producto en el momento de la compra
    private Double precioUnitario;
}
