package csjar.controlpatrimonial.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import csjar.controlpatrimonial.dto.RequestPerfilDTO;
import csjar.controlpatrimonial.dto.ResponsePerfilDTO;
import csjar.controlpatrimonial.service.PerfilService;

@RestController
@RequestMapping("/perfil")
public class PerfilController {

	private PerfilService perfilService;
	
	public PerfilController(PerfilService perfilService) {
		super();
		this.perfilService = perfilService;
	}

	@GetMapping
	public ResponseEntity<List<ResponsePerfilDTO>> listarPerfiles() throws NoSuchAlgorithmException {
		return new ResponseEntity<>(perfilService.listarPerfiles(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponsePerfilDTO> obtenerPerfil(@PathVariable Integer id) throws NoSuchAlgorithmException {
		return new ResponseEntity<>(perfilService.buscarPerfil(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<ResponseEntity<HttpStatus>> crearPerfil(@RequestBody RequestPerfilDTO perfil) throws NoSuchAlgorithmException {
		this.perfilService.crearPerfil(perfil);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<ResponseEntity<HttpStatus>> modificarPerfil(@RequestBody RequestPerfilDTO perfil) throws NoSuchAlgorithmException {
		this.perfilService.modificarPerfil(perfil);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
}
