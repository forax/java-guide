# Generics
Generics are way to propagate types from when a value is created to when the value is used.
Historically, Java did not have a way to specify generics, by backward compatibility,
you can still create a code without generics

## The problem
Let suppose we have a code somewhere to extract credentials from a config file
Note: this code generates two warnings, that we will discuss later.
```java
record Pair(Object first, Object second) { }
List getCredentials() {
  List list = new ArrayList();
  list.add(new Pair("login", "admin"));
  list.add(new Pair("password", "password"));
  return list; 
}
System.out.println(getCredentials());
```

And in another code, we want to use the method `getCredential()`, we may write
a code like this
```java
List list = getCredentials();
String value = (String) list.get(0);
```

This code will compile but fail at runtime, it throws a `ClassCastException`
because in `getCredential()`, we are creating a list of pairs but in the code
above, we try to extract the first value from the list as a String

Here, we have lost the fact that the list returned by `getCredential()`
is a list of pairs.
Generics allows to propagate the type of values stored inside a class or
more generally, allows to declare relations between types

We have said above that the code generate warnings, it's because java.util.List
is declared as a generics class in the JDK API. So the compiler let you use
List as type if you interact with a code written before generics were introduced
to Java (2004) but emits a warning saying you should not declare it that way.  


## Generics
There are two kinds of generics
- parameterized class
- parameterized method


## Parameterized class
A parameterized class is a class that declares type variables
(variable that contains a type) and use them whenever we can use a type.

### Declaration
The type variables are declared after the name of the class.
```java
record Pair<F, S>(F first, S second) { }
```

When used, as a user of the generics you have to specify the type
of each type variable (here F is String and S is Integer)
so pair.first() which is typed as F will return a String and
pair.second() which is typed as S will return an int.
```java
var pair = new Pair<String, Integer>("port", 8080);
String first = pair.first();
int second = pair.second();
```

If you don't understand why in between the '<' and '>',
there is a Integer here and not an int
Don't worry, it's explained in next chapter

So the idea of a generics class is to specify the type arguments `<String, Integer>`
when you use it and the compiler will propagate the types.


### static context
You may have notice that two different instances may have different type arguments
```java
var pair1 = new Pair<String, Integer>("port", 8080);
var pair2 = new Pair<String, String>("captain amarica", "shield");
```

So the type variable (F and S) are not available inside a static method of Pair.
A static method is called on the class `Pair.hello()`, not on an instance.
```java
record Pair<F, S>(F first, S second) {
  static void hello() {
    // can not access F and S here !
  }
}
```


## Parameterized methods
Like a class, a method can be parameterized, by declaring the type variables
in between `<` and `>` before the return type
So instead of
```java
Object chooseOne(Object o1, Object o2) {
  var random = ThreadLocalRandom.current();
  return random.nextBoolean()? o1: o2;
}
/*
  String s = chooseOne("day", "night");
*/
```

We can write
```java
<T> T chooseOne(T o1, T o2) {
  var random = ThreadLocalRandom.current();
  return random.nextBoolean()? o1: o2;
}
String s = chooseOne("day", "night");
System.out.println(s);
```


## Inference
So, we can now rewrite `getCredentials()`, to correctly type it
```java
List<Pair<String, String>> getCredentials() {
  List<Pair<String, String>> list = new ArrayList<Pair<String, String>>();
  list.add(new Pair<String, String>("login", "admin"));
  list.add(new Pair<String, String>("password", "password"));
  return list; 
}
System.out.println(getCredentials());
```

but it's quite verbose, so in Java, we have a mechanism called __inference__
to let the compiler try to guess itself the type arguments instead of
having to specify them by hand

### inference of variable local type
The keyword `var` ask the compiler to find the type of the left of `=`
from the type of the right of `=`.
So instead of
```java
List<Pair<String, String>> list = new ArrayList<Pair<String, String>>();
System.out.println(list);
```

using `var` we get
```java
var list = new ArrayList<Pair<String, String>>();
System.out.println(list);
```

### inference when using `new`
You can ask the compiler to find the type using the left type and the arguments
using the diamond syntax `<>`.
So instead of
```java
Pair<String, String> pair = new Pair<String, String>("login", "admin");
```
using the diamond syntax `<>`
```java
Pair<String, String> pair = new Pair<>("login", "admin");
System.out.println(pair);
```

The left type can be also found when you do a `return` 
```java
Pair<String, String> getOnePair() {
  return new Pair<>("login", "admin");
}
System.out.println(getOnePair());
```

or using the type of the parameter of the method
```java
var list = new ArrayList<Pair<String, String>>();
list.add(new Pair<>("login", "admin"));
```

You may wonder what if we are using `var` and the diamond `<>` at the same time
When the inference doesn't known, it using `Object`
```java
var objectList = new ArrayList<>();   // this is a list of Object
```
 
### inference of parameterized method
Type arguments of a parameterized method are inferred by default and we have
to use a special syntax if we want to specify the type arguments

That's why when we have
```java
class Utils {
  static <T> T chooseOne(T o1, T o2) {
    var random = ThreadLocalRandom.current();
    return random.nextBoolean()? o1: o2;
  }
}
```

we can write
```java
System.out.println(Utils.chooseOne("foo", "bar"));
```

and if we want to specify the type arguments, you have to
specify them in between `<` and `>`, after the `.` and
before the name of the method
```java
System.out.println(Utils.<String>chooseOne("foo", "bar"));
```


### Raw type
Types without the '<' '>', raw types in Java speak, are still supported
to interact with old codes so you may by mistake forget the '<' '>' and
have the declaration to compile.
But it will be nasty when trying to use such type.

The for-loop below doesn't compile because StringList is an AbstractList
so a List of Object and not a List<String>
```java
class StringList extends AbstractList {  // should be AbstractList<String>
  public int size() {
    return 5;
  }
  public String get(int index) {
    Objects.checkIndex(index, 5);
    return "" + index;
  }
}
for(String s: new StringList());
```


### So using inference
`getCredentials()` can be simplified to
```java
List<Pair<String, String>> getCredentials() {
  var list = new ArrayList<Pair<String, String>>();
  list.add(new Pair<>("login", "admin"));
  list.add(new Pair<>("password", "password"));
  return list; 
}
var list = getCredentials();
/*
 String value = (String) list.get(0);
*/
```

And the last line (commented) that was throwing a ClassCastException
now does not compile thank to the use of generics.


## Bounds
By default a type variable like `T` acts as Object, i.e.
you can call on T only the public methods of java.lang.Object.
you can defines a more precise __bound__ reusing the keyword `extends`
Note: `extends` in this context mean subtype not inherits from.

```java
<T extends Comparable<T>> T min(T o1, T o2) {
  return (o1.compareTo(o2) < 0)? o1: o2;
}
System.out.println(min("day", "night"));
```

Because the bound of T is an object, you can not declare a List<int> !
But you can declare a List<Integer> instead, see the next chapter for that !
