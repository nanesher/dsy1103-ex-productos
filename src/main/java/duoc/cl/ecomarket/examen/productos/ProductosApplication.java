package duoc.cl.ecomarket.examen.productos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductosApplication.class, args);
	}
	@Value("${ventas-api-url}")
	private String ventasApiUrl;
	@Value("${envios-api-url}")
	private String enviosApiUrl;
	@Value("${usuarios-api-url}")
	private String usuariosApiUrl;
	@Value("${pagos-api-url}")
	private String pagossApiUrl;

}
