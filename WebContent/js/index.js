function init() {
    url_site = "http://li328.lip6.fr:8280/gr2_Bourmaud_Bizzozzero"
    NB_MESSAGES_PAR_SCROLL = 10;

    env = new Object();
    env.noConnection = false;
    setVirtualMessages();
}


/**
 * Hashe une chaine de caractères avec l'algorithme SHA-512.
 */
function hasher(string) {
    return SHA512(string);
}


/**
 * Permet de construire un objet Javascript précis depuis un objet JSON.
 * TODO: A mon avis elle bug, les noms de clefs se chevauchent.
 */
function revival(key, value) {
    // Cas où on a une erreur
    if (key == "erreur" && value != 0) {
        return {erreur: value};
    }

    // Cas où on a un Message
    else if (value.comments != undefined) {
        var m = new Message(value.id, value.auteur, value.texte, value.date,
                            value.comments, value.nbComments, value.likes);
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


/**
 * Charge le contenu de la page HTML du header + celle de la page principale.
 */
function makePagePrincipale() {
    $("body").load("html/en_tete.html", function() {
        $("#corp_page").load("html/page_principale.html");
    });
}


/**
 * Charge le contenu de la page HTML du header + celle du profil de
 * l'utilisateur.
 */
function makePageProfilUtilisateur(id_utilisateur) {
    $("body").load("html/en_tete.html", function() {
        $("#corp_page").load("html/page_profil.html");
    });
}


/**
 * Charge le contenu de la page HTML du header + celle du profil d'un ami de 
 * l'utilisateur.
 */
function makePageProfilAmi(id_ami) {
    //TODO: Implanter
}


/**
 * Charge le contenu de la page HTML du header + celle du profil d'un inconnu
 */
function makePageProfilInconnu(id_inconnu) {
    //TODO: Implanter
}


function makeMainPanel(fromId, query) {
    env.fromId = fromId;
    env.query = query;
    env.messages = [];
    env.minId = -1;
    env.maxId = -1;

    // Cas où on a demandé la page d'accueil
    if (env.fromId == -1) {
        makePagePrincipale();
    }

    // Cas où l'utilisateur a demandé sa page de profil
    else if (env.fromId == env.id_utilisateur) {
        makePageProfilUtilisateur(env.id_utilisateur);
    }

    /* Cas où l'utilisateur a demandé une page de profil d'un utilisateur
       non-suivi. */
    else if (env.follows[fromId] == undefined) {
                s+= "<div id=\"cote\" class=\"corp\">\n\
                	<div id=\"photo\">\n\
                        	<img src=\"res/image_profil.jpg\" alt=\"votre photo de profil\"/>\n\
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
                		<form id=\"nv_message\" method=\"post\"  action=\"javascript:(function(){return;})()\" onSubmit=\"javascript:newMessage(this)\">\n\
                       			<textarea form=\"nv_message\" name=\"nv_msg\"> Poster un nouveau message </textarea>\n\
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
		<div id=\"amis\">\n\
					Amis\n\
			<script type=\"text/javascript\">\n\
				$(listerAmis);\n\
			</script>\n\
		</div>\n\
                    </div>";
			//console.log("Page de profil d'une personne qu'on ne suit pas encore chargée");
 	}
    /* Cas où l'utilisateur a demandé une page de profil d'un utilisateur
       qu'il suivait. */
    else {
        s+=  "<div id=\"cote\" class=\"corp\">\n\
                            	<div id=\"photo\">\n\
                                    	<img src=\"res/image_profil.jpg\" alt=\"votre photo de profil\"/>\n\
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
                            		<form id=\"nv_message\" method=\"post\"  action=\"javascript:(function(){return;})()\" onSubmit=\"javascript:newMessage(this)\">\n\
                                   			<textarea form=\"nv_message\" name=\"nv_msg\"> Poster un nouveau message </textarea>\n\
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
    				<div id=\"amis\">\n\
          					Amis\n\
    					<script type=\"text/javascript\">\n\
    						$(listerAmis);\n\
    					</script>\n\
    				</div>\n\
                               </div>";
                  	//console.log("Page de profil d'une personne qu'on suit chargée");
    }
}


/**
 * Ajoute un message d'erreur dans la div prévue à cet effet.
 * @param {string} message - Le message à écrire dans la div.
 */
function func_erreur(message) {
    $("#div_erreur").text(message);
}
