# Class and encapsulation

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


## Encapsulation principle
In a pure functional language, the language doesn't allow you to
do side effect. In an OO language, if you want to survive, the idea is
to limit the functions that can do side effects to the instance methods.

This idea is named the encapsulation principle and is sum up by this sentence
> The only way to change the value of an object is to use one of the methods of this object.

In Java, the way to ensure the encapsulation principle is to do information hiding,
i.e. to separate the __public__ API part (what the user code can use) from the __private__
implementation part (how the class is implemented).

This separation is done by using a special syntax named __class__ that allows
to precisely control of the visibility of its members.

## Class
A class defines
- private fields that is like a record component but not visible by the user code
- a public constructor (Library), that guarantee that any objects will be correctly initialized
- public and private instance and static methods

### Unmodifiable class
```java
class Library {
  private final List<Book> books;
  public Library(List<Book> books) {
    this.books = List.copyOf(books);
  }
  public String toString() {
    return "Library " + books.toString();
  }
}
var library = new Library(books);
System.out.println(library);
```

Now changing the list of books has no effect on the library
because the field `books` and the argument of the constructor `books` are different references
```java
books.remove(new Book("DaVinci Code", "Dan Brown"));
System.out.println(library);
```

You can notice that the constructor has no return type, it's because it's always void.

The field 'books' is declared final which means must be initialized
in the constructor (and not changed afterward) so we are sure that in toString(),
the field 'books' has been initialized.

Unlike a record, the method equals()/hashCode() and toString() are not provided and has
to be hand written. We will see how to implement them later.


### Modifiable class
The code above is an unmodifiable implementation of Library.
We can also write a mutable version with the caveat that using it
as element of a list or a map is not recommended.
```java
class ModifiableLibrary {
  private final ArrayList<Book> books;
  public ModifiableLibrary() {
    books = new ArrayList<>();
  }
  public void add(Book book) {
    Objects.requireNonNull(book);
    books.add(book);
  }
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


### Modifiable class and accessors
An error sometime seen is to add a method to get the content of the library
and forget that it may expose the private list of books
```java
class ModifiableLibrary {
  private final ArrayList<Book> books;
  public ModifiableLibrary() {
    books = new ArrayList<>();
  }
  public void add(Book book) {
    Objects.requireNonNull(book);
    books.add(book);
  }
  public List<Book> getBooks() {
    return books;
  }
  public String toString() {
    return "ModifiableLibrary " + books.toString();
  }
}
```

The following code breaks the encapsulation because you can 
modify the library without calling a method of the Library
(`add()` is called on the List<Book> not on the Library)
```java
var library = new ModifiableLibrary();
var books = library.getBooks();
books.add(new Book("DaVinci Code", "Dan Brown"));
```

One solution is to return a copy, or better a non modifiable view
of the internal list of books
```java
class ModifiableLibrary {
  private final ArrayList<Book> books;
  public ModifiableLibrary() {
    books = new ArrayList<>();
  }
  public void add(Book book) {
    books.add(book);
  }
  public List<Book> getBooks() {
    return Collections.unmodifiableList(books);
  }
  public String toString() {
    return "ModifiableLibrary " + books.toString();
  }
}
var library = new ModifiableLibrary();
var books = library.getBooks();
books.add(new Book("DaVinci Code", "Dan Brown"));
```

The best solution being to not have a method `getBook()` at all,
the less code you write the less bug you have.
So please don't write getters and setters unless you really need them.


## Record constructor
Records also provides ways to customize the code to respect the
encapsulation principle
Here, we only need to change the canonical constructor 
```java
record Library(List<Book> books) {
  public Library(List<Book> books) {
    this.books = List.copyOf(books);
  }
}
```


To summarize, a class is a general mechanism to describe how things
are implemented and make a separation between what is publicly visible
and what is privately implemented to make the code working.
A record is a special case when there is no separation, everything is public.
