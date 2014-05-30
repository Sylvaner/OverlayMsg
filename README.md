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

### Second example 
* Display a message. 
* At the top of the screen. 
* Add a black background to the text. 
* Surround a group of radio buttons with a rectangle.

```java
OverlayMsg ovm = new OverlayMsg(this);
ovm.showTextBackground = true;
ovm.textBackgroundColor = 0xFF000000;
ovm.showTextWithRect(R.id.globalLayout, R.id.radiogroup, "A radio group", OverlayMsg.POSITION_TOP, null);
```

![Second example](http://www.sdangin.fr/git/screenshot2.png)

### Third example
* Display a message.
* At the bottom of the screen.
* Surround a radio button in a circle.
* With a green border.
* And a thickness of 50 pixels.

```java
OverlayMsg ovm = new OverlayMsg(this);
ovm.borderColor = 0xFF00FF00;
ovm.borderSize = 50;
ovm.showTextWithCircle(R.id.globalLayout, R.id.radio1, "This things", OverlayMsg.POSITION_BOTTOM, null);
```

![Third example](http://www.sdangin.fr/git/screenshot3.png)

### Fourth example
* Display a message.
* At the top of the screen.
* With a special font.
* A dimension 50sp.
* Surround two CheckBox with a large rectangle.

```java
OverlayMsg ovm = new OverlayMsg(this);
Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/alex-toth.ttf");
ovm.textFont = typeface;
ovm.textSize = convertSpToPixel(50);
ovm.showTextWithBigRect(R.id.globalLayout, R.id.checkbox1, R.id.checkbox2, "A big box", OverlayMsg.POSITION_TOP, null);
```

![Quatrième exemple](http://www.sdangin.fr/git/screenshot4.png)

### Fifth example
* Display a message.
* In the middle of the screen.
* Surround three different elements.
* Using different forms.
* Show a message to the action of a user.

```java
OverlayMsg ovm = new OverlayMsg(this);
int[] viewIdArray = new int[3];
viewIdArray[0] = R.id.textView1;
viewIdArray[1] = R.id.button2;
viewIdArray[2] = R.id.button5;
int[] shapeArray = new int[3];
shapeArray[0] = OverlayMsg.SHAPE_CIRCLE;
shapeArray[1] = OverlayMsg.SHAPE_RECTANGLE;
shapeArray[2] = OverlayMsg.SHAPE_CIRCLE;

ovm.showTextWithMultiple(
	R.id.globalLayout, 
	viewIdArray, 
	shapeArray, 
	"A lot of things", 
	OverlayMsg.POSITION_CENTER, 
	new OverlayMsg.Event() {
		public void event() {
			Toast.makeText(getApplicationContext(), "Final sample", Toast.LENGTH_LONG).show();
		}
	});
```

![Cinquième exemple](http://www.sdangin.fr/git/screenshot5.png)

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

### Deuxième exemple
* Afficher un message.
* En haut de l'écran.
* Ajouter un fond noir à ce texte.
* Entourer un groupe de boutons radio avec un rectangle.

```java
OverlayMsg ovm = new OverlayMsg(this);
ovm.showTextBackground = true;
ovm.textBackgroundColor = 0xFF000000;
ovm.showTextWithRect(R.id.globalLayout, R.id.radiogroup, "A radio group", OverlayMsg.POSITION_TOP, null);
```

![Deuxième exemple](http://www.sdangin.fr/git/screenshot2.png)

### Troisième exemple
* Afficher un message.
* En bas de l'écran.
* Entourer un bouton radio d'un cercle.
* Avec un bord vert.
* Et une épaisseur de 50 pixels.

```java
OverlayMsg ovm = new OverlayMsg(this);
ovm.borderColor = 0xFF00FF00;
ovm.borderSize = 50;
ovm.showTextWithCircle(R.id.globalLayout, R.id.radio1, "This things", OverlayMsg.POSITION_BOTTOM, null);
```

![Troisième exemple](http://www.sdangin.fr/git/screenshot3.png)

### Quatrième exemple
* Afficher un message.
* En haut de l'écran.
* Avec une police de caractère spéciale.
* Une dimension de 50sp.
* Entourer deux CheckBox d'un grand rectangle.

```java
OverlayMsg ovm = new OverlayMsg(this);
Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/alex-toth.ttf");
ovm.textFont = typeface;
ovm.textSize = convertSpToPixel(50);
ovm.showTextWithBigRect(R.id.globalLayout, R.id.checkbox1, R.id.checkbox2, "A big box", OverlayMsg.POSITION_TOP, null);
```

![Quatrième exemple](http://www.sdangin.fr/git/screenshot4.png)

### Cinquième exemple
* Afficher un message.
* Au milieu de l'écran.
* Entourer trois éléments différents.
* Avec des formes différentes.
* Afficher un message à l'action d'un utilisateur.

```java
OverlayMsg ovm = new OverlayMsg(this);
int[] viewIdArray = new int[3];
viewIdArray[0] = R.id.textView1;
viewIdArray[1] = R.id.button2;
viewIdArray[2] = R.id.button5;
int[] shapeArray = new int[3];
shapeArray[0] = OverlayMsg.SHAPE_CIRCLE;
shapeArray[1] = OverlayMsg.SHAPE_RECTANGLE;
shapeArray[2] = OverlayMsg.SHAPE_CIRCLE;

ovm.showTextWithMultiple(
	R.id.globalLayout, 
	viewIdArray, 
	shapeArray, 
	"A lot of things", 
	OverlayMsg.POSITION_CENTER, 
	new OverlayMsg.Event() {
		public void event() {
			Toast.makeText(getApplicationContext(), "Final sample", Toast.LENGTH_LONG).show();
		}
	});
```

![Cinquième exemple](http://www.sdangin.fr/git/screenshot5.png)

