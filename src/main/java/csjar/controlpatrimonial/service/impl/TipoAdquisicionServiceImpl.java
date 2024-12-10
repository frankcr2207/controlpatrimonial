package csjar.controlpatrimonial.service.impl;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import csjar.controlpatrimonial.domain.TipoAdquisicion;
import csjar.controlpatrimonial.repository.TipoAdquisicionRepository;
import csjar.controlpatrimonial.service.TipoAdquisicionService;

@Service
public class TipoAdquisicionServiceImpl implements TipoAdquisicionService {

	private TipoAdquisicionRepository tipoAdquisicionRepository;
	
	public TipoAdquisicionServiceImpl(TipoAdquisicionRepository tipoAdquisicionRepository) {
		super();
		this.tipoAdquisicionRepository = tipoAdquisicionRepository;
	}

	@Override
	public TipoAdquisicion obtenerEntidad(Integer id) {
		TipoAdquisicion tipoAdquisicion = tipoAdquisicionRepository.findById(id).orElse(null);
		if(Objects.isNull(tipoAdquisicion))
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo Adquisicion no existe");
		return tipoAdquisicion;
	}

}
