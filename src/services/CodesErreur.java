package services;

public abstract class CodesErreur {
	public static final int ERREUR_ARGUMENTS = -1;
	public static final int ERREUR_CONNEXION_BD_MYSQL = 0;
	public static final int ERREUR_UTILISATEUR_EXISTANT = 1;
	public static final int ERREUR_UTILISATEUR_INEXISTANT = 2;
	public static final int ERREUR_MDP_NON_SECURISE = 3;
	public static final int ERREUR_MDP = 4;
	public static final int ERREUR_DECONNEXION = 5;
	public static final int HOTE_INCONNU = 6;
	public static final int ERREUR_DEJA_SUIVI = 7;
	public static final int ERREUR_ID_IDENTIQUES = 8;
	public static final int ERREUR_NE_SUIVAIT_PAS = 9;
	public static final int ERREUR_UTILISATEUR_DECONNECTE = 10;
	public static final int ERREUR_MDP_TROP_COURT = 11;
	public static final int ERREUR_MDP_TROP_LONG = 12;
	public static final int ERREUR_HASHAGE = 13;
	public static final int ERREUR_INDEX_INVALIDE = 14;
	public static final int ERREUR_UTILISATEUR_DEJA_CONNECTE = 15;
	public static final int ERREUR_JSON = 100;
	public static final int ERREUR_SQL = 1000;
	public static final int ERREUR_JAVA = 10000;
	public static final int ERREUR_INCONNUE = 100000;

}
