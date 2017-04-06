function init() {
    env = new Object();
    env.noConnection = false;
    //setVirtualMessages();
    //env.id = 1;
    //env.login = "Nicolas";
    //env.key = 0;
}


/**
 * Constructeur de message.
 */
function Message(id, auteur, texte, date, comments) {
        this.id = id;
        this.auteur = auteur;
        this.texte = texte;
        this.date = date;
        if (comments == undefined){
		    comments = []
	    }
        this.comments = comments;
}


/**
 * Constructeur de commentaire.
 */
function Commentaire(id, auteur, texte, date) {
        this.id = id;
        this.auteur = auteur;
        this.texte = texte;
        this.date = date;
}


/**
 * Recupère le code HTML permettant d'afficher un message.
 */
Message.prototype.getHtml = function() {
	auteur_id = this.auteur.id;
	auteur_login = "\"" + this.auteur.login + "\"";
    var retour =    "<div id=\"message_"+this.id+"\" class=\"messageUtilisateur\">\n\
                    <div class=\"text_message\">"+this.texte+"</div>\n\
                    <div class=\"info_mesage\">\n\
                            <span>Posté par <span class=\"liens\" onClick=\"javascript:makeMainPanel("+auteur_id+","+auteur_login+", 9)\" >"+this.auteur.login+"</span><span> le "+this.date+"</span><img src=\"images/image_plus.png\" title=\"Afficher les messages\" alt=\"Afficher les messages\" id=\"image_plus\"  onClick=\"javascript:developpeMessage("+this.id+")\"/>\n\
                            </span>\n\
                    </div>\n\
                    <div class=\"comments\">\n\
                    </div>\n\
                    <div class=\"new_comment\">\n\
                    </div>\n\
                 </div>";
	return retour;
}


/**
 * Recupère le code HTML permettant d'afficher un commentaire.
 */
Commentaire.prototype.getHtml=function() {
    var retour = "<div id=\"commentaire_"+this.id+"\" class=\"commentaireUtilisateur\">\n\
                    <div class=\"text_commentaire\">"+this.texte+"</div>\n\
                    <div class=\"info_commentaire\">\n\
                            <span>Posté par <span class=\"liens\" onClick=\"javascript:makeMainPanel("+this.auteur.id+","+this.auteur.login+",9)\">"+this.auteur.login+"</span><span> le "+this.date+"</span>\n\
			</span>\n\
                    </div>\n\
                 </div>";
	return retour;
}


/**
 * Permet de construire un objet Javascript précis depuis un objet JSON.
 */
function revival(key, value) {
    // Cas où on a une erreur
    if (key == "erreur" && value != 0){
        return {erreur: value};
    } 
    // Cas où on a un Message
    else if (value.comments != undefined) {
        var m = new Message(value.id, value.auteur, value.texte, value.date, value.comments);
        return m;
    }
    // Cas où on a un Commentaire
    else if (value.texte != undefined) {
        var c = new Commentaire(value.id, value.auteur, value.texte, value.date);
        return c;
	}
    // Cas où on a une date
    else if (key == "date") {
        var d = new Date(value);
        return d;
    }
    // Autres cas
    else {
		return value;
	}
}


function setVirtualMessages(){
    localdb=[];
    env.follows=[];
    var user1={"id":1,"login":"Nicolas"};
    var user2={"id":2,"login":"Alexia"};
 	var user3={"id":3,"login":"Inconnu"};
    env.follows[1]=new Set();
    env.follows[1].add(user2);
    env.follows[1].add(user3);
    var com =new Commentaire (1,user3,"bonjour",new Date());
    localdb[0]= new Message (0,user2,"Salut",new Date(),[com]);
	localdb[1]= new Message (1,user2,"Salut tous le monde",new Date(),[com]);
	localdb[2]= new Message (2,user3,"Salut tous le monde",new Date(),[]);
	console.log("follows[1]",env.follows[1]);
}


/**
 * Charge le code de la page principale.
 */
function makeMainPanel(fromId, fromLogin, query) {
	console.log("entree dans makemainpanel");
        if (fromId == undefined){
		fromId = -1;
	}
        env.fromId = fromId;
        env.fromLogin = fromLogin;
        env.query = query;
        env.msg = [];
	console.log(env.fromId+"   "+env.fromLogin);
        env.minId = -1;
        env.maxId = -1;

        var s="<header>\n\
                <div id=\"logo\" class=\"entete\">\n\
 			<img src=\"images/logo.pgn\" alt=\"logo de notre site\"/> \n\
                </div>\n\
                <div id=\"titre\" class=\"entete\">\n\
                        <h1> TWISTER </h1>\n\
                </div>\n\
                <form id= \"recherche\" class=\"entete\">\n\
                        <input type=\"text\" id=\"recherche\" name=\"recherche\" value=\"Recherche\"/>\n\
                        <input type=\"image\" src=\"images/loupe.jpg\" alt=\"rechercher\"/>\n\
                </form>\n\
                <div id=\"deconnexion\" class=\"entete\">\n\
                        <div class=\"liens\" onclick=\"javascript:makeConnexionPanel()\">Se déconnecter</div>\n\
			<div class=\"liens\" onclick=\"javascript:makeMainPanel(-1,env.login,4)\" >Retour page principale</div>\n\
                </div>\n\
               </header>";
	//console.log("En-tête chargée");
        if (env.fromId < 0){
                s += "<div id=\"cote\" class=\"corp\">\n\
                        <div id=\"photo\">\n\
                                <img src=\"images/image_profil.jpg\" alt=\"votre photo de profil\"/>\n\
                        </div>\n\
 			<div id=\"profil\">\n\
                                <div id=\"link1\" onClick=\"javascript:makeMainPanel(env.id,env.login,4)\">Mon profil</div>\n\
                        </div>\n\
                     </div>\n\
                     <div id=\"milieu\" class=\"corp\">\n\
                        <section >\n\
        			<form  id=\"nv_message\" method=\"post\" action=\"\" onSubmit=\"javascript : ecrire_message(this)\">\n\
         				<textarea form=\"nv_message\" > Poster un nouveau message </textarea>\n\
         				<input type=\"submit\" value=\"Poster\"/>\n\
        			</form >\n\
       			</section>\n\
                        <section id=\"messages\">\n\
				<script type=\"text/javascript\">\n\
					$(completeMessages);\n\
				</script>\n\
                        </section>\n\
                     </div>";
		//console.log("Page d'accueil chargée");
        } else {

                if (env.fromId == env.id){
                        s+= "<div id=\"cote\" class=\"corp\">\n\
                        	<div id=\"photo\">\n\
                                	<img src=\"images/image_profil.jpg\" alt=\"votre photo de profil\"/>\n\
                        	</div>\n\
 				<div id=\"informations\">\n\
        				Nom :\n\
        				<br>Prénom:</br>\n\
        				Date de naissance:\n\
      				</div>\n\
    			    </div>\n\
                    	    <div id=\"milieu\" class=\"corp\">\n\
				<section >\n\
                        		<form  id=\"nv_message\" method=\"post\" action=\"\" onSubmit=\"javascript : ecrire_message(this)\">\n\
                               			<textarea form=\"nv_message\"> Poster un nouveau message </textarea>\n\
                                		<input type=\"submit\" value=\"Poster\"/> \n\
                        		</form >\n\
				</section>\n\
                            	<section id=\"messages\">\n\
					<script type=\"text/javascript\">\n\
						$(completeMessages);\n\
					</script>\n\
                        	</section>\n\
   		    	    </div>\n\
    			    <div id=\"cote\" class=\"corp\">\n\
      				Amis\n\
                            </div>";
			//console.log("Page de profil de l'utilisateur chargée");
                } else if(!env.follows[env.fromId].has(env.id)){
                        s+= "<div id=\"cote\" class=\"corp\">\n\
                        	<div id=\"photo\">\n\
                                	<img src=\"images/image_profil.jpg\" alt=\"votre photo de profil\"/>\n\
                        	</div>\n\
				<button type=\"button\" onclick=\"javascript:ajouter_ami()\" >Suivre</button>\n\
 				<div id=\"informations\">\n\
        				Nom :\n\
        				<br>Prénom:</br>\n\
        				Date de naissance:\n\
      				</div>\n\
                    	    </div>\n\
                    	    <div id=\"milieu\" class=\"corp\">\n\
				<section >\n\
                        		<form  id=\"nv_message\" method=\"post\" action=\"\" onSubmit=\"javascript : ecrire_message(this)\">\n\
                               			<textarea form=\"nv_message\"> Poster un nouveau message </textarea>\n\
                                		<input type=\"submit\" value=\"Poster\"/> \n\
                        		</form >\n\
				</section>\n\
                            	<section id=\"messages\">\n\
					<script type=\"text/javascript\">\n\
						$(completeMessages);\n\
					</script>\n\
                        	</section>\n\
   		    	    </div>\n\
    			    <div id=\"cote\" class=\"corp\">\n\
      				Amis\n\
                            </div>";
			//console.log("Page de profil d'une personne qu'on ne suit pas encore chargée");
 		} else{
                        s+=  "<div id=\"cote\" class=\"corp\">\n\
                        	<div id=\"photo\">\n\
                                	<img src=\"images/image_profil.jpg\" alt=\"votre photo de profil\"/>\n\
                        	</div>\n\
				<button type=\"button\" onclick=\"javascript:supprimer_ami()\" >Suivre</button>\n\
 				<div id=\"informations\">\n\
        				Nom :\n\
        				<br>Prénom:</br>\n\
        				Date de naissance:\n\
      				</div>\n\
                    	    </div>\n\
                    	    <div id=\"milieu\" class=\"corp\">\n\
				<section >\n\
                        		<form  id=\"nv_message\" method=\"post\" action=\"\" onSubmit=\"javascript : ecrire_message(this)\">\n\
                               			<textarea form=\"nv_message\"> Poster un nouveau message </textarea>\n\
                                		<input type=\"submit\" value=\"Poster\"/> \n\
                        		</form >\n\
				</section>\n\
                            	<section id=\"messages\">\n\
					<script type=\"text/javascript\">\n\
						$(completeMessages);\n\
					</script>\n\
                        	</section>\n\
   		    	    </div>\n\
    			    <div id=\"cote\" class=\"corp\">\n\
      				Amis\n\
                           </div>";
			//console.log("Page de profil d'une personne qu'on suit chargée");
                	}
         }
	//$("body").appendTo().html(s);
        $("body").html(s);

}


/**
 * Envoie une requête au serveur pour récupèrer les messages si on dispose
 * d'une connexion. Dans le cas contraire, charge les messages depuis la BDD
 * locale.
 */
function completeMessages() {
	if (!env.noConnection) {
		$.ajax({type: "POST",
                url: "/services/message/listerMessages",
                data: "key=" + env.key + "&query=" + env.query + "&from=" + env.fromId + "&limite=10&id_min=-1&id_max=" + env.idmin,
                dataType:"json",
                success:function(res){
                    completeMessagesReponse(res);
                },
                error:function(xhr, status, err){
                    func_erreur(status);
                }
            });
	} else {
		var tab = getFromLocalDb(env.fromId, env.minId, env.maxId, tab=[], 10);
		completeMessagesReponse(JSON.stringify(tab));
	}
}


/**
 * Gère la réponse du serveur après la requête demandant de lister les messages.
 */
function completeMessagesReponse(rep) {
 	var tab = JSON.parse(rep, revival);

	for (i=0; i < tab.length; i++) {
		var m = tab[i];
		$("#messages").prepend(m.getHtml());
		env.msg[m.id] = m;
		if (m.id > env.maxId) {
            env.maxId = m.id;
        }
		if (m.id < env.minId) {
            env.minId = m.id;
        }
	}

	var last_id = env.msg[tab.length - 1].id;
	$("#message_" + last_id).appear();
	$.force_appear();
}


/**
 * Appellée en mode developpement.
 * Recupère les messages depuis la BDD locale.
 */
function getFromLocalDb(from, minId, maxId, tab=[], nbmax) {
	var rep = [];
	if (from < 0) {
		for (m=0; m < nbmax; m++) {
			if (m < localdb.length) {
				rep.push(localdb[m]);
			}
		}	
	} else {
		var m = 0;
		while (m < nbmax && m < localdb.length) {
			if (localdb[m].auteur.id == from) {
				rep.push(localdb[m]);
				m++;
			} else {
				env.follows[from].forEach(function(valeur) {
					if (localdb[m].auteur == valeur) {
						rep.push(localdb[m]);
						m++;
					}
				});
			}
		}
	}
	return rep;
}



function refreshMessages() {
	if (!env.noConnection) {
		$.ajax({type: "POST",
                url: "/services/message/listerMessages",
                data: "key=" + env.key + "&query=" + env.query + "&from=" + env.maxId + "&limite=-1&id_min=" + env.maxId + "&id_max=-1",
                dataType: "json",
                success:function(res) {
                    refreshMessagesReponse(res);
                },
                error:function(xhr, status, err) {
                    func_erreur(status);
                }
            });
	} else {
		var tab = getFromLocalDb(env.fromId, env.minId, env.maxId, tab=[], 10);
		refreshMessagesReponse(JSON.stringify(tab));
	}
}


function refreshMessagesReponse(rep){
	console.log("rep="+rep);
	var tab=JSON.parse(rep,revival);
	for(var i=tab.length-1;i>=0;i--){
		var m=tab[i];
		$("#messages").prepend(m.getHtml());
		env.msg[m.id]=m;
		if(m.id>env.maxId){ env.maxId=m.id;}
		if(env.minId<0 || m.id<env.minId){ env.minId=m.id;}
	}
}

function newMessage(){
	var texte=$("#nv_message").val();
	if(! env.noConnection){
		$.ajax({type:"GET", url:"/services/message/ajouterMessage", data:"clef="+env.key+"&contenu="+texte, dataType:"json",success:function(res){ newMessageReponse(res);},error:function(xhr,status,err){func_erreur(status);}});
	}else{
		taille_localdb=localdb.length;
		localdb[taille_localdb]= new Message (taille_localdb,{"id":env.id,"login":env.login},"Salut tous le monde",new Date(),[]);	
	}

}



function newMessageReponse(rep){
	var msg=JSON.parse(rep,revival);
	if(key!=undefined && msg.erreur==undefined ){
		refreshMessages();
	}
	else{
		alert("erreur lors de la création du nouveau message");
	}
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
function developpeMessage(id){
	var m=env.msg[id];
	var el = $("#message_"+id+" .comments");
	for(var i=0;i<m.comments.length ;i++){
		var c=m.comments[i];
		el.append(c.getHtml());
	}
	el=$("#message_"+id+" .new_comment");
	el.append("<form  id=\"nv_commentaire\" method=\"post\" action=\"\" onSubmit=\"javascript : ecrire_commentaire(this)\">\n\
                               <textarea form=\"nv_commentaire\"> Poster un nouveau commentaire </textarea>\n\
                               <input type=\"submit\" value=\"Poster\"/> \n\
                   </form >");

	$("#message_"+id+" img").replaceWith("<img src=\"images/image_moins.png\" title=\"Ne plus afficher les messages\" alt=\"Ne plus afficher les messages\" id=\"image_plus\" onClick=\"javascript:replieMessage("+id+")\"/>");
}


function replieMessage(id){
var m=env.msg[id];
	var el = $("#message_"+id+" .comments>div");
	el.remove();
	/*for(var i=0;i<m.comments.length ;i++){
		var c=m.comments[i];
		//el.remove(c.getHtml());
	}*/
	el=$("#message_"+id+" .new_comment>form");
	el.remove();
	$("#message_"+id+" img").replaceWith("<img src=\"images/image_plus.png\" title=\"Afficher les messages\" alt=\"Afficher les messages\" id=\"image_plus\" onClick=\"javascript:developpeMessage("+id+")\"/>");

}

function newComment(id){
	if(!noConection){
	$.ajax({type:"GET", url:"/services/message/ajouterCommentaire", data:"clef="+env.key+"&contenu="+texte+"&id_message="+id, dataType:"json",success:function(res){ newMessageReponse(res);},error:function(xhr,status,err){func_erreur(status);}});
	}else{
		new_comment_response(id,JSON.stringify(new Commantaire(env.msg[i].comments.length+1,{"id": env.id,"login":env.login}, "je suis un texte",this.date)));
	}
	//JSON.parse(...) ?
}
function new_comment_response(id, rep){
	//todo
}

function ajouter_ami(){
	if (!noConnection){
	$.ajax({type:"GET", url:"/services/ami/ajouterAmi", data:"clef="+env.key+"&id_ami="+env.id, dataType:"json",success:function(res){ reponseFollow(res);},error:function(xhr,status,err){func_erreur(status);}});
	}else{
		reponseFollow({});
	}
}

function reponseFollow(){
	$("#cote button").replaceWith("<button type=\"button\" onclick=\"javascript:ne_plus_suivre()\" >Ne plus suivre</button>");
}

function ne_plus_suivre(){
	if (!noConnection){
	$.ajax({type:"GET", url:"/services/ami/suprimerAmi", data:"clef="+env.key+"&id_ami="+env.id, dataType:"json",success:function(res){ reponseStopFollow(res);},error:function(xhr,status,err){func_erreur(status);}});
	}else{
		reponseStopFollow({});
	}
}

function reponseStopFollow(){
	$("#cote button").replaceWith("<button type=\"button\" onclick=\"javascript:ajouter_ami()\" >Suivre</button>");
}


function lister_amis(){
	if (!noConnection){
	$.ajax({type:"GET", url:"/services/ami/listerAmis", data:"clef="+env.key+"&id_utilisateur="+env.id+"&index_debut=0&nombre_demandes=10", dataType:"json",success:function(res){ reponseFollow(res);},error:function(xhr,status,err){func_erreur(status);}});
	}else{
		reponseListerAmis({});
	}
}


function reponseListerAmis(rep){
	//todo
}
