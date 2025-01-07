package csjar.controlpatrimonial.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import csjar.controlpatrimonial.dto.ResponseActaDTO;
import csjar.controlpatrimonial.service.ActaService;
import csjar.controlpatrimonial.service.UsuarioService;

@Controller
public class ControlPatrimonialController {
	
	private UsuarioService usuarioService;
	private ActaService actaService;
	
    public ControlPatrimonialController(UsuarioService usuarioService, ActaService actaService) {
		super();
		this.usuarioService = usuarioService;
		this.actaService = actaService;
	}

	@GetMapping("/")
    public String loginForm() {
        return "login";
    }
    
    @GetMapping("/principal")
    public String formPrincipal(Model model) {
    	model.addAttribute("sesion", this.usuarioService.obtenerNombreSesion());
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
	
	@GetMapping("/formAsignacion")
	public String formAsignacion(){
		return "vistas/asignacion";
	}
	
	@GetMapping("/formTrazabilidad")
	public String formTrazabilidad(){
		return "vistas/trazabilidad";
	}
	
	@GetMapping("/acta/validar")
	public String formAsignacion(Model model, @RequestParam Integer code, @RequestParam String token) throws Exception{
		ResponseActaDTO response = this.actaService.validarActa(code, token);
		model.addAttribute("dto", response);
		return "vistas/validar";
	}

}
