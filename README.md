# General

- A function that returns nothing is implicitly returning the `Unit` type
- A function that never returns at all is implicitly returning the `Nothing` type (e.g. the `TODO()` function)
- Anonymous functions do not require, nor allow, the `return` keyword to output data; the last line of the function body is implicitly the return value

# Terminology

## Single-Expression Functions

Function's that only evaluate a single expression can have their return type, curly braces, and return statement removed.

```kotlin
// Verbose
fun sayHello(name: String) {
  println("Hello, $name")
}

// Single-expression function
fun sayHello(name: String) = println("Hello, $name")
```

## Shorthand Lambda Syntax

When a function accepts a function type as its last parameter, you can omit the parentheses around the lambda argument.

It's common convention to define functions such that their function type parameters are intentially the final parameter, to enable the shorthand for the function's users.

```kotlin
// Verbose
"Mississippi".count({ it == 's' })

// Shorthand
"Mississippi".count { it == 's' }
```

## Function Inlining

A lambda is represented on the JVM as a memory-allocated object instance, as are all of the variables accessible to the lambda. This incurs an associated memory overhead and performance cost.

`inlining` is an optimization that removes the overhead when using lambdas as arguments to other functions. It eliminates the need for the JVM to use an object instance and to perform variable memory allocations for the lambda.

To use it, mark a function that accepts a lambda using the inline keyword.

If a function accepts lambdas as any arguments, mark it as `inline`. In only a few instances is it not possible. e.g. in a recursive funciton that accepts a lambda, since the result of inlining a fucntion would be an infinite loop of copying and pasting function bodies. But, in such cases, the compiler will let you know if it won't work.

## Function References

A function reference converts a named (defined with `fun`) function to a value that can be passed as an argument. On its own, a named function is an `Int` type (as an address in memory).

Use the `::` operator to convert the function address to its function value.

## Safety Mechanisms

### `?.` Safe call operator

When the compiler encounters `?.`, it knows to check for a null value. If if finds one, it skips over the skipp and does not evaluate it, instead returning null.

### `let`

`let` can be called on _any_ value. Its purpose is to enable the definition of variables and expression in a new scope.

The stuff inside the `let` scope is _only evaluated if the value on which its invoked is non-null._

```kotlin
// Read username from stdin and capitalize it
var name = readLine()?.let { it.capitalize() }
println("Your name is: $name");
```

### `!!.` Double-bang operator

Formally, the non-null assertion operator. Only use in very special cases where there the execution of a program is entirely dependent on a value being not null, without option for recovery. Can also be used when non-nullness is already guaranteed.

### `?:` Elvis Operator

Formally, the null coalescing operator.



# Tidbits

File-level functions in Kotlin are represented in Kotlin as static methods on a class with a name based on the file in which they are declared.
