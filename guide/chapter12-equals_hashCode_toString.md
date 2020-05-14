# Implementing equals()/hashCode() and toString()

## Why equals()/hashCode() and toString() are important
Those methods are used by the data structures (list, map) to implement operations
like add(), contains(), etc. So most of the data structure doesn't work
correctly if `equals()`/`hashCode()` and `toString()` are not correctly written
on the element.

By example, `ArraysList.contains(value)` uses `value.equals()`,
`HashMap.get(key)` uses both `key.hashCode()` and `key.equals()`.


## Default implementations from java.lang.Object
Object defines several methods and provide a default implementation for them
- `boolean equals(Object)`
  test if two objects are equals (same type and same values), but
  the default implementation do an ==, so only check if the two objects are at
  the same address in memory
- `int hashCode()`
  return a summary of the content of the object as an int
  the default implementation choose a random number when the object is created
- `String toString()`
  return a textual representation of the object
  the default implementation return a concatenation of the 

So the default implementations only ensure that an object is equals to itself.


## Writing your own equals()/hashCode()
- `equals()` must be valid for any object and returns false if it's not the right type
  so it starts with an `instanceof` and calls `equals()` if the field value
  is a reference.
- `hashCode()` delegates to the hashCode of the field value
```java
class User {
  private final String name;
  public User(String name) {
    this.name = Objects.requireNonNull(name);
  }
  public boolean equals(Object o) {
    return o instanceof User user &&
      name.equals(user.name);
  }
  public int hashCode() {
    return name.hashCode();
  }
  public String toString() {
    return "User " + name;
  }
}
var user1 = new User("Bob");
var user2 = new User("Bob");
System.out.println(user1.equals(user2));
System.out.println(user1.hashCode() == user2.hashCode());
System.out.println(user1);
```


### With two fields
- `equals()`, it's better to first check the primitive fields because a primitive check
  is usually faster than a call to `equals()`. 
- `hashCode()` can use the exclusive or `^` to mix the hash code.
```java
class User {
  private final String name;
  private final int age;
  public User(String name, int age) {
    this.name = Objects.requireNonNull(name);
    this.age = age;
  }
  public boolean equals(Object o) {
    return o instanceof User user &&
      age == user.age && name.equals(user.name);
  }
  public int hashCode() {
    return name.hashCode() ^ age;
  }
  public String toString() {
    return "User " + name + " " + age;
  }
}
var user1 = new User("Bob", 31);
var user2 = new User("Bob", 31);
System.out.println(user1.equals(user2));
System.out.println(user1.hashCode() == user2.hashCode());
System.out.println(user1);
```


### With several fields
- equals(), as said in chapter 'basic_types', array.equals() doesn't work,
  Arrays.equals() should be used instead
- hashCode(), `Object.hash` compute the hash of several values separated by commas. 
```java
class User {
  private final String name;
  private final int age;
  private final String login;
  private final char[] password;
  public User(String name, int age, String login, char[] password) {
    this.name = Objects.requireNonNull(name);
    this.age = age;
    this.login = Objects.requireNonNull(login);
    this.password = password.clone();
  }
  public boolean equals(Object o) {
    return o instanceof User user &&
      age == user.age && name.equals(user.name) &&
      login.equals(user.login) && Arrays.equals(password, user.password);
  }
  public int hashCode() {
    return Objects.hash(name, age, login, Arrays.hashCode(password));
  }
  public String toString() {
    return "User " + name + " " + age + " " + login + " " + "*".repeat(password.length);
  }
}
var user1 = new User("Bob", 31, "bob", "df15cb4e019ec2eac654fb2e486c56df285c8c7b".toCharArray());
var user2 = new User("Bob", 31, "bob", "df15cb4e019ec2eac654fb2e486c56df285c8c7b".toCharArray());
System.out.println(user1.equals(user2));
System.out.println(user1.hashCode() == user2.hashCode());
System.out.println(user1);
```


## Record implementation

For a record, the methods `equals()`/`hashCode()` and `toString()` are already provided
so usually you don't have to provide a new implementation.
```java
record User(String name, int age) {
  public User {
    Objects.requireNonNull(name);
  }
  // the compiler automatically adds equals/hashCode/toString !
}
var user1 = new User("Bob", 31);
var user2 = new User("Bob", 31);
System.out.println(user1.equals(user2));
System.out.println(user1.hashCode() == user2.hashCode());
System.out.println(user1);
```
