fun main() {
  Game.start()
  val players = (1..10).map { Player() }

  players.forEach {
    println("Player ${it.id}")
  }

}

object Game {
  init {
    println("Game is initializing...")
  }

  fun start() {
    println("Starting the game!")
  }
}

class Player() {
  companion object {
    private var lastId = 0

    init {
      println("Player companion object init...")
    }

    private fun register(): Int {
      return ++lastId
    }
  }

  val id: Int

  init {
    id = register()
  }
}
