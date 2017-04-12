/**
 * Construit le code HTML d'une page de profil d'un utilisateur.
 * Une page différente est créée si l'utilisateur est lui-même, un de ses amis
 * ou un inconnu.
 */
function makePageProfil(id_utilisateur) {
    env.id_ami=undefined;
    $.ajax({type:"GET", url: url_site + "/services/ami/listerAmis", data:"clef="+env.clef+"&id_utilisateur="+env.id_utilisateur+"&index_debut=0&nombre_demandes=10", dataType:"json",success:function(res){ faireListeAmis(res,env.id_utilisateur);},error:function(xhr,status,err){func_erreur(status);},async: false});
    console.log("env.id_utilisateur",env.id_utilisateur);
    console.log("id_utilisateur",id_utilisateur);
    console.log("env.follows[env.id_utilisateur]",env.follows[env.id_utilisateur]);
    if (id_utilisateur == env.id_utilisateur) {
        makePageProfilUtilisateur();
    } else if ((env.follows[env.id_utilisateur] == undefined)||(!env.follows[env.id_utilisateur].has(id_utilisateur.toString()))){
	console.log("choix de makePageProfilInconnu"); 
        makePageProfilInconnu(id_utilisateur);
    //} else if (env.follows[id_utilisateur] != undefined) {
    } else {  
	console.log("choix de makePageProfilAmi"); 
        makePageProfilAmi(id_utilisateur);
    }
}


/**
 * Charge le contenu de la page HTML du header + celle du profil de
 * l'utilisateur.
 */
function makePageProfilUtilisateur() {
    $("body").load("html/en_tete.html", function() {
        $("#corp_page").load("html/page_profil_utilisateur.html");
    });
}


/**
 * Charge le contenu de la page HTML du header + celle du profil d'un ami de 
 * l'utilisateur.
 */
function makePageProfilAmi(id_ami) {
    env.id_ami=id_ami;
    console.log("entree ds makePageProfilAmi"); 
    $("body").load("html/en_tete.html", function() {
        $("#corp_page").load("html/page_profil_ami.html");
    });
}


/**
 * Charge le contenu de la page HTML du header + celle du profil d'un inconnu
 */
function makePageProfilInconnu(id_inconnu) {
    env.id_ami=id_inconnu;
    console.log("entree ds makePageProfilInconnu"); 
    $("body").load("html/en_tete.html", function() {
        $("#corp_page").load("html/page_profil_inconnu.html");
    });
}

function recuperationInfoUtilisateur(id){
	 $.ajax({type:"GET", url: url_site + "/services/utilisateur/recuperationInfoUtilisateur", data:"id="+id, dataType:"json",success:function(res){ reponseRecuperationInfoUtilisateur(res);},error:function(xhr,status,err){func_erreur(status);}});
	
}

function reponseRecuperationInfoUtilisateur(rep){
	var prenom="non remplis";
	var nom="non remplis";
	var anniversaire="non remplis";
	if(rep.prenom!=undefined){
		prenom=rep.prenom;
	}
	if(rep.nom!=undefined){
		nom=rep.nom;
	}
	if(rep.anniversaire!=undefined){
		anniversaire=rep.anniversaire;
	}
	var pseudo=rep.pseudo;
	$("#informations").append('<div id="pseudo" >'+pseudo+'</div>');
	$("#informations").append("Nom : "+nom+"<br>");
	$("#informations").append("Prénom : "+prenom+"<br>");
	$("#informations").append("Anniversaire : "+anniversaire+"<br>");
	
}