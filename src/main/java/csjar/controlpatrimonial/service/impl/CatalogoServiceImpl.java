package csjar.controlpatrimonial.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import csjar.controlpatrimonial.domain.Catalogo;
import csjar.controlpatrimonial.repository.CatalogoRepository;
import csjar.controlpatrimonial.service.CatalogoService;

@Service
public class CatalogoServiceImpl implements CatalogoService {

	private CatalogoRepository catalogoRepository;
	
	public CatalogoServiceImpl(CatalogoRepository catalogoRepository) {
		super();
		this.catalogoRepository = catalogoRepository;
	}

	@Override
	public List<Catalogo> obtenerCatalogo(List<Integer> ids) {
		return this.catalogoRepository.findByIdIn(ids);
	}

}
