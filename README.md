# General

- A function that returns nothing is implicitly returning the `Unit` type
- A function that never returns at all is implicitly returning the `Nothing` type (e.g. the `TODO()` function)
- Anonymous functions do not require, nor allow, the `return` keyword to output data; the last line of the function body is implicitly the return value
- All exceptions in Kotlin are _unchecked_; that is, you're not forced to guard against exceptions, which has the side-effect of doing away with the issue of error swallowing
- `==` is the structural equality operator; `===` is the referential equality operator, which checks if the operands are pointing to the same object instance memory on the heap
- All numeric types in Kotlin are _signed_
- A `receiver` is the subject of an extension function
- _Relative scoping_ is a behavior wherein function calls in a scope are called relative to the scopes receiver (see `apply`)
- A `List`'s immutability is not enforced; it's possible to cast `List` to a `MutableList` and mutate it
- `to` is a `Pair` constructor function; it's a special type of function that allows you to drop the dot and the parenthesis around its arguments; `"x" to "y" //=> (x, y) Pair<String, String>`
- The `field` keyword is similar to `it`, in that it represents the backing field for a class property referenced in getters/setters
- Class properties with custom getters and setters cannot be declared in constructor parameters
- Use initializer `init` blocks for property assertion logic; an `init` block will be called when a class is instantiated, regardless of which constructor is used
- Frequent use of `isInitialized` is a code smell; consider using nullable types instead
- Any class can be made destructurable by adding component operator functions; using a data class will implement destructuring automatically
- If you override the `equals` operator, you should also override `hashCode`
- Extensions used by multiple files should be stored in their own project package; the convention for file naming is typically the type the extension applies to, plus `Ext.kt`, e.g. `extensions/IterableExt.kt`
- Standard library files that contain extensions to a type are often named `<the type>s.kt`, e.g. `Strings.kt`

# Concepts

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

Formally, the null coalescing operator. In short, if the expression to the left of the operator evaluates to null, evaluate the thing on the right side.

```kotlin
// Read username from stdin or use noname if none provided
val name = readLine()?.let { it.capitalize() } ?: "noname"
println("Your name is: $name");
```

### Standard Precondition Functions

`checkNotNull`

If argument value is `null`, throws `IllegalStateException`.
Otherwise return non-null value.

`require`

If argument value is `false`, throws `IllegalArgumentException`.

`requireNotNull`

If argument value is `null`, throws `IllegalArgumentException`.
Otherwise return non-null value.

`error`

If argument value is `null`, throws `IllegalArgumentException` with second argument as the provided message.
Otherwise return non-null value.

`assert`

If argument value is `false` _and_ the assertion compiler flag is enabled, throws `AssertionError`.

## Standard Extension Functions

`apply`

"Configuration function". Allows you to call a series of functions on a receiver to configure it for use. `apply` scopes each function call within the lambda to the receiver it is called on. (relative scoping)

Passes nothing to the lambda. (no receiver `it`)
Returns the receiver.

```kotlin
val menuFile = File("menu-file.txt").apply {
  setReadable(true) // Implicitly: menuFile.setReadable(true)
  setWritable(true)
  setExecutable(false)
}
```

`let`

Passes the receiver to the lambda.
Returns the value of the evaluated lambda expression(s).

`run`

"Pipe". Nearly the same as `apply`, but it does not return the receiver; rather it returns the evaluated lambda.

`with`

Identical to `run`, but different calling convention. For stylistic reasons, don't use it.

`also`

"Tap". Nearly the same as `let`, except that it returns the receiver, rather than the evaluated lambda.

Usage: Performing multiple side effects from a common source.

```kotlin
var fileLines: List<String>
File("file.txt")
  .also { print(it.name) }
  .also { fileLines = it.readLines() }
```

`takeIf`

Sorta similar to `let`. Provided with a _predicate_ lambda, it'll return the receiver if the predicate evaluates to true, `null` otherwise.

Usage: It's similar to a regular `if` statement, except that it can usually avoid the creation of a temporary variable.

```kotlin
// Read a file if and only if it's readable

// Verbose
val file = File("file.txt")
val fileContents = if (file.canRead() && file.canWrite()) {
  file.readText()
} else {
  null
}

// With `takeIf`
val fileContents = File("file.txt")
  .takeIf { it.canRead() && it.canWrite() }
  ?.readText()
```

`takeUnless`

Complement of `takeIf`.

## Collections

Use `getOrElse` or `getOrNull` for safe index access.
`getOrElse`'s second argument is a lambda that returns a default value.


## Initialization Order

Initialization of a class instance's properties happens in the following order:

1. Primary constructor's inline properties
2. Required class-level property assignments in the class body
3. `init` block property assignments and function calls
4. Secondary constructor property assignments and function calls

## Lazy Initialization

Lazy initialization is implemented using a mechanism called a _delegate_, which define templates for how a property is initialized. It takes a lambda in which you define code that's executed when the property is initialized.

Delegates use the `by` keyword. `lazy` is the delegate used for lazy initialization.

A lazy property remains uninitialized until it's referenced for the first time. _Initialization code is executed only once._ Future access uses a cached result.

```kotlin
class RandomCity {
  val name by lazy { selectRandomCity() }

  private fun selectRandomCity() = File("cities.txt")
    .readText()
    .split("\n")
    .shuffled()
    .first()
}
```

## Objects

Do no have constructors. Instantiated automatically on first use. Use `init` block for initialization logic.

There are 3 ways to use `object` singletons.

### 1. Object Declarations

Useful for organization and state management.

Private classes can be nested in objects.

### 2. Object expressions

Used when you need a class instance that is a variation of an existing class and will be used for a one-off purpose.

Not sure yet how these are useful...

### 3. Companion objects

Ties the initialization of an object to a class instance. Declared within another class declaration using the `companion` modifier.

Classes cannot have more than one companion object.

Useful when you need a place for singleton data that has a contextual connection to a class definition.

Initialized in one of two cases:
1. When a companion's object enclosing class is initialized
2. When a companion's object's property or function is accessed directly

## Data Classes

Data classes provide major advantages over regular classes: they provide default implementations for structural equality checking, as well as instance copying; they allow destructuring declarations.

### Limitations

1. Data classes must have a primary constructor with at least one parameter
2. Primary constructor parameters must be marked `val` or `var`
3. Cannot be `abstract`, `open`, `sealed`, or `inner`

Although these don't really seem like limitation from an FP perspective...

## Enumerated Classes

Enums are a special type of class for defining a collection of constants called _enumerated types._

Enum values can be accessed as a stringed key using `EnumName.valueOf(str)`.

## Sealed Classes

`sealed` classes allow implementation of more complex ADTs, enabling more control over the specific subtypes than a regular enum provides.


```kotlin
sealed class Flight {
  object  Arrived : Flight()
  object  Departing : Flight()
  class   EnRoute(val distance: Int) : Flight()
}

fun printFlightStatus(flight: Flight) {
  when (flight) {
    is Flight.Arrived -> println("Flight has arrived at its destination")
    is Flight.Departing -> println("Flight is preparing to leave")
    is Flight.EnRoute -> println("Flight has traveled ${flight.distance} miles")
  }
}
```

## Generics

`vararg` causes a type variable to be treated as an `Array`

The following parameter definitions are equivalent:

```kotlin
fun main(args: Array<String>)
fun main(varargs args: String)
```

### Variance

A generic type variable can have one of two roles: _producer_ or _consumer_.

- `out`: producer, covariant, readable, not writable
- `in`: consumer, contravariant, writable, not readable

### Reification

Type variables are subject to what the Kotlin compiler calls _type erasure._ Type information for a type variable is not available at runtime, making an expression like `x is T` impossible to evaluate.

However, Kotlin provides the `reified` keyword, which allows the retainment of type information at runtime, without requiring reflection (learning the name or a type of property or function at runtime).


### Generic Function Receivers

Relative scoping of a generic lambda is achieved using the `T.()` expression.

To clarify, this is the definition of the relatively-scoped `.apply` function:

```kotlin
public inline fun <T> T.apply(block: T.() -> Unit): T {
  block()
  return this
}
```

This style of function literals with receivers enables the use of _domain-specific languages._

# Tidbits

File-level functions in Kotlin are represented in Kotlin as static methods on a class with a name based on the file in which they are declared.
