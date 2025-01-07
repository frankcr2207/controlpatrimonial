package csjar.controlpatrimonial.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.WriterException;
import com.itextpdf.text.DocumentException;

import csjar.controlpatrimonial.dto.RequestBienesDTO;
import csjar.controlpatrimonial.dto.RequestEtiquetaDTO;
import csjar.controlpatrimonial.dto.ResponseBienDTO;
import csjar.controlpatrimonial.dto.ResponseTrazabilidadDTO;
import csjar.controlpatrimonial.service.BienService;

@RequestMapping("/bien")
@RestController
public class BienController {

	private BienService bienService;
	
	public BienController(BienService bienService) {
		super();
		this.bienService = bienService;
	}
	
	@GetMapping("/buscar")
	public ResponseEntity<ResponseBienDTO> obtenerBien(@RequestParam String codigo, 
		@RequestParam Integer idEmpleado, @RequestParam String tipoActa) throws NoSuchAlgorithmException {
		return new ResponseEntity<>(this.bienService.obtenerBien(codigo, idEmpleado, tipoActa), HttpStatus.OK);
	}
	
	@GetMapping("/{idAdquisicion}")
	public ResponseEntity<List<ResponseBienDTO>> obtenerBienes(@PathVariable Integer idAdquisicion) throws NoSuchAlgorithmException {
		return new ResponseEntity<>(this.bienService.obtenerBienes(idAdquisicion), HttpStatus.OK);
	}
	
	@GetMapping("/trazabilidad/{codigo}")
	public ResponseEntity<ResponseTrazabilidadDTO> obtenerTrazabilidad(@PathVariable String codigo) throws NoSuchAlgorithmException {
		return new ResponseEntity<>(this.bienService.obtenerTrazabilidad(codigo), HttpStatus.OK);
	}
	
	@PostMapping("/generar")
	public ResponseEntity<HttpStatus> generarBienes(@RequestBody RequestBienesDTO request) throws NoSuchAlgorithmException {
		this.bienService.generarBienes(request);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/etiqueta")
	public ResponseEntity<byte[]> generarBienes( @RequestBody List<RequestEtiquetaDTO> requestEtiquetaDTO) throws DocumentException, IOException, WriterException {

		try {
            byte[] pdfBytes = bienService.generarEtiquetas(requestEtiquetaDTO);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"output.pdf\"")
                    .body(pdfBytes);

        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
	}
}
