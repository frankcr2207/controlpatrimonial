package csjar.controlpatrimonial.controller;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import csjar.controlpatrimonial.service.UsuarioService;

@Controller
public class ControlPatrimonialController {
	
	private UsuarioService usuarioService;
	
    public ControlPatrimonialController(UsuarioService usuarioService) {
		super();
		this.usuarioService = usuarioService;
	}

	@GetMapping("/")
    public String loginForm() {
        return "login";
    }
    
    @GetMapping("/principal")
    public String formPrincipal(Model model) {
    	model.addAttribute("sesion", this.usuarioService.obtenerNombreSesion());
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            authorities.forEach(authority -> {
                System.out.println("Rol: " + authority.getAuthority());
            });
        }
        return "vistas/principal";
    }
    
	@GetMapping("/formRegistro")
	public String formRegistro(){
		return "vistas/registro";
	}
	
	@GetMapping("/formUsuarios")
	public String formUsuarios(){
		return "vistas/usuarios";
	}
	
	@GetMapping("/formGeneracion")
	public String formGeneracion(){
		return "vistas/generacion";
	}

	/*@GetMapping(value = "/abc")
	public String getToken() throws NoSuchAlgorithmException {
		String userId = "12345";
        String secretKey = "mi_clave_secreta";

        // Generar el hash
        String generatedHash = generateHash(userId, secretKey);
        return ("Hash generado: " + generatedHash);
	}
	
	@GetMapping(value = "/validar")
	public String validar(@RequestParam String code, @RequestParam String token) throws NoSuchAlgorithmException {
		String userId = code;
        String secretKey = "mi_clave_secreta";

        // El hash recibido de la URL
        String receivedHash = token;  // Este sería el hash que se pasa en la URL

        // Verificar el hash
        boolean isValid = verifyHash(receivedHash, userId, secretKey);
        
        if (isValid) {
            return ("La solicitud es legítima.");
        } else {
            return ("La solicitud no es válida.");
        }
	}
	
	public static String generateHash(String data, String secretKey) throws NoSuchAlgorithmException {
        // Concatenar los datos sensibles con una clave secreta
        String input = data + secretKey;

        // Crear una instancia de MessageDigest con SHA-256
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        // Generar el hash a partir de la entrada
        byte[] hashBytes = digest.digest(input.getBytes());

        // Convertir los bytes del hash en una cadena en Base64
        return Base64.getEncoder().encodeToString(hashBytes);
    }
	
	public static boolean verifyHash(String receivedHash, String data, String secretKey) throws NoSuchAlgorithmException {
        // Generar el hash esperado usando los mismos datos y la misma clave secreta
		String generatedHash = generateHash(data, secretKey);

        // Compara el hash recibido con el generado
        return generatedHash.equals(receivedHash);
    }
	*/
}
