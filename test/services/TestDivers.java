package services;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDivers {
	public static final int TEMPS_AVANT_DECONNEXION = 3600000; // 60 minutes
	
	public static void main(String[] args) throws ParseException, InterruptedException, UnknownHostException {
		System.out.println(services.amis.ListerAmis.listerAmis("R59EB2DP22A87X57R18B3UK01WCSO522", "502", "0", "10"));
		//clef=R59EB2DP22A87X57R18B3UK01WCSO522&id_utilisateur=502&index_debut=0&nombre_demandes=10
	}
}
