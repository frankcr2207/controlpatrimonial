package csjar.controlpatrimonial.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.mysql.cj.util.StringUtils;

import csjar.controlpatrimonial.dto.RequestUsuarioDTO;
import csjar.controlpatrimonial.dto.ResponseUsuarioDTO;
import csjar.controlpatrimonial.entity.Perfil;
import csjar.controlpatrimonial.entity.Usuario;
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
	public ResponseUsuarioDTO buscarUsuario(Integer id) {
		Usuario usuario = this.usuarioRepository.findById(id)
			.orElse(null);
		if(Objects.isNull(usuario))
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se pudo encontrar usuario");
		return this.usuarioMapperService.toDTO(usuario);
	}
	
	@Override
	public List<ResponseUsuarioDTO> listarUsuarios() {
		List<Usuario> usuarios = this.usuarioRepository.findByPerfilIsNotNull();
		return this.usuarioMapperService.toDTO(usuarios);
	}
	
	@Override
	public ResponseUsuarioDTO buscarEmpleado(String dni) {
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
		Usuario usuario = this.usuarioRepository.findById(usuarioDTO.getId()).get();
		if(Objects.nonNull(usuarioDTO.getIdPerfil())){
			Perfil perfil = this.perfilService.obtenerEntidad(usuarioDTO.getIdPerfil());
			usuario.setPerfil(perfil);
			usuario.setClave(EncriptarClave.generar(""));
			usuario.setEstado("A");
			usuario.setLogin(usuarioDTO.getLogin());
			this.usuarioRepository.save(usuario);
		}
	}

	@Override
	public void modificarUsuario(RequestUsuarioDTO usuarioDTO) {
		Usuario usuario = this.usuarioRepository.findById(usuarioDTO.getId()).orElse(null);
		if(Objects.nonNull(usuarioDTO.getIdPerfil())){
			Perfil perfil = this.perfilService.obtenerEntidad(usuarioDTO.getIdPerfil());
			usuario.setPerfil(perfil);
			usuario.setEstado(usuarioDTO.getEstado());
			usuario.setCorreo(usuarioDTO.getCorreo());
			usuario.setLogin(usuarioDTO.getLogin().toUpperCase());
			if(StringUtils.isNullOrEmpty(usuario.getClave()))
				usuario.setClave(EncriptarClave.generar(""));
			this.usuarioRepository.save(usuario);
		}
	}

	@Override
	public void cambiarClave(RequestUsuarioDTO usuarioDTO) {
		Usuario usuario = this.usuarioRepository.findById(usuarioDTO.getId()).get();
		usuario.setClave(EncriptarClave.generar(usuarioDTO.getClave()));
	}

	@Override
	public String obtenerNombreSesion() {
		Usuario usuario = this.usuarioRepository.findByLogin(obtenerUsuario());
		return usuario.getNombres();
	}
	
	public String obtenerUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null) {
            String usuario = authentication.getName();
            return usuario;
        }
        
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "La sesión ha finalizado");
    }

	@Override
	public Usuario buscarPorLogin(String login) {
		return this.usuarioRepository.findByLogin(login);
	}

	@Override
	public List<Usuario> obtenerEntidades(List<Integer> ids) {
		return this.usuarioRepository.findByIdIn(ids);
	}

	@Override
	public Usuario obtenerEntidad(Integer id) {
		return this.usuarioRepository.findById(id).orElse(null);
	}

}
