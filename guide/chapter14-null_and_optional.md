# Null and Optional

In Java, any reference can be null which usually means that there is no object.
Trying to call a method or a field on null throws a `NullPointerException`.
It's not rare for a program written by a beginner to randomly stop due to a
`NullPointerException`.

Conceptually, there are two ways to avoid a null to be dereferenced,
either protect against null each time you read a value, or each time you write a value
By example, with
```java
record Person(String name) {
  public String toString() {
    return "hello " + name.toString(); 
  }
}
```

You get an exception if you write
```java
System.out.println(new Person(null));
```

A stupid idea is to try to guard against all reads
```java
record Person(String name) {
  public String toString() {
    if (name == null) {
      return "hello unnamed"; 
    }
    return "hello " + name.toString(); 
  }
}
System.out.println(new Person(null));
```

It forces you to define a meaning of a name being null.

It's easier to refuse to create a Person with null as name.
```java
record Person(String name) {
  public Person {   // it's a compact constructor
    Objects.requireNonNull(name);
  }
  public String toString() {
    return "hello " + name.toString(); 
  }
}
new Person(null);
```

You may think that we have trade a `NullPointerException` to a `NullPointerException`.
But there is a big difference, the former code was throwing a `NullPointerException`
only when toString() is called, so depending on how the class `Person` was used,
a `NullPointerException` is thrown __randomly__.
The latter code throws a `NullPointerException` if you dare to try to create
a `Person` with a name null so a `NullPointerException` is thrown __consistently__.

So in Java, the idea to avoid the spurious is `NullPointerException` to never let
a user code to get a null
- do not allow to create an object with null fields
- never return null from a method (use an Optional or an empty collection instead)


## Defensive programming

The best way to not store null in a field (or a record component) is to reject any attempt
to call a public method with null as argument. So any public methods should call
`Objects.requireNonNull()` on all their arguments that are references.
```java
record Animal(String kind, boolean wild) {
  public Animal {
    Objects.requireNonNull(kind);
    // no need to do a requireNonNull on 'wild', a boolean can not be null
  }
  public boolean isDangerousWith(Animal animal) {
    Objects.requireNonNull(animal);
    return wild || !kind.equals(animal.kind);
  }
}
new Animal(null, true);
```

### Map.get()

You may sometimes want to pass null to a public method or return null from a method
but it should be an exceptional case and it should be documented

A good example is `Map.get(key)` that is used a lot and is specified to return `null`
if the key is not stored in the map. refer to use `Map.getOrDefault()` instead
```java
var map = Map.of("John", 5, "Paul", 7);
System.out.println(map.get("Lena"));
System.out.println(map.getOrDefault("Lena", 0));
```

See chapter 'list and map' for more information.


## Optional
Optional is a special class which means that a return value of a method may not be present.
Unlike a usual object type that can be null, an Optional can be present or empty.
It forces the user code be prepared to handle an empty Optional.

In the following code a `Car` has a color and optionally has a driver
```java
public class Car {
  private final Person driver;  // may be null
  private final String color;
  public Car(String color, Person driver) {
    this.color = Objects.requireNonNull(color);
    this.driver = driver;  // may be null
  }
  public String color() {
    return color;
  }
  public Optional<Person> driver() {
    return Optional.ofNullable(driver);
  }
}
```

Trying to call a method of `Person` on an `Optional`, obviously doesn't work
```java
var car = new Car("red", null);
var name = car.driver().name(); // doesn't compile
```


so the user code as to be changed to handle `Optional`, and the fact that
an `Optional` can be empty
```java
var car = new Car("red", null);
var name = car.driver().map(Person::name).orElse("autopilot");
System.out.println(name);
```

> Don't use Optional for anything else than a return type
> Never store null in an Optional because it defeats its purpose.


