import java.io.File

fun main(args: Array<String>) {
  val lorem = File("data/lorem.txt")
    .readText()
    .split("\n")
    .filter { it.isEmpty().not() }

  println(lorem)

  val p = "x" to "y" to "z"
  println(p)
}
