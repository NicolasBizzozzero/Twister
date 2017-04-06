/**
 * Génère le code HTML du panneau de connexion.
 */
function makeConnexionPanel() {
	var s = "<div id=\"div_connexion\">\n\
      			<h1> Connexion </h1>\n\
      			<form method=\"post\" onsubmit=\"return connexionF(this)\">\n\
          			<div class=\"ids\">\n\
            				<label for=\" pseudo\"> Pseudo </label>\n\
           				 <input type=\"text\" name=\"pseudo\" id=\"pseudo\" placeholder=\"name\" required autocomplete=\"off\"/>\n\
          			</div>\n\
          			<div class =\"ids\" >\n\
            				<label for= \"mdp\">Mot de passe</label>\n\
            				<input type=\"password\" name=\"mdp\" id=\"mdp\" required />\n\
          			</div>\n\
         			<div class =\"boutons\" >\n\
          				<input type=\"submit\" name=\"connexion\" id=\"connexion\" value=\"Se connecter\"/>\n\
          			</div>\n\
          			<div class=\"links\">\n\
           				<div id=\"link1\">Mot de passe oublié</div>\n\
           				<div id=\"link2\" onClick=\"javascript:makeEnregistrementPanel()\">Pas encore inscrit</div>\n\
          			</div>\n\
      			</form>\n\
   		    </div>";

	$("body").html(s);
}


/**
 * Recupère les valeurs du formulaire et vérifie que l'utilisateur peut se
 * connecter.
 */
function connexionF(formulaire) {
	formulaire.submit(function(event) {
            event.preventDefault();
    });
	var login = formulaire.pseudo.value;
	var password = formulaire.mdp.value;
	var ok = verif_formulaire_connexion(login, password);
	if (ok) {
		connect(login, password);
		return true;
	} else {
		return false;
	}
}


/**
 * Vérifie la validité des paramètres passés par l'utilisateur.
 */
function verif_formulaire_connexion(login, password) {
	if (login.length < 1) {
		func_erreur("Login trop court");
		return false;
	}

	if (login.length > 32) {
        func_erreur("Login trop long");
		return false;
	}

	if (password.length < 8) {
		func_erreur("Mot de passe trop court");
		return false;
	}

	if (password.length > 64) {
        func_erreur("Mot de passe trop long");
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
 * Envoie une requête au serveur pour connecter un utilisateur si on dispose
 * d'une connexion. Dans le cas contraire, on le connecte avec une fausse clef
 * et un faux ID.
 */
function connect(login, password) {
	console.log("Connect " + login + ", " + password);
	var id_user = 1;
	var key = 8546515;
	if (!env.noConnection) {
		$.ajax({type: "GET",
                url: "services/authentification/login",
                data: "pseudo=" + login + "&motDePasse=" + password,
                dataType: "json",
                success: function(res) {
                    reponseConnection(res);
                },
                error: function(xhr, status, err) {
                    func_erreur(status);
                }
            });
	} else {
		reponseConnection({"key": key,
                           "id": id_user,
                           "login": login,
                           "follows": env.follows[id_user]});
	}
}


/**
 * Gère la réponse du serveur et construit le panneau du menu principal avec de
 * vrais messages si l'utilisateur est connecté, ou avec de faux si on est en
 * mode développement.
 */
function reponseConnection(rep) {
    if (rep.erreur == undefined) {
        console.log("pas erreur connexion");
        env.key = rep.key;
        env.id = rep.id;
        env.login = rep.login;
        env.follows[rep.id] = new Set();
        rep.follows.forEach(function(valeur) {
            env.follows[rep.id].add(valeur);
        });
        if (env.noConnection) {
            env.follows[rep.id] = new Set();
            rep.follows.forEach(function(valeur) {
                env.follows[rep.id].add(valeur);
            });
        }
        makeMainPanel(-1, env.login, 4);
    } else {
        func_erreur(rep.erreur);
    }
}
