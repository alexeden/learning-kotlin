Function | Relatively scoped | Returns | Alternate name
--|--|--|--
`also` |  | `this` | Tap
`apply` | Yes | `this` | Configuration/DSL
`let` |  | Evaluation | Map
`run` | Yes | Evaluation |


`apply`

```kotlin
fun <T> T.apply(block: T.() -> Unit): T {
  block()
  return this
}
```

`also`

```kotlin
fun <T> T.also(block: (T) -> Unit): T {
  block(this)
  return this
}
```

`let`

```kotlin
fun <T, U> T.let(block: (T) -> U): U {
  return block(this)
}
```
