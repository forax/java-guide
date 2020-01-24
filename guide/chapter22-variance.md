# Generics Variance
Let say we have a list of String and a list of Integer and
we want to write a method being able to print all the values
from those lists, we may write something like this
```java
void printAll(List<Object> list) {
  list.forEach(System.out::println);
}
List<Integer> list = List.of(42, 777);
printAll(list);
```

This line doesn't compile because generics in Java are invariant,
you can only call `printAll()` with a list of Object and not a list of String.

You may be confused because both for a List<Object> and a List<String>,
you can get any items and see it as an Object.
But List<Object> also means that you can set() any cell with an Object,
something which is clearly not possible if it's a List<String>.
That's why generics in Java are invariant.

## `? extends`
The way to solve this problem is to say to the compiler, i want a list of Object
and I pinky swear that i will not call set() with an Object.

The type system not trusting humans, we invent a notation
`List<? extends Object>` for that
```java
void printAll(List<? extends Object> list) {
  list.forEach(System.out::println);
}
List<Integer> list = List.of(42, 777);
printAll(list);
```

There is no class List<? extends Object> at runtime, it's just a type that can be
a list of anything (`?` means any types) that is a subtype of Object at runtime.

Note that `?` is not a type, the type is `? extends Something` and it only exist
in between `<` and `>`.

If a method as a parameter typed Optional<? extends CharSequence>, you can call
that method with anything which is a optional of a subtype of CharSequence.
Inside the method, you can not call any method of Optional<E> that takes
an E because, it may be stored inside the Optional.

Note that this is an approximation, this code does not compile even if orElse()
doesn't change the content of the Optional because the compiler has no way
to know that implementation of orElse(). 
```java
/*
void printOptional(Optional<? extends Object> list) {
  System.out.println(list.orElse(new Object()));
}
*/
```

There is an exception, you call the method that takes an E if the value is null
because you can store null in any Optional. So the following code compiles
```java
void printOptional(Optional<? extends Object> list) {
  System.out.println(list.orElse(null));
}
Optional<String> optional = Optional.of("foo");
printOptional(optional);
```


### `?`
`?` is a short syntax for `? extends Object`.
```java
void printOptional(Optional<?> list) {
  System.out.println(list.orElse(null));
}
Optional<String> optional = Optional.of("foo");
printOptional(optional);
```


## `? super`
Sometimes you want to do the opposite, store something in a list
by example, you want to write a method addOne that add an element
into any list that can store a String, but it you declare it like
this, you can not call it with a List<Object>
```java
void addOne(String s, List<String> list) {
  list.add(s);
}
addOne("foo", new ArrayList<Object>());
```

Again, we have a notation for that, List<? super String>, it means
a list of the supertype of a String.
```java
void addOne(String s, List<? super String> list) {
  list.add(s);
}
addOne("foo", new ArrayList<Object>());
```

In that case, it means that if you try to call a method a method
that return a E, the compiler will not be able to type it correctly
because it can be any supertype of String

Again, there is an exception because you can always store anything
as Object so the following code compiles
```java
void foo(List<? super String> list) {
  // I can call any method that takes an E with a String
  // an i can also write because any object is an Object in Java
  Object o = list.get(0);
}
```


## Where to put some `? extends`/`? super`
For any public methods that takes a generics as parameter, you should ask yourself
if you can use `? extends` or `? super`.

You can note that this is similar to using List as parameter instead of using
ArrayList. Using a type less precise allow the user code to call with
generics with different type arguments.

Apart in case of overriding, never, never use a `? extends`/`? super` as
a return type. Otherwise, every developers that use your method will have to
introduce some `? extends`/`? super` in it's own code.


### PECS: Produce Extends Consumer Super
The rule PECS is a mnemonic to know when to use `? extends`/`? super`.
From the point of view of a generics class Foo<E>
- if the class acts as a producer of E (calling only methods that return an E)
   you want to use `Foo<? extends E>`
- if the class acts as a consumer of E (calling only methods that takes an E)
   you want to use `Foo<? super E>`
- if the class acts as a producer and a consumer, use Foo<E>.


## Relation with the variable of type
Instead of `printAll(List<? extends Object>)`, one can write
```java
<T extends Object> void printAll(List<T> list) {
  list.forEach(System.out::println);
}
List<String> list = List.of("hello");
printAll(list);
```

But in Java, we prefer to not introduce a type variable if it's not necessary
You can also notice that it doesn't work with `? super` because <T super Whatever>
is not a valid syntax.
