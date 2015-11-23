/**
 * 
 */
package edu.depaul.se491.util;

/**
 * methods for working with parameters
 * @author Malik
 *
 */
public abstract class ParametersUtil {

	private ParametersUtil() {}
	
	
	public static Integer parseInt(String param) {
		Integer result = null;
		if (param == null)
			return result;
		
		try {
			result = Integer.parseInt(param);
			if (result < 0)
				result = null;
		} catch (NumberFormatException e) {}
		
		return result;
	}
	
	public static Long parseLong(String param) {
		Long result = null; 
		if (param == null)
			return result;
		
		try {
			result = Long.parseLong(param);
			if (result < 0)
				result = null;
		} catch (NumberFormatException e) {}
		
		return result;
	}
	
	public static Double parseDouble(String param) {
		Double result = null; 
		if (param == null)
			return result;
		
		try {
			result = Double.parseDouble(param);
			if (result < 0)
				result = null;
		} catch (NumberFormatException e) {}
		
		return result;
	}
	
	public static String validateString(String s) {
		return s == null? null : (s = s.trim()).isEmpty()? null : s;
	}
	
	public static boolean validateLength(String s, int lenMax) {
		return s == null? false : s.length() <= lenMax;
	}
	
	public static boolean validateLengths(String[] strings, int[] maxLengths) {
		if (strings == null || maxLengths == null)
			return false;
		if (strings.length != maxLengths.length)
			return false;
		
		for (int i=0; i < strings.length; i++) {
			if (validateLength(strings[i], maxLengths[i]) == false)
				return false;
		}
		return true;
	}
	
	public static boolean isAjaxRequest(String requestTypeParam) {
		return requestTypeParam != null && requestTypeParam.equals(Values.AJAX_REQUEST);
	}
	
	public static boolean isFirstUpdate(String firstUpdateParam) {
		return firstUpdateParam != null && firstUpdateParam.equals("true");
	}
}
