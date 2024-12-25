package csjar.controlpatrimonial.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import csjar.controlpatrimonial.domain.Catalogo;
import csjar.controlpatrimonial.domain.GrupoClase;
import csjar.controlpatrimonial.dto.ResponseCatalogoDTO;
import csjar.controlpatrimonial.mapper.service.CatalogoMapperService;
import csjar.controlpatrimonial.repository.CatalogoRepository;
import csjar.controlpatrimonial.service.CatalogoService;
import csjar.controlpatrimonial.service.GrupoClaseService;

@Service
public class CatalogoServiceImpl implements CatalogoService {

	private CatalogoRepository catalogoRepository;
	private GrupoClaseService grupoClaseService;
	private CatalogoMapperService catalogoMapperService;
	
	public CatalogoServiceImpl(CatalogoRepository catalogoRepository, GrupoClaseService grupoClaseService,
			CatalogoMapperService catalogoMapperService) {
		super();
		this.catalogoRepository = catalogoRepository;
		this.grupoClaseService = grupoClaseService;
		this.catalogoMapperService = catalogoMapperService;
	}

	@Override
	public List<Catalogo> obtenerCatalogo(List<Integer> ids) {
		return this.catalogoRepository.findByIdIn(ids);
	}

	@Override
	public List<ResponseCatalogoDTO> obtenerCatalogo(Integer idGrupo, Integer idClase) {
		GrupoClase grupoClase = this.grupoClaseService.obtenerEntidad(idGrupo, idClase);
		List<Catalogo> catalogo = grupoClase.getCatalogo();
		return this.catalogoMapperService.toDTO(catalogo);
	}

}
