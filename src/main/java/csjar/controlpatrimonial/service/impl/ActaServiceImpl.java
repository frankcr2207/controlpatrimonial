package csjar.controlpatrimonial.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import csjar.controlpatrimonial.constants.GeneralConstants;
import csjar.controlpatrimonial.dto.RequestActaDTO;
import csjar.controlpatrimonial.dto.RequestEmailDTO;
import csjar.controlpatrimonial.dto.ResponseActaDTO;
import csjar.controlpatrimonial.dto.ResponseBienDTO;
import csjar.controlpatrimonial.dto.ResponseUsuarioDTO;
import csjar.controlpatrimonial.entity.Acta;
import csjar.controlpatrimonial.entity.Area;
import csjar.controlpatrimonial.entity.Bien;
import csjar.controlpatrimonial.entity.Catalogo;
import csjar.controlpatrimonial.entity.Usuario;
import csjar.controlpatrimonial.external.service.NotificacionExternalService;
import csjar.controlpatrimonial.repository.ActaRepository;
import csjar.controlpatrimonial.service.ActaService;
import csjar.controlpatrimonial.service.AreaService;
import csjar.controlpatrimonial.service.BienService;
import csjar.controlpatrimonial.service.BienVerService;
import csjar.controlpatrimonial.service.CatalogoService;
import csjar.controlpatrimonial.service.FtpService;
import csjar.controlpatrimonial.service.UsuarioService;

@Service
public class ActaServiceImpl implements ActaService {

	@Value("${secret.key}")
	private static String SECRET_KEY;
	
	private ActaRepository repository;
	private UsuarioService usuarioService;
	private BienService bienService;
	private AreaService areaService;
	private CatalogoService catalogoService;
	private NotificacionExternalService notificacionExternalService;
	private FtpService ftpService;
	private BienVerService bienVerService;

	public ActaServiceImpl(ActaRepository repository, UsuarioService usuarioService, BienService bienService,
			NotificacionExternalService notificacionExternalService, FtpService ftpService, AreaService areaService,
			CatalogoService catalogoService, BienVerService bienVerService) {
		super();
		this.repository = repository;
		this.usuarioService = usuarioService;
		this.bienService = bienService;
		this.notificacionExternalService = notificacionExternalService;
		this.ftpService = ftpService;
		this.areaService = areaService;
		this.catalogoService = catalogoService;
		this.bienVerService = bienVerService;
	}

	@Transactional
	@Override
	public ResponseActaDTO guardarActa(RequestActaDTO requestActaDTO) throws Exception {
		Usuario usuario = usuarioService.buscarPorLogin(obtenerUsuario());
		Acta actaAnterior = this.repository.findActaWithMaxNumeroByYear(LocalDateTime.now().getYear());
		Integer numero = Objects.nonNull(actaAnterior) ? actaAnterior.getNumero() + 1 : 1;

		Usuario empleadoEntity = this.usuarioService.obtenerEntidad(requestActaDTO.getIdEmpleado());
		
		Acta acta = new Acta();
		acta.setUsuario(empleadoEntity);
		acta.setEstado(GeneralConstants.ACTA_ESTADO_REGISTRADO);
		acta.setNumero(numero);
		acta.setIdUsuario(usuario.getId());
		acta.setIdArea(requestActaDTO.getIdArea());
		acta.setTipo(requestActaDTO.getTipo().equals("A") ? GeneralConstants.ACTA_TIPO_ASIGNACION : GeneralConstants.ACTA_TIPO_DEVOLUCION);
		List<Bien> bienes = new ArrayList<>();
		requestActaDTO.getBienes().stream().forEach(b -> {
			Bien bien = bienService.obtenerEntidad(b.getCodigoPatrimonial());

			if(requestActaDTO.getTipo().equals(GeneralConstants.BIEN_ESTADO_ASIGNADO)) {
				if(Objects.nonNull(bien.getIdEmpleado()) && !bien.getIdEmpleado().equals(requestActaDTO.getIdEmpleado())) 
					throw new ResponseStatusException(HttpStatus.CONFLICT, "El código " + b.getCodigoPatrimonial() + " se encuentra asignado a otro empleado");
				if(Objects.nonNull(bien.getIdEmpleado()) && bien.getIdEmpleado().equals(requestActaDTO.getIdEmpleado())) 
					throw new ResponseStatusException(HttpStatus.CONFLICT, "El código " + b.getCodigoPatrimonial() + " ya se está asignado a este empleado");
				
				bien.setEstado(GeneralConstants.BIEN_ESTADO_ASIGNADO);
				bien.setIdEmpleado(requestActaDTO.getIdEmpleado());
			}
			else if(requestActaDTO.getTipo().equals(GeneralConstants.BIEN_ESTADO_DEVUELTO)){
				if(Objects.isNull(bien.getIdEmpleado()) || !bien.getIdEmpleado().equals(requestActaDTO.getIdEmpleado()))
					throw new ResponseStatusException(HttpStatus.CONFLICT, "El código " + b.getCodigoPatrimonial() + " no se encuentra asignado a este empleado para devolución.");
				
				bien.setIdEmpleado(null);
				bien.setEstado(GeneralConstants.BIEN_ESTADO_DEVUELTO);
			}
			
			bien.setEstadoConservacion(b.getEstadoConservacion());
			bien.setObservacion(b.getObservacion());
			
			bienes.add(bien);
		});
		
		String nombreArchivo = GeneralConstants.NOMENCLATURA_ACTA_PDF + acta.getNumero() + GeneralConstants.EXTENSION_PDF;
		String directorio = "/" + String.valueOf(LocalDateTime.now().getYear());
		
		acta.setBienes(bienes);
		acta.setNombrePdfOriginal(nombreArchivo);
		acta.setRutaPdf(directorio);
		
		Acta newActa = repository.saveAndFlush(acta);
		
		String token = generaHash(newActa.getId().toString());
		newActa.setToken(token);
		
		this.bienVerService.generarVersion(bienes, newActa.getId());

		ByteArrayOutputStream jxlsOutStream = new ByteArrayOutputStream();
		
		ResponseUsuarioDTO empleado = this.usuarioService.buscarUsuario(requestActaDTO.getIdEmpleado());
		generarDatosActa(jxlsOutStream, numero, requestActaDTO, bienes, empleado);
		
		byte[] fileBytes = convertExcelToPdf(jxlsOutStream);

		ftpService.conectarFTP();
		ftpService.cargarArchivo(nombreArchivo, directorio, fileBytes);
		
		ResponseActaDTO response = new ResponseActaDTO();

		if(this.enviarEmail(newActa, empleado, token, fileBytes)) {
			response.setStatus("OK");
			response.setMensaje("Acta Nro " + acta.getNumero() + "-" + LocalDateTime.now().getYear() + " generada y notificada correctamente.");
			newActa.setEstado(GeneralConstants.ACTA_ESTADO_NOTIFICADO);
			newActa.setFecNotificado(LocalDateTime.now());
		}
		else {
			response.setStatus("KO");
			response.setMensaje("Acta Nro " + acta.getNumero() + "-" + LocalDateTime.now().getYear() + " generada correctamente, sin embargo hubo un error de notificación, intente por el apartado de consulta de ACTAS.");
		}
		
		return response;
	}

	public String obtenerUsuario() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null) {
			String usuario = authentication.getName();
			return usuario;
		}

		throw new ResponseStatusException(HttpStatus.FORBIDDEN, "La sesión ha finalizado");
	}

	private void generarDatosActa(ByteArrayOutputStream jxlsOutStream, Integer numero, RequestActaDTO requestActaDTO, List<Bien> listaBienes, ResponseUsuarioDTO empleado) {


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
		data.put("tipo", requestActaDTO.getTipo().equals("A") ? GeneralConstants.ACTA_TIPO_ASIGNACION : GeneralConstants.ACTA_TIPO_DEVOLUCION);
		data.put("secuenciaActa", numero + "-" + LocalDateTime.now().getYear());

		List<Integer> idsCatalogo = listaBienes.stream().map(Bien::getIdCatalogo).distinct()
				.collect(Collectors.toList());

		Map<Integer, Catalogo> mapCatalogos = this.catalogoService.obtenerCatalogo(idsCatalogo).stream()
				.collect(Collectors.toMap(Catalogo::getId, Function.identity()));

		int orden = 1;
		List<ResponseBienDTO> bienes = new ArrayList<>();
		for(Bien b : listaBienes){
			ResponseBienDTO bien = new ResponseBienDTO();
			bien.setOrden(orden);
			bien.setCodigoPatrimonial(b.getCodigoPatrimonial());
			bien.setDenominacion(mapCatalogos.get(b.getIdCatalogo()).getDenominacion());
			bien.setMarca(b.getModelo().getMarca().getNombre());
			bien.setModelo(b.getModelo().getNombre());
			bien.setSerie(b.getSerie());
			bien.setColor(b.getColor());
			bien.setObservaciones(b.getObservacion());
			orden++;
			bienes.add(bien);
		}

		data.put("bienes", bienes);

		this.generarExcel(jxlsOutStream, GeneralConstants.ACTA_PLANTILLA_EXCEL, data);
	}

	public void generarExcel(OutputStream outStream, String templateName, Map<String, Object> data) {
		String pathTemplateName = ("/reports/").concat(templateName).concat(GeneralConstants.EXTENSION_EXCEL);
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

		String outputPdfPath = pdfDirectory + "\\" + new File(inputExcelFilePath).getName().replace(GeneralConstants.EXTENSION_EXCEL, GeneralConstants.EXTENSION_PDF);

		File pdfFile = new File(outputPdfPath);
		if (!pdfFile.exists()) {
			throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY,
					"Error al convertir el archivo Excel a PDF.");
		}

		byte[] pdfBytes = Files.readAllBytes(Paths.get(outputPdfPath));

		tempExcelFile.delete();
		pdfFile.delete();

		return pdfBytes;

	}

	private boolean enviarEmail(Acta acta, ResponseUsuarioDTO empleado, String token, byte[] fileBytes) {
		RequestEmailDTO requestEmailDTO = new RequestEmailDTO();
		String mensaje = GeneralConstants.NOTIFICACION_CUERPO.replace("<nombres>", empleado.getNombres().concat(" ").concat(empleado.getApellidos()))
				.replace("<numeroActa>", acta.getNumero().toString().concat("-").concat(String.valueOf(acta.getFecRegistro().getYear())))
					.replace("<enlace>", GeneralConstants.MS_CONTROL_PATRIMONIAL_VERIFICAR.concat("?code=").concat(acta.getId().toString()).concat("&token=").concat(token));
		
		requestEmailDTO.setAsunto(GeneralConstants.NOTIFICACION_ASUNTO);
		requestEmailDTO.setDestino(empleado.getCorreo());
		requestEmailDTO.setMensaje(mensaje);
		requestEmailDTO.setRemitente(GeneralConstants.NOTIFICACION_REMITENTE);
		requestEmailDTO.setArchivo(Base64.getEncoder().encodeToString(fileBytes));
		
		return this.notificacionExternalService.enviarEmail(requestEmailDTO);
	}
	
	public static String generaHash(String data) throws NoSuchAlgorithmException {
        String input = data + SECRET_KEY;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(input.getBytes());
        return Base64.getEncoder().encodeToString(hashBytes);
    }

	@Override
	public ResponseActaDTO validarActa(Integer code, String token) throws Exception {
		
		ResponseActaDTO response = new ResponseActaDTO();
		boolean validation = this.validar(code, token.replace(" ", "+"));
		response.setMensaje(validation ? "Validación de datos correcta" : "Validación de datos incorrecta");
		response.setStatus(validation ? "OK" : "KO");
		if(validation) {
			Acta acta = this.repository.findById(code).orElse(null);
			if(Objects.nonNull(acta.getFecFirmado())){
				response.setMensaje("Acta ya fue confirmada");
				response.setStatus("KO");		
			}
			else {
				ResponseUsuarioDTO empleado = this.usuarioService.buscarUsuario(acta.getUsuario().getId());
				response.setIdActa(acta.getId());
				response.setNumeroActa(acta.getNumero().toString().concat("-").concat(String.valueOf(acta.getFecRegistro().getYear())));
				response.setDni(empleado.getDni());
				response.setNombresApellidos(empleado.getNombres().concat(" ").concat(empleado.getApellidos()));
				response.setFecha(acta.getFecRegistro());
			}
		}
		return response;
	}
	
	public boolean validar(Integer code, String token) throws NoSuchAlgorithmException {
		Integer id = code;
        String secretKey = SECRET_KEY;
        String receivedHash = token;
        return verificaHash(receivedHash, id, secretKey);
	}
	
	public static boolean verificaHash(String receivedHash, Integer data, String secretKey) throws NoSuchAlgorithmException {
		String generatedHash = generaHash(data.toString());
        return generatedHash.equals(receivedHash);
    }

	@Transactional
	@Override
	public void guardarConfirmacion(Integer id, MultipartFile multipartFile) throws IOException {
		
		Acta acta = this.repository.findById(id).get();
		String pdf = acta.getNombrePdfOriginal();
		pdf = pdf.substring(0, pdf.lastIndexOf("."));
		pdf = pdf.concat("_[F]").concat(GeneralConstants.EXTENSION_PDF);
		String directorio = "/" + String.valueOf(acta.getFecRegistro().getYear());
		
		Path path = Paths.get(GeneralConstants.SERVER_TEMP + pdf);
        Files.write(path, multipartFile.getBytes());
        
        contieneFirmaDigital(new File(GeneralConstants.SERVER_TEMP + pdf));
		
		this.ftpService.conectarFTP();
		this.ftpService.cargarArchivo(pdf, directorio, multipartFile.getBytes());

		if(Objects.nonNull(acta.getFecFirmado())) 
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Acta ya fue confirmada!!");
		
		acta.setFecFirmado(LocalDateTime.now());
		acta.setNombrePdfFirmado(pdf);
		acta.setEstado(GeneralConstants.ACTA_ESTADO_CONFIRMADO);
		this.repository.save(acta);
	}
	
	public void contieneFirmaDigital(File pdfFile) throws IOException {
        PDDocument document = PDDocument.load(pdfFile);
            List<PDSignature> signatures = document.getSignatureDictionaries();
            if(signatures.isEmpty())
            	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El archivo no está firmado digitalmente.");

    }

	@Override
	public byte[] descargarActa(Integer id) throws Exception {
		Acta acta = this.repository.findById(id).get();
		this.ftpService.conectarFTP();
		byte[] fileBytes = this.ftpService.descargarArchivo(acta.getRutaPdf(), acta.getNombrePdfFirmado());
		return fileBytes;
	}

	@Override
	public List<ResponseActaDTO> buscarActa(String parametro) throws Exception {
		List<Acta> actas = this.repository.findByUsuarioNombresContaining(parametro);
		List<ResponseActaDTO> response = new ArrayList<>();
		
		if(CollectionUtils.isEmpty(actas))
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontraon resultados");
		
		actas.stream().forEach(a -> {
			ResponseActaDTO dto = new ResponseActaDTO();
			dto.setIdActa(a.getId());
			dto.setNumeroActa(a.getNumero().toString().concat(" - ").concat(String.valueOf(a.getFecRegistro().getYear())));
			dto.setNombresApellidos(a.getUsuario().getNombres().concat(" ").concat(a.getUsuario().getApellidos()));
			dto.setFecRegistro(a.getFecRegistro());
			dto.setEstado(a.getEstado());
			response.add(dto);
		});
		
		return response;
	}

}
