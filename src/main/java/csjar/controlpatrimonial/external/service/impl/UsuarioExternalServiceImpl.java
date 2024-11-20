package csjar.controlpatrimonial.external.service.impl;

import org.springframework.web.client.RestTemplate;

import csjar.controlpatrimonial.dto.ResponseUsuarioDTO;
import csjar.controlpatrimonial.external.service.UsuarioExternalService;

public class UsuarioExternalServiceImpl implements UsuarioExternalService{

	private RestTemplate restTemplate;
	
	public UsuarioExternalServiceImpl(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	@Override
	public ResponseUsuarioDTO buscarServicioPersonal(String dni) {
		StringBuilder sb = new StringBuilder();
		sb.append("http://localhost:8081/personal/");
		sb.append(dni);
		return restTemplate.getForObject(sb.toString(), ResponseUsuarioDTO.class);
	}

}
