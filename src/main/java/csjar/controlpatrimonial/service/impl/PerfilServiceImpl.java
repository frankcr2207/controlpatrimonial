package csjar.controlpatrimonial.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import csjar.controlpatrimonial.domain.Perfil;
import csjar.controlpatrimonial.dto.RequestPerfilDTO;
import csjar.controlpatrimonial.dto.ResponsePerfilDTO;
import csjar.controlpatrimonial.mapper.service.PerfilMapperService;
import csjar.controlpatrimonial.repository.PerfilRepository;
import csjar.controlpatrimonial.service.PerfilService;

@Service
public class PerfilServiceImpl implements PerfilService {

	private PerfilRepository perfilRepository;
	private PerfilMapperService perfilMapperService; 
	
	public PerfilServiceImpl(PerfilRepository perfilRepository, PerfilMapperService perfilMapperService) {
		super();
		this.perfilRepository = perfilRepository;
		this.perfilMapperService = perfilMapperService;
	}

	@Override
	public ResponsePerfilDTO buscarPerfil(Integer id) {
		Perfil perfil = obtenerEntidad(id);
		return this.perfilMapperService.toDTO(perfil);
	}

	@Override
	public List<ResponsePerfilDTO> listarPerfiles() {
		List<Perfil> perfiles = this.perfilRepository.findAll();
		return this.perfilMapperService.toDTO(perfiles);
	}

	@Override
	public void crearPerfil(RequestPerfilDTO requestPerfilDTO) {
		Perfil perfil = new Perfil();
		perfil.setDescripcion(requestPerfilDTO.getDescripcion());
		perfil.setEstado("ACTIVO");
		perfilRepository.save(perfil);
	}

	@Override
	public void modificarPerfil(RequestPerfilDTO requestPerfilDTO) {
		Perfil perfil = obtenerEntidad(requestPerfilDTO.getId());
		perfil.setDescripcion(requestPerfilDTO.getDescripcion());
		perfil.setEstado(requestPerfilDTO.getEstado());
		perfilRepository.save(perfil);
	}

	@Override
	public Perfil obtenerEntidad(Integer id) {
		return perfilRepository.findById(id).orElseThrow(() ->
			new ResponseStatusException(HttpStatus.NO_CONTENT, "No se encontró perfil"));
	}

}
