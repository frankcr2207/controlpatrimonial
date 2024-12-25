package csjar.controlpatrimonial.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import csjar.controlpatrimonial.dto.ResponseCatalogoDTO;
import csjar.controlpatrimonial.service.CatalogoService;

@RestController
@RequestMapping("/catalogo")
public class CatalogoController {
	
	private CatalogoService catalogoService;
	
	public CatalogoController(CatalogoService catalogoService) {
		super();
		this.catalogoService = catalogoService;
	}

	@GetMapping
	public ResponseEntity<List<ResponseCatalogoDTO>> getCatalogo(@RequestParam Integer idGrupo, @RequestParam Integer idClase ) {
		return new ResponseEntity<>(catalogoService.obtenerCatalogo(idGrupo, idClase), HttpStatus.OK);
	}
	
}
