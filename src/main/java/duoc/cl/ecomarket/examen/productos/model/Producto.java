package duoc.cl.ecomarket.examen.productos.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name="productos")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Producto {
    @Schema(description = "Identificador unico del producto", example = "1")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "producto_id")
    private Integer productoId;

    @Schema(description = "nombre del producto", example = "Cepillo de bambú")
    @Column(name="nombre",nullable=false)
    private String nombre;

    @Schema(description = "Categoría a la que pertenece el producto", example = "Higiene")
    @Column(name="categoria",nullable=false)
    private String categoria;

    @Schema(description = "Detalles o características específicas del producto", example = "Cepillo biodegradable con mango de bambú y cerdas suaves")
    @Column(name="descripcion",nullable=false)
    private String descripcion;

    @Schema(description = "Material del cual está hecho el producto", example = "bambú")
    @Column(name="material",nullable=false)
    private String material;

    @Schema(description = "Años de vida util del producto", example = "3")
    @Column(name="vida_util",nullable=false)
    private String vidaUtil;

    @Schema(description = "Cantidad de stock disponible del producto", example = "5")
    @Column(name="stock",nullable=false)
    private int stock;

    @Schema(description = "Precio del producto en pesos chilenos", example = "10000")
    @Column(name="precio",nullable=false)
    private int precio;

}



