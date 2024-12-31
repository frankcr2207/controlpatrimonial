
package csjar.controlpatrimonial.service.impl;

import org.springframework.stereotype.Service;

import csjar.controlpatrimonial.domain.Area;
import csjar.controlpatrimonial.repository.AreaRepository;
import csjar.controlpatrimonial.service.AreaService;

@Service
public class AreaServiceImpl implements AreaService {

	private AreaRepository repository;
	
	public AreaServiceImpl(AreaRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Area obtenerEntidad(Integer id) {
		return this.repository.findById(id).get();
	}

}
