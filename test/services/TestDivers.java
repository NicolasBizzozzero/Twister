package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDivers {
	public static void main(String[] args) throws ParseException {
		Date maDate = new Date();
		String maDateStr = maDate.toString();
		System.out.println(maDateStr);
		
		@SuppressWarnings("deprecation")
		Date maNouvelleDate = new Date(maDateStr);
		String maNouvelleDateStr = maNouvelleDate.toString();
		System.out.println(maNouvelleDateStr);
	}
}
