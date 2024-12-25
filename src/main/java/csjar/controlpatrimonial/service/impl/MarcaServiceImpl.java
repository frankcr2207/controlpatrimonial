package csjar.controlpatrimonial.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import csjar.controlpatrimonial.domain.Marca;
import csjar.controlpatrimonial.dto.ResponseMarcaDTO;
import csjar.controlpatrimonial.mapper.service.MarcaMapperService;
import csjar.controlpatrimonial.repository.MarcaRepository;
import csjar.controlpatrimonial.service.MarcaService;

@Service
public class MarcaServiceImpl implements MarcaService {

	private MarcaRepository repository;
	private MarcaMapperService mapper;
	
	public MarcaServiceImpl(MarcaRepository repository, MarcaMapperService mapper) {
		super();
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public List<ResponseMarcaDTO> listarMarcas() {
		List<Marca> marcas = this.repository.findAll();
		return this.mapper.toDTO(marcas);
	}

}
