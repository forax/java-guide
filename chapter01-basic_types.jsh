// To starts, run jshell --enable-preview which is a program able to interpret Java syntax
// then cut and paste the following lines to see how it works
// To exit jshell type /exit

// # Basic Types
// Java has two kinds of type,
// - primitive types that are directly mapped to CPU basic types
// - reference types that have the address of the object in memory

// ## Primitive types 
// primitive types, written in lower case, have no method

// ### boolean (true|false)
var result = true;
var anotherResult = false;

// ### char (character)
var firstLetter = 'j';

// ### int (signed 32-bit integer)
var numberOfLegs = 2;

// ### double (64-bit floating point)
var cost = 3.78;

// ### long and float
// some more exotic types that requires a suffix (`L` or `f`)
// long (64-bit integers) and float (32-bit floating point numbers)
var longValue = 123L;
var floatValue = 123.5f;

// ### byte and short
// you also have byte (a signed 8-bit integer) and short (a signed 16-bit short integer)
// that are only useful to use less memory when defining an object
record CompactHeader(byte tag, short version) {}

// when used in variables, they are promoted to a 32-bit integer.
// In the following code, `result` is a 32-bit integer (so an int)
short value = 12;
var result = value + value;


// ### primitive conversions
// You have automatic conversions if there is no loose of precision
// and converting to double or float is always allowed
int intValue = 13;
long longValue = intValue;

// you can force conversion in the opposite direction using a cast
// supplementary bits will be shaved (use with reluctance)
long longValue = 1_000_000_000_000L;
int intValue = (int) longValue;
System.out.println(intValue);


// ## Objects
// All other types are objects, there are two special types, String and arrays
// that are objects but considered as built-in by the compiler

// ### String
// A String that stores a text (a sequence of characters) is delimited
// by two doublequotes
var text = "hello"; 
System.out.println(text);

// a String can also span several lines, it's called a __text block__
// and starts and ends with 3 double quotes
var multilineText = """
   This is
   a multilines string
   """;
System.out.println(multilineText);

// The indentation is determined by the alignment compared to position of the last """
// By example, to have an indentation of two spaces
var multilineText = """
     This is
     a multilines string
     indented by two spaces
   """;
System.out.println(multilineText);

// Strings have a lot of methods, here is some of them:

// length of a String
System.out.println("hello".length());

// to upper/lower case
// Locale.ROOT here asks for a result independent of the OS language
System.out.println("hello".toUpperCase(Locale.ROOT));
System.out.println("hello".toLowerCase(Locale.ROOT));

// repeat the same pattern
System.out.println("|*|".repeat(3));

// char at an index (starting with index 0)
System.out.println("hello".charAt(0));

// index of a character
System.out.println("hello".indexOf('l'));
System.out.println("hello".indexOf('o'));

// primitive to String
// The fastest and easy way to convert a primitive value to a String is
// to use the string concatenation (see chapter 'string formatting' for more)
System.out.println("" + 3);
System.out.println("" + 7.6);

// String to primitive
// There are a bunch of static methods in Boolean, Integer or Double
// (see chapter 'wrapper' for more info) 
System.out.println(Integer.parseInt("3"));
System.out.println(Double.parseDouble("7.6"));


// ### Array
// an array initialized with zeros (false, 0, 0.0, etc)
var intArray = new int[2];

// An array initialized with some default values
// Because a value like `2` or `3` can be an numeric type
// (an `int`, a `long`, a `short`, etc)
// you have to specify the type of the array when you create it
var intArray = new int[] { 2, 3 };
var longArray = new long[] { 2, 3 };

// you can use the operator [] to access or change the value
// of an array at a specific index
System.out.println(intArray[0]);
intArray[0] = 42;
System.out.println(intArray[0]);

// trying to access an array out of its bound raised an exception
intArray[-1] = 42;   // throws IndexOutOfBoundsException

// and a special syntax to get the length of an array
// Notice that there is no parenthesis when calling length,
// we will see later why.
var arrayLength = intArray.length;
System.out.println(arrayLength);

// arrays have methods like `toString()` or `equals()` but
// they are not implemented correctly, we will see later why
System.out.println(intArray);
System.out.println(new int[] {42}.equals(new int[] {42}));


// ### On arrays 
// We don't use array much in Java, we have more
// powerful object like List, that we will see later 
var intList = List.of(2, 3);


// ## Static methods
// Because primitive types and arrays have (almost) no method,
// if you want to play with them you have to use static methods.
// A static method is a function that is declared on a type somewhere
// that you can call using the syntax `SomeWhere.methodName(arg0, arg1, arg2)`

// by example to transform a String to an int, we call the method
// parseInt stored in the type `java.lang.Integer`
var resultAsInt = java.lang.Integer.parseInt("42");
System.out.println(resultAsInt);

// To transform an array to a text, there is the static method toString
// on the type `java.util.Arrays`
var text = java.util.Arrays.toString(intArray);
System.out.println(text);
