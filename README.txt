# Java_BattleShip

Battleship game board, Java

Les bateaux se placent de façon alleatoire pour l'ordinateur.Il y a un ArrayList pour
gèrer la memoire de coordonées produis alleatoirement et pour que l'ordinateur produissait seulement des bateaux où il n'y a 
pas des bateaux.

Le joueur peut chosir son positionamment dans le tableau selon son desire(verticale
ou horizontal) et selon le 4 types de bateaux (Porte-avion, Destroyer, Sousmarine, Patrouille). Du même façon la grille du joueur 
deviens "unabled" quand c'est le tour de l'ordinateur de placer ses bateaux, empechant aussi, au joueur de placer ou changer des
autres bateaux.

Une characteristique de l'algorithme c'est un tablau des entiers, qui permetre retourner
le type de bateau coullé. Ce était fait avec l'idée de permettre d'avoir un "score" precis du type de bateau 
qui est coullé (non implementé encore). 
 
Il y a un message qui s'affiche si on veux placer le bateaux à partir d'un cas trop proche du bordeur du tableau. C'est là
alors, qui se detonne un message qu'indique qu'il n'y a pas suffisentment d'espace pour mettre le bateau. Message qui s'affiche pour partie
gagné ou perdu.

Dans le menu principale, bouttons : jouer, instructions, et quitter. 

Fenêtre pour afficher le nom de joueur, qui apparetre en haut du tableau.

Dans la côtée resseaux, developement au principe de l'algorithme (non implementé).

********
Programme fait en collaboration dans lequel j'ai developpé tout l'algorithme, avec l'exception de l'UI, qui a été dévéloppé par des 
autres collaborateurs.
