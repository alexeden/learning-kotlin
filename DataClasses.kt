fun main() {
  val item1 = Item()
  val item2 = Item()
  val item3 = Item(Coordinate(y = 1))
  println("Item coordinates: ${item1.currentPosition}")
  println(item1.currentPosition == item2.currentPosition)
  println(item1.currentPosition == item3.currentPosition)
}

data class Coordinate(
  val x: Int = 0,
  val y: Int = 0
) {
  val isInBounds = x >= 0 && y >= 0
}

class Item(
  var currentPosition: Coordinate = Coordinate()
)
