# Genesis
In Java, there is strong division between primitive types like double that are written in lower case and
objects like String that have a name that starts with an uppercase letter.

## Types
A primitive type is stored as value while an object is stored as
a reference (the address of the object in memory).
In Java, `var` creates a new variable
```java
var maxIntensity = 1.0;   // it's a value
var colorName = "black";  // it's a reference to String somewhere in memory
```

you can also indicate the type instead of `var`
if you are using var, you are asking the compiler to find the type for you.
```java
String colorName = "black";
```

### System.out.println()
To print a value in Java we have a weird incantation `System.out.println()` that we will detail later.
```java
System.out.println(maxIntensity);
```

Primitive types and objects can be printed using the same incantation.
```java
System.out.println(colorName);
```

### Concatenation with +
If we want to print a text followed by a value, we use the operator `+`.
```java
System.out.println("the value of colorName is " + colorName);
```


## A record is a user defined type
here Light is defined as containing two components: a color (typed as a String) and
an intensity (typed as a 64-bit floating number double).
```java
record Light(String color, double intensity) {}
```

### Object creation with `new`
To create an object in memory, we use the operator `new` followed by the value of each record component.
The following instruction creates a Light with "blue" as color and 1.0 as intensity.
```java
var blueLight = new Light("blue", 1.0);
System.out.println(blueLight);
```

### Record methods
To interact with an object in Java, we use methods, that are functions attached to an object.
To call a method, we use the operator `.` followed by the name of the method and its arguments.
A record automatically declares methods to access its components so Light declares two methods
color() and intensity().

By example to get the intensity of the object blueLight
```java
System.out.println(blueLight.intensity());
```

### toString()
By default a record knows how to transform itself into a String
in Java, the method to transform an object to a String is named toString().
In fact, println() calls toString() if the argument is an object
so when using println(), calling explicitly toString() is not necessary.
```java
System.out.println(blueLight.toString());
System.out.println(blueLight);
```

### equals()
In Java, you can ask if two objects are equal, using the method equals(Object).
The return value is a boolean (a primitive type that is either true or false).
```java
var redLight = new Light("red", 0.5);
var redLight2 = new Light("red", 0.5);
System.out.println(blueLight.equals(redLight));
System.out.println(redLight.equals(redLight2));
```

### hashCode()
You can also ask to get an integer summary (a hash) of any objects.
This is used to speed up data structures (hash tables).
Two objects that are equals() must have the same hashCode().
```java
var greenLight = new Light("green", 0.2);
var greenLight2 = new Light("green", 0.2);
System.out.println(greenLight.hashCode());
System.out.println(greenLight2.hashCode());
```


## Summary
A `record` has components that are the parameters used to create an object
To create an object we use the operator `new` followed by the arguments of the
record components in the same order.
To interact with an object, we are using methods that are functions that you
call on an object using the operator `.`.
A Record defines methods to access the value of a component, and also
`toString()` to get the textual representation of an object and
`equals()` and `hashCode()` to test if two objects are equal.
