# Création de la table Utilisateurs
CREATE TABLE Utilisateurs(
    id Integer PRIMARY KEY AUTO_INCREMENT,
	pseudo Varchar(32) UNIQUE,
	mot_de_passe Varchar(64),
	mail Varchar(64),
	prenom Varchar(64),
	nom Varchar(64),
	anniversaire Date
);

# Création de la table Amities
CREATE TABLE Amities(
	id_ami1 Integer,
	id_ami2 Integer,
	timestamp Timestamp,
	primary key(id_ami1, id_ami2)
);

# Création de la table Sessions
CREATE TABLE Sessions(
    	clef Varchar(32) unique,
		id Integer,
		timestamp Timestamp,
		est_administrateur boolean,
    	primary key(id)
)
