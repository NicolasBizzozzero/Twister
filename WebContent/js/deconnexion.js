/**
 * Deconnecte un utilisateur.
 */
function deconnexion() {
    var clef = env.clef
    var ok = verif_params_deconnexion(clef);
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


