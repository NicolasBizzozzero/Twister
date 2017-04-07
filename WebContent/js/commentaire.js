function Commentaire(id, auteur, texte, date){
        this.id = id;
        this.auteur = auteur;
        this.texte = texte;
        this.date = date;
}


Commentaire.prototype.getHtml=function(){
    //console.log("this.auteur.id "+this.auteur.id);
    //console.log("this.auteur.login "+this.auteur.login);
        var retour="<div id=\"commentaire_"+this.id+"\" class=\"commentaireUtilisateur\">\n\
                        <div class=\"text_commentaire\">"+this.texte+"</div>\n\
                        <div class=\"info_commentaire\">\n\
                                <span>Posté par <span class=\"liens\" onClick=\"javascript:pageUser("+this.auteur.id+")\">"+this.auteur.login+"</span><span> le "+this.date+"</span>\n\
                </span>\n\
                        </div>\n\
                     </div>";
    return retour;
}


function newCommentaire(id){
    var texte=$("textarea[NAME=nv_com]").val();
    if(! env.noConnection){
        $.ajax({type:"GET", url: url_site + "/services/message/ajouterCommentaire", data:"clef="+env.clef+"&contenu="+texte+"&id_message="+id, dataType:"json",success:function(res){ newCommentaireReponse(id,res);},error:function(xhr,status,err){func_erreur(status);}});
    }else{
        
        var com= new Commentaire(env.messages[id].comments.length+1,{"id": env.id_utilisateur,"login":env.pseudo}, texte ,this.date)
        env.messages[id].comments.push(com);
        //console.log("env.messages[id]",env.messages[id]);
        newCommentaireReponse(id,JSON.stringify(com));
    }
    //JSON.parse(...) ?
    return true;
}
function newCommentaireReponse(id, rep){
    //console.log("rep newCommentaireReponse", rep);
    var msg=JSON.parse(rep,revival);
    //console.log("msg",msg);
    //if(msg.key!=undefined && msg.erreur==undefined ){
    if(msg.erreur==undefined ){
        refreshCommentaires(id);
    }
    else{
        alert("erreur lors de la création du nouveau commentaire");
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