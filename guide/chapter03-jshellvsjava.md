
We are using jshell, jshell has mostly the same behavior as Java
but because it's interactive you can do more

you can define a record or a variable several times, jshell will use the most recent one
```java
record Point(int x, int y) { }
var p = new Point(2, 3);
```

```java
record Point(int x, int y) {
  int distanceInX(int anotherX) {
    return Math.abs(x - anotherX);
  }
}
var p = new Point(2, 3);
System.out.println(p.distanceInX(0));
```

and you can also directly write a method outside a record,
it will act as a static method
```java
void hello() {
  System.out.println("hello !");
}
hello();
```


you have a bunch of special command that starts with '/'
you can use /help if you want to know more

By example, you have also a list of the packages automatically imported
```java
/import
```

and you can import new package dynamically
```java
import java.util.zip.*
```

note that unlike import in Pyhton or #include in C, Java import doesn't load any code,
it says that is you search a name of a type, here are the packages to search.
If a type appear in more than one package, an error will be reported and you will
have to import the type explicitly (without any *)
by example, to import a List of the package java.util
```java
import java.util.List;
```
