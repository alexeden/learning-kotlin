// Chapter 5 - Anaonymous Functions & the Function Type
// SimVillage

fun main(args: Array<String>) {
  val greeter = createGreeter("Kansas City")
  runSimulation(greeter)
  // Lambda shorthand
  // runSimulation("Alex", rand) { name, population ->
  //   val currentYear = 2020
  //   "Welcome to SimVillage, population $population, $name! © $currentYear"
  // }
}

fun createGreeter(city: String): (String) -> String {
  val currentYear = 2020
  val population = rand(1, 40).times(1000);

  return { mayorName ->
    "Welcome to $city, population $population, $mayorName! © $currentYear"
  }
}

val rand = { min: Int?, max: Int? -> ((min ?: 0) .. (max ?: 1)).shuffled().last() }

typealias Greeter = (String) -> String

inline fun runSimulation(greet: Greeter) {
  // val population = (1..40).shuffled().last() * 1000;
  // println(greet(name, randomizer(1, 40)))
  println(greet("Alex Eden"))
}
