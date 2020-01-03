// To starts, run jshell --enable-preview which is a program able to interpret Java syntax
// then cut and paste the following lines to see how it works
// To exit jshell type /exit

// if you have not read the previous chapter on interfaces, starts by it first

// # Lambda and Method reference 
// Java unlike JavaScript or Python, don't let you pass a method as argument of a method
// without ceremony
// Let say i want to write a method that do either the sum of an array of values or the sum of their square,
// it can write if that way
int sumOf(int[] array, boolean squareSum) {
  var sum = 0;
  for(var value: array) {
    if (squareSum) {
      sum = sum + value * value;
    } else {
      sum = sum + value;
    }
  }
  return sum;
}

// but you every values of the array, squareSum will have the same value so it's equivalent to write
int sumOf(int[] array, boolean squareSum) {
  var sum = 0;
  if (squareSum) {
    for(var value: array) {
      sum = sum + value * value;
    }
  } else {
    for(var value: array) {
      sum = sum + value;
    }
  }
  return sum;
}

// and at that point, you have code duplication.
// Usually testing a condition in the middle of a computation is a code smell.
// There is a way to solve that, it's to take the part of the computation that change as parameter
// so sumOf instead of a boolean that take a function as parameter more or less like this
/*
int sumOf(int[] array, ??? function) {
  var sum = 0;
  for(var value: array) {
    sum = sum + function(value);
  }
  return sum;
}
*/

// ## Functional interface
// the question is what ??? is. The answer in simple in Java, if it can be either a value or another one,
// then it's an interface. Exactly like in the previous chapter, we have introduce an interface in
// between two records.
// Here my interface is a function that takes an int and return an int so
interface Fun {
  int apply(int value);
}

int sumOf(int[] array, Fun function) {
  var sum = 0;
  for(var value: array) {
    sum = sum + function.apply(value);
  }
  return sum;
}

// then using the lambda syntax we have seeing in the previous chapter sumOf can be called
var array = new int[] { 1, 2, 3 };
System.out.println(sumOf(array, x -> x));
System.out.println(sumOf(array, x -> x * x));


// ## Package java.util.function

// because it's not convenient to have to declare an interface every times you want to send
// a function as parameter, Java already provides a bunch of interfaces in the package
// java.lang.function, so you often don't have to write your own
// Moreover most interface also have variant for primitive types 

// java.lang.Runnable is equivalent to () -> void
Runnable runnable = () -> { System.out.println("hello"); }
runnable.run();

// Supplier<T> is equivalent to () -> T
Supplier<String> supplier = () -> "hello supplier";
System.out.println(supplier.get());

// IntSupplier, LongSupplier and DoubleSupplier
IntSupplier supplier = () -> 42;
System.out.println(supplier.getAsInt());

// Consumer<T> is equivalent to (T) -> void
Consumer<String> consumer = message -> System.out.println(message);
consumer.accept("hello consumer");

// IntConsumer, LongConsumer and DoubleConsumer
DoubleConsumer consumer = message -> System.out.println(message);
consumer.accept(42);

// Predicate<T> is equivalent to (T) -> boolean
Predicate<String> predicate = text -> text.length() < 5;
System.out.println(predicate.test("hello predicate"));

// IntPredicate, LongPredicate and DoublePredicate
DoublePredicate isPositive = v -> v >= 0;
System.out.println(isPositive.test(17.3));

// Function<T,U> is equivalent to (T) -> U
Function<String, String> fun = s -> "hello " + s;
System.out.println(fun.apply("function"));

// IntFunction<T>, LongFunction<T> and DoubleFunction<T>
IntFunction<String[]> arrayCreator = size -> new String[size];
System.out.println(arrayCreator.apply(0));

// ToIntFunction<T>, ToLongFunction<T> and ToDoubleFunction<T>
ToIntFunction<String> stringLength = s -> s.length();
System.out.println(stringLength.applyAsInt("hello"));

// UnaryOperator<T> is equivalent to (T) -> T
UnaryOperator<String> unaryOp =  s -> "hello " + s;
System.out.println(unaryOp.apply("unary operator"));

// IntUnaryOperator, LongUnaryOperator and DoubleUnaryOperator
IntUnaryOperator unaryOp =  x -> - x;
System.out.println(unaryOp.applyAsInt(7));

// BiPredicate is equivalent to (T, U) -> boolean
BiPredicate<String, String> predicate = (s, prefix) -> s.startsWith(prefix);
System.out.println(predicate.test("hello", "hell"));

// BiFunction<T,U,V> is equivalent to (T, U) -> V
BiFunction<String, String, String> fun = (s1, s2) -> s1 + " " + s2;
System.out.println(fun.apply("hello", "bi-function"));

// BinaryOperator<T> is equivalent to (T, T) -> T
BinaryOperator<String> binaryOp =  (s1, s2) -> s1 + " " + s2;
System.out.println(binaryOp.apply("hello", "binary operator"));

// IntBinaryOperator, LongBinaryOperator and DoubleBinaryOperator
IntBinaryOperator binaryOp =  (a, b) -> a + b;
System.out.println(binaryOp.applyAsInt(40, 2));



// ## Lambda
// Lambda syntax is similar to arrow part the switch syntax
// - with 0 parameter: () -> expression
// - with 1 parameter: x -> expression
// - with 2 or more parameters: (a, b) -> expression
DoubleUnaryOperator op = x -> 2.0 * x;

// instead of an expression, you can have statements between curly braces
DoubleUnaryOperator op = x -> {
    return 2.0 * x;
  };

// The types of the parameters are optional so you can declare them or not
// if you don't declare them the parameter types of the abstract method
// of the interface are used
DoubleUnaryOperator op = (double x) -> 2.0 * x;


// ## Method references
// There are 5 kinds of method references

// 1. a reference to an instance method

//    Seeing an instance method as a function means you have to
//    take the type of `this` into account, here `startsWith` as
//    one parameter but the function as two 
BiPredicate<String,String> predicate = String::startsWith;
System.out.println(predicate.test("hello", "hell"));

// 2. a bound reference to an instance method

//    The value of this is fixed so the parameter of the function
//    are the same as the parameter of the instance method
var text = "hello";
IntSupplier supplier = text::length;
System.out.println(supplier.getAsInt());

// 3. a reference to a static method

//    No instance here, so the parameter of the function
//    are the same as the parameter of the static method
ToIntFunction<String> function = Integer::parseInt;
System.out.println(function.applyAsInt("42"));

// 4. a reference to a new instance

//    The parameter of the function are the same as the
//    parameter of the constructor. The return type is
//    is class of the constructor
record Person(String name) {}
Function<String, Person> factory = Person::new;
System.out.println(factory.apply("John"));

// 5. a reference to a new array

//    Same as above, the return type is the array.
IntFunction<String[]> arrayCreator = String[]::new;
System.out.println(arrayCreator.apply(2));

// A frequent error is to think that String::length is a reference
// to a static method because the syntax is close to String.length()
// which is a call to a static method. But for a method reference,
// the same syntax is used to reference an instance method and
// a static method. So String::length is a reference to an instance
// method because the method length() in the class String is declared
// as an instance method.

