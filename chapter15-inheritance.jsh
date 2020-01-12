// To starts, run jshell --enable-preview which is a program able to interpret Java syntax
// then cut and paste the following lines to see how it works
// To exit jshell type /exit

// # Inheritance

// Historically, inheritance have been the way to sell Object Oriented Programming.
// It appears later that, inheritance is less important that it was envision first.

// The initial idea is to be able to reuse part of the definition of one class `A`
// to define another class `B` which is a kind of like `A`.
class A {
}
class B extends A {  // I want to reuse and augment the definition of A
}

// Precisely, inheritance is 3 different things grouped together 
// - __subtyping__
//   everywhere something is typed `A`, you can send a `B` instead
// - __members inheritance__
//   all instance members of `A` are copied in `B`
// - __polymorphism__
//   you can replace the code of a method from `A` to adapt it to `B`

// ## Subtyping
// Subtyping is the most important part of the inheritance, it allows to reuse
// an existing code written for an `A` with an instance of `B`.

// let suppose, I have a method `sayHello()` for a `A`
class A {
}
void sayHello(A a) {
  System.out.println("hello " + a);
}
var a = new A();
sayHello(a);

// if I create a `B` that inherits `A`, then i can use instance of `B`
// as argument of `hello()`.
class B extends A {  // so B is a subtype of A
}
var b = new B();
sayHello(b);

// __subtyping__ is very important, because it means that we can reuse a
// method by calling it with several different types. And given that,
// The more a method is used, the less buggy it is, _subtyping_ helps
// to make applications more robust by sharing methods.


// ## Polymorphism
// Polymorphism works with __subtyping__, __subtyping__ allow to call a
// code with a subtype of the declared type. Polymorphism allows to
// adapt parts of the shared code to the subclass at runtime.

// By example, let suppose we have a class able to 'enhance' a text
// by making it more beautiful
class Enhancer {
  String enhance(String text) {
    return "_" + text + "_";
  }
}
void sayHello(Enhancer enhancer, String text) {
  System.out.println("hello " + enhancer.enhance(text));
}
var enhancer = new Enhancer();
sayHello(enhancer, "polymorphism");
 
// 
class StarEnhancer extends Enhancer {
  String enhance(String text) {
    return "*" + text + "*";
  }
}
var enhancer = new StarEnhancer();
sayHello(enhancer, "polymorphism");


// So not only we can call `sayHello()` with a `StarEnhancer` (__subtyping__),
// but inside `sayHello()`, the method call to `enhance()` will call
// the methode `StarEnhancer.enhance()` adapting the code of `hello()`
// to the fact that at runtime the enhancer is in fact a `StarEnhancer`.

// The mechanism that choose the `right` method in function of the object
// at runtime is called __polymorphism__.


// ### Overriding
// In the example above, `enhancer.enhance()` inside the method `sayHello()`
// can call `Enhancer.enhance()` or `StarEnhancer.enhance()`.
// We say that the method `enhance()` of  `StarEnhancer` __overrides__
// the method `enhance()` of  `Enhancer`.

// A method to __override__ another has to
// - have the same name
// - have the same number of parameter
// - can have a subtype as return type
// - can have subtypes of the declared exceptions (`throws`).  


// ### `@Override`
// You can notice in the code below that we are using the annotation
// `@Override`. It is an annotation to document that the method
// override an existing method. The compiler also verifies that
// there is  a method in the base class with the same parameter types.
class Enhancer {
  String enhance(String text) {
    return "_" + text + "_";
  }
}
void sayHello(Enhancer enhancer, String text) {
  System.out.println("hello " + enhancer.enhance(text));
}
class StarEnhancer extends Enhancer {
  @Override  // <-- aah
  String enhance(String text) {
    return "*" + text + "*";
  }
}
var enhancer = new StarEnhancer();
sayHello(enhancer, "polymorphism");

// The annotation is not used by the runtime so it just make
// the code easier to understand for a human.


// ### Calling a method using `super.`
// The method that override another one can call the method it replace
// using the syntax `super.enhance(...)`.
class Enhancer {
  String enhance(String text) {
    return "_" + text + "_";
  }
}
class StarEnhancer extends Enhancer {
  @Override
  String enhance(String text) {
    return "*" + super.enhance(text) + "*";
  }
}
var enhancer = new StarEnhancer();
sayHello(enhancer, "polymorphism");


// ## Members inheritance
// And last, when a class inherits from another one, then
// all the fields and methods defined in the super class
// are defined in the subclass.

// Here by example, the field `name` defined in `Animal`
// is also _implicily_ defined in `Lion`.
class Animal {
  String name;
}
class Lion extends Animal {
  boolean young;
  void roar() {
    System.out.println(name + " roar");
  }
}
var lion = new Lion();
lion.name = "leo";
lion.young = true;
lion.roar();

// This mechanism is controversial because if the implementation of
// `Animal` change, the implementation of `Lion` has to be changed too.
// So a super class and a subclass are tightly bound to the point
// it's hard to maintain an application if the maintainer of the super class
// and the sub class are not the same person.

// > It's discouraged to inherits from a class you don't control.


// ### Constructor and inheritance
// In Java, the initialization of the super class has to be done first,
// before the initialization of the subclass.
// It's mandatory that the first statement of the constructor of the subclass
// to call the constructor of the super class.

// With a class `Point`
class Point {
  private final int x;
  private final int y;
  Point(int x, int y) {
    this.x = x;
    this.y = y;
  }
  public String toString() {
    return "x: " + x + " y: " + y;
  }
}
var point = new Point(1, 5);
System.out.println(point);

// If the class `Point3D` inherits from `Point`, then the first statement of
// the constructor has to be a call to the constructor of the super class
// using the syntax `super(...)`.
class Point3D extends Point {
  private final int z;
  Point3D(int x, int y, int z) {
    super(x, y);  // call constructor of the super class
    this.z = z;
  }
  public String toString() {
    return super.toString() + " z: " + z;
  }
}
var point3D = new Point3D(2, 4, 7);
System.out.println(point3D);

// > Note: that unlike the other members of a class, constructors are not inherited.


// ## Members inheritance and encapsulation
// We have seen that to have encapsulation, we have to declare the fields `private`.
// But with inheritance, a private field declared in the super class is present
// in the subclass but not accessible.

// Here the field `roomPrice` is inherited in `Palace` but can not be accessed
// in the method `price()` of the class `Palace` which doesn't compile 
class Hotel {
  private final int roomPrice;
  public Hotel(int roomPrice) {
    this.roomPrice = roomPrice;
  }
  public int price(int rooms) {
    return rooms * roomPrice;
  }
}
class Palace extends Hotel {
  private final int extra;
  public Palace(int roomPrice, int extra) {
    super(roomPrice);
    this.extra = extra;
  }
  public int price(int rooms) {
    return rooms * (roomPrice + extra);  // don't compile !
  }
}

// The usual practice is to declare the super class and the subclass
// in the same package so declaring the field `roomPrice` with no keyword
// make it visible to the subclass
class Hotel {
  /*package*/ final int roomPrice;
  public Hotel(int roomPrice) {
    this.roomPrice = roomPrice;
  }
  public int price(int rooms) {
    return rooms * roomPrice;
  }
}
class Palace extends Hotel {
  private final int extra;
  public Palace(int roomPrice, int extra) {
    super(roomPrice);
    this.extra = extra;
  }
  public int price(int rooms) {
    return rooms * (roomPrice + extra);
  }
}
var palace = new Palace(100, 50);
System.out.println(palace.price(2));


// ### Field protected
// In the code above, one can use the modifier `protected` too but
// because a `protected` field is visible by any subclass even the one
// the author of the subclass do not control. It means that the field
// can not be changed the same way a `public` field can not be changed.

// > Never use the keyword protected in Java.


// ## Relation with interfaces
// Nowadays, inheritance is used less and less in Java because interface
// provides __subtyping__ and __overriding__ without __members inheritance__.
// Given that the later mechanism is the one causing trouble,
// using an interface is often preferred to using inheritance.

// ### Records doesn't support inheritance
// Records doesn't support inheritance because it's so simple to declare
// a new record component that trying to share them will result into more
// code that necessary.

// Here is the class `Hotel` and `Palace` rewritten without inheritance
interface Bookable {
  int price(int rooms);  
}
record Hotel(int roomPrice) implements Bookable {
  public int price(int rooms) {
    return rooms * roomPrice;
  }
}
record Palace(int roomPrice, int extra) implements Bookable {
  public int price(int rooms) {
    return rooms * (roomPrice + extra);
  }
}
Bookable hotel = new Hotel(100);
System.out.println(hotel.price(2));
Bookable palace = new Palace(100, 50);
System.out.println(palace.price(2));
 

// ## Use delegation not inheritance
// Sometimes people are using inheritance where they should not !
// The worst occurrences is when people want __members inheritance__
// to avoid to write too many methods but forget that they get
// all the methods even the one they don't want.

// The problem is that if a class has a lot of methods, you are sure
// that at least one will not work correctly with the subclass.

// By example, this is a snippet of how the class `java.util.Properties`
// is defined in JDK. Because it inherits from `Hashtable<Object, Object>`,
// it means you can store a value which is not a String but get it as a String.

// Obviously, it will not work at runtime
class Properties extends Hashtable<Object, Object> {
  public String getProperty(String key, String defaultValue) {
    Objects.requireNonNull(key);
    return (String)getOrDefault(key, defaultValue);
  }
  public void setProperty(String key, String value) {
    Objects.requireNonNull(key);
    Objects.requireNonNull(value);
    put(key, value);
  }
}
var properties = new Properties();
properties.put("java", 42);
System.out.println(properties.getProperty("java", "??"));

// The traditional advice is if you want to use part of an existing
// implementation, instead of inherits from that class, store it
// in a field and so your method can delegate a part of their implementations.

// So for the class `Properties`, it should be implemented like this
class Properties {
  private final HashMap<String, String> map = new HashMap<>();
  public String getProperty(String key, String defaultValue) {
    Objects.requireNonNull(key);
    return map.getOrDefault(key, defaultValue);
  }
  public void setProperty(String key, String value) {
    Objects.requireNonNull(key);
    Objects.requireNonNull(value);
    map.put(key, value);
  }
}
var properties = new Properties();
properties.setProperty("java", "best language ever, for life !");
System.out.println(properties.getProperty("java", "??"));
System.out.println(properties.getProperty("brainfuck", "??"));


// > Always prefer delegation to inheritance
