
function Message(id, auteur, texte, date, comments){
        this.id=id;
        this.auteur=auteur;
        this.texte=texte;
        this.date=date;
        if(comments==undefined){ comments=[]}
        this.comments=comments;
}

function Commentaire(id, auteur, texte, date){
        this.id=id;
        this.auteur=auteur;
        this.texte=texte;
        this.date=date;
}

Message.prototype.getHtml=function(){
        var retour="<div id=\"message_"+this.id+"\"class=message\"\n\
                        <div class=\"text_message\">"+this.texte+">\div>\n\
                        <div class=\"info_mesage\">\n\
                                <span>Posté par <span class=\"links\" onCliks=\"javascrpit: pageUser("+this.auteur.id+","+this.auteur.login+")\">"+this.auteur.login+"</span><span>le"+this.date+"</span><img src=\"Images/image_plus.jpg\" title=\"Afficher les messages\" alt=\"Afficher les messages\" onClick=\"javascript:developpeMessage("+this.id+")\"/>\n\
                                </span>\n\
                        </div>\n\
                        <div class=\"comments\">\n\
                        </div>\n\
                        <div class=\"new_comments\">\n\
                        </div>\n\
                     </div>\n\";
}


Commentaire.prototype.getHtml=function(){
        var retour="<div id=\"message_"+this.id+"\"class=message\"\n\
                        <div class=\"text_message\">"+this.texte+">\div>\n\
                        <div class=\"info_mesage\">\n\
                                <span>Posté par <span class=\"links\" onCliks=\"javascrpit: pageUser("+this.auteur.id+","+this.auteur.login+")\">"+this.auteur.login+"</span><span>le"+this.date+"</span><img src=\"Images/image_plus.jpg\" title=\"Afficher les messages\" alt=\"Afficher les messages\" onClick=\"javascript:developpeMessage("+this.id+")\"/>\n\
				</span>\n\
                        </div>\n\
                     </div>\n\";
}


function revival(key,value){
        if(key=="erreur"&&value!=0){
                return {erreur:value};
        }
        if(value.comments!=undefined){
                var m=new Message( value.id,value.auteur,value.texte,value.date,value.comments);
                return m;
        }
        else if(value.texte!=undefined){
                var c=new Commentaire(value.id,value.auteur,value.texte,value.date);
                return c;
 }
        else if(key=="date"){
                var d=new Date(value);
                return d;
        }
}

function init(){
        env=new Objet();
        env.noConnection=true;
        setVirtualMessages();
        env.id=1;
        env.login="Nicolas";
        env.key=0;
}


function setVirtualMessages(){
        localdb=[];
        follows=[];
        var user1={"id":1;"login":"Nicolas"};
        var user2={"id":2;"login":"Alexia"};
 	var user3={"id":3;"login":"Inconnu"};
        follows[1]=new Set();
        follows[1].add(2);
        follows[1].add(3);
        var com =new Commentaire (1,user3,"bonjour",new Date());
        localdb[2]= new Message (2,user2,"Salut",new Date(),[com]);
}


function makeMainPanel(fromId, fromLogin,query){
        if(fromId==undefined){ fromId=-1;}
        env.fromId=fromId;
        env.fromLogin=fromLogin;
        env.query=query;
        env.msg=[];
        Console.log(env.fromLogin);
        env.minId=-1;
        env.maxId=-1;

        var s="<header>\n\
                <div id=\"logo\" class=\"entete\">\n\
 			<img src=\"logo.pgn\" alt=\"logo de notre site\"/> \n\
                </div>\n\
                <div id=\"titre\" class=\"entete\">\n\
                        <h1> TWISTER </h1>\n\
                </div>\n\
                <form id= \"recherche\" class=\"entete\">\n\
                        <input type=\"text\" id=\"recherche\" name=\"recherche\" value=\"Recherche\"/>\n\
                        <input type=\"image\" src=\"loupe.jpg\" alt=\"rechercher\"/>\n\
                </form>\n\
                <div id=\"deconnexion\" class=\"entete\">\n\
                        <a href=\"deconnexion.html\"> Se déconnecter</a>\n\
			<button type=\"button\" onclick=\"javascript:retour()\" >Retour page principale</button>\n\
                </div>\n\
               </header>";
        if(env.fromId<0){
                s+="<div id=\"cote\" class=\"corp\">\n\
                        <div id=\"photo\">\n\
                                <img src=\"image_profil.jpg\" alt=\"votre photo de profil\"/>\n\
                        </div>\n\
 			<div id=\"profil\">\n\
                                <a href=\"profil.html\">Mon Profil</a>\n\
                        </div>\n\
                    </div>\n\
                    <div id=\"milieu\" class=\"corp\">\n\
                        <form  id=\"nv_message\" method=\"post\" action=\"\" onSubmit=\"javascript : ecrire_message(this)\">\n\
                                <input type=\"text\" id=\"nouveau_msg\" name=\"nouveau_msg\" value=\"Nouveau message\"/>\n\
                                <textarea > Nouveau message corp</textarea>\n\
                                <input type=\"submit\" value=\"Poster\"/> \n\
                        </form >\n\
                        <section id=\"messages\" onload=\"javascript: CompleteMessages()\">\n\
                        </section>\n\
                    </div>";
        }else{
                if(env.fromId==env.id){
                        s+= "<div id=\"cote\" class=\"corp\">\n\
                        	<div id=\"photo\">\n\
                                	<img src=\"image_profil.jpg\" alt=\"votre photo de profil\"/>\n\
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
                            	<section id=\"messages\" onload=\"javascript: CompleteMessages()\">\n\
                            	</section>\n\
                           </div>";
                }else if(!env.follows[env.fromId].has(env.id)){
                        s+= "<div id=\"cote\" class=\"corp\">\n\
                        	<div id=\"photo\">\n\
                                	<img src=\"image_profil.jpg\" alt=\"votre photo de profil\"/>\n\
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
                            	<section id=\"messages\" onload=\"javascript: CompleteMessages()\">\n\
                            	</section>\n\
                           </div>";
 }else{
                        s+=  "<div id=\"cote\" class=\"corp\">\n\
                        	<div id=\"photo\">\n\
                                	<img src=\"image_profil.jpg\" alt=\"votre photo de profil\"/>\n\
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
                            	<section id=\"messages\" onload=\"javascript: CompleteMessages()\">\n\
                            	</section>\n\
                           </div>";
                }
         }
         aler(s);
         return s;
}

function CompleteMessages(){
	if(!env.noConnection){}
	else{
		var tab =getFromLocalDb(env.fromId,env.minId,env.maxId,var tab=[],15);

		CompleteMessageReponse(Json.strintBy(tab));
	}
}

function CompleteMessagesReponse(rep){
 	var=Json.parse(rep,revival);
	for(i=0;i<tab.lenght;i++){
		var m=tab[i];
		alert(m.getHtml());
		env.msg[m.id]=m;
		if(m.id>env.maxId){env.maxId=m.id;}
		if(m.id<env.minId){env.minId=m.id;}
	}
}

function getFormLocalDb(from,minId,maxId,var tab=[],nbmax){
	if(from<0){
	}
	else{
	}







