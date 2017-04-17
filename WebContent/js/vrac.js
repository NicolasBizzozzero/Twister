function init() {
    url_site = "http://li328.lip6.fr:8280/gr2_Bourmaud_Bizzozzero"
    NB_MESSAGES_PAR_SCROLL = 10;

    env = new Object();
    
    // Initialisation des locks
    lock_liste_messages = false;
}


/**
 * Hashe une chaine de caractères avec l'algorithme SHA-512.
 */
function hasher(string) {
    return SHA512(string);
}


/**
 * Permet de construire un objet Javascript précis depuis un objet JSON.
 */
function revival(key, value) {
    // Cas où on a un Message
    if (value.id_message != undefined) {
        return new Message(value.id_message, value.auteur, value.contenu,
                           value.date, value.commentaires,
                           value.nb_commentaires, value.likes);
    }

    // Cas où on a un Commentaire
    else if (value.id_commentaire != undefined) {
        return new Commentaire(value.id_commentaire, value.auteur,
                               value.contenu, value.date);
    }

    // Cas où on a une date
    //TODO: Faire ce cas

    // Autres cas
    else {
        return value;
    }
}


/**
 * Ajoute un message d'erreur dans la div prévue à cet effet.
 * @param {string} message - Le message à écrire dans la div.
 */
function func_erreur(message) {
    $("#div_erreur").text(message);
}


/**
 * Retire un elemnent d'une Liste si il existe puis le retourne.
 * Sinon, retourne undefined.
 */
Array.prototype.pull = function(object) {
    for (i = 0; i < this.length; i++) {
        if (this[i] == object) {
            this.splice(i, 1);
        }
    }
};
