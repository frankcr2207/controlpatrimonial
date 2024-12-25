package csjar.controlpatrimonial.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mysql.cj.util.StringUtils;

public class EncriptarClave {

	public static String generar(String clave) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
		return bCryptPasswordEncoder.encode(StringUtils.isNullOrEmpty(clave) ? "123456" : clave);
	} 
	
}
