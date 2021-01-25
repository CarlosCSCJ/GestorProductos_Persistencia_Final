package org.jesuitasrioja.productos.controllers;

import java.util.List;
import java.util.Optional;
import org.jesuitasrioja.productos.modelo.producto.Producto;
import org.jesuitasrioja.productos.persistencia.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductoController {

	@Autowired
	private ProductoService ps;
	

	@GetMapping("/productos")
	public ResponseEntity<?> allProducts() {
		List<Producto> productos = ps.findAll();	
		
		return ResponseEntity.status(HttpStatus.OK).body(productos);
	}
	
	
	@GetMapping("/producto/{id}")
	public ResponseEntity<?> getProducto(@PathVariable String id) {
		Optional<Producto> productoOptional = ps.findById(id);
		if(productoOptional.isPresent()) {
			Producto producto = productoOptional.get();
			return ResponseEntity.status(HttpStatus.OK).body(producto);
		}else {
			throw new ProductoNoEncontradoException(id);
		}
	}

	@GetMapping("/producto")
	public ResponseEntity<?> getProducto2(@RequestParam String id) {
		Producto p =  ps.findById(id).get();
		return ResponseEntity.status(HttpStatus.OK).body(p);

	}

	@PostMapping("/producto")
	public ResponseEntity<?> postProducto(@RequestBody Producto nuevoProducto) {
		Producto p=  ps.save(nuevoProducto);
		return ResponseEntity.status(HttpStatus.OK).body(p);
	}

	@PutMapping("/producto")
	public ResponseEntity<?>  putProducto(@RequestBody Producto editadoProducto) {
		ps.edit(editadoProducto).toString();
		return ResponseEntity.status(HttpStatus.OK).build();

	}

	@DeleteMapping("/producto/{id}")
	public ResponseEntity<?>  deleteProducto(@PathVariable String id) {
		ps.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
