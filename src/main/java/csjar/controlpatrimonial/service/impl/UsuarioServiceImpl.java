package csjar.controlpatrimonial.service.impl;

import java.util.Objects;

import csjar.controlpatrimonial.domain.Perfil;
import csjar.controlpatrimonial.domain.Usuario;
import csjar.controlpatrimonial.dto.RequestUsuarioDTO;
import csjar.controlpatrimonial.dto.ResponseUsuarioDTO;
import csjar.controlpatrimonial.external.service.UsuarioExternalService;
import csjar.controlpatrimonial.repository.UsuarioRepository;
import csjar.controlpatrimonial.service.PerfilService;
import csjar.controlpatrimonial.service.UsuarioService;
import csjar.controlpatrimonial.utils.EncriptarClave;

public class UsuarioServiceImpl implements UsuarioService {

	private UsuarioRepository usuarioRepository;
	private UsuarioExternalService usuarioExternalService;
	private PerfilService perfilService;
	
	public UsuarioServiceImpl(UsuarioRepository usuarioRepository, UsuarioExternalService usuarioExternalService,
			PerfilService perfilService) {
		super();
		this.usuarioRepository = usuarioRepository;
		this.usuarioExternalService = usuarioExternalService;
		this.perfilService = perfilService;
	}

	@Override
	public ResponseUsuarioDTO buscarUsuario(String dni) {
		Usuario usuario = this.usuarioRepository.findByDni(dni);
		ResponseUsuarioDTO response = new ResponseUsuarioDTO();
		if(Objects.isNull(usuario)) {
			response = usuarioExternalService.buscarServicioPersonal(dni);
			usuario = usuarioRepository.save(usuario);
			response.setId(usuario.getId());
			return response;
		}
		else {
			return response;
		}
	}

	@Override
	public void crearUsuario(RequestUsuarioDTO usuarioDTO) {
		Usuario usuario = this.usuarioRepository.findByDni(usuarioDTO.getDni());
		if(Objects.nonNull(usuarioDTO.getIdperfil())){
			Perfil perfil = this.perfilService.buscarPerfil(usuarioDTO.getIdperfil());
			usuario.setPerfil(perfil);
			usuario.setClave(EncriptarClave.generar());
		}
	}

}
