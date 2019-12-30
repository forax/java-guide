
# Implementing equals()/hashCode() and toString()

## Why equals()/hashCode() and toString() are important
Those methods are important TODO

## Default implementations from java.lang.Object
Object defines several methods and provide a default implementation for them
- `boolean equals(Object)`
  test if two objects are equals (same type and same values), but
  the default implementation do an ==, so only check if the two objects are at
  the same address in memory
- `int hashCode()`
  return a summary of the content of the object as an int
  the default implementation choose a random number when the object is created
- String toString()
  return a textual representation of the object
  the default implementation return a concatenation of the 

So the default implementations only ensure that an object is equals to itself.


## Writing your own equals()/hashCode()
If we want to be able to use instances of a class into data structure like list or map,
We must write equals() and hashCode()
- equals() has to return false if it's not the right class, and
  use the fields to see if their values are equals
- hashCode() has to use the same fields to compute a hash value

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
```

```java
var user1 = new User("Bob");
var user2 = new User("Bob");
System.out.println(user1.equals(user2));
System.out.println(user1.hashCode());
System.out.println(user2.hashCode());
System.out.println(user1);
```


## With two fields
for equals(), it's better to first check the primitive fields because a primitive check is faster
than a call to equals(). 
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
```

```java
var user1 = new User("Bob", 31);
var user2 = new User("Bob", 31);
System.out.println(user1.equals(user2));
System.out.println(user1.hashCode());
System.out.println(user2.hashCode());
System.out.println(user1);
```


### several fields
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
```

```java
var user1 = new User("Bob", 31, "bob", "df15cb4e019ec2eac654fb2e486c56df285c8c7b".toCharArray());
var user2 = new User("Bob", 31, "bob", "df15cb4e019ec2eac654fb2e486c56df285c8c7b".toCharArray());
System.out.println(user1.equals(user2));
System.out.println(user1.hashCode());
System.out.println(user2.hashCode());
System.out.println(user1);
```


## Record implementation

For a record, the method equals/hashCode() and toString() are already provided
so usually you don't have to provide a new implementation.
But arrays are not correctly supported
- equals() will calls Object.equals() on the array instead of Arrays.equals()
- hashCode() will calls Object.hashCode() on the array instead of Arrays.hashCode()

So we have to provide an equals()/hashCode() and toString()
```java
record User(String name, int age, String login, char[] password) {
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
System.out.println(user1.hashCode());
System.out.println(user2.hashCode());
System.out.println(user1);
```

