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

### Function Inlining

A lambda is represented on the JVM as a memory-allocated object instance, as are all of the variables accessible to the lambda. This incurs an associated memory overhead and performance cost.

`inlining` is an optimization that removes the overhead when using lambdas as arguments to other functions. It eliminates the need for the JVM to use an object instance and to perform variable memory allocations for the lambda.

To use it, mark a function that accepts a lambda using the inline keyword.

If a function accepts lambdas as any arguments, mark it as `inline`. In only a few instances is it not possible. e.g. in a recursive funciton that accepts a lambda, since the result of inlining a fucntion would be an infinite loop of copying and pasting function bodies. But, in such cases, the compiler will let you know if it won't work.

# Tidbits

File-level functions in Kotlin are represented in Kotlin as static methods on a class with a name based on the file in which they are declared.
