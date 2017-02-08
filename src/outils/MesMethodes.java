package outils;

import java.lang.Math;

public abstract class MesMethodes {

    /**
     * Retourne une chaine de caractères aléatoires (entre A-Z et 0-9).
     * @param taille : La taille de la String générée.
     */
    public static String getStringAleatoire(int taille) {
        String lettres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String chiffres = "0123456789";
        String resultat = "";

        int n_aleatoire;
        while (taille > 0) {
            /* On genere un premier nombre pour décider de prendre une lettre ou un chiffre. */
            if (MesMethodes.getIntAleatoire(0, 1) == 0) {
                // On récupère une lettre aléatoirement
                n_aleatoire = MesMethodes.getIntAleatoire(0, 25);
                resultat = String.format("%s%s", resultat, lettres.charAt(n_aleatoire));
            } else {
                // On récupère un chiffre aléatoirement
                n_aleatoire = MesMethodes.getIntAleatoire(0, 9);
                resultat = String.format("%s%s", resultat, chiffres.charAt(n_aleatoire));
            }
            taille--;
        }

        return resultat;
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
     * Retourne une chaine de caractères de taille 'taille' composée uniquement d'entiers.
     * @param taille: La taille de la chaine de caractères retournée.
     * @return Une chaine de caractères d'entiers de taille 'taille'.
     */
    public static String getIntAleatoireDeTaille(int taille){
        if (taille <= 0)
            return "";

        int nombre =  getIntAleatoire((int) Math.pow(10, taille - 1), (int) (Math.pow(10, taille)) - 1);
        return Integer.toString(nombre);
    }

    /**
     * Calcule et retourne le nombre de chiffres d'un entier positif passé en paramètre.
     * @param nombre : L'entier positif dont on souhaite avoir le nombre de chiffres.
     * @return Le nombre de chiffres de l'entier passé en paramètre.
     */
    public static int getNombreDeChiffres(int nombre) {
        if (nombre == 0)
                return 1;

        return (int) (Math.log10(Math.abs(nombre)) + 1);
    }
}