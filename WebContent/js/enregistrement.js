/**
 * Génère le code HTML du panneau d'enregistrement.
 */
function makeEnregistrementPanel() {
    $("body").load("html/enregistrement.html");
}


/**
 * Génère le code HTML du panneau d'enregistrement avec un message.
 */
function makeEnregistrementPanelAvecMessage(message) {
    $("body").load("html/enregistrement.html", function() {
        func_erreur(message);
    });
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
		enregistrement(login, hasher(password1), email, prenom, nom, anniversaire);
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
    } else if (password1.length > 128) {
        func_erreur("Votre mot de passe doit contenir plus de 64 caratères.");
        return false;
    }

    // On vérifie la validité du prénom
    if (prenom.length > 64) {
        func_erreur("Prenom trop long.");
        return false;
    }

    // On vérifie la validité du nom
    if (nom.length > 64) {
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
 * Gère la réponse du serveur et construit le panneau de connexion si
 * l'utilisateur est enregistré, ou avec un message d'erreur sinon.
 */
function reponseEnregistrement(rep) {
  console.log(rep);
	if (rep.errorcode == undefined) {
      console.log("Je make le connexion pannel");
      makeConnexionPanelAvecMessage("Enregistrement effectué avec succès. Veuillez vous connecter.");
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
    $.ajax({type: "GET",
            url: url_site + "/services/utilisateur/creationUtilisateur",
            data: "pseudo=" + pseudo + "&motDePasse=" + password + "&email=" + email + "&prenom=" + prenom + "&nom=" + nom + "&anniversaire=" + anniversaire,
            dataType: "json",
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
}
