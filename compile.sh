#!/bin/bash

# 1. Nettoyage et préparation
rm -rf build doc
rm -f JeuTicTacToeAfric.jar
mkdir -p build/view

# 2. Compilation
echo " Compilation en cours..."
javac -d build -sourcepath src src/*.java src/**/*.java

# 3. Copie des ressources
cp src/view/*.png build/view/
cp -r src/sounds build/sounds

# 4. Génération de la Javadoc (La nouveauté)
echo " Génération de la Javadoc..."
javadoc -d doc \
        -sourcepath src \
        -cp build \
        -subpackages controllers:model:utils:view \
        -tag requires:a:"Requires:" \
        -tag ensures:a:"Ensures:" \
        src/Main.java src/TestViewGraphique.java

# 5. Création du JAR
echo "Main-Class: TestViewGraphique" > manifest.txt
jar cvfm dist/JeuTicTacToeAfric.jar manifest.txt -C build .
rm manifest.txt

echo "---------------------------------------"
echo " JAR généré avec succès : dist/JeuTicTacToeAfric.jar"
echo " Documentation générée dans /doc généree avec succès"
echo "---------------------------------------"

# 6. Lancement du test
echo " Lancement du jeu..."
java -jar dist/JeuTicTacToeAfric.jar