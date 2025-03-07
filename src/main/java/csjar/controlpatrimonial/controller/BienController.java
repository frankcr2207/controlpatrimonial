package csjar.controlpatrimonial.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.WriterException;
import com.itextpdf.text.DocumentException;

import csjar.controlpatrimonial.constants.GeneralConstants;
import csjar.controlpatrimonial.dto.RequestBienesDTO;
import csjar.controlpatrimonial.dto.RequestDetalleBienesDTO;
import csjar.controlpatrimonial.dto.RequestEtiquetaDTO;
import csjar.controlpatrimonial.dto.ResponseBienDTO;
import csjar.controlpatrimonial.dto.ResponseTrazabilidadDTO;
import csjar.controlpatrimonial.service.BienService;
import csjar.controlpatrimonial.service.ExcelService;

@RequestMapping("/bien")
@RestController
public class BienController {

	private BienService bienService;
	private ExcelService excelService;
	
	public BienController(BienService bienService, ExcelService excelService) {
		super();
		this.bienService = bienService;
		this.excelService = excelService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseBienDTO> obtenerBienPorId(@PathVariable Integer id) throws NoSuchAlgorithmException {
		return new ResponseEntity<>(this.bienService.obtenerBienPorId(id), HttpStatus.OK);
	}
	
	@GetMapping("/buscar")
	public ResponseEntity<ResponseBienDTO> obtenerBien(@RequestParam String codigo, 
		@RequestParam Integer idEmpleado, @RequestParam String tipoActa) throws NoSuchAlgorithmException {
		return new ResponseEntity<>(this.bienService.obtenerBien(codigo, idEmpleado, tipoActa), HttpStatus.OK);
	}
	
	@GetMapping("/adquisicion/{idAdquisicion}")
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
	
	@PutMapping
	public ResponseEntity<HttpStatus> modificarBienes(@RequestBody RequestDetalleBienesDTO request) throws NoSuchAlgorithmException {
		this.bienService.modificarBien(request);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/listarDetalle")
	public ResponseEntity<ResponseBienDTO> listarDetalle(@RequestParam String codigo, 
		@RequestParam Integer idEmpleado, @RequestParam String tipoActa) throws NoSuchAlgorithmException {
		return new ResponseEntity<>(this.bienService.obtenerBien(codigo, idEmpleado, tipoActa), HttpStatus.OK);
	}
	
	@PostMapping("/movimiento")
	public ResponseEntity<HttpStatus> nuevoMovimiento(@RequestBody RequestDetalleBienesDTO request) throws NoSuchAlgorithmException {
		this.bienService.guardarMovimiento(request);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping(value = "download/reporte", produces = MediaType.ALL_VALUE)
	public void excelResultados(HttpServletResponse response, @RequestParam Integer idSede, @RequestParam List<Integer> idsOrgano) throws IOException {
		
		ByteArrayOutputStream jxlsOutStream = new ByteArrayOutputStream();
		List<ResponseBienDTO> bienes = bienService.reporte(idSede, idsOrgano);
		Map<String, Object> data = new HashMap<>();

		data.put("bienes", bienes);

		this.excelService.generarDocumento(jxlsOutStream, GeneralConstants.BIEN_REPORTE_PLANTILLA_EXCEL, data);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");	
		response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=Report_Bienes_" + LocalDateTime.now().format(formatter) + GeneralConstants.EXTENSION_EXCEL);
		InputStream inputStream = new ByteArrayInputStream(jxlsOutStream.toByteArray());
		IOUtils.copy(inputStream, response.getOutputStream());

	}
}
