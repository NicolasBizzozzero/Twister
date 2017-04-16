/**
 * Construit un objet Commentaire.
 */
function Commentaire(id, auteur, texte, date){
    this.id = id;
    this.auteur = auteur;
    this.texte = texte;
    // TODO: Parser la date en la recuperant comme ca
    // this.date = date;["$date"]
    this.date = date;
}


/**
 * Recupère et construit le code HTML correspondant à un commentaire.
 */
Commentaire.prototype.getHtml = function() {
    var retour;
    var variables_format = {id_commentaire: this.id,
                            contenu_commentaire: this.texte,
                            auteur_id: this.auteur.id_auteur,
                            auteur_pseudo: this.auteur.pseudo_auteur,
                            date_commentaire: this.date};

    $.ajax({url: "html/commentaire.html",
            success: function(res) {
                retour = Mustache.render(res, variables_format);
            },
            async: false
        });

    return retour;
}


function afficheCommentaires(id_message) {
    $.ajax({type:"GET",
            url: url_site + "/services/commentaire/listerCommentaires",
            data: "clef=" + env.clef + "&id_message=" + id_message,
            dataType:"text",
            success: function(res) {
                afficheCommentairesReponse(res, id_message);
            },
            error: function(xhr, status, err) {
                func_erreur(status + ": " + err);
            }
        });
}


function afficheCommentairesReponse(rep, id_message) {
    if (rep.errorcode == undefined) {
        var listeCommentaires = (JSON.parse(rep, revival)).commentaires;

        for(var i=0; i < listeCommentaires.length; i++) {
            var commentaire = listeCommentaires[i];
            $("#message_" + id_message + " .commentaires").prepend(commentaire.getHtml());
        }

        $("#commentaire_" + listeCommentaires.length).appear();
        $.force_appear();
    } else {
        console.log(rep.message + ", ERROR_CODE: " + rep.errorcode);
        func_erreur(rep.message);
    }
}


function newCommentaire(id) {	
    // On recupère le texte du message à poster
    var texte = $("textarea[NAME=nv_commentaire]").val();

    // On vide le texte qui était dedans
    $("textarea[NAME=nv_commentaire]").val('');

    $.ajax({type: "GET",
            url: url_site + "/services/commentaire/ajouterCommentaire",
            data: "clef=" + env.clef + "&contenu=" + texte + "&id_message=" + id,
            dataType: "json",
            success: function(res) {
                newCommentaireReponse(id, res);
            },
            error: function(xhr, status, err) {
                func_erreur(status + ": " + err);
            }
        });
}


function newCommentaireReponse(id, rep) {
    if (rep.errorcode == undefined) {
        refreshCommentaires(id);
    } else {
        console.log(rep.message + ", ERROR_CODE: " + rep.errorcode);
        func_erreur(rep.message);
    }
}


function refreshCommentaires(id_message) {
    $.ajax({type:"GET",
            url: url_site + "/services/commentaire/listerCommentaires",
            data: "clef=" + env.clef + "&id_message=" + id_message,
            dataType:"text",
            success: function(res) {
                refreshCommentairesReponse(res, id_message);
            },
            error: function(xhr, status, err) {
                func_erreur(status + ": " + err);
            }
        });
}


function refreshCommentairesReponse(rep, id_message) {
    if (rep.errorcode == undefined) {
        // On nettoie les commentaires précédents
        $("#message_" + id_message + " .commentaires").empty();

        // On ajoute le code HTML de tous les commentaires
        var listeCommentaires = (JSON.parse(rep, revival)).commentaires;
        for (var i=0; i < listeCommentaires.length; i++) {
            var commentaire = listeCommentaires[i];
            $("#message_" + id_message + " .commentaires").prepend(commentaire.getHtml());
        }

        // On remplace la liste des commentaires actualisée dans le message
        env.messages[id_message].comments = listeCommentaires;

        // On fait apparaitre le commentaire
        $("#commentaire_" + listeCommentaires.length).appear();
        $.force_appear();
    } else {
        console.log(rep.message + ", ERROR_CODE: " + rep.errorcode);
        func_erreur(rep.message);
    }
}
