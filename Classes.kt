// Chapter 12 - Defining Classes

import java.io.File

class Person(
  _name: String,
  val isImmortal: Boolean = false
) {

  lateinit var career: String

  var name = _name.trim()
      get() = field.capitalize()
      private set(value) {
        field = value.trim()
      }

  fun printCareer() {
    if (::career.isInitialized) println("$name is a $career")
  }

  val favoriteNumber
      get() = (18..99).shuffled().first().toString()
}

fun main(args: Array<String>) {
  val person = Person("alex  ")

  println(person.name + ", Favorite number: " + person.favoriteNumber + ", Immortal? " + person.isImmortal)
  person.printCareer()
}
