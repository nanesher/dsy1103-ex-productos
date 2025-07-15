package duoc.cl.ecomarket.examen.productos.assemblers;



import duoc.cl.ecomarket.examen.productos.controller.ProductoController;
import duoc.cl.ecomarket.examen.productos.model.Producto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
                linkTo(methodOn(ProductoController.class).getProductoById(producto.getProductoId())).withSelfRel(),
                linkTo(methodOn(ProductoController.class).getAllProductos()).withRel("Lista de todos los productos (con metodo get)"),
                linkTo(methodOn(ProductoController.class).deleteProducto(producto.getProductoId())).withRel("Borrar un producto (con metodo delete)"),
                linkTo(methodOn(ProductoController.class).updateProducto(producto.getProductoId(),null)).withRel("Actualizar un producto (con metodo put)"));
    }

}
