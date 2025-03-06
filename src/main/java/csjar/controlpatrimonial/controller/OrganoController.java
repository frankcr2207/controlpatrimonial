package csjar.controlpatrimonial.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import csjar.controlpatrimonial.dto.ResponseOrganoDTO;
import csjar.controlpatrimonial.service.OrganoService;

@RequestMapping("/organo")
@RestController
public class OrganoController {

	private OrganoService organoService;
	
	public OrganoController(OrganoService organoService) {
		super();
		this.organoService = organoService;
	}

	@GetMapping("/{idSede}")
	public ResponseEntity<List<ResponseOrganoDTO>> listarOrganos(@PathVariable Integer idSede) throws NoSuchAlgorithmException {
		return new ResponseEntity<>(organoService.listarOrganos(idSede), HttpStatus.OK);
	}
	
}
