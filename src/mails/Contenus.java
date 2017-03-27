package mails;

public abstract class Contenus {
	public static final String TEST = String.format("%s%s%s%s%s",
			"You shouldn't see this mail in your mailbox.\n",
            "If that is the case, please quickly contact our admins at :\n",
            "twister.devteam@gmail.com\n",
            "Thank you for your effort.\n\n",
            
				"\tThe Twister team");

	public static final String RECUPERATION_MOT_DE_PASSE = String.format("%s%s%s%s",
			"Hello \n",
            "Here's your new password, keep-it somewhere safe :\n",
            "%s\n\n",
            	"\tThe Twister team");
	
	public static final String MESSAGE_BIENVENUE = String.format("%s%s",
			"Hello %s and welcome to Twister !\n\n",
            	"\tThe Twister team");
	
	public static final String MESSAGE_AU_REVOIR = String.format("%s%s%s%s%s%s",
			"Hello %s\n",
            "It's with much sadness that we have learned of the ",
            "desactivation of your Twister account. We hope it's just ",
            "temporary and that we'll see you soon !\n",
            "Sincerely,\n\n",
            	"\tThe Twister team");
}
