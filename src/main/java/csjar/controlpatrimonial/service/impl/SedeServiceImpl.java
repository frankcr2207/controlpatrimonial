package csjar.controlpatrimonial.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import csjar.controlpatrimonial.dto.ResponseSedeDTO;
import csjar.controlpatrimonial.entity.Sede;
import csjar.controlpatrimonial.mapper.service.SedeMapperService;
import csjar.controlpatrimonial.repository.SedeRepository;
import csjar.controlpatrimonial.service.SedeService;

@Service
public class SedeServiceImpl implements SedeService {

	private SedeRepository repository;
	private SedeMapperService mapper;
	
	public SedeServiceImpl(SedeRepository repository, SedeMapperService mapper) {
		super();
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public List<ResponseSedeDTO> obtenerSedes() {
		List<Sede> sedes = this.repository.findAll();
		return this.mapper.toDTO(sedes);
	}

}
