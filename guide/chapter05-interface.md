
# Interface
Java is a typed language, even if you don't explicitly write a type
the compiler you compute the type of every variables
Once you start to want to mix several records, you need to declare
common type between records, such type are known as interface

### The problem
let say we have a Square and Rectangle, and both have a method surface
```java
record Square(int side) {
  public double surface() {
    return side * side;
  }
}
record Rectangle(int width, int height) {
  public double surface() {
    return width * height;
  }
}
```

let create a list of a square and a rectangle
```java
var figures = List.of(new Square(2), new Rectangle(3, 4));
```

try to loop over the elements of the figures to print the surface doesn't compile
```java
/* for(var figure: figures) {
     System.out.println(figure.surface());
}*/
```

The problem is that compiler try to find the type of the element of the list
and find that they are java.lang.Object, and Object has no method surface()
so it doens't compile

the idea is to introduce a type Figure has a common type for Square and Rectangle
```java
interface Figure {
  public double surface();
}
```

and declare that a Square and a Rectangle are a kind of Surface
using the keyword 'implements'
```java
record Square(int side) implements Figure {
  public double surface() {
    return side * side;
  }
}
record Rectangle(int width, int height) implements Figure {
  public double surface() {
    return width * height;
  }
}
```

Now, the list is correctly typed as a list of figure (List<Figure>)
so looping over the figures to call surface() works
```java
var figures = List.of(new Square(2), new Rectangle(3, 4));
for(var figure: figures) {
  System.out.println(figure.surface());
}
```

An interface is a common type that you need to declare when you want to
call the same method on different records
At runtime, when you call a method of the interface, the interpreter calls
the correct implementation (this is called polymorphism)

Technically, we have already used interfaces, List is an interface too


## Implementing an interface
In Java, not only record can implement an interface, 
you have three other syntax
- anonymous class
- lambda
- method reference

### Anonymous class
```java
var anotherFigure = new Figure() {
  public double surface() {
    return 4;
  }
};
```

An anonymous class allow you to only provide the code of the methods of the interface
note that the syntax is a little weird because you may call new on a Figure but infact,
you ask to create something that implements Figure not a figure by itself.

you may think that this syntax is useless because you can not have the surface computed
from the values of some components like with a record, but if you create an anonymous class
inside a method you can use the parameters of the method inside the anonymous class
```java
Figure rectangularTriangle(int width, int height) {
  return new Figure() {
    public double surface() {
      return width * height / 2.0;
    }
  };
};
```

```java
var figures = List.of(new Square(2), rectangularTriangle(3, 4));
for(var figure: figures) {
  System.out.println(figure.surface());
}
```


### Lambda
In case of the interface is itself an interface with only one abstract method,
we calls that interface a functional interface, you have even a shorter syntax
```java
Figure anotherFigure = () -> 4;
```

and rewrite the method rectangularTriangle() like this
```java
Figure rectangularTriangle(int width, int height) {
  return () -> width * height / 2.0;
}
```

```java
var figures = List.of(new Square(2), rectangularTriangle(3, 4));
for(var figure: figures) {
  System.out.println(figure.surface());
}
```


### Method Reference
In case of the method already exists instead of 
calling it inside a lambda, we can make a reference on it using the operator ::
(notice that EquilaterlaTriangle doesn't implement Figure)
```java
record EquilateralTriangle(int side) {
  double surface() {
    return Math.sqrt(3) * side * side / 4.0;
  }
}
var equilateral = new EquilateralTriangle(2);
```

so instead of
```java
var figures = List.<Figure>of(new Square(2), () -> equilateral.surface());
for(var figure: figures) {
  System.out.println(figure.surface());
}
```

you can use a method reference
```java
var figures = List.<Figure>of(new Square(2), equilateral::surface);
for(var figure: figures) {
  System.out.println(figure.surface());
}
```


More about lambdas and method references in the following chapter
