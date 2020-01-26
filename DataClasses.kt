fun main() {
  val c1 = Coordinate(1, 2)

  val (x, y) = c1

  println("$c1, $x, $y")
}


data class Coordinate(
  val x: Int,
  val y: Int
)
