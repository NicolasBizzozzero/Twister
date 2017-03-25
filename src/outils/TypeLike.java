package outils;

public enum TypeLike {
	JE_DETESTE,
	J_AIME_PAS,
	BOF,
	J_AIME,
	J_ADORE;

	public int valeur;
	
	static {
		JE_DETESTE.valeur = 0;
		J_AIME_PAS.valeur = 1;
		BOF.valeur = 2;
		J_AIME.valeur = 3;
		J_ADORE.valeur = 4;
	}
}
