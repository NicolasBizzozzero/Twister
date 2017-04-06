/**
 * Génère le code HTML du panneau d"enregistrement.
 */
function makeEnregistrementPanel() {
	var s = "<div id=\"div_inscription\">\n\
      			<h1> Inscription </h1>\n\
    			<form method=\"get\"  action=\"javascript:(function(){return;})()\" onSubmit=\"javascript:enregistrementF(this)\">\n\
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


/**
 * Recupère les valeurs du formulaire et vérifie que l'utilisateur peut se
 * créer un compte.
 */
function enregistrementF(formulaire) {
    console.log("On va enregistrer l'utilisateur");
	var prenom = formulaire.prenom.value;
	var nom = formulaire.nom.value;
	var email = formulaire.email.value;
	var login = formulaire.pseudo.value;
	var password1 = formulaire.mdp.value;
	var password2 = formulaire.mdp2.value;
    var anniversaire = formulaire.anniversaire.value;
	var ok = verif_formulaire_enregistrement(login, password1, password2, prenom, nom, email, anniversaire);
    console.log("On a checké tous les paramètres");
	if (ok) {
        console.log("Enregistrement réussit, on lance une requête");
		enregistrement(login, password1, email, prenom, nom, anniversaire);
		return true;
	} else {
        console.log("Echec de l'enregistrement");
		return false;
	}
}


/**
 * Vérifie la validité des paramètres passés par l'utilisateur.
 */
function verif_formulaire_enregistrement(login, password1, password2, prenom, nom, email, anniversaire) {
    // On vérifie la validité du login
	if (login.length < 1) {
		func_erreur("Login trop court.");
		return false;
	} else if (login.length > 32) {
		func_erreur("Login trop long.");
		return false;
	}

    // On vérifie la validité du mot de passe
    if (password1 != password2 ) {
        func_erreur("Erreur de mot de passe. Vous n'avez pas saisi deux fois les mêmes caractères.");
        return false;
    } else if (password1.length < 8) {
        func_erreur("Votre mot de passe doit posséder au moins 8 caractères.");
        return false;
    } else if (password1.length > 64) {
        func_erreur("Votre mot de passe doit contenir plus de 64 caratères.");
        return false;
    } else if (!mdpAssezSecurise(password1)) {
        func_erreur("Votre mot de passe doit contenir au moins une majuscule, une minuscule et un chiffre.");
        return false;
    }

    // On vérifie la validité du prénom
    if (prenom.length < 1) {
        func_erreur("Prenom trop court.");
        return false;
    } else if (prenom.length > 64) {
        func_erreur("Prenom trop long.");
        return false;
    }

    // On vérifie la validité du nom
    if (nom.length < 1) {
        func_erreur("Nom trop court.");
        return false;
    } else if (nom.length > 64) {
        func_erreur("Nom trop long.");
        return false;
    }

    // On vérifie la validité de l'adresse mail
    var re_email = new RegExp("[a-z]+[@][a-z]+[\.][a-z]+");
	if (email.length < 1) {
		func_erreur("Adresse email trop courte.");
		return false;
	} else if (email.length > 64) {
        func_erreur("Adresse email trop long.");
        return false;
    } else if (!re_email.exec(email)) {
		func_erreur("L'adresse email saisie n'est pas correcte.");
		return false;
	}

    // TODO: On vérifie la validité de l'anniversaire

	return true;
}


/**
 * Vérifie qu'un mot de passe est assez sécurisé.
 * Il doit contenir au moins:
 * - Une majuscule
 * - Une minuscule
 * - Un chiffre
 */
function mdpAssezSecurise(mdp) {
    //TODO: Remplir cette fonction
    return true;
}


/**
 * Ajoute un message d'erreur dans la div prévue à cet effet.
 * @param {string} message - Le message à écrire dans la div.
 */
function func_erreur(message) {
	var s = "<div id=\"msg_err_enregistrement\">" + message + "</div>";
	var old_mess = $("#msg_err_enregistrement");

	// Cas où il n'y avait pas de message d'erreur
	if (old_mess.length == 0) {
		$("form").prepend(s);
	} 

	// Cas où il y'avait déjà un message d'erreur
	else {
		old_mess.replaceWith(s);
	}
}


/**
 * Gère la réponse du serveur et construit le panneau du menu principal avec de
 * vrais messages si l'utilisateur est enregistré, ou avec de faux si on est en
 * mode développement.
 */
function reponseEnregistrement(rep) {
  console.log(rep);
	if (rep.errorcode == undefined) {
      console.log("Je make le connexion pannel");
      makeConnexionPanel();
      func_erreur("Enregistrement effectué avec succès. Veuillez vous connecter.");
	} else {
      console.log(rep.message + ", ERROR_CODE: " + rep.errorcode);
		  func_erreur(rep.message);
	}
}


/**
 * Effectue une requête auprès du serveur pour enregistrer un utilisateur si on
 * a une connexion, on fait semblant de l'enregistrer si on est en mode
 * developpement.
 */
function enregistrement(pseudo, password, email, prenom, nom, anniversaire) {
	if (!env.noConnection) {
    var url_site = "http://li328.lip6.fr:8280/gr2_Bourmaud_Bizzozzero"
    $.ajax({type: "GET",
            url: url_site + "/services/utilisateur/creationUtilisateur",
            data: "pseudo=" + pseudo + "&motDePasse=" + password + "&email=" + email + "&prenom=" + prenom + "&nom=" + nom + "&anniversaire=" + anniversaire,
            success: function(data) {
                console.log("Y'a eu du succes !")
                reponseEnregistrement(data);
            },
            error: function(xhr, status, err) {
                console.log("Y'a eu de l'erreur ...")
                console.log(status)
                console.log(err)
                func_erreur(status + ": " + err);
            }
        });
	} else {
		reponseEnregistrement({});
	}
}
