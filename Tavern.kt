// Chapter 6 - Null Safety and Exceptions
// NyetHack Tavern

fun main(args: Array<String>) {
  var name = readLine()?.let { it.capitalize() }
  println("Your name is: $name");
}
