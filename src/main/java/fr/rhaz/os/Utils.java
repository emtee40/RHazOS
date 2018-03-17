package fr.rhaz.os;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
	public static <T> T[] removeFirst(T[] array) {
		return Arrays.copyOfRange(array, 1, array.length);
	}
	
	public static <T> ArrayList<T> list(T... array){
		return new ArrayList<>(Arrays.asList(array));
	}
	
	public static String join(String join, String... strs) {
		String result = "";
		for(String str:strs)
			result += (str.equals(strs[0]))? str:(str + join);
		return result;
	}
	
	public static String join(String join, List<String> strs) {
		String result = "";
		for(String str:strs)
			result += str + join;
		return result.substring(0, result.length()-join.length());
	}
}
