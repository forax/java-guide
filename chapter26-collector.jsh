// To starts, run jshell --enable-preview which is a program able to interpret Java syntax
// then cut and paste the following lines to see how it works
// To exit jshell type /exit

// A `java.util.stream.Collector` is defined by 4 methods
// - a container creator
// - an accumulator method that adds an element to a collection
// - a joining method that merges two collections
// - a finishing method that can transform the collector at the end
//   (by example to make it immutable)
