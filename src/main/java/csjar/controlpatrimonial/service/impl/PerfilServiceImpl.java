package csjar.controlpatrimonial.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import csjar.controlpatrimonial.domain.Perfil;
import csjar.controlpatrimonial.repository.PerfilRepository;
import csjar.controlpatrimonial.service.PerfilService;

@Service
public class PerfilServiceImpl implements PerfilService {

	private PerfilRepository perfilRepository;
	
	public PerfilServiceImpl(PerfilRepository perfilRepository) {
		super();
		this.perfilRepository = perfilRepository;
	}

	@Override
	public Perfil buscarPerfil(Integer id) {
		return perfilRepository.findById(id).orElseThrow(() ->
			new ResponseStatusException(HttpStatus.NO_CONTENT, "No se encontró perfil"));
	}

}
