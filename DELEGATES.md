# Delegates

> There are two types of delegates:
> 1. *Class delegates* replace inheritance with composition.
> 2. *Property delegates* replace the getters and setters for a property with those from another class.


## Class Delegates

> Delegation allows public functionality of a contained object to be added to a wrapper object.

We can define a class describing a smartphone by first defining its disparate, independently-functioning components. For instance, a smartphone has a phone, making it *Dialable*, and it has a camera, making it *Snappable*.

First we declare interfaces that define the API of things that are Dialable and Snappable.

```kotlin
interface Dialable {
  fun dial(number: String): String
}

interface Snappable {
  fun takePicture(): String
}
```

Then we need the associated classes that implement these interfaces.

```kotlin
class Phone : Dialable {
  fun dial(number: String): "Calling $number..."
}

class Camera : Snappable {
  fun takePicture(): String = "Taking picture..."
}
```

Now we can define our smartphone.

```kotlin
class Smartphone(
  private val phone: Dialable = Phone(),
  private val camera: Snappable = Camera()
) : Dialable by phone, Snappable by camera

Smartphone().run {
  dial("8168052288") //=> "Calling 8168052288..."
  takePicture() //=> "Taking picture..."
}
```

It's a phone! Note that the contained objects themselves, `phone` and `camera`, are not exposed; just their public functions are.

> The expression `Smartphone(val phone: Dialable) : Dialable by phone` can be read "`Smartphone` is made dialable *by* `phone`"

## Property Delegates

> On a class property, the `by` keyword means that the property's getter and setter are implemented by a different object, called a *delegate.*

Most property delegates implement one of several property interfaces, depending on the desired behavior: `ReadWriteProperty`, `ReadOnlyProperty`, `ObservableProperty`, etc.

Commonly used built-in delegates: `lazy`, `notNull`, `observable`, `vetoable`.



### TODO: Add example of delegating to a `Map`
