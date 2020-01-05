
# A record is a user defined type
here Light is defined as containing two components: a color (typed as a String) and
an intensity (typed as a 64 bits floating number double).
```java
record Light(String color, double intensity) {}
```

In Java, there is strong division between primitive types like double that are written in lower case and
objects like String or Light that have a name that starts with an uppercase letter.

A primitive type is stored as value while an object is stored as
a reference (the address of the object in memory).
In Java, `var` create a new variable
```java
var maxIntensity = 1.0;   // it's a value
var colorName = "black";  // it's a reference to String somewhere in memory
```

you can also indicate the type instead of `var`
if you are using var, you are asking the compiler to find the type for you.
```java
String colorName = "black";
```


System.out.println()
To print a value in Java we have a weird incantation `System.out.println()` that we will detail later.
```java
System.out.println(maxIntensity);
```

Primitive types and objects can be printed using the same incantation.
We will see later its exact meaning
```java
System.out.println(colorName);
```

## Concatenation with +
If we want to print a text followed by a value, we use the operator `+`.
```java
System.out.println("the value of colorName is " + colorName);
```

To create an object in memory, we use the operator `new` followed by the value of each record components
the following instruction create a Light with "blue" as color and 1.0 as intensity.
```java
var blueLight = new Light("blue", 1.0);
```

To interact with an object in Java, we use methods, that are functions attached to an object.
To call a method, we use the operator `.` followed by the name of the method and its arguments.
A record automatically declares methods to access its components so Light declares two methods
color() and intensity().

By example to get the intensity of the object blueLight
```java
var blueLightIntensity = blueLight.intensity();
System.out.println(blueLightIntensity);
```

## toString()
By default a record knows how to transform itself into a String
in Java, the method to transform an object to a String is named toString().
```java
System.out.println(blueLight.toString());
```

In fact, println() calls toString() if the argument is an object
so when using println(), calling explicitly toString() is not necessary.
```java
System.out.println(blueLight);
```

## equals()
Let's create another Light
```java
var redLight = new Light("red", 1.0);
```

In Java, you can ask if two objects are equals, using the method equals(Object).
the return value is a boolean (a primitive type that is either true or false).
```java
System.out.println(blueLight.equals(redLight));
```

Let's create another red light
```java
var anotherRedLight = new Light("red", 1.0);
System.out.println(redLight.equals(anotherRedLight));
```

## hashCode()
You can also ask to get an integer summary (a hash) of any objects.
This is used to speed up data structures (hash tables).
Two objects that are equals() must have the same hashCode().
```java
System.out.println(redLight.hashCode());
System.out.println(anotherRedLight.hashCode());
```


# Summary
A `record` has components that are the parameters used to create an object
To create an object we use the operator `new` followed by the arguments of the
record components in the same order.
To interact with an object, we are using methods that are functions that you
call on an object using the operator `.`.
A Record defines methods to access the value of a component, and also
`toString()` to get the textual representation of an object and
`equals()` to test if two objects are equals.
