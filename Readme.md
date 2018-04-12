# BubbleGum - Sweet Android Gradients

[ ![Download](https://api.bintray.com/packages/crysis21/Android/BubbleGum/images/download.svg) ](https://bintray.com/crysis21/Android/BubbleGum/_latestVersion)

BubbleGum was designed to quickly apply gradients into different UI elements of an android app. The overhead and performance impact is minimal, as BubbleGum is based on Android drawables.  

#### Features
* Apply gradient to a view with an animation effect or transition between 2 gradients.
* Created an animated view with a set of gradients that interchange at the specified amount of time.
* It applies a vignetting effect over the gradient to enhance the view. (will become optional in the future)
* Control gradient angle
* Apply the gradient colors at custom positions. If custom positions are not specified, they will be distributed equally.


#### Demo
[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/pY5ek5JTmZE/0.jpg)](https://www.youtube.com/watch?v=pY5ek5JTmZE)

Or give it a try with the sample app:
https://play.google.com/store/apps/details?id=com.hold1.bubblegum


#### Installation
Add the following line in your build.gradle file

```gradle
implementation 'com.hold1:bubblegum:0.0.1'
```

#### Usage

BubbleGum can be used in 3 ways, depending of your needs:

###### 1. Embed a `BubbleView` into your layout and use it as a canvas for displaying other views on top of it. This is the easiest way if you are trying to achieve immediate results from your xml layout.

```xml
    <com.hold1.bubblegum.BubbleView
        android:id="@+id/headerBackground"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:bbStartColor="@color/demoStartColor"
        app:bbEndColor="@color/demoEndColor"
        app:bbAngle="45"/>
```

You can always change the applied gradient of a `BubbleView` programatically. Please refer to `SlideFragment` in Demo app.

###### 2. Create an `GradientDrawable` and use it. 

```kotlin
    val g1 = Gradient(intArrayOf(Color.RED, Color.GREEN))
    val g2 = Gradient(intArrayOf(Color.DKGRAY, Color.BLUE))
    val g3 = Gradient(intArrayOf(Color.CYAN, Color.MAGENTA))
    
    val animation = GradientDrawable(arrayOf(g2, g1, g3))
    baseView.background = animation
        
    animation.start()
```

###### 3. Use the `BubbleBuilder` class to apply a gradient as a background to an existing view

```kotlin

    BubbleBuilder()
            .withStartColor(Color.RED)
            .withEndColor(Color.BLUE)
            .withAngle(35)
            .intoView(exampleView)
```
