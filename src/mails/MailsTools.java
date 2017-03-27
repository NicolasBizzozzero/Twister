package mails;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailsTools {
	public static final String MAIL_ENVOYEUR = "twister.devteam@gmail.com";
	public static final String MOT_DE_PASSE_ENVOYEUR = "nz5QcHdRLNHCPpjK";
	
	
	/**
	 * Envoie un email a un destinataire avec l'adresse mail
	 * de la Team Twister.
	 * Merci au site www.javatpoint.com pour ses explications ! 
	 * http://www.javatpoint.com/example-of-sending-email-using-java-mail-api
	 * @param destinataire : Le destinataire du mail
	 * @param enTete : L'en-tete du mail
	 * @param contenu : Le contenu du mail
	 * @throws MessagingException
	 */
	public static void envoyerMail(String destinataire, String enTete, String contenu) throws MessagingException {  
        // On modifie les proprietes du mail
        Properties props = new Properties();    
        props.put("mail.smtp.host", "smtp.gmail.com");    
        props.put("mail.smtp.socketFactory.port", "465");    
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");    
        props.put("mail.smtp.auth", "true");    
        props.put("mail.smtp.port", "465");
        
        // On se connecte  
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {    
	         protected PasswordAuthentication getPasswordAuthentication() {    
		         return new PasswordAuthentication(MAIL_ENVOYEUR, MOT_DE_PASSE_ENVOYEUR);
	         }
	    });
        
        // On ecrit le message
    	MimeMessage message = new MimeMessage(session);    
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinataire));    
        message.setSubject(enTete);    
        message.setText(contenu);    
        
        // On envoie le message  
        Transport.send(message);    
	}
	
	
	/**
	 * Envoie un mail de test a un destinataire.
	 * @param destinataire
	 * @throws MessagingException
	 */
	public static void envoyerMailTest(String destinataire) throws MessagingException {
		String enTete = mails.EnTetes.TEST;
		String contenu = mails.Contenus.TEST;
		envoyerMail(destinataire, enTete, contenu);
	}
	
	
	/**
	 * Envoie un mail de recuperation de mot de passe a un destinataire.
	 * TODO: Le mot de passe envoye est en clair, il faudrait trouver
	 * une autre alternative.
	 * @param destinataire
	 * @param motDePasseEnClair
	 * @throws MessagingException
	 */
	public static void envoyerMailRecuperationMotDePasse(String destinataire, String motDePasseEnClair) throws MessagingException {
		String enTete = mails.EnTetes.RECUPERATION_MOT_DE_PASSE;
		String contenu = String.format(mails.Contenus.RECUPERATION_MOT_DE_PASSE, motDePasseEnClair);
		envoyerMail(destinataire, enTete, contenu);
	}
	
	
	/**
	 * Envoie un mail de bienvenue a un destinataire.
	 * @param destinataire
	 * @param pseudo
	 * @throws MessagingException
	 */
	public static void envoyerMailBienvenue(String destinataire, String pseudo) throws MessagingException {
		String enTete = mails.EnTetes.MESSAGE_BIENVENUE;
		String contenu = String.format(mails.Contenus.MESSAGE_BIENVENUE, pseudo);
		envoyerMail(destinataire, enTete, contenu);
	}
	
	
	/**
	 * Envoie un mail d'au revoir a un destinataire.
	 * @param destinataire
	 * @param pseudo
	 * @throws MessagingException
	 */
	public static void envoyerMailAuRevoir(String destinataire, String pseudo) throws MessagingException {
		String enTete = mails.EnTetes.MESSAGE_AU_REVOIR;
		String contenu = String.format(mails.Contenus.MESSAGE_AU_REVOIR, pseudo);
		envoyerMail(destinataire, enTete, contenu);
	}
}
