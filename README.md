# OverlayMsg (English)

## Presentation
Simply display messages over your Android app to explain its functioning. 
Only 2 files to add to your project, 2 lines of code to a first display.
![Sample screenshot](http://www.sdangin.fr/git/screenshot4.png)

## Setup
1. Add the file OverlayMsg.java to your project sources. 
2. Add the file Overlay_msg.xml in the res/values​​/ directory of your project.
3. The main container must be a ```FrameLayout```

## Code examples
[From the XML file of the Sample Project.](https://github.com/Sylvaner/OverlayMsg/blob/master/SampleProject/res/layout/activity_main.xml)

![Base screenshot](http://www.sdangin.fr/git/base.png)

###  First example
* Display a message.
* In the middle of the screen.
* Change text size.
* Surround a TextView with a circle.

```java
OverlayMsg ovm = new OverlayMsg(this);
ovm.textSize = convertSpToPixel(40);
ovm.showTextWithCircle(R.id.globalLayout, R.id.textView1, "First text view", OverlayMsg.POSITION_CENTER, null);
```

![Premier exemple](http://www.sdangin.fr/git/screenshot1.png)

# OverlayMsg (Français)

## Présentation
Afficher simplement des messages par dessus votre application Android pour expliquer son fonctionnement.
Seulement 2 fichiers à ajouter à votre projet, 2 lignes de code pour un premier affichage.
![Capture d'écran d'un exemple](http://www.sdangin.fr/git/screenshot4##ng)

## Installation
1. Ajoutez le fichier OverlayMsg.java aux sources de votre projet.
2. Ajoutez le fichier overlay_msg.xml dans le répertoire res/values/ de votre projet.
3. Le container principal doit être un ```FrameLayout```

## Exemples de code
[A partir du fichier XML du projet d'exemple (SampleProject)](https://github.com/Sylvaner/OverlayMsg/blob/master/SampleProject/res/layout/activity_main.xml)

![Base screenshot](http://www.sdangin.fr/git/base.png)

###  Premier exemple
* Afficher un message.
* Au milieu de l'écran.
* Changer la taille du texte.
* Entourer un TextView avec un cercle.

```java
OverlayMsg ovm = new OverlayMsg(this);
ovm.textSize = convertSpToPixel(40);
ovm.showTextWithCircle(R.id.globalLayout, R.id.textView1, "First text view", OverlayMsg.POSITION_CENTER, null);
```

![Premier exemple](http://www.sdangin.fr/git/screenshot1.png)
