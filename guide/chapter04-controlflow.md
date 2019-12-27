
# Expression and Control Flow
Most of the control flow syntax of Java comes from C with some enhancements

### Variables
if you explicitly type a variable you can declare a variable without initializing it
```java
int x;
```

but a variable can be used only after being initialized
```java
x = 3;
System.out.println(x);
```

block of code
a variable declared in a block of code, can not be used outside that block
```java
{
  var value = 42;
}
```
value can not be used here !

### Test with `if`
```java
void oldEnough(int age) {
  if (age >= 21) {
    System.out.println("you are old enough to drink a beer");
  }
}
oldEnough(22);
```

### Test with `if ... else`
```java
void oldEnough(int age) {
  if (age >= 21) {
    System.out.println("you are old enough to drink a beer");
  } else {
    System.out.println("too bad for you !");
  }
}
oldEnough(17);
```


### Test with a `switch` statement
default case is not mandatory
```java
void vehicle(int wheels) {
  switch(wheels) {
    case 1 -> System.out.println("monocycle !");
    case 2 -> System.out.println("bicycle !");
    case 3, 4 -> System.out.println("car !");
    default -> {
      // if there are several lines
      System.out.println("whaat !");
      }
  }
}
vehicle(3);
```

### Test with a `switch` expression
default case is mandatory
```java
String vehicle(int wheels) {
  return switch(wheels) {
    case 1 -> "monocycle !";
    case 2 -> "bicycle !";
    case 3, 4 -> "car !";
    default -> "whaat !";
  };
}
System.out.println(vehicle(3));
```

you can switch on integers, strings and enums
```java
int doors(String kind) {
  return switch(kind) {
    case "smart" -> 3;
    case "sedan", "hatchback" -> 5;
    default -> -1;
  };
}
System.out.println(doors("sedan"));
```

### Test with a `switch` compatible with C
(you can not mix `->` and `:` )
```java
void vehicle(int wheels) {
  switch(wheels) {
    case 1:
      System.out.println("monocycle !");
      break;
    case 2:
      System.out.println("bicycle !");
      break;
    case 3:
    case 4:
      System.out.println("car !");
      break;
    default:
      System.out.println("whaat !");
  }
}
vehicle(3);
```


### `instanceof`
instanceof test the class of a value at runtime
if instanceof succeeds, the value is stored in the variable
declared as last argument
```java
record Car(int seats) {}
record Bus(int capacity) {}
int maxPersons(Object value) {
  if (value instanceof Car car) {
    return car.seats();
  }
  if (value instanceof Bus bus) {
    return bus.capacity();
  }
  return 0;
}
System.out.println(maxPersons(new Car(4)));
System.out.println(maxPersons(new Bus(32)));
```


### `while` loop
```java
void printFirstIntegers(int n) {
  var i = 0;
  while(i < n) {
    System.out.println(i);
    i++;
  }
}
printFirstIntegers(5);
```

### `for` loop
```java
void printFirstIntegers(int n) {
  for(var i = 0; i < n; i++) {
    System.out.println(i);
  }
}
printFirstIntegers(5);
```

### `for` loop on array or list
```java
var list = List.of("iron man", "captain america", "black panther");
for(var value: list) {
  System.out.println(value);
}
```


### On loops
Most of the loops can also be abstracted using higher order constructs
if you don't understand that code know, don't panic, we will come back
to that later

using `IntStream.range()`
```java
IntStream.range(0, 5).forEach(System.out::println);
```

using `List.forEach()`
```java
list.forEach(System.out::println);
```
