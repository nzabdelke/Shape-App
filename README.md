# Shape-App

## General Info 
In this project, I used several Object-Oriented Design Principles such as inheritance, polymorphism, and interfaces. Furthermore, I created my own LinkedList and Node classes in Java in order to ameliorate my understanding of algorithm complexity analysis and basic data structures.  

## How to Use Application
This program will allow you to enter a colour for a shape (orange or blue) and select either an ellipse or a rectangle. Once the shape and colour are selected, you can draw the shape with its selected colour on the panel titled “Draw Here”. When a shape is drawn, it will be stored in my own LinkedList class and will be shown in the left panel titled “List of Shapes”. This acts as a basic database that displays the coordinates of the shape. A shape can be removed from the "List of Shapes" panel by selecting it and clicking the “Remove Data” button. The shape will also be removed from the “Draw Here” panel. You can also clear all the drawings from both panels by pressing the “Clear Drawing” button. The application is capable of restoring the data (list of shapes and drawn shapes) via the “Restore Data” button, which retrieves the data from the LinkedList.

## Setup
To run this project, please cd into the project directory. Then compile AppViewer.java to run the Java program. 

```
$ cd ../Shape-App
$ javac AppViewer.java
$ java AppViewer
```