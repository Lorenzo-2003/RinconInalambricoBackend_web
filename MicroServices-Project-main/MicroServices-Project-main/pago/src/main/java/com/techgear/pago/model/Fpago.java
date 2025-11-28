package com.techgear.pago.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fpago")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fpago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_fpago", nullable = false)
    private String nombreFpago;
}
