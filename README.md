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

## Todo
### Bugs
* La date d'anniversaire doit être au format YYYY-MM-DD, il faut le checker
* La fonction retournant le temps d'inactivite d'un utilisateur ne fonctionne pas de midi a 13h, ensuite elle refonctionne
* Tester SupprimerLike avec un utilisateur n'ayant pas like
* Faire un schema de la BDD MongoDB et MySQL
* On ne supprime pas les messages ou les commentaires lors de la suppression d'un utilisateur, le preciser lors de l'ecriture du CDG
* Quand on aura tout fini, rajouter pour chaque service un catch (Exception e) avec un JSON D'erreur : ERREUR_INCONNUE
* Ajouter un petit message ou un petit design de site différent lorsque c'est l'anniversaire d'un utilisateur

### Services à implanter
* AjouterPhotoProfil (⚠️️ Imposer limite photos)
* ModifierPhotoProfil (⚠️️ Imposer limite photos)
* SupprimerPhotoProfil
* AjouterCommentaireAvecPhoto (⚠️️ Imposer limite photos)

### Implantations côté client
* ModifierUtilisateur
* SupprimerCommentaire
* SupprimerUtilisateur

