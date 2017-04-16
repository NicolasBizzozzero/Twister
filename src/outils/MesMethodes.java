package outils;

import java.lang.Math;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.GregorianCalendar;

public abstract class MesMethodes {

	
    /**
     * Retourne une chaine de caracteres aleatoires (entre A-Z et 0-9).
     * @param taille : La taille de la String generee.
     */
    public static String getStringAleatoire(int taille) {
        String lettres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String chiffres = "0123456789";
        String resultat = "";

        int n_aleatoire;
        while (taille > 0) {
            /* On genere un premier nombre pour decider de prendre une lettre ou un chiffre. */
            if (getBooleanAleatoire() == true) {
                // On recupere une lettre aleatoirement
                n_aleatoire = MesMethodes.getIntAleatoire(0, 25);
                resultat = String.format("%s%s", resultat, lettres.charAt(n_aleatoire));
            } else {
                // On recupere un chiffre aleatoirement
                n_aleatoire = MesMethodes.getIntAleatoire(0, 9);
                resultat = String.format("%s%s", resultat, chiffres.charAt(n_aleatoire));
            }
            taille--;
        }
        return resultat;
    }
    
    
    /**
     * Retourne aleatoiremet un boolean
     * @return Un boolean choisit aleatoirement.
     */
    public static boolean getBooleanAleatoire() {
    	if (getIntAleatoire(0, 1) == 0) {
    		return false;
    	} else {
    		return true;
    	}
    }

    
    /**
     * Retourne un int compris entre minimum inclu et maximum inclu.
     * @param minimum : L'int minimum possible, inclusif.
     * @param maximum : L'int maximum possible, inclusif.
     */
    public static int getIntAleatoire(int minimum, int maximum) {
        return (int) ((Math.random() * (maximum + 1)) + minimum);
    }

    
    /**
     * Retourne une chaine de caracteres de taille 'taille' composee uniquement d'entiers.
     * @param taille: La taille de la chaine de caracteres retournee.
     * @return Une chaine de caracteres d'entiers de taille 'taille'.
     */
    public static String getIntAleatoireDeTaille(int taille){
        if (taille <= 0)
            return "";

        int nombre = getIntAleatoire((int) Math.pow(10, taille - 1), (int) (Math.pow(10, taille)) - 1);
        return Integer.toString(nombre);
    }

    
    /**
     * Calcule et retourne le nombre de chiffres d'un entier positif passse en parametre.
     * @param nombre : L'entier positif dont on souhaite avoir le nombre de chiffres.
     * @return Le nombre de chiffres de l'entier passe en parametre.
     */
    public static int getNombreDeChiffres(int nombre) {
        if (nombre == 0)
                return 1;

        return (int) (Math.log10(Math.abs(nombre)) + 1);
    }
    
    
	/**
	 * Utilise l'algorithme SHA-512 pour hasher un mot de passe.
	 * @param motDePasse: Le mot de passe en clair a hasher.
	 * @return : Une string hashee correspondant au mot de passe passe en parametre.
	 * @throws NoSuchAlgorithmException 
	 */
 	public static String hasherMotDePasse(String motDePasse) throws NoSuchAlgorithmException {
         MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
         messageDigest.update(motDePasse.getBytes());
         motDePasse = new String(messageDigest.digest());
 		return motDePasse;
 	}
 	
 	
	/**
	 * Retourne Le temps d'inactivite d'un utilisateur
	 * en fonction de sa date de derniere activite.
	 * @param derniereActivite : Date de la derniere activite de l'utilisateur
	 * @return En millisecondes, le temps d'inactivite de l'utilisateur
	 */
	public static long getTempsInactivite(Date derniereActivite) {
		Date maintenant = new GregorianCalendar().getTime();
		return maintenant.getTime() - derniereActivite.getTime();
	}
}