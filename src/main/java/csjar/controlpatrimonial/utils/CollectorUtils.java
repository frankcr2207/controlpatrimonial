package csjar.controlpatrimonial.utils;

import java.util.List;

public class CollectorUtils {

	public static <T> boolean isValidate(List<T> lista) {
		return lista != null && !lista.isEmpty();
	}
	
}
