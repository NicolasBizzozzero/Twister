# gr2_Bourmaud_Bizzozzero
3I017 Projet Web

## Lien vers le site :
http://twister.ga

## Sites utiles
http://www-connex.lip6.fr/~soulier/content/TechnoWeb/TechnoWeb.html<br>
http://www.cssdesk.com/<br>
http://www.freeformatter.com/html-formatter.html

#### Tomcat
http://li328.lip6.fr:8280/manager/html<br>
Login: admin<br>
Mdp: admin.li328$

#### PHPMyAdmin
http://li328.lip6.fr/phpmyadmin<br>
Login: gr2_bourmaud_biz<br>
Mdp: nz5QcHdRLNHCPpjK

## Trucs à faire

### Services
* La suppression de commentaires doit prendre l'ID du commentaire en paramètre, pas son contenu
* ModifierNom/Prenom/Mail
* SupprimerCommentaire
* SupprimerUtilisateur
* RetweestsCommentaire
* ModifierMotDePasse
* RetirerDroitAdmin
* AjouterDroitAdmin

### Autre
* Il faut imposer une limite de taille sur tout ce que rentre l'utilisateur. Cela implique : Son nom, prenom, pseudo, etc. Mais aussi ses commentaires
* Il faut supprimer une session de la BD si l'utilisateur n'a pas fait une action depuis un bout de temps. Pour cela, on va modifier le timestamp de la Session de l'utilisateur à chaque action qu'il effectue. On va rajouter dans chaque service une verification du timestamp. Si NOW() - timestamp > TEMPS_LIMITE, alors on supprime sa session.

### Bonus
* AjouterPhotoProfil
* ModifierPhotoProfil
* SupprimerPhotoProfil
* AjouterCommentaireAvecPhoto
* AjouterSousCommentaire (commentaire d'un commentaire, le prof en avait parlé vite fait. Je propose qu'on autorise qu'un seul niveau de sous-commentaire pour éviter d'avoir à faire des sous-commentaires de sous-commentaires)
* ⚠️️ Imposer limite photos
