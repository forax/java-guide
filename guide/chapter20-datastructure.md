
In Java, there are already plenty of data structures already available
there are grouped under the name the collection API.

to create a simple list
'''java
var list = List.of(1, 2, 3);
'''

a list is an indexed data structure that stores object in the order of insertions
get() access to an element given an index
'''java
var firstElement = list.get(0);
var lastElement = list.get(list.size() - 1);
'''

contains return true if a value is contained in the list
'''java
System.out.println(list.contains(4));
'''

indexOf returns the first index of the element in the list
'''java
System.out.println(list.indexOf(2));
'''

to loop over the elements of a list, we have a special syntax using the keyword 'for'
'''java
var countries = List.of('UK', 'US', 'France');
for(var country: countries) {
  System.out.println(country);
}
'''

you can also loop over the elements using a method forEach
if you don't understand this one, don't panic, we will see it later
'''java
countries.forEach(country -> System.out.println(country));
'''


a list also defines the method equals() and toString(), so
you can print a list or test if two list are equals
'''java
System.out.println(countries);
System.out.println(list.equals(countries));
'''


in Java, depending on how you create a collection it can be changed
after creation or not. Implementation that allow mutation after creation
are called modifiable

by example, the list above (created with the static method of()) is not modifiable
'''java
//countries.set(0, 'Poland')  // throws an UnsupportedOperationException
'''

To create a modifiable list, we use an ArrayList, created using the operator 'new'
here because there is no element in the list, the compiler has no way to know
the type of the elements so we have to provide it in between angle brackets ('<' and '>')
'''java
var modifiableCountries = new ArrayList<String>();
'''

To add elements in a list, we have the method add()
'''java
modifiableCountries.add("UK");
modifiableCountries.add("US");
modifiableCountries.add("France");
modifiableCountries.add("Poland");
'''

to remove an element, we have the method remove()
'''java
modifiableCountries.remove("UK");
'''

an unmodifiable list or a modifiable list have the same set of methods,
so you can loop over the modiable list the same way
'''java
for(var country: modifiableCountries) {
  System.out.println(country);
}
'''

You can create a modifiable list from an unmodifiable one using new ArrayList
with the unmodifiable list as argument
In that case you don't have to specify the type of the elements
the compiler already knows the type of list hence the <> (diamond)
'''java
var modifiableList = new ArrayList<>(list);
System.out.println(modifiableList);
'''


Lists are not the only data structure in Java, you also have set, queue and map
- a set is set where you can not store the same object twice
  (object are the same is equals() return true)
- a queue add or remove object at the head or at the tail of the queue
  (so a stack is a queue, a FIFO is a queue, etc)
- a map is a dictionary that associate a key (which is unique) to a value

so to create an unmodifiable set, using the static method of()
'''java
var authors = Set.of("J.R.R. Tolkien", "Philip K. Dick", "George R.R. Martin");
System.out.println(authors);
'''

elements inside a set are organized in a way that make contains fast
'''java
System.out.println(authors.contains(""Philip K. Dick""));
'''

there are 3 modifiable sets
- HashSet
- LinkedHashSet, as fast as set
- TreeSet, elements are sorted

a set has no order by default, apart if you create a LinkedHashSet




