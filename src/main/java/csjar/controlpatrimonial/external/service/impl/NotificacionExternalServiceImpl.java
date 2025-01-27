package csjar.controlpatrimonial.external.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import csjar.controlpatrimonial.dto.RequestEmailDTO;
import csjar.controlpatrimonial.external.service.NotificacionExternalService;

@Service
public class NotificacionExternalServiceImpl implements NotificacionExternalService {

	@Value("${url.servicio.notificacion}")
	private String URL_SERVICIO_NOTIFICACION;
	
	@Value("${servicio.notificacion.enviarEmail}")
	private String METHOD_ENVIAR_EMAIL;
	
	private RestTemplate restTemplate;
	
	public NotificacionExternalServiceImpl(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	@Override
	public boolean enviarEmail(RequestEmailDTO requestEmailDTO) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(URL_SERVICIO_NOTIFICACION + METHOD_ENVIAR_EMAIL);
			return restTemplate.postForObject(sb.toString(), requestEmailDTO,  Boolean.class);
		}
		catch(HttpClientErrorException.NotFound e) {
            return false;
		}
		catch(Exception ex) {
			return false;
		}
	}
	
}
