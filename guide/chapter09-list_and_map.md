# List and Map
In Java, the most used data structures are List (an indexed list) and Map (a dictionary)

## List
To create a simple list
```java
var numbers = List.of(1, 2, 3);
System.out.println(numbers);
```

a list is an indexed data structure that stores object in the order of insertions
get() access to an element given an index
```java
var firstElement = numbers.get(0);
var lastElement = numbers.get(numbers.size() - 1);
```

contains return true if a value is contained in the list
```java
System.out.println(numbers.contains(4));
```

indexOf returns the first index of the element in the list
```java
System.out.println(numbers.indexOf(2));
```

a list also defines the method equals()/hashCode() and toString(), so
you can print a list or test if two list are equals
```java
var friends = List.of("cesar", "rosalie", "david");
System.out.println(friends);
System.out.println(friends.hashCode());
System.out.println(friends.equals(numbers));
```


### Unmodifiable/modifiable list
in Java, depending on how you create a data structure it can be changed
after creation or not. Implementation that allow mutation after creation
are called modifiable

by example, the list above (created with the static method of()) is not modifiable
```java
var countries = List.of("UK", "US", "France");
countries.set(0, "Poland"); // throws an UnsupportedOperationException
```

To create a modifiable list, we use an ArrayList, created using the operator 'new'
and because because there is no element in the list, the compiler has no way to know
the type of the elements so we have to provide it in between angle brackets ('<' and '>')
```java
var modifiableCountries = new ArrayList<String>();
System.out.println(modifiableCountries);
```

To add elements in a list, we have the method add()
```java
modifiableCountries.add("UK");
modifiableCountries.add("US");
modifiableCountries.add("France");
modifiableCountries.add("Poland");
System.out.println(modifiableCountries);
```

to remove an element, we have the method remove()
```java
modifiableCountries.remove("UK");
System.out.println(modifiableCountries);
```


### Iterating
an unmodifiable list or a modifiable list have the same set of methods.
to loop over the elements of a list, we have a special syntax using the keyword 'for'
```java
var countries = List.of("UK", "US", "France");
for(var country: countries) {
  System.out.println(country);
}
```

you can also loop over the elements using a method forEach
if you don't understand this one, don't panic, we will see it later
```java
countries.forEach(country -> System.out.println(country));
```


### Conversions
To can create an unmodifiable list from a modifiable one with List.copyOf()
```java
var unmodifiableList = List.copyOf(modifiableCountries);
System.out.println(unmodifiableList);
```

To create a modifiable list from an unmodifiable one using `new ArrayList(List)`
In that case you don't have to specify the type of the elements
the compiler already knows the type of list hence the <> (diamond)
```java
var modifiableList = new ArrayList<>(unmodifiableList);
System.out.println(modifiableList);
```

### Useful patterns
To remove some elements depending on a predicate (if something is true)
```java
var elements = new ArrayList<>(List.of("table", "chair", "stool"));
elements.removeIf(element -> element.charAt(0) == 'c');
System.out.println(elements);
```


## Map
A Map associate a value to a key
To create a simple Map
```java
var petCost = Map.of("cat", 200, "dog", 350, "lion", 5000);
System.out.println(petCost);
```

to get the value from a key
```java
var costOfADog = petCost.get("dog");
System.out.println(costOfADog);
```

__Warning__! __warning__! asking for a key which is not in the map will return null
```java
var costOfAGirafe = petCost.get("girafe");
System.out.println(costOfAGirafe);
```

to avoid null, use the method `getOrDefault()` that let you specify a default value
```java
var costOfAGirafe = petCost.getOrDefault("girafe", 0);
System.out.println(costOfAGirafe);
```

And like a list, a map defines the method `equals()`/`hashCode()` and `toString()`
```java
var lighter = Map.of("blue", "lightblue", "gray", "white");
var darker = Map.of("blue", "darkblue", "gray", "black");
System.out.println(lighter);
System.out.println(darker.hashCode());
System.out.println(lighter.equals(darker));
```


### Unmodifiable/modifiable
The Map create by Map.of() are non modifiable
To create a modifiable map, we use new HashMap to create the map
and map.put to put key/value in it
```java
var modifiableMap = new HashMap<String, String>();
modifiableMap.put("blue", "lightblue");
modifiableMap.put("gray", "white");
System.out.println(modifiableMap);
```

Removing the key, remove the couple key/value
```java
modifiableMap.remove("blue");
System.out.println(modifiableMap);
```


### Iterating on `keySet()` or `entrySet()`
you can not use a for loop directly on a Map
but you can do it on the set of keys
```java
var petCost = Map.of("cat", 200, "dog", 350, "lion", 5000);
for(var pet: petCost.keySet()) {
  System.out.println(pet);
}
```

or on the set of couples key/value (a `Map.Entry`)
```java
for(var entry: petCost.entrySet()) {
  var pet = entry.getKey();
  var cost = entry.getValue();
  System.out.println(pet + " " + cost);
}
```

You can also loop over the entries using the method `forEach()`
```java
petCost.forEach((pet, cost) -> {
  System.out.println(pet + " " + cost);
});
```


### Conversions
To create a unmodifiableMap from a modifiable map, use `Map.copyOf()`
```java
var modifiableMap = new HashMap<String, String>();
modifiableMap.put("jack sparrow", "pirate");
modifiableMap.put("hector barbossa", "pirate");
var unmodifiableMap = Map.copyOf(modifiableMap);
System.out.println(unmodifiableMap);
```

To create a modifiableMap from an unmodifiable map, use `new HashMap<>()`
```java
var unmodifiableMap = Map.of("jack sparrow", "pirate", "hector barbossa", "pirate");
var modifiableMap = new HashMap<>(unmodifiableMap);
System.out.println(modifiableMap);
```


### Useful patterns
To make the Map acts as a cache, use `computeIfAbsent()`
```java
record Person(String name, int age) { }
var persons = List.of(new Person("Bob", 23), new Person("Anna", 32), new Person("Bob", 12));
var group = new HashMap<String, List<Person>>();
persons.forEach(person -> group.computeIfAbsent(person.name(), name -> new ArrayList<>())
                                .add(person));
System.out.println(group);
```

to count the number of occurrence, use `merge()` that takes a key, a value and the function
to call if there is already an existing value to combine them
```java
var letters = List.of("a", "b", "e", "b");
var occurrenceMap = new HashMap<String, Integer>();
letters.forEach(letter -> occurrenceMap.merge(letter, 1, Integer::sum));
System.out.println(occurrenceMap);
```


### More on transformations
To do more transformations of lists and maps, use the Stream API.
