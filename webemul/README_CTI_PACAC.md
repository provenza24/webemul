# Exécution de l'application en local

1- Séléctionner les profiles dev/IDE (IDE est utilisé pour mapstruct)

2- Vérificer le fichier de configuration s8.properties
	
   -> Décommenter la partie CONFIG LOCALE   
   -> Commenter la partie CONFIG PRD

3- Exécuter la classe fr.cnamts.cti_pacac.webXXXX.WebXXXXApp en tant qu'application Java

# Package de la version de production sous Eclipse

1- Si ce n'est déjà fait, créer un "Run configuration" de type Maven avec les options suivantes:

   -> Goal: package
   -> Profiles: prod no-liquibase
   -> Cocher la case "Skip tests" 
   
2- Vérificer le fichier de configuration s8.properties
	
   -> Commenter la partie CONFIG LOCALE   
   -> Décommenter la partie CONFIG PRD 
   
3- Lancer la tache

# Package de la version de production avec Maven

1- Vérificer le fichier de configuration s8.properties
	
   -> Commenter la partie CONFIG LOCALE   
   -> Décommenter la partie CONFIG PRD 

2- Télécharger Maven sur son poste

3- Si la variable JAVA_HOME ne pointe pas sur la JRE 1.8, mettre à jour celle-ci

    SET JAVA_HOME="c:\........."

4- Lancer la commande suivante:

	mvn -Pprod -Pno-liquibase package -DskipTests	

# Migration BDD du PHP vers cette version

1- Exporter la table indicent
2- Modificer dans le script script_creation.sql la valeur de l'increment en prenant la valeur max de la table incident existante
3- Supprimer la table incident de la BDD
4- Exécuter le script script_creation.sql
5- Récupérer les inserts générer par l'export et générer un script insert.sql
6- Remplacer 'true' par '1' et 'false' par '0' dans ce script
7- Exécuter le script insert.sql
8- exécuter la commande suivante
	
			update incident set auteur = resolu_par
9- Si possible, remplacer les valeurs des auteurs / resolu_par par les noms S8 des agents

# BUGS

- Le plugin Maven pour générer à la volée le fichier de configuration s8j.properties ne fonctionne pas (raison à déterminer)
- Sous IE11, les inputs de type number ne fonctionnent pas (les boutons pour modifier les valeurs (+/- 1) ne sont pas affichés)
- Sous IE11, les copier-coller dans les textarea comportant une longueur maximale ne fonctionnent pas (une erreur de validation de formulaire indique que la taille est supérieure à la taille maximale
- Sous IE11, l'icône calendrier de séléction de date n'est pas bien affiché (pas aligné verticalement avec l'input)
- L'export CSV d'un tableau vide provoque une exception

# AMELIORATIONS

- Spinner pour la saisie des nombres
- Pagination de la page de recherche