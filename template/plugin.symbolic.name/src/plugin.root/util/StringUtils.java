package ${plugin.root}.util;

import java.util.Arrays;

public class StringUtils {
	
	public static boolean isValuePresent(String[] strs, String value) {
		return Arrays.stream(strs)
				.filter(str -> str != null && str.equals(value))
				.findFirst()
				.isPresent();
	}
}
