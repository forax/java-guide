In Java, there are already plenty of data structures already available
there are grouped under the name the collection API.

Lists are not the only data structure in Java, you also have set, queue and map
- a set is set where you can not store the same object twice
  (object are the same is equals() return true)
- a queue add or remove object at the head or at the tail of the queue
  (so a stack is a queue, a FIFO is a queue, etc)
- a map is a dictionary that associate a key (which is unique) to a value

so to create an unmodifiable set, using the static method of()
```java
var authors = Set.of("J.R.R. Tolkien", "Philip K. Dick", "George R.R. Martin");
System.out.println(authors);
```

elements inside a set are organized in a way that make `contains` fast
```java
System.out.println(authors.contains("Philip K. Dick"));
```

there are 3 modifiable sets
- HashSet
- LinkedHashSet, as fast as set
- TreeSet, elements are sorted

a set has no order by default, apart if you create a LinkedHashSet




