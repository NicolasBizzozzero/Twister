function makeEnregistrementPanel() {
	var s = "<div id=\"div_inscription\">\n\
      			<h1> Inscription </h1>\n\
    			<form method=\"post\"  action=\"\" onSubmit=\"return enregistrementF(this)\">\n\
          			<div class=\"ids_haut\">\n\
            				<label for=\" prenom\"> Prénom </label>\n\
            				<div>\n\
            					<input type=\"text\" name=\"prenom\" id=\"prenom\"/>\n\
            				</div>\n\
          			</div>\n\
          			<div class=\"ids_haut\">\n\
            				<label for= \"nom\"> Nom</label>\n\
            				<div>\n\
            					<input type=\"text\" name=\"nom\" id=\"nom\"/>\n\
           				</div>\n\
          			</div>\n\
          			<div class =\"ids\" >\n\
            				<label for= \"anniversaire\">Anniversaire</label>\n\
            				<input type=\"text\" name=\"anniversaire\" id=\"anniversaire\"/>\n\
          			</div>\n\
          			<div class =\"ids\" >\n\
            				<label for= \"pseudo\"> Pseudo*</label>\n\
          				<input type=\"text\" name=\"pseudo\" id=\"pseudo\"/>\n\
          			</div>\n\
          			<div class =\"ids\" >\n\
            				<label for= \"email\"> Email*</label>\n\
          				<input type=\"text\" name=\"email\" id=\"email\"/>\n\
          			</div>\n\
          			<div class =\"ids\" >\n\
            				<label for= \"mdp\">Mot de passe*</label>\n\
          				<input type=\"password\" name=\"mdp\" id=\"mdp\"/>\n\
         			</div>\n\
          			<div class =\"ids\" >\n\
            				<label for= \"mdp2\"> Répéter le mot de passe</label>\n\
          				<input type=\"password\" name=\"mdp2\" id=\"mdp2\"/>\n\
          			</div>\n\
          			<div class =\"boutons\" >\n\
          				<input type=\"submit\" name=\"enregistrer\" id=\"enregistrer\" value=\"Inscription\"/>\n\
            				<input type=\"button\" name=\"annuler\" id=\"annuler\" value=\"Annuler\" onClick=\"javascript:makeConnexionPanel()\"/>\n\
          			</div>\n\
         			<div class=\"champs_ob\">\n\
           				<text> * Champs obligatoires</text>\n\
          			</div>\n\
      			</form>\n\
    		</div>";
	$("body").html(s);
}
function enregistrementF(formulaire) {
	var prenom=formulaire.prenom.value;
	var nom=formulaire.nom.value;
	var email=formulaire.email.value;
	var login = formulaire.pseudo.value;
	var password1 = formulaire.mdp.value;
	var password2 = formulaire.mdp2.value;
	var ok = verif_formulaire_enregistrement(login, password1,password2,prenom,nom,email);
	if (ok) {
		console.log("entree dans ok=true");
		connect(login, password1);
		return true;
	} else {
		console.log("entree dans ok=false");
		return false;
		//func_erreur("Couple 'Login-Password' invalide.");
	}
}

function verif_formulaire_enregistrement(login, password1,password2,prenom,nom,email) {
	var re_email=new RegExp("[a-z]+[@][a-z]+[\.][a-z]+");
	if (login.length ==0) {
		func_erreur("Saisissez un identifiant.");
		return false;
	}

	if (login.length > 20) {
		func_erreur("Votre identifiant doit posséder moins de 20 caratères.");
		return false;
	}
	if (email.length==0) {
		func_erreur("Saisissez une adresse email.");
		return false;
	}
	if (!re_email.exec(email)) {
		func_erreur("L'adresse email saisie n'est pas corecte.");
		return false;
	}
	if (password1 != password2 ) {
		func_erreur("Erreur de mot de passe. Vous n'avez pas saisi deux fois les mêmes caractères.");
		return false;
	}

	if (password1.length < 8) {
		func_erreur("Votre mot de passe doit posséder au moins 8 caractères.");
		return false;
	}

	if (password1.length > 20) {
		func_erreur("Votre mot de passe doit contenir moins de 20 caratères.");
		return false;
	}
	return true;
}

function func_erreur(message) {
	var s = "<div id=\"msg_err_connexion\">" + message + "</div>";
	var old_mess = $("#msg_err_connexion");

	// Cas où il n'y avait pas de message d'erreur
	if (old_mess.length == 0) {
		$("form").prepend(s);
	} 

	// Cas où il y'avait déjà un message d'erreur
	else {
		old_mess.replaceWith(s);
	}
}

function reponseEnregistrement(rep) {
	if (rep.erreur == undefined) {
		env.key = rep.key;
		env.id = rep.id;
		env.login = rep.login;
		env.follows = new Set();
		
		for (follower=0; follower < rep.follows.length; follower++) {
			env.follows.add(rep.follows[follower]);
		}

		if (env.noConnection) {
			follows[rep.id] = new Set();
			for (follower=0; follower < rep.follows.length; follower++) {
				follows[rep.id].add(rep.follows[follower]);
			}
		}
		makeMainPanel(-1,env.login,4);
	} else {
		func_erreur(rep.erreur);
	}
}

function enregistrement(login, password) {
	console.log("Connect " + login + ", " + password);
	var id_user = 78;
	var key = 8546515;
	if (!env.noConnection) {
		// TODO: REMPLIR QUAND ON POURRA PARLER AU SERVEUR
	} else {
		reponseConnection({"key": key, "id": id_user, "login": login, "follows": [2, 4]});
	}
}
