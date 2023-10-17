package pe.isil.cliente_2978.model;

import lombok.Data;

@Data
public class Producto {

    private Integer id;
    private String codigo;
    private String descripcion;
    private String nombre;
    private Float precio;
    private String categoria;
    private Integer stock;
}
