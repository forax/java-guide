
Java has two kinds of type,
- primitive types that are directly mapped to CPU basic types
- reference types that address of the object in memory

# primitive types 
primitive types, written in lower case, have no method

## boolean (true|false)
```java
var result = true;
var anotherResult = false;
```

## char (character)
```java
var firstLetter = 'j';
```

## int (signed 32 bits integer)
```java
var numberOfLegs = 2;
```

## double (64 bits floating point)
```java
var cost = 3.78;
```

## long and float
some more exotic types that requires a suffix ('L' or 'f')
long (64 bits integers) and float (32 bits floating point numbers)
```java
var longValue = 123L;
var floatValue = 123.5f;
```

## byte and short
you also have byte (a signed 8 bits integer) and short (a signed 16 bits short integer)
that are only useful to take less memory when defining an object
```java
record CompactHeader(byte tag, short version) {}
```

when used in variables, they are promoted to 32 bits integer
in the following code result is a 32 bits integer (so an int)
```java
short value = 12;
var result = value + value;
```


# primitive conversions
You have automatic conversions if there is no loose of precision
unless it's to convert to double or float which you are always allowed.
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


# Objects
All other types are objects, there are two special types, String and arrays
that are object but considered as built-in by the compiler

## String
String that stores a text (a sequence of characters)
```java
var text = "hello"; 
```

Strings have a lot of methods
length of a String
```java
System.out.println("hello".length());
```

to upper/lower case
```java
System.out.println("hello".toUpperCase());
System.out.println("hello".toLowerCase());
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


## Array
arrays that use the syntax new type[]
```java
var intArray = new int[] {2, 3};
```

you can use the operator [] to access or change the value
of an array at a specific index
```java
System.out.println(intArray[0]);
intArray[0] = 42;
```

trying to access an array out of its bound raised an exception
```java
//intArray[-1] = 42;   // throws IndexOutOfBoundsException
```

arrays are objects but with only one method
clone() duplicates an array
```java
var clonedArray = intArray.clone();
```

and a special syntax to get the length of an array
Notice that there is no parenthesis when calling length,
we will see later why.
```java
var arrayLength = intArray.length;
System.out.println(arrayLength);
```

arrays have methods like toString() or equals()
are not implemented correctly, we will see later why
```java
System.out.println(intArray);
System.out.println(intArray.equals(clonedArray));
```

you can also create arrays of several dimensions
```java
var matrix = new double[][] { { 2.0, 3.0}, { 4.0, 5.0 } };
```


Because primitive types and arrays have (almost) no method,
if you want to play with them you have to use static methods.
A static method is a function that is declared on a type somewhere
that you can call using the syntax SomeWhere.methodName(arg0, arg1, arg2)

by example to transform a String to an int, we call the method
parseInt stored in the type java.lang.Integer
```java
var resultAsInt = java.lang.Integer.parseInt("42");
System.out.println(resultAsInt);
```

or to transform an array to a text, there is the static method toString
on the type java.util.Arrays
```java
var text = java.util.Arrays.toString(intArray);
System.out.println(text);
```

## On arrays 
Also, we don't use array much in Java, we have more
powerful object like List, that we will see later 
```java
var intList = List.of(2, 3);
```





