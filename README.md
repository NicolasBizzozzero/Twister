# gr2_Bourmaud_Bizzozzero
3I017 Projet Web

## Sites utiles
http://www.cssdesk.com/

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
