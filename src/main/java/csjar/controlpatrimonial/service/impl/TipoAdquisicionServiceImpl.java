package csjar.controlpatrimonial.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import csjar.controlpatrimonial.domain.TipoAdquisicion;
import csjar.controlpatrimonial.dto.ResponseTipoAdquisicionDTO;
import csjar.controlpatrimonial.mapper.service.TipoAdquisicionMapperService;
import csjar.controlpatrimonial.repository.TipoAdquisicionRepository;
import csjar.controlpatrimonial.service.TipoAdquisicionService;

@Service
public class TipoAdquisicionServiceImpl implements TipoAdquisicionService {

	private TipoAdquisicionRepository repository;
	private TipoAdquisicionMapperService mapper;
	
	public TipoAdquisicionServiceImpl(TipoAdquisicionRepository repository, TipoAdquisicionMapperService mapper) {
		super();
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public TipoAdquisicion obtenerEntidad(Integer id) {
		TipoAdquisicion tipoAdquisicion = repository.findById(id).orElse(null);
		if(Objects.isNull(tipoAdquisicion))
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo Adquisicion no existe");
		return tipoAdquisicion;
	}

	@Override
	public List<ResponseTipoAdquisicionDTO> listarTiposAdquisicion() {
		List<TipoAdquisicion> tipos = this.repository.findAll();
		return this.mapper.toDTO(tipos);
	}

}
