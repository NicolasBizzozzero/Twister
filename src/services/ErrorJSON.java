package services;

import org.json.JSONException;
import org.json.JSONObject;


public class ErrorJSON {
	
	/**
	 * Genere un objet JSON en cas d'erreur du service.
	 * @param message : Le message d'erreur
	 * @param codeErreur : Le code d'erreur
	 * @return : L'objet JSON
	 */
	public static JSONObject serviceRefused(String message, int codeErreur){
		try {
			JSONObject retour = new JSONObject();
			retour.put("errorcode", codeErreur);
			retour.put("message", message);
			return retour;
		} catch (JSONException e) {
			return null;
		}
	}

	/**
	 * Genere un objet JSON en cas de succes du service.
	 * @return : L'objet JSON
	 */
	public static JSONObject serviceAccepted(){
		try {
			JSONObject retour = new JSONObject();
			return retour;
		} catch (JSONException e) {
			return null;
		}
	}
}
