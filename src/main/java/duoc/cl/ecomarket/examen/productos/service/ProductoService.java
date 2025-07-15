package duoc.cl.ecomarket.examen.productos.service;


import duoc.cl.ecomarket.examen.productos.model.Producto;
import duoc.cl.ecomarket.examen.productos.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> findAll() {
        List<Producto> productos = productoRepository.findAll();
        return productos;
    }
    public Producto findById(Integer productoId) {

        return productoRepository.findById(productoId).get();
    }
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }
    public void deleteById(Integer ProductoId) {

        productoRepository.deleteById(ProductoId);
    }
    public Producto update(Integer productoId, Producto producto2){

        Producto  producto1 = findById(productoId);

        producto1.setNombre(producto2.getNombre());
        producto1.setCategoria(producto2.getCategoria());
        producto1.setDescripcion(producto2.getDescripcion());
        producto1.setMaterial(producto2.getMaterial());
        producto1.setVidaUtil(producto2.getVidaUtil());
        if (producto2.getStock()<0) {
            throw new RuntimeException("El stock no puede ser negativo");
        }
        producto1.setStock(producto2.getStock());
        producto1.setStock(producto2.getStock());
        producto1.setPrecio(producto2.getPrecio());

            return productoRepository.save(producto1);
        }
    }

