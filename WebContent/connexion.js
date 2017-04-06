function makeConnexionPanel() {
	var s = "<div id=\"div_connexion\">\n\
      			<h1> Connexion </h1>\n\
      			<form method=\"get\" action=\"javascript:(function(){return;})()\" onsubmit=\"javascript:connexionF(this)\">\n\
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


function connexionF(formulaire) {
	event.preventDefault();
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
	} else if (password.length > 64) {
		func_erreur("Password trop long");
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


