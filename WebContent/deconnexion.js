/**
 * Deconnecte un utilisateur.
 */
function deconnexion() {
    event.preventDefault();

    var clef = env.key
    var ok = verif_formulaire_connexion(clef);
    if (ok) {
        disconnect(clef);
        return true;
    } else {
        return false;
    }
}


/**
 * Vérifie la validité des paramètres passés par l'utilisateur.
 */
function verif_params_deconnexion(clef) {
    // On verifie la validite de la clef
    if (clef.length > 32) {
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
 * Gère la réponse du serveur et construit le panneau de connexion si la
 * deconnexion s'est bien effectuée.
 */
function reponseDeconnection(rep) {
    console.log(rep);
    if (rep.errorcode == undefined) {
        makeConnexionPanel();
    } else {
        console.log(rep.message + ", ERROR_CODE: " + rep.errorcode);
        func_erreur(rep.message);
    }
}


/**
 * Envoie une requête au serveur pour deconnecter un utilisateur.
 */
function disconnect(clef) {
    console.log("Disconnect: " + clef);
    if (!env.noConnection) {
        $.ajax({type: "GET",
                url: url_site + "/services/authentification/logout",
                data: "clef=" + clef,
                dataType: "json",
                success: function(res) {
                    reponseDeconnection(res);
                },
                error: function(xhr, status, err) {
                    func_erreur(status + ": " + err);
                }
            });
    }
}


