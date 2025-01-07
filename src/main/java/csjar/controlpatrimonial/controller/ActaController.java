package csjar.controlpatrimonial.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import csjar.controlpatrimonial.dto.RequestActaDTO;
import csjar.controlpatrimonial.service.ActaService;

@RequestMapping("/acta")
@RestController
public class ActaController {

	private ActaService actaService;

	public ActaController(ActaService actaService) {
		super();
		this.actaService = actaService;
	}

	@PostMapping
	public ResponseEntity<HttpStatus> gestionarMovimiento(@RequestBody RequestActaDTO requestActaDTO) throws Exception {
		this.actaService.guardarActa(requestActaDTO);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("/confirmar")
	public ResponseEntity<HttpStatus> guardarConfirmacion(HttpServletResponse response,
			@RequestParam(value = "id") Integer id, @RequestPart(value = "adjunto") MultipartFile multipartFile)
			throws Exception {
		this.actaService.guardarConfirmacion(id, multipartFile);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> descargarActa(@PathVariable Integer id) throws Exception {
		try {
			byte[] pdfBytes = actaService.descargarActa(id);

			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/pdf")
					.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"output.pdf\"").body(pdfBytes);

		} catch (IOException e) {
			return ResponseEntity.status(500).body(null);
		}
	}

}
