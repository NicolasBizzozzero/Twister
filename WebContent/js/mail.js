function demanderMail() {
	var email = prompt("Entrez votre email pour recuperer votre mot de passe");

	if (email != null) {
		envoyerMailRecuperationMotDePasse(email);
	}
}


function envoyerMailRecuperationMotDePasse(email) {
    $.ajax({type: "GET",
        url: url_site + "/services/mail/recuperationMotDePasse",
        data: "email=" + email,
        dataType: "text",
        success: function(res) {
        	envoyerMailRecuperationMotDePasseReponse(res);
        },
        error: function(xhr, status, err) {
            func_erreur(status + ": " + err);
            lock_liste_messages = false;
        }
    });
}


function envoyerMailRecuperationMotDePasseReponse(res) {
	func_erreur("Votre mot de passe vous a été envoyé à l'adresse : " + rep.mail);
}
