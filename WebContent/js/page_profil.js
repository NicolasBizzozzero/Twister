/**
 * Construit le code HTML d'une page de profil d'un utilisateur.
 * Une page différente est créée si l'utilisateur est lui-même, un de ses amis
 * ou un inconnu.
 */
function makePageProfil(id_utilisateur, pseudo, nom, prenom, anniversaire, nb_messages) {
	env.fromId = id_utilisateur
	env.messages = [];
	env.minId = Infinity;
	env.maxId = -Infinity;

    if (id_utilisateur == env.id_utilisateur) {
        makePageProfilUtilisateur(nb_messages);
    } else if (! env.follows.indexOf(id_utilisateur.toString()) > -1) {
        env.id_ami = id_utilisateur;
        makePageProfilInconnu(id_utilisateur, pseudo, nom, prenom, anniversaire, nb_messages);
    } else {
        env.id_ami = id_utilisateur;
        makePageProfilAmi(id_utilisateur, pseudo, nom, prenom, anniversaire, nb_messages);
    }
}


/**
 * Charge le contenu de la page HTML du header + celle du profil de
 * l'utilisateur.
 */
function makePageProfilUtilisateur(nb_messages) {
    $("body").load("html/en_tete.html", function() {
        var variables_format = {pseudo_utilisateur: env.pseudo,
        		                prenom_utilisateur: env.prenom,
        		                nom_utilisateur: env.nom,
        		                date_de_naissance: env.anniversaire,
        		                nb_messages: nb_messages};

        $.ajax({url: "html/page_profil_utilisateur.html",
                success: function(res) {
                	$("#corp_page").html(Mustache.render(res, variables_format));
                },
                async: false
            });
    });
}


/**
 * Charge le contenu de la page HTML du header + celle du profil d'un ami de 
 * l'utilisateur.
 */
function makePageProfilAmi(id_ami, pseudo, nom, prenom, anniversaire, nb_messages) {
    $("body").load("html/en_tete.html", function() {
        var variables_format = {pseudo_utilisateur: pseudo,
        		                prenom_utilisateur: prenom,
        		                nom_utilisateur: nom,
        		                date_de_naissance: anniversaire,
        		                nb_messages: nb_messages};

        $.ajax({url: "html/page_profil_ami.html",
                success: function(res) {
                	$("#corp_page").html(Mustache.render(res, variables_format));
                },
                async: false
            });
    });
}


/**
 * Charge le contenu de la page HTML du header + celle du profil d'un inconnu
 */
function makePageProfilInconnu(id_inconnu, pseudo, nom, prenom, anniversaire, nb_messages) {
    $("body").load("html/en_tete.html", function() {
        var variables_format = {pseudo_utilisateur: pseudo,
        		                prenom_utilisateur: prenom,
        		                nom_utilisateur: nom,
        		                date_de_naissance: anniversaire,
        		                nb_messages: nb_messages};

        $.ajax({url: "html/page_profil_inconnu.html",
                success: function(res) {
                	$("#corp_page").html(Mustache.render(res, variables_format));
                },
                async: false
            });
    });
}


function voirProfil(form) {
    // On recupère le pseudo de l'ami a voir
    var pseudo = $("input[NAME=voirprofil]").val();

    // On vide le texte qui était dedans
    $("input[NAME=voirprofil]").val('');

    $.ajax({type:"GET",
            url: url_site + "/services/utilisateur/informationsUtilisateur",
            data:"clef=" + env.clef + "&pseudo=" + pseudo,
            dataType: "json",
            success: function(res) {
                voirProfilReponse(res);
            },
            error: function(xhr, status, err) {
                func_erreur(status + ": " + err);
            }
        });
}


function voirSonProfil() {
    $.ajax({type:"GET",
        url: url_site + "/services/utilisateur/informationsUtilisateur",
        data:"clef=" + env.clef + "&pseudo=" + env.pseudo,
        dataType: "json",
        success: function(res) {
            voirProfilReponse(res);
        },
        error: function(xhr, status, err) {
            func_erreur(status + ": " + err);
        }
    });
}


function voirProfilAmi(pseudo) {
    $.ajax({type:"GET",
        url: url_site + "/services/utilisateur/informationsUtilisateur",
        data:"clef=" + env.clef + "&pseudo=" + pseudo,
        dataType: "json",
        success: function(res) {
            voirProfilReponse(res);
        },
        error: function(xhr, status, err) {
            func_erreur(status + ": " + err);
        }
    });
}


function voirProfilReponse(res) {
	if (res.id != undefined) {
		makePageProfil(res.id, res.pseudo, res.nom, res.prenom, res.anniversaire, res.nb_messages)
	} else {
		func_erreur("L'utilisateur n'existe pas");
	}
}