package csjar.controlpatrimonial.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import csjar.controlpatrimonial.domain.Acta;
import csjar.controlpatrimonial.domain.Bien;
import csjar.controlpatrimonial.domain.Usuario;
import csjar.controlpatrimonial.dto.RequestActaDTO;
import csjar.controlpatrimonial.dto.RequestEmailDTO;
import csjar.controlpatrimonial.external.service.NotificacionExternalService;
import csjar.controlpatrimonial.repository.ActaRepository;
import csjar.controlpatrimonial.service.ActaService;
import csjar.controlpatrimonial.service.BienService;
import csjar.controlpatrimonial.service.UsuarioService;

@Service
public class ActaServiceImpl implements ActaService {

	private ActaRepository repository;
	private UsuarioService usuarioService;
	private BienService bienService;
	private NotificacionExternalService notificacionExternalService;
	
	@Override
	public void guardarActa(RequestActaDTO requestActaDTO) {
		Usuario usuario = usuarioService.buscarRntidad(obtenerUsuario());
		Acta actaAnterior = this.repository.findActaWithMaxNumeroByYear(LocalDateTime.now().getYear());
		Acta acta = new Acta();
		acta.setIdEmpleado(requestActaDTO.getIdEmpleado());
		acta.setEstado("R");
		acta.setNumero(Objects.nonNull(actaAnterior) ? actaAnterior.getNumero() + 1 : 1);
		acta.setIdUsuario(usuario.getId());
		acta.setIdArea(requestActaDTO.getIdArea());
		acta.setTipo(requestActaDTO.getTipo());
		List<Bien> bienes = new ArrayList<>();
		requestActaDTO.getBienes().stream().forEach(b -> {
			Bien bien = bienService.obtenerEntidad(b.getCodigoPatrimonial());
			bien.setEstadoConservacion(b.getEstadoConservacion());
			bien.setObservacion(b.getObservacion());
			bienes.add(bien);
		});
		acta.setBienes(bienes);
		repository.save(acta);
		
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
	public void generarActa(OutputStream outStream, String templateName, Map<String, Object> data) {
		String pathTemplateName = ("/reports/").concat(templateName).concat(".xlsx");
		try(InputStream input = this.getClass().getResourceAsStream(pathTemplateName)) {
		
            Context context = new Context();
            for (Entry<String, Object> element : data.entrySet()) { 
            	context.putVar(element.getKey(), element.getValue());
			}
            JxlsHelper.getInstance().processTemplate(input, outStream, context);

		} catch (Exception exception) {

		} finally {
			closeAndFlushOutput(outStream);
		}
	}
	
	private void closeAndFlushOutput(OutputStream outStream) {
		try {
			outStream.flush();
			outStream.close();
		} catch (IOException exception) {

		}
	}
	
	private void enviarEmail() {
		RequestEmailDTO requestEmailDTO = new RequestEmailDTO();
		this.notificacionExternalService.enviarEmail(requestEmailDTO);
	}
	
}
