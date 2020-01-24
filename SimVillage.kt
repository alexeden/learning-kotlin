/**
 * Chapter 5 - Anaonymous Functions & the Function Type
 * SimVillage
 */

fun main(args: Array<String>) {
  println({
    val currentYear = 2020
    "Welcome to SimVillage, Mayor! ©$currentYear"
  }())
}
