function Commentaire(id, auteur, texte, date){
    this.id = id;
    this.auteur = auteur;
    this.texte = texte;
    this.date = date;
}


Commentaire.prototype.getHtml = function() {
    var retour;
    var variables_format = {id_commentaire: this.id,
                            contenu_commentaire: this.texte,
                            auteur_id: this.auteur.id_auteur,
                            auteur_pseudo: this.auteur.pseudo_auteur,
                            date_commentaire: this.date};

    $.get("html/commentaire.html", function(res) {
        retour = Mustache.render(res, variables_format);
    })

    return retour;
}


function newCommentaire(id) {
    var texte = $("textarea[NAME=nv_com]").val();
    if (! env.noConnection) {
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
    } else {
        var com = new Commentaire(env.messages[id].comments.length + 1, {"id": env.id_utilisateur, "login": env.pseudo}, texte, this.date);
        env.messages[id].comments.push(com);
        //console.log("env.messages[id]",env.messages[id]);
        newCommentaireReponse(id, JSON.stringify(com));
    }
    //JSON.parse(...) ?
    return true;
}


function newCommentaireReponse(id, rep) {
    if (rep.errorcode == undefined) {
        //console.log("rep newCommentaireReponse", rep);
        var msg = JSON.parse(rep, revival);
        //console.log("msg",msg);
        refreshCommentaires(id);
    } else {
        console.log(rep.message + ", ERROR_CODE: " + rep.errorcode);
        func_erreur(rep.message);
    }
}


function refreshCommentaires(id){
    var m=env.messages[id];
    var el = $("#message_"+id+" .comments");
    el.empty();
    for(var i=0;i<m.comments.length ;i++){
        var c=m.comments[i];
        el.append(c.getHtml());
    }
}
