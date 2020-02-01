A `java.util.stream.Collector` is defined by 4 methods
- a container creator
- an accumulator method that adds an element to a collection
- a joining method that merges two collections
- a finishing method that can transform the collector at the end
  (by example to make it immutable)
