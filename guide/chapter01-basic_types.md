# Basic Types
Java has two kinds of type,
- primitive types that are directly mapped to CPU basic types
- reference types that have the address of the object in memory

## Primitive types 
primitive types, written in lower case, have no method

### boolean (true|false)
```java
var result = true;
var anotherResult = false;
```

### char (character)
```java
var firstLetter = 'j';
```

### int (signed 32-bit integer)
```java
var numberOfLegs = 2;
```

### double (64-bit floating point)
```java
var cost = 3.78;
```

### long and float
some more exotic types that requires a suffix (`L` or `f`)
long (64-bit integers) and float (32-bit floating point numbers)
```java
var longValue = 123L;
var floatValue = 123.5f;
```

### byte and short
you also have byte (a signed 8-bit integer) and short (a signed 16-bit short integer)
that are only useful to use less memory when defining an object
```java
record CompactHeader(byte tag, short version) {}
```

when used in variables, they are promoted to a 32-bit integer.
In the following code, `result` is a 32-bit integer (so an int)
```java
short value = 12;
var result = value + value;
```


### primitive conversions
You have automatic conversions if there is no loose of precision
and converting to double or float is always allowed
```java
int intValue = 13;
long longValue = intValue;
```

you can force conversion in the opposite direction using a cast
supplementary bits will be shaved (use with reluctance)
```java
long longValue = 1_000_000_000_000L;
int intValue = (int) longValue;
System.out.println(intValue);
```


## Objects
All other types are objects, there are two special types, String and arrays
that are object but considered as built-in by the compiler

### String
A String that stores a text (a sequence of characters) is delimited
by two doublequotes
```java
var text = "hello"; 
System.out.println(text);
```

a String can also span several lines, it's called a __text block__
and starts and ends with 3 double quotes
```java
var multilineText = """
   This is
   a multilines string
   """;
System.out.println(multilineText);
```

The indentation is determined by the alignment compared to position of the last """
By example, to have an indentation of two spaces
```java
var multilineText = """
     This is
     a multilines string
     indented by two spaces
   """;
System.out.println(multilineText);
```

Strings have a lot of methods, here is some of them
length of a String
```java
System.out.println("hello".length());
```

to upper/lower case
Locale.ROOT here ask for a result independent of the OS language
```java
System.out.println("hello".toUpperCase(Locale.ROOT));
System.out.println("hello".toLowerCase(Locale.ROOT));
```

repeat the same pattern
```java
System.out.println("|*|".repeat(3));
```

char at an index (starting with index 0)
```java
System.out.println("hello".charAt(0));
```

index of a character
```java
System.out.println("hello".indexOf('l'));
System.out.println("hello".indexOf('o'));
```

primitive to String
The fastest and easy way to convert a primitive value to a String is
to use the string concatenation (see chapter 'string formatting' for more)
```java
System.out.println("" + 3);
System.out.println("" + 7.6);
```

String to primitive
There are a bunch of static methods in Boolean, Integer or Double
(see chapter 'wrapper' for more info) 
```java
System.out.println(Integer.parseInt("3"));
System.out.println(Double.parseDouble("7.6"));
```


### Array
an array initialized with zeros (false, 0, 0.0, etc)
```java
var intArray = new int[2];
```

An array initialized with some default values
Because a value like `2` or `3` can be an numeric type
(an `int`, a `long`, a `short`, etc)
you have to specify the type of the array when you create it
```java
var intArray = new int[] {2, 3 };
var longArray = new long[] { 2, 3 };
```

you can use the operator [] to access or change the value
of an array at a specific index
```java
System.out.println(intArray[0]);
intArray[0] = 42;
System.out.println(intArray[0]);
```

trying to access an array out of its bound raised an exception
```java
intArray[-1] = 42;   // throws IndexOutOfBoundsException
```

and a special syntax to get the length of an array
Notice that there is no parenthesis when calling length,
we will see later why.
```java
var arrayLength = intArray.length;
System.out.println(arrayLength);
```

arrays have methods like \toString()` or `equals()` but
they are not implemented correctly, we will see later why
```java
System.out.println(intArray);
System.out.println(new int[] {42}.equals(new int[] {42}));
```


### On arrays 
We don't use array much in Java, we have more
powerful object like List, that we will see later 
```java
var intList = List.of(2, 3);
```


## Static methods
Because primitive types and arrays have (almost) no method,
if you want to play with them you have to use static methods.
A static method is a function that is declared on a type somewhere
that you can call using the syntax `SomeWhere.methodName(arg0, arg1, arg2)`

by example to transform a String to an int, we call the method
parseInt stored in the type `java.lang.Integer`
```java
var resultAsInt = java.lang.Integer.parseInt("42");
System.out.println(resultAsInt);
```

To transform an array to a text, there is the static method toString
on the type `java.util.Arrays`
```java
var text = java.util.Arrays.toString(intArray);
System.out.println(text);
```
