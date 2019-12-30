// To starts, run jshell --enable-preview which is a program able to interpret Java syntax
// then cut and paste the following lines to see how it works
// To exit jshell type /exit

// # Class and encapsulation

// Let's say i want to create a library of books,
// so we need a record Book and a record Library that stores the books has a list
record Book(String title, String author) { }
record Library(List<Book> books) { }

// and use it that way
var book = new Book("DaVinci Code", "Dan Brown");
var books = new ArrayList<Book>();
books.add(book);
var library = new Library(books);
System.out.println(library);

// The problem with a Library declared like this in that the library is not really
// in control of the books inside itself, one can write
books.add(new Book("Effective Java", "Joshua Bloch"));
System.out.println(library);

// The result is surprising, you can add books in the library without calling
// a method of the library which make the code hard to debug because changing
// an object has an effect to another object.


// ## Encapsulation principle
// The encapsulation principle: the only way to change the value of an object
// is to use one of the methods of this object.

// In Java, the way to ensure the encapsulation principle is based on
// separating the API part (what the user code can use) from the implementation part
// (how the class is implemented).

// This separation is done by using a special syntax named __class__ that allow
// to precisely control what is part of the API and what is part of the impelemntation.

// ## Class
// A class defines
// - a field (books) that is like a record component but not necessarily visible by the user code
// - a constructor (Library), that guarantee that any objects will be correctly initialized
// - fields and methods have a visibility (public/private) so it's easy to control
//   what is visible from the outside and what is not.


// ### Unmodifiable class
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

// Now changing the list of books has no effect on the library
// because the field `books` and the argument of the constructor `books` are different references
books.remove(new Book("DaVinci Code", "Dan Brown"));
System.out.println(library);

// You can notice that the constructor has no return type, it's because it's always void.

// The field 'books' is declared final which means must be initialized
// in the constructor (and not changed afterward) so we are sure that in toString(),
// the field 'books' has been initialized.

// Unlike a record, the method equals/hashCode() and toString() are not provided and has
// to be hand written. We will see how to implement them later.


// ### Modifiable class
// The code above is an unmodifiable implementation of Library
// that can be used as element of list or map.
// We can also write a mutable version with the caveat that using it
// as element of a list or a map is not recommended
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


// ### Modifiable class and accessor
// An error sometime seen is to add a method to get the content of the library
// and forget that it may expose the private list of books
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

// The following code breaks the encapsulation because you can 
// modify the library without calling a method of the Library
// (add is done on the List<Book> not on the Library)
var library = new ModifiableLibrary();
var books = library.getBooks();
books.add(new Book("DaVinci Code", "Dan Brown"));

// One solution is to return a copy, or better a non modifiable view
// of the internal list of books
// The best solution being to not have a method `getBook()` at all,
// the less code you write the less bug you have.
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


// ## Record constructor
// Records also provides ways to customize the code to respect the
// encapsulation principle
// Here, we only need to change the canonical constructor 
record Library(List<Book> books) {
  public Library(List<Book> books) {
    this.books = List.copyOf(books);
  }
}


// To summarize, a class is a general mechanism to describe how things
// are implemented and make a separation between what is publicly visible
// and what is privately used to make the code working.
// A record is a special case when the implementation is public.
