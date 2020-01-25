// Chapter 5 - Anaonymous Functions & the Function Type
// SimVillage

fun main(args: Array<String>) {
  println(args)
  // Lambda shorthand
  runSimulation("Alex") { name, population ->
    val currentYear = 2020
    "Welcome to SimVillage, population $population, $name! © $currentYear"
  }
}

val randPopulation = { (1..40).shuffled().last().times(1000) }

typealias Greeter = (String, Int) -> String

inline fun runSimulation(name: String, greet: Greeter) {
  // val population = (1..40).shuffled().last() * 1000;
  println(greet(name, randPopulation()))
}
