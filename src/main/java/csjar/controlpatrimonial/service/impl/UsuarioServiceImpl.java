package csjar.controlpatrimonial.service.impl;

import java.util.Objects;

import org.springframework.stereotype.Service;

import csjar.controlpatrimonial.domain.Perfil;
import csjar.controlpatrimonial.domain.Usuario;
import csjar.controlpatrimonial.dto.RequestUsuarioDTO;
import csjar.controlpatrimonial.dto.ResponseUsuarioDTO;
import csjar.controlpatrimonial.external.service.UsuarioExternalService;
import csjar.controlpatrimonial.mapper.service.UsuarioMapperService;
import csjar.controlpatrimonial.repository.UsuarioRepository;
import csjar.controlpatrimonial.service.PerfilService;
import csjar.controlpatrimonial.service.UsuarioService;
import csjar.controlpatrimonial.utils.EncriptarClave;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private UsuarioRepository usuarioRepository;
	private UsuarioExternalService usuarioExternalService;
	private PerfilService perfilService;
	private UsuarioMapperService usuarioMapperService;
	
	public UsuarioServiceImpl(UsuarioRepository usuarioRepository, UsuarioExternalService usuarioExternalService,
			PerfilService perfilService, UsuarioMapperService usuarioMapperService) {
		super();
		this.usuarioRepository = usuarioRepository;
		this.usuarioExternalService = usuarioExternalService;
		this.perfilService = perfilService;
		this.usuarioMapperService = usuarioMapperService;
	}

	@Override
	public ResponseUsuarioDTO buscarUsuario(String dni) {
		Usuario usuario = this.usuarioRepository.findByDni(dni);
		ResponseUsuarioDTO response = new ResponseUsuarioDTO();
		if(Objects.isNull(usuario)) {
			response = usuarioExternalService.buscarEmpleado(dni);
			usuario = this.usuarioMapperService.toEntity(response);
			usuario = usuarioRepository.save(usuario);
			response.setId(usuario.getId());
		}
		
		return this.usuarioMapperService.toDTO(usuario);
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
