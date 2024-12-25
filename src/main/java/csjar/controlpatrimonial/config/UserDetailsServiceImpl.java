package csjar.controlpatrimonial.config;

import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import csjar.controlpatrimonial.domain.Usuario;
import csjar.controlpatrimonial.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLoginAndEstadoAndPerfilIsNotNull(username, "A");
        if(Objects.isNull(usuario))
              throw new UsernameNotFoundException("Usuario no encontrado");

        return new User(usuario.getLogin(), usuario.getClave(), usuario.getAuthorities());
    }

}
