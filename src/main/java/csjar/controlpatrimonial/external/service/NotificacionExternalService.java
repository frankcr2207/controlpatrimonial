package csjar.controlpatrimonial.external.service;

import csjar.controlpatrimonial.dto.RequestEmailDTO;

public interface NotificacionExternalService {

	boolean enviarEmail(RequestEmailDTO requestEmailDTO);
	
}
