package csjar.controlpatrimonial.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import csjar.controlpatrimonial.constants.GeneralConstants;
import csjar.controlpatrimonial.dto.RequestAdquisicionDTO;
import csjar.controlpatrimonial.dto.RequestDetalleAdquisicionDTO;
import csjar.controlpatrimonial.dto.ResponseAdquisicionDTO;
import csjar.controlpatrimonial.entity.Adquisicion;
import csjar.controlpatrimonial.entity.Catalogo;
import csjar.controlpatrimonial.entity.DetalleAdquisicion;
import csjar.controlpatrimonial.entity.TipoAdquisicion;
import csjar.controlpatrimonial.mapper.service.AdquisicionMapperService;
import csjar.controlpatrimonial.repository.AdquisicionRepository;
import csjar.controlpatrimonial.service.AdquisicionService;
import csjar.controlpatrimonial.service.CatalogoService;
import csjar.controlpatrimonial.service.FtpService;
import csjar.controlpatrimonial.service.TipoAdquisicionService;
import csjar.controlpatrimonial.utils.CollectionUtils;

@Service
public class AdquisicionServiceImpl implements AdquisicionService {

	private AdquisicionRepository adquisicionRepository;
	private AdquisicionMapperService adquisicionMapperService;
	private TipoAdquisicionService tipoAdquisicionService;
	private CatalogoService catalogoService;
	private FtpService ftpService;
	
	public AdquisicionServiceImpl(AdquisicionRepository adquisicionRepository,
		AdquisicionMapperService adquisicionMapperService, TipoAdquisicionService tipoAdquisicionService,
		CatalogoService catalogoService, FtpService ftpService) {
		super();
		this.adquisicionRepository = adquisicionRepository;
		this.adquisicionMapperService = adquisicionMapperService;
		this.tipoAdquisicionService = tipoAdquisicionService;
		this.catalogoService = catalogoService;
		this.ftpService = ftpService;
	}
	
	@Override
	public ResponseAdquisicionDTO verAdquisicion(Integer id) {
		Adquisicion adquisicion = this.adquisicionRepository.findById(id).get();
		return this.adquisicionMapperService.toDTO(adquisicion);
	}
	
	@Override
	public List<ResponseAdquisicionDTO> buscarAdquisicion(String documento) {
		List<Adquisicion> lista = this.adquisicionRepository.findByDocumentoContains(documento);
		if(!CollectionUtils.isValidate(lista))
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontraron coincidencias");	
		return this.adquisicionMapperService.toDTO(lista);
	}

	@Transactional
	@Override
	public void guardarAdquisicion(RequestAdquisicionDTO requestAdquisicionDTO) {
		Adquisicion adquisicion = new Adquisicion();
		if(!Objects.isNull(requestAdquisicionDTO.getId())) {
			adquisicion = this.adquisicionRepository.findById(requestAdquisicionDTO.getId()).get();
		}
		else{
			adquisicion = this.adquisicionMapperService.toEntity(requestAdquisicionDTO);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate fecha = LocalDate.parse(requestAdquisicionDTO.getFecAdquisicion(), formatter);
		    LocalDateTime fechaHora = fecha.atStartOfDay();
	        adquisicion.setFecAdquisicion(fechaHora);
	        adquisicion.setEstado(requestAdquisicionDTO.getRegularizar().equals("S") ? 
	        	GeneralConstants.ADQUISICION_ESTADO_REGULARIZADO : GeneralConstants.ADQUISICION_ESTADO_REGISTRADO);
	        adquisicion.setUsuario(this.obtenerUsuario());
		}
		TipoAdquisicion tipoAdquisicion = tipoAdquisicionService.obtenerEntidad(requestAdquisicionDTO.getIdTipoAdquisicion());
		adquisicion.setTipoAdquisicion(tipoAdquisicion);
		
		if(CollectionUtils.isValidate(requestAdquisicionDTO.getDetalle())){
			
			List<Integer> idsCatalogo = requestAdquisicionDTO.getDetalle().stream()
				.map(RequestDetalleAdquisicionDTO::getIdCatalogo).collect(Collectors.toList());
			List<Catalogo> catalogos = this.catalogoService.obtenerCatalogo(idsCatalogo);
			List<DetalleAdquisicion> listaDetalle = new ArrayList<>();
			requestAdquisicionDTO.getDetalle().stream().forEach(requestDetalle -> {
				DetalleAdquisicion detalle = new DetalleAdquisicion();
				detalle.setCantidad(requestDetalle.getCantidad());
				detalle.setCatalogo(catalogos.stream().filter(c -> c.getId().equals(requestDetalle.getIdCatalogo())).findFirst().get());
				detalle.setPrecioUnitario(requestDetalle.getPrecioUnitario());
				listaDetalle.add(detalle);
			});
			adquisicion.setDetalleAdquisicion(listaDetalle);
			
		}
		
		Adquisicion nnuevaAdquisicion = this.adquisicionRepository.saveAndFlush(adquisicion);
		
		String nombreArchivo = GeneralConstants.NOMENCLATURA_ADQUISICION_PDF + nnuevaAdquisicion.getId() + GeneralConstants.EXTENSION_PDF;
		String directorio = GeneralConstants.DIRECTORIO_FTP_ADQUISICION + String.valueOf(LocalDateTime.now().getYear());
		
		nnuevaAdquisicion.setRutaPdf(directorio);
		nnuevaAdquisicion.setNombrePdf(nombreArchivo);
		
		byte[] fileBytes = Base64.getDecoder().decode(requestAdquisicionDTO.getFile());
		
		this.ftpService.conectarFTP();
		this.ftpService.cargarArchivo(nombreArchivo, directorio, fileBytes);
				
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
	public Adquisicion obtenerEntidad(Integer id) {
		return this.adquisicionRepository.findById(id).get();
	}

	@Override
	public void actualizarEntidad(Adquisicion request) {
		Adquisicion adquisicion = adquisicionRepository.findById(request.getId()).get();
		adquisicion.setEstado(request.getEstado());
	}

	@Override
	public byte[] descargarActa(Integer id) throws Exception {
		Adquisicion adquisicion = this.adquisicionRepository.findById(id).get();
		this.ftpService.conectarFTP();
		return this.ftpService.descargarArchivo(adquisicion.getRutaPdf(), adquisicion.getNombrePdf());
	}
	
}
