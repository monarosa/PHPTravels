package utilities;

public class Utill {
	public static String onlyNum(String str) {
		str.substring(str.indexOf("de")+3);
		
	return	str.substring(str.indexOf("de")+3);
	}
	
	public static String onlyMoney(String str) {
		String str1=str.substring(str.indexOf(" ")+1);
		String str2=str1.replace(",", "");
		if(str.contains(".")) return str2.substring(0, str2.length()-1);
		
	return	str2;
	}

}
