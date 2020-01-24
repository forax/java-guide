# Methods
Methods are action to execute on an object

The method syntax starts with the return type, the name of the method,
then its parameters
The first parameter named `this` is a special keyword indicating the value
before the `.`
The keyword `return` indicates the return value of the method.
```java
record Rectangle(int width, int height) {
  boolean hasTheSameHeight(Rectangle this, Rectangle rectangle) {
    return this.height == rectangle.height;
  }
}
```

A method is called on an instance (an object) of a record
here rectangle1 and rectangle2 are instances of Rectangle
```java
var rectangle1 = new Rectangle(2, 3);
var rectangle2 = new Rectangle(4, 3);
System.out.println(rectangle1.hasTheSameHeight(rectangle2));
```

The value before the `.`, here rectangle1, is called the receiver,
and is stored as the parameter `this` for the method hasTheSameHeight(). 

If you don't declare `this` as first parameter, it is declared implicitly
so the record Rectangle below is equivalent to the record Rectangle above
```java
record Rectangle(int width, int height) {
  boolean hasTheSameHeight(Rectangle rectangle) {  // implicit this
    return this.height == rectangle.height;
  }
}
```

In Java, it's very unusual to declare `this` explicitly.
Moreover, if inside a method you access to a variable which is not a parameter
it will be automatically prefixed by 'this.', so the code can be simplified to
```java
record Rectangle(int width, int height) {
  boolean hasTheSameHeight(Rectangle rectangle) {
    return height == rectangle.height;   // no this.height needed !
  }
}
var rectangle1 = new Rectangle(2, 3);
var rectangle2 = new Rectangle(4, 3);
System.out.println(rectangle1.hasTheSameHeight(rectangle2));
```


### void
There is also a special type named 'void' if the method return no value
```java
record Rectangle(int width, int height) {
  void hello() {
    System.out.println("hello i'm " + width + " x " + height);
  }
}
new Rectangle(4, 5).hello();
```


## instance methods vs static methods
In Java, there are two kinds of methods, the one attached to an instance (the 'this')
that are called 'instance' methods and the one that are independent of an instance
that are called 'static' method. A static method is prefixed by the keyword static.

Unlike an instance method, a static method has no 'this'
by example, we can create a static method createSquare() that create a Rectangle
with the same width and the same height

```java
record Rectangle(int width, int height) {
  int area() {
    return width * height;
  }
  static Rectangle createSquare(int side) {
    return new Rectangle(side, side);
  }
}
```

because a static method is independent of an instance, you can not access to the
record component inside a static method because the value of the component depends
on the instance (rectangle1 and rectangle2 has not the same width or height).

To call a static method, you call it on the record name
```java
 var square1 = Rectangle.createSquare(3);
 System.out.println(square1.area());
```
 
 
```java
 // ### static methods are useful to share code
 // by example, to calculate the length of the diagonal of a Rectangle, one can write
record Rectangle(int width, int height) {
  double diagonal() {
    return Math.sqrt(width * width + height * height);
  }
}
var rectangle2 = new Rectangle(4, 3);
System.out.println(rectangle2.diagonal());
```

it can also be written using a static method `pow2()` to share some code
```java
record Rectangle(int width, int height) {
  double diagonal() {
    return Math.sqrt(pow2(width) + pow2(height));
  }
  static int pow2(int value) {
    return value * value;
  }
}
var rectangle2 = new Rectangle(4, 3);
System.out.println(rectangle2.diagonal());
```


In fact, there is already a static method named hypot in java.lang.Math
that computes the hypotenuse, so Rectangle can be written like this
```java
record Rectangle(int width, int height) {
  double diagonal() {
    return Math.hypot(width, height);
  }
}
var rectangle2 = new Rectangle(4, 3);
System.out.println(rectangle2.diagonal());
```


