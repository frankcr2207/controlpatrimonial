package csjar.controlpatrimonial.constants;

public class GeneralConstants {

	private GeneralConstants() {}
	
	public static final String SERVER_TEMP = "D://TEMP/";
	
	public static final String ACTA_ESTADO_REGISTRADO = "REGISTRADO";
	public static final String ACTA_ESTADO_NOTIFICADO = "NOTIFICADO";
	public static final String ACTA_TIPO_ASIGNACION = "ASIGNACIÓN";
	public static final String ACTA_TIPO_DEVOLUCION = "DEVOLUCIÓN";
	public static final String ACTA_PLANTILLA_EXCEL = "plantilla_acta_v2";
	
	public static final String BIEN_ESTADO_INGRESADO = "INGRESADO";
	public static final String BIEN_ESTADO_RETORNADO = "RETORNADOO";
	public static final String BIEN_ESTADO_MANTENIMIENTO = "MANTENIMIENTO";
	public static final String BIEN_ESTADO_ASIGNADO = "ASIGNADO";
	public static final String BIEN_ESTADO_DEVUELTO = "DEVUELTO";
	public static final String BIEN_OBSERVACION_NUEVO_INGRESO = "NUEVO INGRESO";
	
	public static final String ADQUISICION_ESTADO_REGISTRADO = "REGISTRADO";
	public static final String ADQUISICION_ESTADO_REGULARIZADO = "REGULARIZADO";
	public static final String ADQUISICION_ESTADO_GENERADO = "GENERADO";
	
	public static final String NOMENCLATURA_ACTA_PDF = "CP_ACTA_";
	public static final String EXTENSION_EXCEL = ".xlsx";
	public static final String EXTENSION_PDF = ".pdf";
	
	public static final String NOTIFICACION_REMITENTE = "Corte Superior de Justicia de Arequipa - Control Patrimonial <pj.csj.arequipa@gmail.com>";
	public static final String NOTIFICACION_ASUNTO = "Control Patrimonial - Remisión de acta";
	public static final String MS_CONTROL_PATRIMONIAL_VERIFICAR = "http://172.28.3.11:8080/acta/validar";
	public static final String NOTIFICACION_CUERPO = "<html><head><meta charset='utf-8'><title></title></head><body><br>" +
		"Estimado(a) <nombres>, por el presente se remite acta de movimiento y traslado N° <numeroActa>, para su respectiva conformidad con firma digital; descargue el documento, "+
		"firme con el certificado digital y finalmente haga click en el enlace líneas abajo para subir el acta firmada.<br><br>" +
		"Haga click en: <enlace> <br>";
	
}
