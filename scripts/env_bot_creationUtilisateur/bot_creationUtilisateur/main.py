import requests
from time import sleep
from insert_1000_utilisateurs import get_utilisateur_aleatoire


url = "http://li328.lip6.fr:8280/gr2_Bourmaud_Bizzozzero"

servlet_creer_utilisateur = "/utilisateur/creationUtilisateur"


def faire_requete(url: str, arguments: dict) -> str:
    return requests.get(url, params=arguments)


def creer_utilisateur2(pseudo: str, mot_de_passe: str, email: str,
                       prenom: str = None, nom: str = None,
                       anniversaire: str = None) -> str:
    global url
    global servlet_creer_utilisateur

    arguments = {"pseudo": pseudo,
                 "motDePasse": mot_de_passe,
                 "email": email}
    if prenom:
        arguments["prenom"] = prenom
    if nom:
        arguments["nom"] = nom
    if anniversaire:
        arguments["anniversaire"] = anniversaire

    return faire_requete(url + servlet_creer_utilisateur, arguments)


def creer_utilisateur(prenom, nom, password, login) -> str:
    global url
    global servlet_creer_utilisateur

    arguments = {"prenom": prenom,
                 "nom": nom,
                 "password": password,
                 "login": login}

    return faire_requete(url + servlet_creer_utilisateur, arguments)


def main():
    while True:
        utilisateur = get_utilisateur_aleatoire()
        print(creer_utilisateur(*utilisateur))
        sleep(3)


if __name__ == '__main__':
    main()
