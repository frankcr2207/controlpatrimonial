package csjar.controlpatrimonial.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import csjar.controlpatrimonial.domain.DetalleAdquisicion;
import csjar.controlpatrimonial.repository.DetalleAdquisicionRepository;
import csjar.controlpatrimonial.service.DetalleAdquisicionService;

@Service
public class DetalleAdquisicionServiceImpl implements DetalleAdquisicionService {

	private DetalleAdquisicionRepository repository;
	
	public DetalleAdquisicionServiceImpl(DetalleAdquisicionRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public List<DetalleAdquisicion> obtenerEntidades(List<Integer> ids) {
		return this.repository.findAllById(ids);
	}

}
