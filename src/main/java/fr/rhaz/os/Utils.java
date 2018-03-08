package fr.rhaz.os;

import java.util.Arrays;

public class Utils {
	public static <T> T[] removeFirst(T[] array) {
		return Arrays.copyOfRange(array, 1, array.length);
	}
}
