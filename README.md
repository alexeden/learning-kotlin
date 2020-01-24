# General

- A function that returns nothing is implicitly returning the `Unit` type
- A function that never returns at all is implicitly returning the `Nothing` type (e.g. the `TODO()` function)
- Anonymous functions do not require, nor allow, the `return` keyword to output data; the last line of the function body is implicitly the return value

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

### Shorthand Lambda Syntax

When a function accepts a function type as its last parameter, you can omit the parentheses around the lambda argument.

It's common convention to define functions such that their function type parameters are intentially the final parameter, to enable the shorthand for the function's users.

```kotlin
// Verbose
"Mississippi".count({ it == 's' })

// Shorthand
"Mississippi".count { it == 's' }
```

# Tidbits

File-level functions in Kotlin are represented in Kotlin as static methods on a class with a name based on the file in which they are declared.
