/**
 * Construit le code HTML d'une page de profil d'un utilisateur.
 * Une page différente est créée si l'utilisateur est lui-même, un de ses amis
 * ou un inconnu.
 */
function makePageProfil(id_utilisateur) {
    if (id_utilisateur == env.id_utilisateur) {
        makePageProfilUtilisateur();
    } else if ((env.follows[env.id_utilisateur] == undefined)||(!env.follows[env.id_utilisateur].has(id_utilisateur))){
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
    console.log("entree ds makePageProfilAmi"); 
    $("body").load("html/en_tete.html", function() {
        $("#corp_page").load("html/page_profil_ami.html");
    });
}


/**
 * Charge le contenu de la page HTML du header + celle du profil d'un inconnu
 */
function makePageProfilInconnu(id_inconnu) {
    console.log("entree ds makePageProfilInconnu"); 
    $("body").load("html/en_tete.html", function() {
        $("#corp_page").load("html/page_profil_inconnu.html");
    });
}
