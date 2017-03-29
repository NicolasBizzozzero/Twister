import names
from random import choice, randint


FICHIER_SQL = "insert_1000_utilisateurs.sql"
SERVEURS_DOMAINS = ('neuf.fr', 'o2.co.uk', 'wanadoo.fr', 'gmx.de',
	                'globomail.com', 'daum.net', 'talktalk.co.uk', 'gmx.com',
	                'laposte.net', 'comcast.net', 'blueyonder.co.uk',
	                'tiscali.co.uk', 'voo.be', 'me.com', 'virgin.net',
	                'nate.com', 'freeserve.co.uk', 'yahoo.co.id', 'yahoo.fr',
	                'ya.ru', 'terra.com.br', 'ig.com.br', 'pobox.com',
	                'mail.com', 'live.fr', 'fastmail.fm', 'online.de',
	                'prodigy.net.mx', 'live.co.uk', 'hotmail.de', 'free.fr',
	                'hotmail.com', 'yahoo.com.ar', 'bt.com', 'zoho.com',
	                'yahoo.co.kr', 'yahoo.de', 'sina.com', 'tvcablenet.be',
	                't-online.de', 'gmx.net', 'naver.com', 'att.net',
	                'lavabit.com', 'speedy.com.ar', 'yahoo.co.uk',
	                'gmx.fr', 'qq.com', 'aol.com', 'facebook.com',
	                'fibertel.com.ar', 'hush.com', 'hotmail.com.mx',
	                'love.com', 'sbcglobal.net', 'hotmail.com.br',
	                'live.com.ar', 'hotmail.be', 'charter.net', 'uol.com.br',
	                'wow.com', 'sky.com', 'icloud.com', 'hotmail.co.uk',
	                'wanadoo.co.uk', 'verizon.net', 'rambler.ru', 'bol.com.br',
	                'live.com', 'googlemail.com', 'safe-mail.net',
	                'yahoo.com.ph', 'arnet.com.ar', 'zipmail.com.br',
	                'list.ru', 'live.com.mx', 'email.com', 'web.de', 'live.de',
	                'skynet.be', 'outlook.com.br', 'yahoo.com', 'yahoo.com.mx',
	                'mail.ru', 'virginmedia.com', 'hotmail.es', 'inbox.com',
	                'hanmail.net', 'mac.com', 'earthlink.net', 'live.be',
	                'itelefonica.com.br', 'yahoo.co.jp', 'cox.net',
	                'rocketmail.com', 'google.com', 'yandex.com',
	                'outlook.com', 'yahoo.com.sg', 'orange.fr', 'orange.net',
	                'sfr.fr', 'yahoo.co.in', 'r7.com', 'oi.com.br',
	                'telenet.be', 'games.com', 'bellsouth.net', 'ygm.com',
	                'hushmail.com', 'hotmail.com.ar', 'btinternet.com',
	                'yahoo.com.br', 'hotmail.fr', 'msn.com', 'globo.com',
	                'ntlworld.com', 'gmail.com', 'yandex.ru', 'juno.com',
	                'ymail.com')


def get_prenom_aleatoire() -> str:
    return names.get_first_name()


def get_nom_aleatoire() -> str:
    return names.get_last_name()


def get_pseudo_aleatoire(nom, prenom) -> str:
    return concatenation(prenom, '.', nom, get_random_digit(),
                         get_random_digit())


def get_mail_aleatoire(nom, prenom) -> str:
    return concatenation(prenom, '.', nom, "@", choice(SERVEURS_DOMAINS))


def get_mot_de_passe_aleatoire(floor: int, ceil: int) -> str:
    mot_de_passe = ""
    functions = (get_random_char, get_random_digit)
    nb_chars = get_random_integer(floor, ceil + 1)
    for _ in range(nb_chars):
        random_char = choice(functions)()
        mot_de_passe = concatenation(mot_de_passe, random_char)
    return mot_de_passe


def get_anniversaire_aleatoire() -> str:
    annee = get_random_integer(1900, 2016)
    mois = get_random_integer(1, 12)
    jour = get_random_integer(1, 28)
    if mois < 10:
        mois = concatenation('0', mois)
    if jour < 10:
        jour = concatenation('0', jour)
    return concatenation(annee, '-', mois, '-', jour)


def get_random_integer(floor: int, ceil: int) -> int:
    """ Return a random integer between 'floor' inclusive and 'ceil'
        exclusive.
    """
    return randint(floor, ceil)


def get_random_char() -> str:
    chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    return choice(chars)


def get_random_digit() -> str:
    digits = "0123456789"
    return choice(digits)


def concatenation(*strings) -> str:
    """ Return the concatenation of string1 and string2. """
    result = ""
    for string in strings:
        result = "{}{}".format(result, string)
    return result


def creer_requete(id, pseudo, mdp, mail, prenom, nom, anniversaire) -> str:
    return ("INSERT INTO Utilisateurs Values({}, \"{}\", \"{}\", \"{}\", \"{}"
            "\", \"{}\", \"{}\");\n").format(id, pseudo, mdp, mail, prenom,
                                             nom, anniversaire)


def ajoute_requete(requete, nom_fichier):
    with open(nom_fichier, 'a') as file:
        file.write(requete)


def get_utilisateur_aleatoire() -> tuple:
    prenom = get_prenom_aleatoire()
    nom = get_nom_aleatoire()
    pseudo = get_pseudo_aleatoire(nom, prenom)
    mdp = get_mot_de_passe_aleatoire(8, 20)
    mail = get_mail_aleatoire(nom, prenom)
    anniversaire = get_anniversaire_aleatoire()
    return prenom, nom, mdp, pseudo
    # return pseudo, mdp, mail, prenom, nom, anniversaire


def main():
    for id in range(1000, 2001):
        prenom = get_prenom_aleatoire()
        nom = get_nom_aleatoire()
        pseudo = get_pseudo_aleatoire(nom, prenom)
        mdp = get_mot_de_passe_aleatoire(8, 20)
        mail = get_mail_aleatoire(nom, prenom)
        anniversaire = get_anniversaire_aleatoire()
        requete = creer_requete(id, pseudo, mdp, mail,
                                prenom, nom, anniversaire)
        ajoute_requete(requete, FICHIER_SQL)


if __name__ == '__main__':
    main()
