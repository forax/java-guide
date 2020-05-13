# Stream
A stream is an API that defines a query on a source of values.
It's an abstraction of loops over values using a declarative API,
an API that describe the result you want and not how to compute it.

By example, to count the number of persons with a name starting by 'E',
one can write
```java
var names = List.of("Evan", "Helen", "Ebo");
var count = 0;
for(var name: names) {
  if (name.startsWith("E")) {
    count++;
  }
}
System.out.println(count);
```

But using a stream, it's simpler
```java
var names = List.of("Evan", "Helen", "Ebo");
var count = names.stream().filter(name -> name.startsWith("E")).count();
System.out.println(count);
```


### Why using streams instead of loops
The main reason is that it makes the code more readable,
obviously once you are used to read stream query,
- by allowing to easily compose operations
- by removing the declaration of intermediary local variables/states
  (the variable `count` in the example above).


## Sources
There are several ways to create a stream depending on the source
(The following examples are using `count()` to compute the number of values,
we will see later that the API is richer)

- stream of values
```java
var empty = Stream.empty();
var one = Stream.of(4);
var many = Stream.of("hello", "stream");
System.out.println("empty count " + empty.count());
System.out.println("one count " + one.count());
System.out.println("many count " + many.count());
```

- stream from a collection
```java
var listStream = List.of(1, 2, 3, 4).stream();
var mapStream = Map.of("bob", 3, "ana", 7).keySet().stream();
System.out.println("list count " + listStream.count());
System.out.println("map keys count " + mapStream.count());
```

- stream from a range
```java
var range = IntStream.range(0, 10);
System.out.println("range count " + range.count());
```


## Primitive version
Streams are represented by several classes, `java.util.stream.Stream` for a stream of objects and
`IntStream`, `DoubleStream` and `LongStream` for a stream of ints, doubles and longs.
```java
Stream<String> many = Stream.of("hello", "stream");
IntStream ints = IntStream.range(0, 10);
```

Using specialized classes for the numeric types:
- avoid boxing, an IntStream is more efficient than a Stream<Integer>
- `boxed()` convert to a Stream of wrapper
- offer supplementary numeric methods like, `min`, `max`, `sum()`, etc.

To sum of the values between [0, 10[
```java
var range = IntStream.range(0, 10);
System.out.println("range sum: " + range.sum());
```


## Filter, map and flatMap
The main transformation methods are `filter`, `map` and `reduce`.

### filtering
`filter()` take a function as parameter and keep in the stream the values
for which the function returned true.

```java
record Employee(String name, int age) { }
var employees = List.of(new Employee("bob", 55), new Employee("Ana", 32));
var youngCount = employees.stream().filter(e -> e.age() < 30).count();
System.out.println(youngCount);
```

### mapping
`map()` transforms a value to another value
```java
record Employee(String name, int age) { }
var employees = List.of(new Employee("Bob", 55), new Employee("Ana", 32));
var array = employees.stream().map(Employee::name).toArray();
System.out.println(Arrays.toString(array));
```

`map()` has variations (`mapToInt`, `mapToLong`, etc) to transform to numeric streams
```java
record Employee(String name, int age) { }
var employees = List.of(new Employee("bob", 55), new Employee("Ana", 32));
var average = employees.stream().mapToInt(Employee::age).average();
System.out.println(average);
```

### flatMap
`flatMap()` transforms one value to 0 to _n_ values
```java
record Friend(String name, List<String> pets) { }
var friends = List.of(new Friend("Bob", List.of()),
   new Friend("Ana", List.of("dog", "cat")),
   new Friend("Uno", List.of("rabbit"))
   );
System.out.println(friends.stream().flatMap(friends -> friends.pets().stream()).count());
```

Like map, flatMap has also variation to numeric streams
```java
record Friend(String name, List<Integer> kidAges) { }
var friends = List.of(new Friend("Bob", List.of(1, 3)),
   new Friend("Ana", List.of(15, 17)),
   new Friend("Uno", List.of())
   );
System.out.println(friends.stream().flatMapToInt(friends -> friends.kidAges().stream().mapToInt(x -> x)).average());
```

You can notice that while there is a method boxed() to transform a numeric stream to an object stream,
there is no method unboxToInt() equivalent because `mapToInt()` can be used instead.

### flatMap is a generalization of filter and map
`filter()` result in a stream with 0 or 1 value, `map()` result in a stream with one transformed value so
both can be simulated with `flatMap()`.
So instead of
```java
record Employee(String name, int age) { }
var employees = List.of(new Employee("Bob", 55), new Employee("Ana", 32));
System.out.println(employees.stream().filter(e -> e.age() < 30).count());
System.out.println(Arrays.toString(employees.stream().map(Employee::name).toArray()));
```

one can write
```java
System.out.println(employees.stream().flatMap(e -> (e.age() < 30)? Stream.of(e): Stream.empty()).count());
System.out.println(Arrays.toString(employees.stream().flatMap(e -> Stream.of(e.name())).toArray()));
```

While flatMap can simulate filter and map, please use `filter()` and `map()` directly because 
the code is more readable and they are implemented in a more effective way.


## Distinct, sorted, min and max
Like in SQL, you can ask to filter values to only have distinct values or sort
the value with a comparator.
`distinct()` or `sorted()` are operations that requires to store all the values
in an intermediary collection so they are not cheap.

`distinct()` ask for unique values
```java
System.out.println(IntStream.range(0, 10).map(x -> x / 2).distinct().count());
```

`sorted()` ask to sort the values with a comparator
```java
record Employee(String name, int age) { }
var employees = List.of(new Employee("Bob", 55), new Employee("Ana", 32));
var youngest = employees.stream().sorted(Comparator.comparingInt(Employee::age)).findFirst();
System.out.println(youngest);
```

The Stream API also provides `min()` and `max()` that are more efficient that sorting
all the values if you just want the minimum or the maximum
```java
var youngest = employees.stream().min(Comparator.comparingInt(Employee::age));
System.out.println(youngest);
var oldest = employees.stream().max(Comparator.comparingInt(Employee::age));
System.out.println(oldest);
```


## ForEach, reduce, collect and toArray

### forEach
Takes a consumer as parameter that is called for each values of the stream
This method is not used often because if you want to do a side effect on collection
using the method `collect` is easier.
```java
record Point(int x, int y) { }
var points = List.of(
  new Point(1, 2), new Point(2, 5), new Point(3, -1));
points.stream().filter(p -> p.x() <= 2).forEach(System.out::println);
```

Note: collections (`java.util.Collection`) already have a method `forEach()`,
so no need to create a stream if you don't want to do a transformation on the
elements of the collection 
```java
record Point(int x, int y) { }
var points = List.of(new Point(2, 5));
points.stream().forEach(System.out::println); // stupid !
points.forEach(System.out::println);  // better
```

### reduce
Reduce allows to reduce all the values of a stream to only one result
by applying iteratively the same accumulator function on each value of the stream.

There are two forms of reduce
- reduce the stream values
- reduce using projected values

reduce with the stream values
```java
record Point(int x, int y) { }
var points = List.of(
  new Point(1, 2), new Point(2, 5), new Point(3, -1));
var sum = points.stream().reduce((p1, p2) -> new Point(p1.x() + p2.x(), p1.x() + p2.x()));
System.out.println("sum " + sum);
```

You can notice that the result is an `Optional` because is the stream is empty, `reduce`
has no result to return

reduce using projected values
```java
record Point(int x, int y) { }
var points = List.of(
  new Point(1, 2), new Point(2, 5), new Point(3, -1));
var sumX = points.stream().reduce(0, (acc, p) -> acc + p.x(), Integer::sum);
System.out.println("sumX " + sumX);
```

The first argument is the initial accumulator value, this is also the value
returned is the stream is empty so this variation of reduce doesn't return an `Optional`. 
The last argument of `reduce()` is only used in parallel to aggregate the values
process on different threads (see below for more info on parallel streams).

### collect
reduce works well when the result is one value but not well when the result
is a list, a map or any data structures because collections are mutable
in Java.
For that, there is another mechanism, called `collect` that takes
a `Collector` as parameter and is tailored to create, mutate, merge and
optionally makes unmodifiable any mutable collections.

This section contains only a small number of example because there is a following chapter
dedicated to collector.

While you can create your own collector by implementing the interface `Collector`,
there are already more than 20 collectors available in the class `Collectors` 
```java
import java.util.stream.Collectors;
```

`toList()`: gather all values to a list
```java
var names = List.of("Bob", "Ana", "Elvis", "Emma", "Josh");
var endsWithA = names.stream().filter(name -> name.endsWith("a")).collect(Collectors.toList());
System.out.println(endsWithA);
```

`toUnmodifiableist()`: gather all values to an unmodifiable list
```java
var names = List.of("Bob", "Ana", "Elvis", "Emma", "Josh");
var uppercases = names.stream().map(String::toUpperCase).collect(Collectors.toUnmodifiableList());
System.out.println(uppercases);
```

`toMap()`: gather all values to a map
```java
var names = List.of("Bob", "Ana", "Elvis", "Emma", "Josh");
var uppercaseMap = names.stream().collect(Collectors.toMap(name -> name, String::toUpperCase));
System.out.println(uppercaseMap);
```

`joining()`: gather all strings to one string
```java
var names = List.of("Bob", "Ana", "Elvis", "Emma", "Josh");
var asString = names.stream().collect(Collectors.joining(", "));
System.out.println(asString);
```

`groupingBy()`: gather all values into a map of list of values 
```java
var names = List.of("Bob", "Ana", "Elvis", "Emma", "Josh");
var nameByLength = names.stream().collect(Collectors.groupingBy(String::length));
System.out.println(nameByLength);
```


### toArray
Because in Java, array are typed at runtime there are two ways to create an array
from a stream
- as an array of Object
- as an array of a specific type

As an array Object
```java
var names = List.of(14, 67, 32, 78);
Object[] array = names.stream().toArray();
System.out.println(Arrays.toString(array));
```

As an array of a specific type, passing the constructor as argument
```java
var names = List.of(14, 67, 32, 78);
Integer[] array = names.stream().toArray(Integer[]::new);
System.out.println(Arrays.toString(array));
```

Note that you can provide an array type with a more specific type
because the VM will do a runtime check when the values are inserted
The following example compiles but throws a ClassCastException at runtime
```java
List<Object> names = List.<Object>of(14, 67, 32, "boom !");
Integer[] array = names.stream().toArray(Integer[]::new);
```


## Infinite Stream
Streams can be infinite (like you can create an infinite loop),
and you have shortcut methods to stop the loop.

generate an infinite number of random values between [0, 10[ as String
```java
var random = new Random(0);
var stream = Stream.generate(() -> "" + random.nextInt(10));
System.out.println(stream.limit(5).collect(Collectors.toList()));
```

iterate over all the power of two values
```java
var stream = IntStream.iterate(1, x -> x* 2);
System.out.println(stream.limit(5).boxed().collect(Collectors.toList()));
```

### Shortcut method `limit()`
```java
var sum = IntStream.iterate(1, x -> x* 2).limit(10).sum();
System.out.println("sum: " + sum);
```

### Shortcut method `dropWhile()`
All lines after the one that starts with `#` have a length greater than 10
```java
var text = """
  # a line
  # another one
  a line that doesn't start with #
  """;
var result = text.lines().dropWhile(l -> l.startsWith("#")).allMatch(s -> s.length() > 10);
System.out.println(result);
```

### Shortcut method `takeWhile()`
Find the first words that have a length lesser than 5
```java
var list = List.of("foo", "bar", "baz", "whizzzz", "bob");
var result = list.stream().takeWhile(s -> s.length() < 5).collect(Collectors.joining(", "));
System.out.println(result);
```


## Sequential vs parallel API
By default stream are executed sequentially in the same thread (think CPU core if you don't know what a thread is).
You can ask to split the processing of the stream on several threads using `.parallel()`
Using `.parallel()` is usually slower because you have to first distribute the calculation
and at the end gather the results from several threads. As a rule of thumb, it only worth to use `.parallel()`
if either you have a lot of value (like 100_000 or more) or the calculation is slooow.

By example, if you want to calculate the square root (not a slow operation) on the first 1_000_000 values
and prints only the first 10 values
```java
import static java.util.stream.Collectors.toList;
var squareRoots = IntStream.range(0, 1_000_000).parallel().mapToDouble(Math::sqrt).toArray();
System.out.println(Arrays.stream(squareRoots).limit(10).boxed().collect(toList()));
```

### findAny()/findFirst(), forEach()/forEachOrdered()
Because a stream can be evaluated in parallel and maintaining a strict order in parallel cost a lot,
usual methods like `findAny()` or `forEach()` doesn't maintain the order on a parallel stream.
You have to use specialized method (resp `findFirst()` and `forEachOrdered()`) to maintain the order.


## Limitations

### Reusing stream objects
You can not reuse a stream for several queries
```java
var stream = Stream.of(1, 2, 3);
System.out.println(stream.count());
System.out.println(stream.count());
```

> One query, one stream !

### Source mutation
A stream can not modify the source from which it was created
The following example throws a ConcurrentModificationException
```java
var list = new ArrayList<>(List.of("foo", "bar"));
list.stream().map(String::toUpperCase).forEach(list::add); 
```

Use an iterator (in the example a `ListIterator`) for that
```java
var list = new ArrayList<>(List.of("foo", "bar"));
var it = list.listIterator();
while(it.hasNext()) {
  var value = it.next();
  it.add(value.toUpperCase());
}
System.out.println(list);
```
