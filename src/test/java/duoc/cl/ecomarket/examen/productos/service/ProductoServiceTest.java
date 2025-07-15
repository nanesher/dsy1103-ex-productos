package duoc.cl.ecomarket.examen.productos.service;



import duoc.cl.ecomarket.examen.productos.model.Producto;
import duoc.cl.ecomarket.examen.productos.repository.ProductoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class ProductoServiceTest {

    @Autowired
    private ProductoService productoService;

    @MockitoBean
    private ProductoRepository productoRepository;

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
    }

    @Test
    public void testFindAll() {
        when(productoRepository.findAll()).thenReturn(List.of(producto));
        List<Producto> productos = productoService.findAll();
        Assertions.assertNotNull(productos);
        assertEquals(1,productos.size());

    }

    @Test
    public void testFindById() {
        when(productoRepository.findById(id)).thenReturn(Optional.of(producto));
        when(productoRepository.existsById(id)).thenReturn(true);
        Producto found = productoService.findById(id);
        Assertions.assertNotNull(productoService.findById(1));
        assertEquals(1,found.getProductoId());
        assertEquals("Producto",found.getNombre());
    }

    @Test
    public void testDelete() {
        doNothing().when(productoRepository).deleteById(id);
        when(productoRepository.existsById(id)).thenReturn(Boolean.TRUE);
        productoService.deleteById(id);
        verify(productoRepository, times(1)).deleteById(id);
    }

    @Test
    public void testSave() {
        when(productoRepository.save(producto)).thenReturn(producto);
        Producto found = productoService.save(producto);
        Assertions.assertNotNull(found);
        assertEquals(1,found.getProductoId());
        assertEquals("Producto",found.getNombre());
        assertEquals("Descripcion",found.getDescripcion());
        assertEquals("5 años",found.getVidaUtil());
        assertEquals("Material",found.getMaterial());
        assertEquals("Categoria",found.getCategoria());
        assertEquals(10000,found.getPrecio());
    }

    @Test
    public void testUpdate() {
        when(productoRepository.findById(id)).thenReturn(Optional.of(producto));
        when(productoRepository.save(producto)).thenReturn(producto);
        when(productoRepository.existsById(id)).thenReturn(Boolean.TRUE);
        producto.setStock(3);
        productoService.update(1,producto);
        assertEquals(3,producto.getStock());
    }

    @Test
    public void testUpdateFallido() {
        when(productoRepository.findById(id)).thenReturn(Optional.of(producto));
        when(productoRepository.save(producto)).thenReturn(producto);
        when(productoRepository.existsById(id)).thenReturn(Boolean.TRUE);
        producto.setStock(-3);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productoService.update(1,producto);
        });

        assertEquals("El stock no puede ser negativo", exception.getMessage());
    }



}
