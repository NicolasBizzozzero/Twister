package services;

public abstract class CodesErreur {
	public static final int ERREUR_ARGUMENTS = -1;
	public static final int ERREUR_UTILISATEUR_EXISTANT = 1;
	public static final int ERREUR_UTILISATEUR_INEXISTANT = 2;
	public static final int ERREUR_MDP_FAIBLE = 3;
	public static final int ERREUR_MDP = 4;
	public static final int ERREUR_DECONNEXION = 5;
	public static final int HOTE_INCONNU = 6;
	public static final int ERREUR_DEJA_AMIS = 7;
	public static final int ERREUR_ID_IDENTIQUES = 8;
	public static final int ERREUR_JSON = 100;
	public static final int ERREUR_SQL = 1000;
	public static final int ERREUR_JAVA = 10000;
	public static final int ERREUR_INCONNUE = 100000;
}
