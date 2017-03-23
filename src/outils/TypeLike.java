package outils;

public enum TypeLike {
	POO,
	JE_DETESTE,
	J_AIME_PAS,
	BOF,
	J_AIME,
	J_ADORE;
	
	public int valeur;
	
	static {
		POO.valeur = 0;
		JE_DETESTE.valeur = 1;
		J_AIME_PAS.valeur = 2;
		BOF.valeur = 3;
		J_AIME.valeur = 4;
		J_ADORE.valeur = 5;
	}
}
