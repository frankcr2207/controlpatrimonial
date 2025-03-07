package csjar.controlpatrimonial.service;

import java.io.OutputStream;
import java.util.Map;

public interface ExcelService {

	void generarDocumento(OutputStream outStream, String templateName, Map<String, Object> data);
}
