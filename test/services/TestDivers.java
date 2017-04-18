package services;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDivers {
	public static final int TEMPS_AVANT_DECONNEXION = 3600000; // 60 minutes
	
	public static void main(String[] args) throws ParseException, InterruptedException, UnknownHostException {
		System.out.println(bd.tools.MessagesTools.getIDAuteur("110"));
	}
}
