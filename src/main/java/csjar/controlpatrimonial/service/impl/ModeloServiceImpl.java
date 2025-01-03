package csjar.controlpatrimonial.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import csjar.controlpatrimonial.entity.Modelo;
import csjar.controlpatrimonial.repository.ModeloRepository;
import csjar.controlpatrimonial.service.ModeloService;

@Service
public class ModeloServiceImpl implements ModeloService {

	private ModeloRepository repository;
	
	public ModeloServiceImpl(ModeloRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public List<Modelo> obtenerEntidades(List<Integer> ids) {
		return this.repository.findByIdIn(ids);
	}

}
