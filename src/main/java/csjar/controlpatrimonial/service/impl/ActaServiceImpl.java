package csjar.controlpatrimonial.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import csjar.controlpatrimonial.domain.Acta;
import csjar.controlpatrimonial.domain.Area;
import csjar.controlpatrimonial.domain.Bien;
import csjar.controlpatrimonial.domain.Catalogo;
import csjar.controlpatrimonial.domain.Usuario;
import csjar.controlpatrimonial.dto.RequestActaDTO;
import csjar.controlpatrimonial.dto.RequestEmailDTO;
import csjar.controlpatrimonial.dto.ResponseBienesDTO;
import csjar.controlpatrimonial.dto.ResponseUsuarioDTO;
import csjar.controlpatrimonial.external.service.NotificacionExternalService;
import csjar.controlpatrimonial.repository.ActaRepository;
import csjar.controlpatrimonial.service.ActaService;
import csjar.controlpatrimonial.service.AreaService;
import csjar.controlpatrimonial.service.BienService;
import csjar.controlpatrimonial.service.CatalogoService;
import csjar.controlpatrimonial.service.FtpService;
import csjar.controlpatrimonial.service.UsuarioService;

@Service
public class ActaServiceImpl implements ActaService {

	private ActaRepository repository;
	private UsuarioService usuarioService;
	private BienService bienService;
	private AreaService areaService;
	private CatalogoService catalogoService;
	private NotificacionExternalService notificacionExternalService;
	private FtpService ftpService;

	public ActaServiceImpl(ActaRepository repository, UsuarioService usuarioService, BienService bienService,
			NotificacionExternalService notificacionExternalService, FtpService ftpService, AreaService areaService,
			CatalogoService catalogoService) {
		super();
		this.repository = repository;
		this.usuarioService = usuarioService;
		this.bienService = bienService;
		this.notificacionExternalService = notificacionExternalService;
		this.ftpService = ftpService;
		this.areaService = areaService;
		this.catalogoService = catalogoService;
	}

	@Transactional
	@Override
	public void guardarActa(RequestActaDTO requestActaDTO) throws Exception {
		Usuario usuario = usuarioService.buscarRntidad(obtenerUsuario());
		Acta actaAnterior = this.repository.findActaWithMaxNumeroByYear(LocalDateTime.now().getYear());
		Integer numero = Objects.nonNull(actaAnterior) ? actaAnterior.getNumero() + 1 : 1;

		Acta acta = new Acta();
		acta.setIdEmpleado(requestActaDTO.getIdEmpleado());
		acta.setEstado("R");
		acta.setNumero(numero);
		acta.setIdUsuario(usuario.getId());
		acta.setIdArea(requestActaDTO.getIdArea());
		acta.setTipo(requestActaDTO.getTipo());
		List<Bien> bienes = new ArrayList<>();
		requestActaDTO.getBienes().stream().forEach(b -> {
			Bien bien = bienService.obtenerEntidad(b.getCodigoPatrimonial());
			
			if(requestActaDTO.getTipo().equals("D") && bien.getIdEmpleado() != requestActaDTO.getIdEmpleado())
				throw new ResponseStatusException(HttpStatus.CONFLICT, "El código " + b.getCodigoPatrimonial() + " no se encuentra asignado a este empleado para devolución.");
			if(requestActaDTO.getTipo().equals("A")) {
				bien.setEstado("A");
				bien.setIdEmpleado(requestActaDTO.getIdEmpleado());
			}
			else {
				bien.setIdEmpleado(null);
				bien.setEstado("D");
			}
			bien.setEstadoConservacion(b.getEstadoConservacion());
			bien.setObservacion(b.getObservacion());
			
			bienes.add(bien);
		});
		acta.setBienes(bienes);
		repository.save(acta);

		ByteArrayOutputStream jxlsOutStream = new ByteArrayOutputStream();
		generarDatosActa(jxlsOutStream, numero, requestActaDTO, bienes);
		
		byte[] fileBytes = convertExcelToPdf(jxlsOutStream);
		
		String nombreArchivo = "CP_acta_" + acta.getNumero() + ".pdf";
		String directorio = "/" + String.valueOf(LocalDateTime.now().getYear());
		ftpService.conectarFTP();
		ftpService.cargarArchivo(nombreArchivo, directorio, fileBytes);

	}

	public String obtenerUsuario() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null) {
			String usuario = authentication.getName();
			return usuario;
		}

		throw new ResponseStatusException(HttpStatus.FORBIDDEN, "La sesión ha finalizado");
	}

	private void generarDatosActa(ByteArrayOutputStream jxlsOutStream, Integer numero, RequestActaDTO requestActaDTO, List<Bien> listaBienes) {

		ResponseUsuarioDTO empleado = this.usuarioService.buscarUsuario(requestActaDTO.getIdEmpleado());
		Area area = this.areaService.obtenerEntidad(requestActaDTO.getIdArea());

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		
		Map<String, Object> data = new HashMap<>();
		data.put("fecha", LocalDateTime.now().format(formatter));
		data.put("dni", empleado.getDni());
		data.put("nombresApellidos", empleado.getNombres() + " " + empleado.getApellidos());
		data.put("correo", requestActaDTO.getCorreo());
		data.put("area", area.getDenominacion());
		data.put("sede", area.getSede().getDenominacion());
		data.put("direccion", area.getSede().getDireccion());
		data.put("tipo", requestActaDTO.getTipo().equals("A") ? "ASIGNACIÓN" : "DEVOLUCIÓN");
		data.put("secuenciaActa", numero + "-" + LocalDateTime.now().getYear());

		List<Integer> idsCatalogo = listaBienes.stream().map(Bien::getIdCatalogo).distinct()
				.collect(Collectors.toList());

		Map<Integer, Catalogo> mapCatalogos = this.catalogoService.obtenerCatalogo(idsCatalogo).stream()
				.collect(Collectors.toMap(Catalogo::getId, Function.identity()));

		int orden = 0;
		List<ResponseBienesDTO> bienes = new ArrayList<>();
		for(Bien b : listaBienes){
			ResponseBienesDTO bien = new ResponseBienesDTO();
			bien.setOrden(orden++);
			bien.setCodigoPatrimonial(b.getCodigoPatrimonial());
			bien.setDenominacion(mapCatalogos.get(b.getIdCatalogo()).getDenominacion());
			bien.setMarca(b.getModelo().getMarca().getNombre());
			bien.setModelo(b.getModelo().getNombre());
			bien.setSerie(b.getSerie());
			bien.setColor(b.getColor());
			bien.setObservaciones(b.getObservacion());
			bienes.add(bien);
		}

		data.put("bienes", bienes);

		this.generarExcel(jxlsOutStream, "plantilla_acta_v2", data);
	}

	public void generarExcel(OutputStream outStream, String templateName, Map<String, Object> data) {
		String pathTemplateName = ("/reports/").concat(templateName).concat(".xlsx");
		try (InputStream input = this.getClass().getResourceAsStream(pathTemplateName)) {

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

	private byte[] convertExcelToPdf(ByteArrayOutputStream jxlsOutStream) throws Exception {
		// Guardar el archivo Excel temporalmente en el disco
		File tempExcelFile = new File("temp_excel_file.xlsx");
		try (FileOutputStream fos = new FileOutputStream(tempExcelFile)) {
			jxlsOutStream.writeTo(fos);
		}

		try (FileInputStream fis = new FileInputStream(tempExcelFile)) {
			Workbook workbook = new XSSFWorkbook(fis);
			Sheet sheet = workbook.getSheetAt(0);

			PrintSetup printSetup = sheet.getPrintSetup();
			printSetup.setLandscape(true); 

			sheet.setFitToPage(true); 
			sheet.getPrintSetup().setFitWidth((short) 1);
			sheet.getPrintSetup().setFitHeight((short) 1);

			try (FileOutputStream outFile = new FileOutputStream(tempExcelFile)) {
				workbook.write(outFile);
			}
		}

		String libreOfficePath = "C:\\Program Files\\LibreOffice\\program\\soffice.exe";
																							
		String pdfDirectory = "E:\\temp\\pdf_output";
		File pdfDir = new File(pdfDirectory);

		if (!pdfDir.exists()) {
			pdfDir.mkdirs();
		}

		String inputExcelFilePath = tempExcelFile.getAbsolutePath(); 

		String command = "\"" + libreOfficePath + "\" --headless --convert-to pdf --outdir \"" + pdfDirectory + "\" \""
				+ inputExcelFilePath + "\"";

		// Ejecutar el proceso
		Process process = Runtime.getRuntime().exec(command);
		process.waitFor();

		String outputPdfPath = pdfDirectory + "\\" + new File(inputExcelFilePath).getName().replace(".xlsx", ".pdf");

		File pdfFile = new File(outputPdfPath);
		if (!pdfFile.exists()) {
			throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY,
					"Error al convertir el archivo Excel a PDF.");
		}

		// Leer el archivo PDF y convertirlo a Base64
		byte[] pdfBytes = Files.readAllBytes(Paths.get(outputPdfPath));
		//String pdfBase64 = Base64.getEncoder().encodeToString(pdfBytes);

		// Limpiar los archivos temporales
		//tempExcelFile.delete();
		//pdfFile.delete();

		return pdfBytes;

	}

	private void enviarEmail() {
		RequestEmailDTO requestEmailDTO = new RequestEmailDTO();
		this.notificacionExternalService.enviarEmail(requestEmailDTO);
	}

}
