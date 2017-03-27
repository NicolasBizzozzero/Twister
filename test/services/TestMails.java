package services;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class TestMails {
	public static void main(String[] args) throws MessagingException {
		String pseudo = "NicolasBi";
		String motDePasse = "CeciEs12fSecurisé";
		String mail = "nicolasbizzozzero@gmail.com";
		
		System.out.println(services.utilisateur.CreationUtilisateur.creationUtilisateur(pseudo, motDePasse, mail, null, null, null));
		System.out.println(services.mail.RecuperationMotDePasse.recuperationMotDePasse(mail));
	}
}
