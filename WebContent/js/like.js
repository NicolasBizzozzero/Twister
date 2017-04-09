/**
 * Cette fonction ajoute un like a un post si l'ID de l'utilisateur n'est pas
 * déjà présent dans la liste de likes, soit il le retire dans le cas contraire
 */
function modifieLike(idMessage, typeLike) {
    if ($.inArray(env.id_utilisateur, env.messages[idMessage].likes[typeLike]) > -1) {
        // L'ID est déjà présent, on retire le like
        supprimeLike(idMessage, typeLike);
    } else {
        // L'ID n'est pas présent, on ajoute le like
        ajouteLike(idMessage, typeLike);
    }
}


/**
 * Ajoute un like au message idMessage.
 */
function ajouteLike(idMessage, typeLike) {
    $.ajax({type: "GET",
            url: url_site + "/services/like/ajouterLike",
            data: "clef=" + env.clef + "&type_like=" + typeLike + "&id_message=" + idMessage,
            dataType: "json",
            success: function(res) {
                env.messages[idMessage].likes[typeLike].push(env.id_utilisateur);
                refreshLikes(idMessage, typeLike);
            },
            error: function(xhr, status, err) {
                func_erreur(status + ": " + err);
            }
        });
}


/**
 * Supprime un like du message idMessage.
 */
function supprimeLike(idMessage, typeLike) {
    $.ajax({type: "GET",
            url: url_site + "/services/like/supprimerLike",
            data: "clef=" + env.clef + "&type_like=" + typeLike + "&id_message=" + idMessage,
            dataType: "json",
            success: function(res) {
                env.messages[idMessage].likes[typeLike].pull(env.id_utilisateur);
                refreshLikes(idMessage, typeLike);
            },
            error: function(xhr, status, err) {
                func_erreur(status + ": " + err);
            }
        });
}


/**
 * Actualise le nombre de likes d'un message.
 */
function refreshLikes(idMessage, typeLike) {
    $("#message_" + idMessage + " #like_" + typeLike + " p").text(env.messages[idMessage].likes[typeLike].length);
}

