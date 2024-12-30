package csjar.controlpatrimonial.external.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import csjar.controlpatrimonial.dto.RequestEmailDTO;
import csjar.controlpatrimonial.external.service.NotificacionExternalService;

@Service
public class NotificacionExternalServiceImpl implements NotificacionExternalService {

	private RestTemplate restTemplate;
	
	public NotificacionExternalServiceImpl(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	@Override
	public boolean enviarEmal(RequestEmailDTO requestEmailDTO) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("http://localhost:8082/enviarEmail/");
			return restTemplate.postForObject(sb.toString(), requestEmailDTO,  Boolean.class);
		}
		catch(HttpClientErrorException.NotFound e) {
            String errorResponse = e.getResponseBodyAsString(); 
            String mensajeDeError = extractMessageFromErrorResponse(errorResponse);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, mensajeDeError);
		}
		catch(Exception ex) {
			throw new RuntimeException();
		}
	}
	
	private String extractMessageFromErrorResponse(String errorResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(errorResponse);
            return rootNode.path("message").asText(); 
        } catch (Exception e) {
            return "Error desconocido"; 
        }
    }

}
