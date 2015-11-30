Ce programme Java permet la génération de grilles de sudoku, la résolution de grilles contenues dans un fichier et propose une interface graphique afin d'effectuer des parties.

Pour la compilation et la création du fichier jar

Veuillez svp dans un terminal taper la commande : (nécéssite Ant)

$ ant

Ensuite pour l'execution, veuillez vous placer dans le dossier exe (celui qui contient le fichier jar) puis taper :


Pour l'execution de l'inteface graphique :

$ java -jar Sudoku.jar -a

Pour la resolution automatique de grilles contenues dans le fichier  grille.txt (par exemple, vous pouvez utiliser un autre fichier) et qui mettra les grilles resolues dans grillesresolues.txt

$ java -jar Sudoku.jar -r grille.txt grillesresolues.txt


Pour la generation automatique de grilles et le stockage dans un fichier grille.txt :

$ java -jar Sudoku.jar -g grille.txt
