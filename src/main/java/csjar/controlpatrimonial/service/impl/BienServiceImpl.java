package csjar.controlpatrimonial.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import csjar.controlpatrimonial.domain.Adquisicion;
import csjar.controlpatrimonial.domain.Bien;
import csjar.controlpatrimonial.domain.Catalogo;
import csjar.controlpatrimonial.domain.Modelo;
import csjar.controlpatrimonial.dto.RequestBienesDTO;
import csjar.controlpatrimonial.dto.RequestDetalleBienesDTO;
import csjar.controlpatrimonial.dto.ResponseBienesDTO;
import csjar.controlpatrimonial.repository.BienRepository;
import csjar.controlpatrimonial.service.AdquisicionService;
import csjar.controlpatrimonial.service.BienService;
import csjar.controlpatrimonial.service.CatalogoService;
import csjar.controlpatrimonial.service.ModeloService;
import csjar.controlpatrimonial.utils.CollectorUtils;

@Service
public class BienServiceImpl implements BienService {

	private BienRepository repository;
	private ModeloService modeloService;
	private CatalogoService catalogoService;
	private AdquisicionService adquisicionService;
	
	public BienServiceImpl(BienRepository repository, ModeloService modeloService, CatalogoService catalogoService,
			AdquisicionService adquisicionService) {
		super();
		this.repository = repository;
		this.modeloService = modeloService;
		this.catalogoService = catalogoService;
		this.adquisicionService = adquisicionService;
	}

	@Transactional
	@Override
	public void generarBienes(RequestBienesDTO requestBienesDTO) {
		
		if(CollectorUtils.isValidate(requestBienesDTO.getBienes())) {
			
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
				bien.setObservacion(b.getObservacion());
				bien.setModelo(mapModelos.get(b.getIdModelo()));
				bien.setCodigoPatrimonial(catalogo.getCodigo().concat(String.format("%04d", secuencia)));
				bien.setIdCatalogo(b.getIdCatalogo());
				bienes.add(bien);
				secuencia++;
				catalogo.setSecuencia(secuencia);
				
			});
			
			this.repository.saveAll(bienes);
			
			Adquisicion adquisicion = this.adquisicionService.obtenerEntidad(requestBienesDTO.getIdAdquisicion());
			adquisicion.setEstado("G");
			adquisicionService.actualizarEntidad(adquisicion);
		}
		
	}

	@Override
	public List<ResponseBienesDTO> obtenerBienes(Integer idAdquisicion) {
		List<Bien> bienes = this.repository.findByIdAdquisicion(idAdquisicion);
		List<ResponseBienesDTO> responseBienes = new ArrayList<>();
		
		List<Integer> idsCatalogo = bienes.stream() 
	            .map(Bien::getIdCatalogo).distinct().collect(Collectors.toList());
		
		Map<Integer, String> mapCatalogos = this.catalogoService.obtenerCatalogo(idsCatalogo)
				.stream().collect(Collectors.toMap(Catalogo::getId, Catalogo::getDenominacion));
		
		bienes.stream().forEach(b -> {
			ResponseBienesDTO bien = new ResponseBienesDTO();
			bien.setCatalogo(mapCatalogos.get(b.getIdCatalogo()));
			bien.setCodigoPatrinonial(b.getCodigoPatrimonial());
			bien.setDescripcion(b.getDescripcion());
			responseBienes.add(bien);
		});
		
		return responseBienes;
	}

}
