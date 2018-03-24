package fr.rhaz.os;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
	public static <T> T[] removeFirst(T[] array) {
		return Arrays.copyOfRange(array, 1, array.length);
	}
	
	public static <T> List<T> list(T... array){
		return new ArrayList<>(Arrays.asList(array));
	}

}
