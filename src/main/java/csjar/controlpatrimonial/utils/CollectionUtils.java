package csjar.controlpatrimonial.utils;

import java.util.List;

public class CollectionUtils {

	public static <T> boolean isValidate(List<T> lista) {
		return lista != null && !lista.isEmpty();
	}
	
}
