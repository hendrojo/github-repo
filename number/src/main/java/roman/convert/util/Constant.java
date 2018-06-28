package roman.convert.util;

import java.util.HashMap;
import java.util.Map;

public class Constant {	
	public static String strPath = "/jsp";
	
	public static Map<String,Integer> mapRomanSymbol = new HashMap<String,Integer>();
	public static Map<String,String> mapAlienSymbol = new HashMap<String,String>();
	public static Map<String,Double> mapMetalCredits = new HashMap<String,Double>();
	
	public static String strNoResult = "I have no idea what you are talking about";
	public static String strResMuch = "[roman] is [value]";
	public static String strResMany = "[roman] [metal] is [value] Credits";
}
