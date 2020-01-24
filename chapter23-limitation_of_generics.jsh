// To starts, run jshell --enable-preview which is a program able to interpret Java syntax
// then cut and paste the following lines to see how it works
// To exit jshell type /exit


// # Limitations of generics

// ## No reification

In Java, generics are purely a compiler construct, the type arguments of a generics
are not available at runtime. 

For a type variable (`T`, `E`, etc), the actual value at the execution, the type argument
is not present at runtime. For a parameterized type (`List&gt;String&lt;`

- instanceof 
- cast
- 
- array creation


// ## Array of parameterized type
// Arrays are covariant in Java, it means that you can always consider an
// array of a subtype as an array of supertype,
// By example, see an array of String as an array of Object
String[] stringArray = new String[] { "hello", "array" };
Object[] objectArray = stringArray;

// This is the same reference seen as a String[] or Object[]
System.out.println(objectArray == stringArray);

// At compile time, objectArray is a Object[] but at runtime it's a String[]
System.out.println(objectArray[0]);

// In fact, this is a nonsense, because you can store an object inside a Object[]
// but not inside a String[], so the Java compiler let you write code that will
// fail at runtime

// The code below compiles because objectArray[0] is an Object, and 3 can be
// seen as an Integer. But at runtime, objectArray is a String[] and
// you can not assign an Integer to a String, so you get an ArrayStoreException
objectArray[0] = 3;   // ArrayStoreException

// In practice, this code is occurs rarely, if you consider a String[] as a Object[],
// usually, it's for getting the value out of the array not to change the value of
// the array. Anyway, it's unsafe.



// ### varargs









