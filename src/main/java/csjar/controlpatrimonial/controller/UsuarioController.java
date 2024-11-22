package csjar.controlpatrimonial.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import csjar.controlpatrimonial.dto.RequestUsuarioDTO;
import csjar.controlpatrimonial.dto.ResponseUsuarioDTO;
import csjar.controlpatrimonial.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	private UsuarioService usuarioService;
	
	public UsuarioController(UsuarioService usuarioService) {
		super();
		this.usuarioService = usuarioService;
	}
	
	@GetMapping("/{dni}")
	public ResponseEntity<ResponseUsuarioDTO> buscarUsuario(@PathVariable String dni) throws NoSuchAlgorithmException {
		return new ResponseEntity<>(usuarioService.buscarUsuario(dni), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<ResponseEntity<HttpStatus>> crearUsuario(@RequestBody RequestUsuarioDTO usuario) throws NoSuchAlgorithmException {
		this.usuarioService.crearUsuario(usuario);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
}
