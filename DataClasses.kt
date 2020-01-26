fun main() {
  val item1 = Item()
  val item2 = Item()
  val item3 = Item(Coordinate(y = 1))
  println("Item coordinates: ${item1.currentPosition}")
  println(item1.currentPosition == item2.currentPosition)
  println(item1.currentPosition == item3.currentPosition)

  val d = Direction.N.update(item3.currentPosition)
  println(d)
  val sum = item1.currentPosition + item3.currentPosition
  println(sum)

  val plane1 = Flight.Arrived
  val plane2 = Flight.EnRoute(1000)
  val plane3 = Flight.Arrived

  println(plane2.distance)
  println(plane1 == plane3)
}

fun printFlightStatus(flight: Flight) {
  when (flight) {
    is Flight.Arrived -> println("Flight has arrived at its destination")
    is Flight.Departing -> println("Flight is preparing to leave")
    is Flight.EnRoute -> println("Flight is ${flight.distance} miles away from origin")
  }
}

sealed class Flight {
  object  Arrived : Flight()
  object  Departing : Flight()
  class   EnRoute(val distance: Int) : Flight()
}


enum class Direction(private val coord: Coordinate) {
  N(Coordinate(0,  1)),
  E(Coordinate(1,  0)),
  S(Coordinate(0, -1)),
  W(Coordinate(-1, 0));

  fun update(new: Coordinate) = coord + new
}

data class Coordinate(
  val x: Int = 0,
  val y: Int = 0
) {
  val isInBounds = x >= 0 && y >= 0

  operator fun plus(other: Coordinate) = Coordinate(x + other.x, y + other.y)
}

class Item(
  var currentPosition: Coordinate = Coordinate()
)
