package soundfun.util;

public class StringUtils {
	public static int countLines(String str){
		String[] lines = str.split("\r\n|\r|\n");
		return lines.length;
	}
}
