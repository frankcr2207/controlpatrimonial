package csjar.controlpatrimonial.external.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import csjar.controlpatrimonial.dto.ResponseUsuarioDTO;
import csjar.controlpatrimonial.external.service.UsuarioExternalService;

@Service
public class UsuarioExternalServiceImpl implements UsuarioExternalService{

	private RestTemplate restTemplate;
	
	public UsuarioExternalServiceImpl(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	@Override
	public ResponseUsuarioDTO buscarEmpleado(String dni) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("http://localhost:8081/personal/");
			sb.append(dni);
			return restTemplate.getForObject(sb.toString(), ResponseUsuarioDTO.class);
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
