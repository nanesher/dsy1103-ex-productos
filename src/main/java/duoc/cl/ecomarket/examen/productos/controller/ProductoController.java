package duoc.cl.ecomarket.examen.productos.controller;


import duoc.cl.ecomarket.examen.productos.assemblers.ProductoModelAssembler;
import duoc.cl.ecomarket.examen.productos.model.Producto;
import duoc.cl.ecomarket.examen.productos.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/productos")
@Tag(name="Productos",description = "Operaciones relacionadas con el registro de los productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;
    @Autowired
    private ProductoModelAssembler assembler;

    @Operation(summary="Obtener todos los productos",description = "Obtiene una lista de todos los productos con sus atributos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todos los productos listados correctamente",
                    content = @Content(mediaType = "application/hal+json"))
    })
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Producto>> getAllProductos() {
        List<EntityModel<Producto>> productos = productoService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoController.class).getAllProductos()).withSelfRel(),
                linkTo(methodOn(ProductoController.class).createProducto(null)).withRel("Crear un producto (con metodo post)"));
    }

    @Operation(summary="Crear un producto",description = "Se crea un nuevo producto con los parametros enviados asignadole una ID automaticamente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto creado con éxito",
                    content = @Content(mediaType = "application/hal+json"))
    })
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Producto>> createProducto(@RequestBody Producto producto) {
        Producto newProducto = productoService.save(producto);
        return ResponseEntity
                .created(linkTo(methodOn(ProductoController.class).getProductoById(newProducto.getProductoId())).toUri())
                .body(assembler.toModel(newProducto));
    }

    @Operation(summary="Obtener un producto",description = "Obtiene un producto con sus atributos por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto listado correctamente",
                    content = @Content(mediaType = "application/hal+json"))
    })

    @GetMapping (value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Producto> getProductoById(@Parameter(
            description = "ID del producto a obtener",
            name = "id",
            required = true,
            in = ParameterIn.PATH,
            example = "5"
    )@PathVariable Integer id) {
        Producto producto = productoService.findById(id);
        return assembler.toModel(producto);
    }

    @Operation(summary="Eliminar un producto",description = "Se elimina un producto por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente",
                    content = @Content(mediaType = "application/hal+json"))
    })

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteProducto(@Parameter(
            description = "ID del producto a eliminar",
            name = "id",
            required = true,
            in = ParameterIn.PATH,
            example = "5"
    )@PathVariable Integer id) {
        productoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary="Actualizar un producto",description = "Se actualizan los datos de un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente",
                    content = @Content(mediaType = "application/hal+json")),
            @ApiResponse(responseCode="400", description="El stock no puede ser negativo",
            content = @Content(mediaType = "text/plain",
                    schema = @Schema(example = "El stock no puede ser negativo")
            ))
    })

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> updateProducto(
    @Parameter(
            description = "ID del producto a actualizar",
            name = "id",
            required = true,
            in = ParameterIn.PATH,
            example = "5"
    )@PathVariable Integer id, @RequestBody Producto producto) {
        try {
            Producto updatedProducto = productoService.update(id, producto);
            return ResponseEntity.ok(assembler.toModel(updatedProducto));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
