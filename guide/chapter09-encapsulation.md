
Let's say i want to create a library of books,
so we need a record Book and a record Library that stores the books has a list
```java
record Book(String title, String author) { }
record Library(List<Book> books) { }
```

and use it that way
```java
var book = new Book("DaVinci Code", "Dan Brown");
var books = new ArrayList<Book>();
books.add(book);
var library = new Library(books);
System.out.println(library);
```

The problem with a Library declared like this in that the library is not really
in control of the books inside itself, one can write
```java
books.add(new Book("Effective Java", "Joshua Bloch"));
System.out.println(library);
```

The result is surprising, you can add books in the library without calling
a method of the library which make the code hard to debug because changing
an object has an effect to another object.


The encapsulation principle: the only way to change the value of an object
is to use one of the methods of this object.

To avoid the issue above, we have to make the list of books of the Library
separated from the list of books taken as parameter to initialize the Library

We have to use a new syntax, a class that unlike a record allows
to separate the external API (the one users see) from the internal API
(how things are implemented)
```java
class Library {
  private final List<Book> books;
```
  
```java
  public Library(List<Book> books) {
    this.books = List.copyOf(books);
  }
```
  
```java
  public String toString() {
    return "Library " + books.toString();
  }
}
```

A class defines
- a field (books) that is like a record component but not necessarily visible by the user
- a constructor (Library), a special method used to initialize the library
- fields and methods have a visibility (public/private) so it's easy to control
  what is visible from the outside and what is not.
Unlike a record, the method equals/hashCode() and toString() are not provided and has
to be hand written.

```java
var library = new Library(books);
System.out.println(library);
```

and changing the list of books has no effect on the library
```java
books.remove(new Book("DaVinci Code", "Dan Brown"));
System.out.println(library);
```

You can notice that the constructor has no return type, it's because it's always void.
The field 'books' is declared final which means must be initialized
in the constructor (and not changed afterward) so we are sure that in toString(),
the field 'books' has been initialized.


Writing equals()/hashCode()
If we want to be able to use Library with data structures like list or map,
We must write equals() and hashCode()
- equals() has to return false if it's not the right class, and
  use the fields to see if their values are equals
- hashCode() has to use the same fields to compute a hash value
```java
class Library {
  private final List<Book> books;
```
  
```java
  public Library(List<Book> books) {
    this.books = List.copyOf(books);
  }
```
  
```java
  public boolean equals(Object o) {
    return o instanceof Library library &&
      books.equals(library.books);
  }
```
  
```java
  public int hashCode() {
    return books.hashCode();
  }
```
  
```java
  public String toString() {
    return "Library " + books.toString();
  }
}
```

```java
var library1 = new Library(books);
var library2 = new Library(books);
System.out.println(library1.equals(library2));
```

The code above is an unmodifiable implementation of Library
that can be used as element of list or map.
We can also write a mutable version with the caveat that using it
as element of a list or a map is not recommended
```java
class ModifiableLibrary {
  private final ArrayList<Book> books;
```
  
```java
  public ModifiableLibrary() {
    books = new ArrayList<>();
  }
```
  
```java
  public void add(Book book) {
    books.add(book);
  }
```
  
```java
  public String toString() {
    return "ModifiableLibrary " + books.toString();
  }
}
var library = new ModifiableLibrary();
library.add(new Book("DaVinci Code", "Dan Brown"));
System.out.println(library);
library.add(new Book("Effective Java", "Joshua Bloch"));
System.out.println(library);
```


Record constructor
Records also provides a way to change the constructor used to initialize
a record called the 'canonical constructor'.
```java
record Library(List<Book> books) {
  public Library(List<Book> books) {
    this.books = List.copyOf(books);
  }
}
```

In this example, it's enough to make the implementation of Library
to be compatible with the encapsulation principle


To summarize, a class is a general mechanism to describe how things
are implemented and make a separation between what is publicly visible
and what is privately used to make the code working.
A record is a special case when the implementation is public.
