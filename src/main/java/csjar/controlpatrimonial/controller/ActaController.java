package csjar.controlpatrimonial.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import csjar.controlpatrimonial.dto.ResponseBienesDTO;
import csjar.controlpatrimonial.service.ActaService;

@RequestMapping("/acta")
@RestController
public class ActaController {

	private ActaService actaService;
	
	public ActaController(ActaService actaService) {
		super();
		this.actaService = actaService;
	}

	@GetMapping(value = "/download", produces = MediaType.ALL_VALUE)
	public void excelResultados(HttpServletResponse response) throws IOException {
		
		ByteArrayOutputStream jxlsOutStream = new ByteArrayOutputStream();
		//List<ResponseNotaVO> resultados = respuestaService.getNotas();
		//List<ResponseDetalleNotasVO> respuestas = respuestaService.getDetalleNotas(); 
		Map<String, Object> data = new HashMap<>();
		data.put("fecha", "30-12-2024");
		data.put("dni", "47087903");
		data.put("nombresApellidos", "JORGE COAQUIRA");
		data.put("sede", "PALACIO DE JUSTICIA");
		data.put("direccion", "AV. SIGLO XX S/N");
		
		List<ResponseBienesDTO> bienes = new ArrayList<>();
		ResponseBienesDTO bien = new ResponseBienesDTO();
		bien.setCodigoPatrimonial("70000000000001");
		bienes.add(bien);
			
		ResponseBienesDTO bien2 = new ResponseBienesDTO();
		bien2.setCodigoPatrimonial("70000000000002");
		bienes.add(bien2);
		
		data.put("bienes", bienes);

		this.actaService.generarActa(jxlsOutStream, "plantilla_acta_v2", data);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");	
		response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=Resultado_concurso_" + LocalDateTime.now().format(formatter) + ".xlsx");
		InputStream inputStream = new ByteArrayInputStream(jxlsOutStream.toByteArray());
		IOUtils.copy(inputStream, response.getOutputStream());

	}
	
}
