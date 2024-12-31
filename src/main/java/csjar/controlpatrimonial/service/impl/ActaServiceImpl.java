package csjar.controlpatrimonial.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import csjar.controlpatrimonial.domain.Acta;
import csjar.controlpatrimonial.domain.Bien;
import csjar.controlpatrimonial.domain.Usuario;
import csjar.controlpatrimonial.dto.RequestActaDTO;
import csjar.controlpatrimonial.dto.RequestEmailDTO;
import csjar.controlpatrimonial.external.service.NotificacionExternalService;
import csjar.controlpatrimonial.repository.ActaRepository;
import csjar.controlpatrimonial.service.ActaService;
import csjar.controlpatrimonial.service.BienService;
import csjar.controlpatrimonial.service.UsuarioService;

@Service
public class ActaServiceImpl implements ActaService {

	private ActaRepository repository;
	private UsuarioService usuarioService;
	private BienService bienService;
	private NotificacionExternalService notificacionExternalService;
	
	@Override
	public void guardarActa(RequestActaDTO requestActaDTO) {
		Usuario usuario = usuarioService.buscarRntidad(obtenerUsuario());
		Acta actaAnterior = this.repository.findActaWithMaxNumeroByYear(LocalDateTime.now().getYear());
		Acta acta = new Acta();
		acta.setIdEmpleado(requestActaDTO.getIdEmpleado());
		acta.setEstado("R");
		acta.setNumero(Objects.nonNull(actaAnterior) ? actaAnterior.getNumero() + 1 : 1);
		acta.setIdUsuario(usuario.getId());
		acta.setIdArea(requestActaDTO.getIdArea());
		acta.setTipo(requestActaDTO.getTipo());
		List<Bien> bienes = new ArrayList<>();
		requestActaDTO.getBienes().stream().forEach(b -> {
			Bien bien = bienService.obtenerEntidad(b.getCodigoPatrimonial());
			bien.setEstadoConservacion(b.getEstadoConservacion());
			bien.setObservacion(b.getObservacion());
			bienes.add(bien);
		});
		acta.setBienes(bienes);
		repository.save(acta);
		
	}
	
	public String obtenerUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null) {
            String usuario = authentication.getName();
            return usuario;
        }
        
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "La sesión ha finalizado");
    }
	
	@Override
	public void generarActa(OutputStream outStream, String templateName, Map<String, Object> data) {
		String pathTemplateName = ("/reports/").concat(templateName).concat(".xlsx");
		try(InputStream input = this.getClass().getResourceAsStream(pathTemplateName)) {
		
            Context context = new Context();
            for (Entry<String, Object> element : data.entrySet()) { 
            	context.putVar(element.getKey(), element.getValue());
			}
            JxlsHelper.getInstance().processTemplate(input, outStream, context);

		} catch (Exception exception) {

		} finally {
			closeAndFlushOutput(outStream);
		}
	}
	
	private void closeAndFlushOutput(OutputStream outStream) {
		try {
			outStream.flush();
			outStream.close();
		} catch (IOException exception) {

		}
	}
	
	private void enviarEmail() {
		RequestEmailDTO requestEmailDTO = new RequestEmailDTO();
		this.notificacionExternalService.enviarEmail(requestEmailDTO);
	}
	
	public static String convertExcelToPdfAndBase64(ByteArrayOutputStream excelFile) throws Exception {
        // Leer el archivo Excel
        try (XSSFWorkbook workbook = new XSSFWorkbook(new ByteArrayInputStream(excelFile.toByteArray()))) {
            // Crear un documento PDF
            ByteArrayOutputStream pdfOutStream = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, pdfOutStream);
            document.open();

            // Recorrer todas las hojas del archivo Excel
            Iterator<Sheet> sheetIterator = workbook.iterator();
            while (sheetIterator.hasNext()) {
                Sheet sheet = sheetIterator.next();
                document.newPage();
                PdfPTable table = new PdfPTable(sheet.getRow(0).getPhysicalNumberOfCells()); // Número de columnas

                // Recorrer todas las filas y columnas del Excel
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        String cellValue = cell.toString();  // Obtener valor de la celda
                        table.addCell(cellValue);
                    }
                }

                // Agregar la tabla al documento PDF
                document.add(table);
            }

            document.close();

            // Convertir el archivo PDF a Base64
            byte[] pdfBytes = pdfOutStream.toByteArray();
            return Base64.getEncoder().encodeToString(pdfBytes);  // Convertir a Base64
        }
    }
	
	@Override
	public void convertExcelToPdf(ByteArrayOutputStream excelFile, HttpServletResponse response) throws Exception {
        // Leer el archivo Excel
        try (XSSFWorkbook workbook = new XSSFWorkbook(new ByteArrayInputStream(excelFile.toByteArray()))) {
            // Crear un documento PDF
            Document document = new Document();
            ByteArrayOutputStream pdfOutStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, pdfOutStream);
            document.open();

            // Recorrer todas las hojas del archivo Excel
            Iterator<Sheet> sheetIterator = workbook.iterator();
            while (sheetIterator.hasNext()) {
                Sheet sheet = sheetIterator.next();
                document.newPage();
                PdfPTable table = new PdfPTable(sheet.getRow(0).getPhysicalNumberOfCells()); // Número de columnas

                // Recorrer todas las filas y columnas
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        String cellValue = cell.toString();  // Obtener valor de la celda
                        table.addCell(cellValue);
                    }
                }

                // Agregar la tabla al documento PDF
                document.add(table);
            }

            document.close();
            
            // Establecer los encabezados de respuesta para la descarga del archivo PDF
            response.setContentType("application/pdf");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            response.setHeader("Content-Disposition", "attachment; filename=Resultado_concurso_" + LocalDateTime.now().format(formatter) + ".pdf");

            // Escribir el contenido PDF al OutputStream de la respuesta
            InputStream inputStream = new ByteArrayInputStream(pdfOutStream.toByteArray());
            IOUtils.copy(inputStream, response.getOutputStream());
        }
	}
	
}
