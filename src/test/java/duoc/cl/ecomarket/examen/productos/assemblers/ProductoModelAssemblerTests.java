package duoc.cl.ecomarket.examen.productos.assemblers;



import duoc.cl.ecomarket.examen.productos.model.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;

import static org.junit.jupiter.api.Assertions.*;

class ProductoModelAssemblerTest {

    private ProductoModelAssembler assembler;

    @BeforeEach
    void setUp() {
        assembler = new ProductoModelAssembler();
    }

    @Test
    void toModel_agregaLinksCorrectamente() {
        // Arrange
        Producto producto = new Producto();
        producto.setProductoId(1);
        producto.setNombre("Producto");
        producto.setDescripcion("Descripcion");
        producto.setCategoria("Categoria");
        producto.setMaterial("Material");
        producto.setVidaUtil("5 años");
        producto.setPrecio(10000);
        producto.setStock(5);

        // Act
        EntityModel<Producto> model = assembler.toModel(producto);

        // Assert
        assertNotNull(model.getContent());
        assertEquals(producto, model.getContent());
        assertTrue(model.getLinks().hasLink("self"));
        assertTrue(model.getLinks().hasLink("Lista de todos los productos (con metodo get)"));
    }
}