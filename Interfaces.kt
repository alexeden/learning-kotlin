data class Position(val x: Int = 0, val y: Int = 0) {
  operator fun plus(other: Position) = Position(x + other.x, y + other.y)
  operator fun times(other: Position) = Position(x * other.x, y * other.y)
  fun multiply(scalar: Int) = Position(x * scalar, y * scalar)
}

interface Movable {
  var position: Position;

  fun move(delta: Position): Position;
}

data class Runner(override var position: Position = Position()) : Movable {
  override fun move(delta: Position): Position {
    return (delta + position)
      .run { multiply(2) }
      .also { position = it }
  }
}

data class Walker(override var position: Position = Position()) : Movable {
  override fun move(delta: Position): Position {
    return (delta + position).also { position = it }
  }
}

fun main() {
  val delta = Position(5, 10)
  val runner = Runner().apply { move(delta) }
  val walker = Walker().apply { move(delta) }

  println("Walker is at ${walker.position}")
  println("Runner is at ${runner.position}")
}
