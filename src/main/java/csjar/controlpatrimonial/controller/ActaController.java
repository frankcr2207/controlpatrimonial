package csjar.controlpatrimonial.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
	
	@GetMapping(value = "/downloadPdf", produces = MediaType.ALL_VALUE)
	public String excelResultadosPDF(HttpServletResponse response) throws IOException {
	    ByteArrayOutputStream jxlsOutStream = new ByteArrayOutputStream();
	    // Generar el archivo Excel (igual que tu código original)
	    // ...
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
	    // Convertir Excel a PDF
	    try {
	    	actaService.convertExcelToPdf(jxlsOutStream, response);
	    	return convertExcelToPdf(jxlsOutStream);
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    }
		return null;
	}
	
	 public static String convertExcelToPdf(ByteArrayOutputStream jxlsOutStream) throws Exception {
	        // Guardar el archivo Excel temporalmente en el disco
	        File tempExcelFile = new File("temp_excel_file.xlsx");
	        try (FileOutputStream fos = new FileOutputStream(tempExcelFile)) {
	            jxlsOutStream.writeTo(fos);
	        }
	        
	        try (FileInputStream fis = new FileInputStream(tempExcelFile)) {
	            Workbook workbook = new XSSFWorkbook(fis);
	            Sheet sheet = workbook.getSheetAt(0);

	            // Establecer la orientación como horizontal (landscape)
	            PrintSetup printSetup = sheet.getPrintSetup();
	            printSetup.setLandscape(true);  // Establece la orientación como horizontal (apaisada)

	            // Establecer la impresión en una sola página de ancho y alto
	            sheet.setFitToPage(true);  // Establecer el ajuste de página
	            sheet.getPrintSetup().setFitWidth((short)1);
	            sheet.getPrintSetup().setFitHeight((short)1);

	            // Guardar el archivo Excel con la configuración de orientación
	            try (FileOutputStream outFile = new FileOutputStream(tempExcelFile)) {
	                workbook.write(outFile);
	            }
	        }
	        
	        String libreOfficePath = "C:\\Program Files\\LibreOffice\\program\\soffice.exe";  // Asegúrate de que la ruta sea correcta

		     // Ruta personalizada donde se guardará el archivo PDF
		     String pdfDirectory = "F:\\temp\\pdf_output";  // Por ejemplo, el directorio "C:\\temp\\pdf_output"
		     File pdfDir = new File(pdfDirectory);
	
		     // Crear el directorio si no existe
		     if (!pdfDir.exists()) {
		         pdfDir.mkdirs();
		     }
	
		     // Ruta del archivo Excel de entrada
		     String inputExcelFilePath = tempExcelFile.getAbsolutePath();  // Ruta al archivo Excel temporal generado
	
		     // Comando para ejecutar LibreOffice en modo headless y convertir el archivo Excel a PDF
		     // Es importante que el parámetro --outdir se use solo para indicar el directorio de salida, no el nombre del archivo PDF
		     String command = "\"" + libreOfficePath + "\" --headless --convert-to pdf --outdir \"" + pdfDirectory + "\" \"" + inputExcelFilePath + "\"";
	
		     // Ejecutar el proceso
		     Process process = Runtime.getRuntime().exec(command);
		     process.waitFor();  // Espera a que el proceso termine
	
		     // Verificar si el archivo PDF fue creado correctamente en el directorio especificado
		     // El nombre del archivo PDF debería ser el mismo que el del Excel de entrada, pero con extensión .pdf
		     String outputPdfPath = pdfDirectory + "\\" + new File(inputExcelFilePath).getName().replace(".xlsx", ".pdf");
	
		     File pdfFile = new File(outputPdfPath);
		     if (!pdfFile.exists()) {
		         throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "Error al convertir el archivo Excel a PDF.");
		     }
	
		     // Leer el archivo PDF y convertirlo a Base64
		     byte[] pdfBytes = Files.readAllBytes(Paths.get(outputPdfPath));
		     String pdfBase64 = Base64.getEncoder().encodeToString(pdfBytes);
	
		     // Limpiar los archivos temporales
		     tempExcelFile.delete();
		     pdfFile.delete();
	
		     return pdfBase64;

//	        // Ruta al ejecutable de LibreOffice (asegúrate de que LibreOffice esté instalado)
//	        String libreOfficePath = "C:\\Program Files\\LibreOffice\\program\\soffice.exe";  // Asegúrate de que la ruta sea correcta
//
//	        // Ruta del archivo de salida PDF
//	        String outputPdfPath = "temp_output.pdf";
//
//	        // Comando para ejecutar LibreOffice en modo headless y convertir el archivo Excel a PDF
//	        //String command = libreOfficePath + " --headless --convert-to pdf --outdir " + tempExcelFile.getParent() + " " + tempExcelFile.getAbsolutePath();
//	        String command = "\"" + libreOfficePath + "\" --headless --convert-to pdf --outdir \"" + pdfDirectory + "\" \"" + inputExcelFilePath + "\"";
//
//	        // Ejecutar el proceso
//	        Process process = Runtime.getRuntime().exec(command);
//	        process.waitFor();  // Espera a que el proceso termine
//
//	        // Verificar si el archivo PDF fue creado correctamente
//	        File pdfFile = new File(outputPdfPath);
//	        if (!pdfFile.exists()) {
//	            throw new IOException("Error al convertir el archivo Excel a PDF.");
//	        }
//
//	        // Leer el archivo PDF y convertirlo a Base64
//	        byte[] pdfBytes = Files.readAllBytes(Paths.get(outputPdfPath));
//	        String pdfBase64 = Base64.getEncoder().encodeToString(pdfBytes);
//
//	        // Limpiar los archivos temporales
//	        tempExcelFile.delete();
//	        pdfFile.delete();
//
//	        return pdfBase64;
	 }
	 
}
