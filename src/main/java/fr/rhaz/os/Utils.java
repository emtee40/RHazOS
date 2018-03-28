package fr.rhaz.os;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	public static <T> T[] removeFirst(T[] array) {
		return Arrays.copyOfRange(array, 1, array.length);
	}
	
	public static <T> List<T> list(T... array){
		return new ArrayList<>(Arrays.asList(array));
	}

	public static String join(final String join, final String... strs) {
        String result = "";
        for (final String str : strs) 
            result += (str.equals(strs[0]) ? str : (str + join));
        return result;
    }
    
    public static String join(final String join, final List<String> strs) {
        String result = "";
        for (final String str : strs) 
            result = result + str + join;
        return result.substring(0, result.length() - join.length());
    }
    
    public static String date(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}
    
    public static String[] getArgs(String line) {
		Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(line);
		List<String> list = new ArrayList<String>();
		while (m.find()) {
			String msg = m.group(1);
			if(msg.startsWith("\"") && msg.endsWith("\""))
				msg = msg.substring(1, msg.length()-1);
			list.add(msg);
		}
		return list.toArray(new String[list.size()]);
	}
}
