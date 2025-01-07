package csjar.controlpatrimonial.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.text.DocumentException;

import csjar.controlpatrimonial.constants.GeneralConstants;
import csjar.controlpatrimonial.dto.RequestBienesDTO;
import csjar.controlpatrimonial.dto.RequestDetalleBienesDTO;
import csjar.controlpatrimonial.dto.RequestEtiquetaDTO;
import csjar.controlpatrimonial.dto.ResponseBienDTO;
import csjar.controlpatrimonial.dto.ResponseTrazabilidadDTO;
import csjar.controlpatrimonial.dto.ResponseUsuarioDTO;
import csjar.controlpatrimonial.entity.Acta;
import csjar.controlpatrimonial.entity.Adquisicion;
import csjar.controlpatrimonial.entity.Area;
import csjar.controlpatrimonial.entity.Bien;
import csjar.controlpatrimonial.entity.BienVer;
import csjar.controlpatrimonial.entity.Catalogo;
import csjar.controlpatrimonial.entity.Modelo;
import csjar.controlpatrimonial.entity.Usuario;
import csjar.controlpatrimonial.repository.BienRepository;
import csjar.controlpatrimonial.service.AdquisicionService;
import csjar.controlpatrimonial.service.AreaService;
import csjar.controlpatrimonial.service.BienService;
import csjar.controlpatrimonial.service.BienVerService;
import csjar.controlpatrimonial.service.CatalogoService;
import csjar.controlpatrimonial.service.ModeloService;
import csjar.controlpatrimonial.service.UsuarioService;
import csjar.controlpatrimonial.utils.CollectionUtils;

@Service
public class BienServiceImpl implements BienService {

	private BienRepository repository;
	private ModeloService modeloService;
	private CatalogoService catalogoService;
	private AdquisicionService adquisicionService;
	private BienVerService bienVerService;
	private UsuarioService usuarioService;
	private AreaService areaService;
	
	public BienServiceImpl(BienRepository repository, ModeloService modeloService, CatalogoService catalogoService,
			AdquisicionService adquisicionService, BienVerService bienVerService, UsuarioService usuarioService,
			AreaService areaService) {
		super();
		this.repository = repository;
		this.modeloService = modeloService;
		this.catalogoService = catalogoService;
		this.adquisicionService = adquisicionService;
		this.bienVerService = bienVerService;
		this.usuarioService = usuarioService;
		this.areaService = areaService;
	}

	@Override
	public ResponseBienDTO obtenerBien(String codigo, Integer idEmpleado, String tipoActa) {
		Bien bien = this.repository.findByCodigoPatrimonial(codigo);
		if(Objects.isNull(bien)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró resultado con el código " + codigo);
		}
		
		if(bien.getEstado().equals(GeneralConstants.BIEN_ESTADO_MANTENIMIENTO)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El código " + codigo + " se encuentra en mantenimiento.");
		}
		
		if(tipoActa.equals("A")) {
			if(Objects.nonNull(bien.getIdEmpleado()) && !bien.getIdEmpleado().equals(idEmpleado)) 
				throw new ResponseStatusException(HttpStatus.CONFLICT, "El código " + codigo + " se encuentra asignado a otro empleado.");
			if(Objects.nonNull(bien.getIdEmpleado()) && bien.getIdEmpleado().equals(idEmpleado)) 
				throw new ResponseStatusException(HttpStatus.CONFLICT, "El código " + codigo + " ya se está asignado a este empleado.");
		}
		else {
			if(Objects.isNull(bien.getIdEmpleado()) || !bien.getIdEmpleado().equals(idEmpleado))
				throw new ResponseStatusException(HttpStatus.CONFLICT, "El código " + codigo + " no se encuentra asignado a este empleado para devolución.");
		}
		
		ResponseBienDTO response = new ResponseBienDTO();
		response.setCodigoPatrimonial(bien.getCodigoPatrimonial());
		response.setDescripcion(bien.getDescripcion());
		return response;
	}
	
	@Transactional
	@Override
	public void generarBienes(RequestBienesDTO requestBienesDTO) {
		
		if(CollectionUtils.isValidate(requestBienesDTO.getBienes())) {
			
			List<Integer> idsModelo = requestBienesDTO.getBienes().stream() 
		            .map(RequestDetalleBienesDTO::getIdModelo).distinct().collect(Collectors.toList());
			
			List<Integer> idsCatalogo = requestBienesDTO.getBienes().stream() 
		            .map(RequestDetalleBienesDTO::getIdCatalogo).distinct().collect(Collectors.toList());
			
			Map<Integer, Modelo> mapModelos = this.modeloService.obtenerEntidades(idsModelo)
					.stream().collect(Collectors.toMap(Modelo::getId, Function.identity()));
			
			Map<Integer, Catalogo> mapCatalogos = this.catalogoService.obtenerCatalogo(idsCatalogo)
					.stream().collect(Collectors.toMap(Catalogo::getId, Function.identity()));
			
			List<Bien> bienes = new ArrayList<>();
			
			requestBienesDTO.getBienes().stream().forEach(b -> {
				
				Catalogo catalogo = mapCatalogos.get(b.getIdCatalogo());
				Integer secuencia = catalogo.getSecuencia();
				Bien bien = new Bien();
				bien.setIdAdquisicion(requestBienesDTO.getIdAdquisicion());
				bien.setColor(b.getColor());
				bien.setDescripcion(b.getDescripcion());
				bien.setSerie(b.getSerie());
				bien.setEstado(GeneralConstants.BIEN_ESTADO_INGRESADO);
				bien.setObservacion(GeneralConstants.BIEN_OBSERVACION_NUEVO_INGRESO);
				bien.setObservacion(b.getObservacion());
				bien.setModelo(mapModelos.get(b.getIdModelo()));
				bien.setCodigoPatrimonial(catalogo.getCodigo().concat(String.format("%04d", secuencia)));
				bien.setIdCatalogo(b.getIdCatalogo());
				bienes.add(bien);
				secuencia++;
				catalogo.setSecuencia(secuencia);
				
			});
			
			List<Bien> result = this.repository.saveAll(bienes);
			this.bienVerService.generarVersion(result, null);
			
			Adquisicion adquisicion = this.adquisicionService.obtenerEntidad(requestBienesDTO.getIdAdquisicion());
			adquisicion.setEstado(GeneralConstants.ADQUISICION_ESTADO_GENERADO);
			adquisicionService.actualizarEntidad(adquisicion);
		}
		
	}

	@Override
	public List<ResponseBienDTO> obtenerBienes(Integer idAdquisicion) {
		List<Bien> bienes = this.repository.findByIdAdquisicion(idAdquisicion);
		List<ResponseBienDTO> responseBienes = new ArrayList<>();
		
		List<Integer> idsCatalogo = bienes.stream() 
	            .map(Bien::getIdCatalogo).distinct().collect(Collectors.toList());
		
		Map<Integer, String> mapCatalogos = this.catalogoService.obtenerCatalogo(idsCatalogo)
				.stream().collect(Collectors.toMap(Catalogo::getId, Catalogo::getDenominacion));
		
		bienes.stream().forEach(b -> {
			ResponseBienDTO bien = new ResponseBienDTO();
			bien.setCatalogo(mapCatalogos.get(b.getIdCatalogo()));
			bien.setCodigoPatrimonial(b.getCodigoPatrimonial());
			bien.setDescripcion(b.getDescripcion());
			responseBienes.add(bien);
		});
		
		return responseBienes;
	}

	@Override
	public byte[] generarEtiquetas(List<RequestEtiquetaDTO> requestEtiquetaDTO) throws DocumentException, IOException, WriterException  {
		
		String htmlContent = this.construirHtml(requestEtiquetaDTO);
		try (ByteArrayOutputStream pdfStream = new ByteArrayOutputStream()) {
            HtmlConverter.convertToPdf(htmlContent, pdfStream);
            return pdfStream.toByteArray();
        }
	}

	
	public byte[] generarBarcode(String number) throws WriterException, IOException {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(number, BarcodeFormat.CODE_128, 200, 20);

        BufferedImage image = new BufferedImage(200, 20, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < 200; x++) {
            for (int y = 0; y < 20; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF);
            }
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", baos);
        return baos.toByteArray();
    }
	
	public String construirHtml(List<RequestEtiquetaDTO> requestEtiquetaDTO) throws IOException, WriterException {
		ClassPathResource resource = new ClassPathResource("static/img/pj.jpg");
		byte[] logo = FileUtils.readFileToByteArray(resource.getFile());
		String logoBase64 = "data:image/png;base64, " + Base64.encodeBase64String(logo);

		StringBuilder htmlBuilder = new StringBuilder();    
		htmlBuilder.append("<html>");
		
		 for (RequestEtiquetaDTO etiqueta : requestEtiquetaDTO) {
			byte[] barcodeImage = generarBarcode(etiqueta.getCodigoPatrimonial());
			String barcodeBase64 = Base64.encodeBase64String(barcodeImage);
			
			htmlBuilder.append("<div style='width: 200px; font-size: 8px'>")
			.append("<div style='display: flex'>")
			.append("<div style='width: 20%'><img width='30'src='" + logoBase64 + "'/></div>")
			.append("<div style='width: 60%; font-size: 7px;' align='center'><strong>CONTROL PATRIMONIAL</strong><br>" + etiqueta.getCatalogo() + "</div>")
			.append("<div style='width: 20%'></div></div>")
			.append("<div align='center'><img src='data:image/png;base64," + barcodeBase64 + "' />")
			.append("<br>"+ etiqueta.getCodigoPatrimonial() +"</div>")
			.append("<div style='display: flex'>")
			.append("<div style='width: 50%'>" + etiqueta.getDocumento()+ "</div>")
			.append("<div align='right' style='width: 50%'>UE-AREQUIPA</div>")
			.append("</div><hr></div>");
			htmlBuilder.append("</html>");
		};
		return htmlBuilder.toString();
	}

	@Override
	public Bien obtenerEntidad(String codigo) {
		return this.repository.findByCodigoPatrimonial(codigo);
	}

	@Override
	public ResponseTrazabilidadDTO obtenerTrazabilidad(String codigo) {
		Bien bien = this.repository.findByCodigoPatrimonial(codigo);
		if(Objects.isNull(bien)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró resultado con el código " + codigo);
		}
		
		Catalogo catalogo = this.catalogoService.obtenerCatalogo(Arrays.asList(bien.getIdCatalogo())).get(0);
		ResponseUsuarioDTO empleado = null;
		if(Objects.nonNull(bien.getIdEmpleado()))
			empleado = this.usuarioService.buscarUsuario(bien.getIdEmpleado());
		
		List<Acta> actas = bien.getActas();
		actas.sort(Comparator.comparing(Acta::getFecRegistro).reversed());
		
		Area area = this.areaService.obtenerEntidad(CollectionUtils.isValidate(actas) ? actas.get(0).getIdArea() : 1);
		
		ResponseTrazabilidadDTO traza = new ResponseTrazabilidadDTO();
		traza.setCodigo(bien.getCodigoPatrimonial());
		traza.setCatalogo(catalogo.getDenominacion());
		traza.setDescripcion(bien.getDescripcion());
		traza.setConservacionActual(bien.getEstadoConservacion());
		traza.setEstadoActual(bien.getEstado());
		traza.setEmpleadoActual(Objects.isNull(empleado) ? "" : empleado.getNombres().concat(" ").concat(empleado.getApellidos()));
		traza.setUbicacionActual(area.getSede().getDenominacion().concat(" - ").concat(area.getSede().getDireccion()));
		
		List<BienVer> versiones = this.bienVerService.obtenerEntidades(bien.getId());
		
		if(CollectionUtils.isValidate(versiones)) {
			
			versiones.sort(Comparator.comparing(BienVer::getFecRegistro).reversed());
			
	        List<Integer> ids = versiones.stream()
                    .map(BienVer::getIdEmpleado)
                    .filter(id -> id != null)
                    .collect(Collectors.toList());
	        
	        List<Usuario> empleados = new ArrayList<>();
	        if(CollectionUtils.isValidate(ids)) {
	        	 empleados = this.usuarioService.obtenerEntidades(ids);
	        }
	        Map<Integer, String> mapUsuarios = empleados.stream().collect(Collectors.toMap(Usuario::getId, usuario -> usuario.getNombres() + " " + usuario.getApellidos()));

			
			List<ResponseBienDTO> detalle = new ArrayList<>();
			versiones.stream().forEach(v -> {
				ResponseBienDTO dto = new ResponseBienDTO();
				dto.setEstado(v.getEstado());
				dto.setFecRegistro(v.getFecRegistro());
				dto.setObservaciones(v.getObservacion());
				dto.setEmpleado(mapUsuarios.containsKey(v.getIdEmpleado()) ? mapUsuarios.get(v.getIdEmpleado()) : "");
				dto.setIdActa(v.getIdActa());
				dto.setConservacion(v.getEstadoConservacion());
				detalle.add(dto);
			});
			traza.setDetalle(detalle);
		}
		
		return traza;
	}

}
