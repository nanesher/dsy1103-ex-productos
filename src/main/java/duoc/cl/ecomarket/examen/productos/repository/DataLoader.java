package duoc.cl.ecomarket.examen.productos.repository;


import duoc.cl.ecomarket.examen.productos.model.Producto;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Random;

@Component
@Profile("!test")
public class DataLoader implements CommandLineRunner {
    @Autowired
    private ProductoRepository productoRepository;
    public void run(String... args) throws Exception {
        Faker faker = new Faker(new Locale("es"));
        Random random = new Random();

        for (int i = 0; i < 15; i++) {
            Producto producto = new Producto();
            producto.setNombre(faker.commerce().productName());
            producto.setDescripcion(faker.lorem().sentence(5));
            producto.setPrecio(1 + random.nextInt(10000)); // precios >= 1
            producto.setStock(random.nextInt(10));
            producto.setCategoria(faker.commerce().department());
            producto.setMaterial(faker.commerce().material());
            producto.setVidaUtil(1 + random.nextInt(10)+" años");
            productoRepository.save(producto);
        }
    }
}
