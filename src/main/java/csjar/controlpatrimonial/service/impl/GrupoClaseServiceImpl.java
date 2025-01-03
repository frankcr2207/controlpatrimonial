package csjar.controlpatrimonial.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import csjar.controlpatrimonial.dto.ResponseClaseDTO;
import csjar.controlpatrimonial.dto.ResponseGrupoDTO;
import csjar.controlpatrimonial.entity.Clase;
import csjar.controlpatrimonial.entity.Grupo;
import csjar.controlpatrimonial.entity.GrupoClase;
import csjar.controlpatrimonial.mapper.service.ClaseMapperService;
import csjar.controlpatrimonial.mapper.service.GrupoMapperService;
import csjar.controlpatrimonial.repository.ClaseRepository;
import csjar.controlpatrimonial.repository.GrupoClaseRepository;
import csjar.controlpatrimonial.repository.GrupoRepository;
import csjar.controlpatrimonial.service.GrupoClaseService;

@Service
public class GrupoClaseServiceImpl implements GrupoClaseService {

	private GrupoRepository grupoRepository;
	private GrupoMapperService grupoMapper;
	private ClaseRepository claseRepository;
	private ClaseMapperService claseMapper;
	private GrupoClaseRepository grupoClaseRepository;
	
	public GrupoClaseServiceImpl(GrupoRepository grupoRepository, GrupoMapperService grupoMapper,
			ClaseRepository claseRepository, ClaseMapperService claseMapper,
			GrupoClaseRepository grupoClaseRepository) {
		super();
		this.grupoRepository = grupoRepository;
		this.grupoMapper = grupoMapper;
		this.claseRepository = claseRepository;
		this.claseMapper = claseMapper;
		this.grupoClaseRepository = grupoClaseRepository;
	}

	@Override
	public List<ResponseGrupoDTO> listarGrupos() {
		List<Grupo> grupos = this.grupoRepository.findAll();
		return this.grupoMapper.toDTO(grupos);
	}

	@Override
	public List<ResponseClaseDTO> listarClases() {
		List<Clase> grupos = this.claseRepository.findAll();
		return this.claseMapper.toDTO(grupos);
	}

	@Override
	public GrupoClase obtenerEntidad(Integer idGrupo, Integer idClase) {
		GrupoClase grupoClase = this.grupoClaseRepository.findByIdGrupoAndIdClase(idGrupo, idClase);
		if(Objects.isNull(grupoClase))
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró cat+alogo asociado a grupo y clase");
		return grupoClase;
	}

}
