package csjar.controlpatrimonial.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import csjar.controlpatrimonial.domain.Adquisicion;
import csjar.controlpatrimonial.domain.Bien;
import csjar.controlpatrimonial.domain.Catalogo;
import csjar.controlpatrimonial.domain.DetalleAdquisicion;
import csjar.controlpatrimonial.domain.Modelo;
import csjar.controlpatrimonial.domain.TipoAdquisicion;
import csjar.controlpatrimonial.dto.RequestAdquisicionDTO;
import csjar.controlpatrimonial.dto.RequestBienesDTO;
import csjar.controlpatrimonial.dto.RequestDetalleAdquisicionDTO;
import csjar.controlpatrimonial.dto.RequestDetalleBienesDTO;
import csjar.controlpatrimonial.dto.ResponseAdquisicionDTO;
import csjar.controlpatrimonial.mapper.service.AdquisicionMapperService;
import csjar.controlpatrimonial.repository.AdquisicionRepository;
import csjar.controlpatrimonial.service.AdquisicionService;
import csjar.controlpatrimonial.service.CatalogoService;
import csjar.controlpatrimonial.service.DetalleAdquisicionService;
import csjar.controlpatrimonial.service.ModeloService;
import csjar.controlpatrimonial.service.TipoAdquisicionService;
import csjar.controlpatrimonial.utils.CollectorUtils;

@Service
public class AdquisicionServiceImpl implements AdquisicionService {

	private AdquisicionRepository adquisicionRepository;
	private AdquisicionMapperService adquisicionMapperService;
	private TipoAdquisicionService tipoAdquisicionService;
	private CatalogoService catalogoService;
	private DetalleAdquisicionService detalleAdquisicionService;
	private ModeloService modeloService;
	
	public AdquisicionServiceImpl(AdquisicionRepository adquisicionRepository,
		AdquisicionMapperService adquisicionMapperService, TipoAdquisicionService tipoAdquisicionService,
		CatalogoService catalogoService, DetalleAdquisicionService detalleAdquisicionService,
		ModeloService modeloService) {
		super();
		this.adquisicionRepository = adquisicionRepository;
		this.adquisicionMapperService = adquisicionMapperService;
		this.tipoAdquisicionService = tipoAdquisicionService;
		this.catalogoService = catalogoService;
		this.detalleAdquisicionService = detalleAdquisicionService;
		this.modeloService = modeloService;
	}
	
	@Override
	public List<ResponseAdquisicionDTO> buscarAdquisicion(String documento) {
		List<Adquisicion> lista = this.adquisicionRepository.findByDocumentoContains(documento);
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
		}
		TipoAdquisicion tipoAdquisicion = tipoAdquisicionService.obtenerEntidad(requestAdquisicionDTO.getIdTipoAdquisicion());
		adquisicion.setTipoAdquisicion(tipoAdquisicion);
		
		if(CollectorUtils.isValidate(requestAdquisicionDTO.getDetalle())){
			
			List<Integer> idsCatalogo = requestAdquisicionDTO.getDetalle().stream()
				.map(RequestDetalleAdquisicionDTO::getIdCatalogo).collect(Collectors.toList());
			List<Catalogo> catalogos = this.catalogoService.obtenerCatalogo(idsCatalogo);
			List<DetalleAdquisicion> listaDetalle = new ArrayList<>();
			requestAdquisicionDTO.getDetalle().stream().forEach(requestDetalle -> {
				DetalleAdquisicion detalle = new DetalleAdquisicion();
				detalle.setCantidad(requestDetalle.getCantidad());
				detalle.setCatalogo(catalogos.stream().filter(c -> c.getId().equals(requestDetalle.getIdCatalogo())).findFirst().get());
				listaDetalle.add(detalle);
			});
			adquisicion.setDetalleAdquisicion(listaDetalle);
			
		}
		
		this.adquisicionRepository.save(adquisicion);
				
	}

	@Override
	public void generarBienes(List<RequestBienesDTO> requestAdquisicionDTO) {
		if(CollectorUtils.isValidate(requestAdquisicionDTO)) {
			
			List<Integer> ids = requestAdquisicionDTO.stream()  
		            .flatMap(a -> a.getBienes().stream())  
		            .map(RequestDetalleBienesDTO::getIdModelo) 
		            .collect(Collectors.toList()); 
			List<Modelo> modelos = this.modeloService.obtenerEntidades(ids);
			
			List<Integer> idsDetalle = requestAdquisicionDTO.stream().map(r -> r.getIdDetalleAdquisicion()).collect(Collectors.toList());
			List<DetalleAdquisicion> detalles = this.detalleAdquisicionService.obtenerEntidades(idsDetalle);
			
			requestAdquisicionDTO.stream().forEach(request -> {
				DetalleAdquisicion detalle = detalles.stream()
					.filter(d -> d.getId() == request.getIdDetalleAdquisicion()).findFirst().get();
				
				Integer secuencia = detalle.getCatalogo().getSecuencia();
				for(RequestDetalleBienesDTO b : request.getBienes()){
					Bien bien = new Bien();
					bien.setIdDetalleAdquisicion(detalle.getId());
					bien.setColor(b.getColor());
					bien.setDescripcion(b.getDescripcion());
					bien.setSerie(b.getSerie());
					bien.setObservacion(b.getObservacion());
					bien.setModelo(modelos.stream().filter(m -> m.getId()==b.getIdModelo()).findFirst().get());
					bien.setCodigoPatrimonial(
						detalle.getCatalogo().getCodigo()
							.concat(String.format("%0d", secuencia)));
					secuencia++;
				};
				detalle.getCatalogo().setSecuencia(secuencia);
				
			});
		}
	}
	
}
