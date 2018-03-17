package fr.rhaz.os;

import java.util.ArrayList;
import java.util.Arrays;

public class Utils {
	public static <T> T[] removeFirst(T[] array) {
		return Arrays.copyOfRange(array, 1, array.length);
	}
	
	public static <T> ArrayList<T> list(T... array){
		return new ArrayList<>(Arrays.asList(array));
	}
}
