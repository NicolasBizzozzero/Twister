/**
 * Construit le code HTML d'une page de profil d'un utilisateur.
 * Une page différente est créée si l'utilisateur est lui-même, un de ses amis
 * ou un inconnu.
 */
function makePageProfil(id_utilisateur) {
    if (id_utilisateur == env.id_utilisateur) {
        makePageProfilUtilisateur();
    } else if (env.follows[id_utilisateur] != undefined) {
        makePageProfilAmi(id_utilisateur);
    } else {
        makePageProfilInconnu(id_utilisateur);
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
    $("body").load("html/en_tete.html", function() {
        $("#corp_page").load("html/page_profil_ami.html");
    });
}


/**
 * Charge le contenu de la page HTML du header + celle du profil d'un inconnu
 */
function makePageProfilInconnu(id_inconnu) {
    $("body").load("html/en_tete.html", function() {
        $("#corp_page").load("html/page_profil_icnonnu.html");
    });
}
