import java.io.File

fun main(args: Array<String>) {
  val lorem = File("data/lorem.txt")
    .readText()
    .split("\n")

  println(lorem)
}
