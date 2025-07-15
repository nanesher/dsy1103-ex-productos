package duoc.cl.ecomarket.examen.productos.controller;



import com.fasterxml.jackson.databind.ObjectMapper;
import duoc.cl.ecomarket.examen.productos.assemblers.ProductoModelAssembler;
import duoc.cl.ecomarket.examen.productos.model.Producto;
import duoc.cl.ecomarket.examen.productos.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoService productoService;

    @MockitoBean
    private ProductoModelAssembler assembler;

    @Autowired
    private ObjectMapper objectMapper;

    private Producto producto;
    Integer id = 1;

    @BeforeEach
    public void setUp() {
        producto = new Producto();
        producto.setProductoId(id);
        producto.setNombre("Producto");
        producto.setDescripcion("Descripcion");
        producto.setVidaUtil("5 años");
        producto.setMaterial("Material");
        producto.setCategoria("Categoria");
        producto.setPrecio(10000);
        producto.setStock(5);
        when(assembler.toModel(any(Producto.class))).thenReturn(
                EntityModel.of(producto,
                        linkTo(methodOn(ProductoController.class).getProductoById(1)).withSelfRel()
                )
        );
    }


    @Test
    public void testGetAllProductos() throws Exception {

        when(productoService.findAll()).thenReturn(List.of(producto));

        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(jsonPath("$._embedded.productoList[0].productoId").value(1))
                .andExpect(jsonPath("$._embedded.productoList[0].nombre").value("Producto"))
                .andExpect(jsonPath("$._embedded.productoList[0].descripcion").value("Descripcion"))
                .andExpect(jsonPath("$._embedded.productoList[0]._links").exists())
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links['Crear un producto (con metodo post)']").exists());

    }
    @Test
    public void testGetProducto() throws Exception {
        when(productoService.findById(id)).thenReturn(producto);
        mockMvc.perform(get("/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Producto"))
                .andExpect(jsonPath("$.descripcion").value("Descripcion"))
                .andExpect(jsonPath("$.productoId").value(1))
                .andExpect(jsonPath("$.categoria").value("Categoria"))
                .andExpect(jsonPath("$._links").exists());
    }
    @Test
    public void testCreateProducto() throws Exception {
        when(productoService.save(producto)).thenReturn(producto);
        mockMvc.perform(post("/productos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Producto"))
                .andExpect(jsonPath("$.descripcion").value("Descripcion"))
                .andExpect(jsonPath("$.categoria").value("Categoria"))
                .andExpect(jsonPath("$._links.self").exists());
    }
    @Test
    public void testUpdateProducto() throws Exception {
        Producto actualizado = new Producto();
        actualizado.setProductoId(1);
        actualizado.setNombre("Producto");
        actualizado.setDescripcion("Descripcion");
        actualizado.setCategoria("Categoria");
        actualizado.setMaterial("Material");
        actualizado.setVidaUtil("5 años");
        actualizado.setPrecio(10000);
        actualizado.setStock(6);
        when(productoService.update(id, actualizado)).thenReturn(actualizado);
        when(productoService.findById(id)).thenReturn(producto);
        when(assembler.toModel(any(Producto.class))).thenReturn(
                EntityModel.of(actualizado, // este es el que realmente devuelve el service
                        linkTo(methodOn(ProductoController.class).getProductoById(1)).withSelfRel()
                )
        );

        mockMvc.perform(put("/productos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Producto"));

    }
    @Test
    public void testDeleteProducto() throws Exception {
        doNothing().when(productoService).deleteById(1);
        mockMvc.perform(delete("/productos/1"))
                .andExpect(status().isNoContent());
    }

}
