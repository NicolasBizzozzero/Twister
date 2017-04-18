/**
 * Construit un objet Message.
 */
function Message(id, auteur, texte, date, comments, nbComments, likes) {
    this.id = id;
    this.auteur = auteur;
    this.texte = texte;
    this.date = date;
    if (comments == undefined) {
        comments = [];
        nbComments = 0;
    }
    this.comments = comments;
    this.nbComments = nbComments;
    if (likes == undefined) {
        likes = {"0": [], "1": [], "2": [], "3": [], "4": []};
    }
    this.likes = likes;
}


/**
 * Recupère et construit le code HTML correspondant à un message.
 */
Message.prototype.getHtml = function() {
    var retour;
    var variables_format = {id_message: this.id,
                            contenu_message: this.texte,
                            auteur_id: this.auteur.id_auteur,
                            auteur_pseudo: this.auteur.pseudo_auteur,
                            message_date: this.date,
                            nb_likes_0: this.likes["0"].length,
                            nb_likes_1: this.likes["1"].length,
                            nb_likes_2: this.likes["2"].length,
                            nb_likes_3: this.likes["3"].length,
                            nb_likes_4: this.likes["4"].length};

    $.ajax({url: "html/message.html",
            success: function(res) {
                retour = Mustache.render(res, variables_format);
            },
            async: false
        });

    return retour;
}


function completeMessages() {
    lock_liste_messages = true;
    $.ajax({type: "GET",
            url: url_site + "/services/message/listerMessages",
            data: "clef=" + env.clef + "&recherche=" + env.query + "&id_utilisateur=" + env.fromId + "&limite=" + NB_MESSAGES_PAR_SCROLL + "&id_min=-1&id_max=-1",
            dataType: "text",
            success: function(res) {
                completeMessagesReponse(res);
            },
            error: function(xhr, status, err) {
                func_erreur(status + ": " + err);
                lock_liste_messages = false;
            }
        });
}


function completeMessagesReponse(rep) {
    if (rep.errorcode == undefined) {
        var listeMessages = (JSON.parse(rep, revival)).messages;
        for(var i=0; i < listeMessages.length; i++) {
            var m = listeMessages[i];
            $("#messages").append(m.getHtml());
            env.messages[m.id] = m;
            
            if (m.id > env.maxId) {
                env.maxId = m.id;
            }

            if (m.id < env.minId) {
                env.minId = m.id;
            }
        }

        var last_id = listeMessages[listeMessages.length - 1].id;
        $("#message_" + last_id).appear();
        $.force_appear();
    } else {
        console.log(rep.message + ", ERROR_CODE: " + rep.errorcode);
        func_erreur(rep.message);
    }
    lock_liste_messages = false;
}


function refreshMessages() {
    $.ajax({type:"GET",
            url: url_site + "/services/message/listerMessages",
            data: "clef=" + env.clef + "&recherche=" + env.query + "&id_utilisateur=" + env.fromId + "&limite=" + NB_MESSAGES_PAR_SCROLL + "&id_min=-1&id_max=" + env.minId,
            dataType:"text",
            success: function(res) {
                refreshMessagesReponse(res);
            },
            error: function(xhr, status, err) {
                func_erreur(status + ": " + err);
                lock_liste_messages = false;
            }
        });
}


function refreshMessagesReponse(rep) {
    if (rep.errorcode == undefined) {
        var listeMessages = (JSON.parse(rep, revival)).messages;
        if (listeMessages.length != 0) {
	        for (var i=0; i < listeMessages.length; i++) {
	            var m = listeMessages[i];
	
	            $("#messages").append(m.getHtml());
	            env.messages[m.id] = m;
	            
	            if (m.id > env.maxId) {
	                env.maxId = m.id;
	            }
	
	            if (m.id < env.minId) {
	                env.minId = m.id;
	            }
	        }
	
	        var last_id = listeMessages[listeMessages.length - 1].id;
	        $("#message_" + last_id).appear();
	        $.force_appear();
        }
    } else {
        console.log(rep.message + ", ERROR_CODE: " + rep.errorcode);
        func_erreur(rep.message);
    }
    lock_liste_messages = false;
}


function newMessage(form) {
    // On recupère le texte du message à poster
    var texte = $("textarea[NAME=nv_message]").val();

    // On vide le texte qui était dedans
    $("textarea[NAME=nv_message]").val('');

    $.ajax({type:"GET",
            url: url_site + "/services/message/ajouterMessage",
            data:"clef=" + env.clef + "&contenu=" + texte,
            dataType: "text",
            success: function(res) {
                newMessageReponse(res);
            },
            error: function(xhr, status, err) {
                func_erreur(status + ": " + err);
            }
        });
}


function newMessageReponse(rep) {
    if (rep.errorcode == undefined) {
        var nouveau_message = JSON.parse(rep, revival);
	    env.messages[nouveau_message.id] = nouveau_message;
	    $("#messages").prepend(nouveau_message.getHtml());
	    
	    if (nouveau_message.id > env.maxId) {
	        env.maxId = nouveau_message.id;
	    }
	
	    if (nouveau_message.id < env.minId) {
	        env.minId = nouveau_message.id;
	    }
	
	    var last_id = nouveau_message.id;
	    $("#message_" + last_id).appear();
	    $.force_appear();
    } else {
        console.log(rep.message + ", ERROR_CODE: " + rep.errorcode);
        func_erreur(rep.message);
    }
}


function developpeMessage(id_message) {
    var message = env.messages[id_message];

    // On recupère tous les commentaires du message
    var html_commentaires = $("#message_" + id_message + " .commentaires");
    for(var i=0; i < message.comments.length; i++) {
        html_commentaires.prepend(message.comments[i].getHtml());
    }

    // On ajoute le formulaire de nouveau commentaire
    var form_nouveau_commentaire;
    $.ajax({url: "html/form_nouveau_commentaire.html",
            success: function(res) {
                form_nouveau_commentaire = Mustache.render(res, {id_message: id_message});
            },
            async: false
        });
    $("#message_" + id_message + " .new_comment").append(form_nouveau_commentaire);

    // On remplace l'image du plus par une image de moins
    var image_moins;
    $.ajax({url: "html/image_moins.html",
            success: function(res) {
                image_moins = Mustache.render(res, {id_message: id_message});
            },
            async: false
        });
    $("#message_" + id_message + " #image_plus").replaceWith(image_moins);
}


function replieMessage(id_message) {
    var message = env.messages[id_message];

    // On retire les commentaires du message
    $("#message_" + id_message + " .commentaires>div").remove();

    // On retire le formulaire de nouveau commentaire
    $("#message_" + id_message + " .new_comment>form").remove();

    // On remplace l'image du moins par une image de plus
    var image_plus;
    $.ajax({url: "html/image_plus.html",
            success: function(res) {
                image_plus = Mustache.render(res, {id_message: id_message});
            },
            async: false
        });
    $("#message_" + id_message + " #image_moins").replaceWith(image_plus);
}

function supprimerMessage(id){
	$.ajax({type: "GET",
        url: url_site + "/services/message/supprimerMessage",
        data: "clef=" + env.clef+ "&id_message=" +id,
        dataType: "text",
        success: function(res) {
            supprimerMessageReponse(id);
        },
        error: function(xhr, status, err) {
            func_erreur(status + ": " + err);
            lock_liste_messages = false;
        }
    });
}

function supprimerMessageReponse(id){
	$("#message_" + id).remove();
	
    
}
