# General

- A function that returns nothing is implicitly returning the `Unit` type
- A function that never returns at all is implicitly returning the `Nothing` type (e.g. the `TODO()` function)

# Terminology

### Single-Expression Functions

Function's that only evaluate a single expression can have their return type, curly braces, and return statement removed.

```kotlin
// Verbose
fun sayHello(name: String) {
  println("Hello, $name")
}

// Single-expression function
fun sayHello(name: String) = println("Hello, $name")
```

# Tidbits

File-level functions in Kotlin are represented in Kotlin as static methods on a class with a name based on the file in which they are declared.
