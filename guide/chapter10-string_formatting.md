# String formatting
There are several ways to concatenate/format objects to strings in Java,
mostly depending if there are a fixed number of values or
if the values are in a list or any other data structures.

Let say we have some friends
```java
record Friend(String name) {}
var bob = new Friend("bob");
var ana = new Friend("ana");
var jul = new Friend("jul");
```


## With a fixed number of values
If there is a fixed number of value, the concatenation using '+' is the
most readable (ok, when your are used to) and the fastest 


### Concatenation with +
Just do a '+' between the different values,
this code is heavily optimized and will allocate only one String
```java
System.out.println(bob.name() + ", " + ana.name() + ", " + jul.name());
```


### Concatenation with String.format()
If you want more control on the formatting, you can use `String.format`
that reuse the C formatting style
But the method `format()` is quite slow.
```java
System.out.println(String.format("%s, %s, %s", bob, ana, jul));
System.out.printf("%s, %s, %s\n", bob, ana, jul);
```


## with a variable number of values
If there is a variable numbers of values, you have two cases,
depending if it's a collection of String or not

```java
var strings = List.of("bob", "ana", "jul");
var friends = List.of(bob, ana, jul);
```

### Concatenation with a +
Never use '+' in this case, because the compiler is not smart enough
to reuse the same buffer of characters for the whole loop, so it will
create a new String for each loop trip.
```java
String concatenate(List<?> list) {
  var string = "";
  var separator = "";
  for(var item: list) {
    string = string + separator + item;  // creates two many strings, ahhhh
    separator = ", ";
  } 
  return string;
}
System.out.println(concatenate(strings));
System.out.println(concatenate(friends));
```

### Concatenation with a StringBuilder
A StringBuilder is a modifiable version of String with an expandable buffer
of characters. There is no notion of separators
```java
String concatenate(List<?> list) {
  var builder = new StringBuilder();
  var separator = "";
  for(var item: list) {
    builder.append(separator).append(item);
    separator = ", ";
  } 
  return builder.toString();
}
System.out.println(concatenate(strings));
System.out.println(concatenate(friends));
```

> Don't use '+' inside a call to  `append()`, you already have a StringBuilder,
so use append() instead


### Concatenation with String.join()
If you have an array of strings or a collection of strings, `String.join`
is the simplest way to concatenate the items with a separator

```java
System.out.println(String.join(", ", strings));
```


### Concatenation with a StringJoiner
If you don't have a list of strings by a list of objects, you can use the
`StringJoiner` which let you specify a separator and is implemented
using expandable buffer of strings (`StringJoiner.add` only accepts strings).

```java
String concatenate(List<?> list) {
  var joiner = new StringJoiner(", ");
  list.forEach(item -> joiner.add(item.toString()));
  return joiner.toString();
}
System.out.println(concatenate(strings));
System.out.println(concatenate(friends));
```


### Concatenation with a Stream
If you use a `Stream` and the collector `joining`, it will use a `StringJoiner` internally.

```java
import java.util.stream.Collectors;
System.out.println(strings.stream().collect(Collectors.joining(", ")));
System.out.println(friends.stream().map(Friend::toString).collect(Collectors.joining(", ")));
```
