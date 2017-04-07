/**
 * Génère le code HTML du panneau de connexion.
 */
function makeConnexionPanel() {
	$("body").load("html/connexion.html");
}


/**
 * Génère le code HTML du panneau de connexion avec un message.
 */
function makeConnexionPanelAvecMessage(message) {
    $("body").load("html/connexion.html", function() {
        func_erreur(message);
    });
}


/**
 * Recupère les valeurs du formulaire et vérifie que l'utilisateur peut se
 * connecter.
 */
function connexionF(formulaire) {
	event.preventDefault();
	var login = formulaire.pseudo.value;
	var password = formulaire.mdp.value;
	var ok = verif_formulaire_connexion(login, password);
	if (ok) {
		connect(login, hasher(password));
		return true;
	} else {
		return false;
	}
}


/**
 * Vérifie la validité des paramètres passés par l'utilisateur.
 */
function verif_formulaire_connexion(login, password) {
	// On verifie la validite du pseudo
	if (login.length == 0) {
		func_erreur("Login obligatoire");
		return false;
	} else if (login.length > 32) {
		func_erreur("Login trop long");
		return false;
	}

	// On verifie la validite du mot de passe
	if (password.length == 0) {
		func_erreur("Password obligatoire");
		return false;
	} else if (password.length > 128) {
		func_erreur("Password trop long");
		return false;
	}
	return true;
}


/**
 * Ajoute un message d'erreur dans la div prévue à cet effet.
 * @param {string} message - Le message à écrire dans la div.
 */
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


/**
 * Gère la réponse du serveur et construit le panneau du menu principal avec de
 * vrais messages si l'utilisateur est enregistré, ou avec de faux si on est en
 * mode développement.
 */
function reponseConnection(rep) {
	if (rep.errorcode == undefined) {
		env.key = rep.clef;
		env.id = rep.id;
		env.login = rep.pseudo;
		env.follows[rep.id] = new Set();
		rep.suivis.forEach(function(valeur) {
			env.follows[rep.id].add(valeur);
		});

		if (env.noConnection) {
			env.follows[rep.id] = new Set();
			rep.suivis.forEach(function(valeur) {
				env.follows[rep.id].add(valeur);
			});
		}
		makeMainPanel(-1, env.login, 4);
	} else {
        console.log(rep.message + ", ERROR_CODE: " + rep.errorcode);
		func_erreur(rep.message);
	}
}


/**
 * Envoie une requête au serveur pour connecter un utilisateur si on dispose
 * d'une connexion. Dans le cas contraire, on le connecte avec une fausse clef
 * et un faux ID.
 */
function connect(login, password) {
	console.log("Connect " + login + ", " + password);
	if (!env.noConnection) {
		$.ajax({type: "GET",
			    url: url_site + "/services/authentification/login",
			    data: "pseudo=" + login + "&motDePasse=" + password,
			    dataType: "json",
			    success: function(res) {
			    	reponseConnection(res);
			    },
			    error: function(xhr, status, err) {
			    	func_erreur(status + ": " + err);
			    }
			});
	} else {
		var id_user = 1;
		var key = 8546515;
		reponseConnection({"clef": key,
			               "id": id_user,
			               "pseudo": login,
			               "suivis": env.follows[id_user]});
	}
}


