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
            this.likes = {"0": [], "1": [], "2": [], "3": [], "4": []};
        }
        this.likes = likes;
}


Message.prototype.getHtml = function() {
    var retour;
    var variables_format = {id_message: this.id,
                            contenu_message: this.texte,
                            auteur_id: this.auteur.id_auteur,
                            auteur_pseudo: this.auteur.pseudo_auteur,
                            date_message: this.date,
                            nb_likes_0: this.likes["0"],
                            nb_likes_1: this.likes["1"],
                            nb_likes_2: this.likes["2"],
                            nb_likes_3: this.likes["3"],
                            nb_likes_4: this.likes["4"]};

    $.get("html/message.html", function(res) {
        retour = Mustache.render(res, variables_format);
    })

    return retour;
}


function setVirtualMessages() {
    localdb = [];
    env.follows = [];
    var user1 = {"id":1,"login":"Nicolas"};
    var user2 = {"id":2,"login":"Alexia"};
    var user3 = {"id":3,"login":"Inconnu"};
        env.follows[1]=new Set();
        env.follows[1].add(user2);
        env.follows[1].add(user3);
        var com =new Commentaire (1,user3,"bonjour",new Date());
        localdb[0]= new Message (0,user2,"Salut",new Date(),[com]);
    localdb[1]= new Message (1,user2,"Salut tous le monde",new Date(),[com]);
    localdb[2]= new Message (2,user3,"Salut tous le monde",new Date(),[]);
}



function completeMessages() {
    if (!env.noConnection) {
        $.ajax({type: "POST",
                url: url_site + "/services/message/listerMessages",
                data: "key=" + env.clef + "&query=" + env.query + "&from=" + env.fromId + "&limite=10&id_min=-1&id_max=" + env.idmin,
                dataType:"json",
                success:function(res) {
                    completeMessagesReponse(res);
                },
                error:function(xhr, status, err) {
                    func_erreur(status);
                }
            });
    } else {
        console.log(env.fromId);
        var tab = getFromLocalDb(env.fromId, env.minId, env.maxId, tab=[], 10);
        completeMessagesReponse(JSON.stringify(tab));
    }
}


function completeMessagesReponse(rep) {
    var tab = JSON.parse(rep, revival);
    for(i=0; i < tab.length; i++) {
        var m = tab[i];
        $("#messages").prepend(m.getHtml());
        env.messages[m.id] = m;
        
        if (m.id > env.maxId) {
            env.maxId = m.id;
        }

        if (m.id < env.minId) {
            env.minId = m.id;
        }
    }

    var last_id = env.messages[tab.length - 1].id;
    $("#message_" + last_id).appear();
    $.force_appear();
}


function getFromLocalDb(from, minId, maxId, tab=[], nbmax) {
    console.log("from:"+from);
    //console.log("env.follows[1]:",env.follows[1]);
    //console.log("env.follows[from]:",env.follows[from]);
    var rep=[];
    //console.log(localdb.length);
    if(from<0){
        for(m=0;m<nbmax;m++){
            if(m<localdb.length){
                rep.push(localdb[m]);
            }
        }
        //console.log("localdb", localdb);      
    }
    else{
        var m=0;
        while(m<nbmax && m<localdb.length){
            //console.log("localdb[m] ",localdb[m]);
            //console.log("localdb[m].auteur ",localdb[m].auteur);
            //console.log("m "+m);
            if( localdb[m].auteur.id==from ){
                rep.push(localdb[m]);
                m++;
            }else{
                /*console.log("follows[from].length"+follows[from].length);
                for(a=0; a < follows[from].length;a++){
                    if(localdb[m].auteur==a ){
                        rep.push(localdb[m]);
                        m++;
                    }
                }*/
                //console.log("env.follows[from]",env.follows[from]);
                env.follows[from].forEach(function (valeur){
                    //console.log("valeur",valeur)
                    //console.log("localdb[m].auteur",localdb[m].auteur);
                    if(localdb[m].auteur==valeur ){
                        rep.push(localdb[m]);
                        m++;

                    }
                });
    
            }
        }
    }
    //console.log("rep",rep);
    return rep;
}


function refreshMessages(){
    console.log("entree dans refreshMessage");
    if(!env.noConnection){
        $.ajax({type:"POST", url: url_site + "/services/message/listerMessages", data:"key="+env.clef+"&query="+env.query+"&from="+env.maxId+"&limite=-1&id_min="+env.maxId+"&id_max=-1", dataType:"json",success:function(res){ refreshMessagesReponse(res);},error:function(xhr,status,err){func_erreur(status);}});
    }
    else {
        var tab = getFromLocalDb(env.fromId,env.minId,env.maxId, tab=[],10);
        refreshMessagesReponse(JSON.stringify(tab));
    }
}


function refreshMessagesReponse(rep){
    console.log("rep="+rep);
    var tab=JSON.parse(rep,revival);
    console.log("tab",tab);
    console.log("tab[0]",tab[0]);
    $("#messages").empty();
    for(i=0;i<tab.length;i++){
        var m=tab[i];
        $("#messages").prepend(m.getHtml());
        env.messages[m.id]=m;
        if(m.id>env.maxId){env.maxId=m.id;}
        if(m.id<env.minId){env.minId=m.id;}
    }
    /*for(var i=tab.length-1;i>=0;i--){
        var m=tab[i];
        console.log("m",m);
        $("#messages").append(m.getHtml());
        env.messages[m.id]=m;
        if(m.id>env.maxId){ env.maxId=m.id;}
        if(env.minId<0 || m.id<env.minId){ env.minId=m.id;}
    }*/
    /*console.log("env.messages",env.messages)
    var last_id=env.messages[tab.length-1].id;
    $("#message_"+last_id).appear();
    $.force_appear();*/
}

function newMessage(form){
    var texte=$("textarea[NAME=nv_msg]").val();
    //console.log("texte message",texte);
    if(! env.noConnection){
        $.ajax({type:"GET",
                url: url_site + "/services/message/ajouterMessage",
                data:"clef="+env.clef+"&contenu="+texte,
                dataType:"json",
                success:function(res){ newMessageReponse(res);},error:function(xhr,status,err){func_erreur(status);}});
    }else{
        taille_localdb=localdb.length;
        localdb[taille_localdb]= new Message (taille_localdb,{"id":env.id_utilisateur,"login":env.pseudo},texte,new Date(),[]);
        //console.log("localdb[taille_localdb]",localdb[taille_localdb]);
        newMessageReponse(JSON.stringify({"key":env.clef}));    
    }
    return true;

}



function newMessageReponse(rep){
    //console.log("rep newMessageReponse", rep);
    var msg=JSON.parse(rep,revival);
    //console.log("msg",msg);
    //if(msg.key!=undefined && msg.erreur==undefined ){
    if(msg.erreur==undefined ){
        refreshMessages();
    }
    else{
        alert("erreur lors de la création du nouveau message");
    }
}

function developpeMessage(id){
    var im=id
    //console.log("im",im);
    var m=env.messages[im];
    var el = $("#message_"+im+" .comments");
    el.empty();
    for(var i=0;i<m.comments.length ;i++){
        var c=m.comments[i];
        el.append(c.getHtml());
    }
    el=$("#message_"+im+" .new_comment");
    el.append("<form id=\"nv_commentaire\" method=\"post\"  action=\"javascript:(function(){return;})()\" onSubmit=\"javascript:newCommentaire("+im+")\">\n\
                               <textarea form=\"nv_commentaire\" name=\"nv_com\"> Poster un nouveau commentaire </textarea>\n\
                               <input type=\"submit\" value=\"Poster\"/> \n\
                   </form >");

    $("#message_"+im+" img").replaceWith("<img src=\"res/image_moins.png\" title=\"Ne plus afficher les messages\" alt=\"Ne plus afficher les messages\" id=\"image_plus\" onClick=\"javascript:replieMessage("+im+")\"/>");
}


function replieMessage(id){
var m=env.messages[id];
    var el = $("#message_"+id+" .comments>div");
    el.remove();
    /*for(var i=0;i<m.comments.length ;i++){
        var c=m.comments[i];
        //el.remove(c.getHtml());
    }*/
    el=$("#message_"+id+" .new_comment>form");
    el.remove();
    $("#message_"+id+" img").replaceWith("<img src=\"res/image_plus.png\" title=\"Afficher les messages\" alt=\"Afficher les messages\" id=\"image_plus\" onClick=\"javascript:developpeMessage("+id+")\"/>");

}