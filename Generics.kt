interface Valuable {
  val value: Int
}

class Vault<T: Valuable>(
  vararg private val item: T
) {
  private var isUnlocked = false

  fun unlock(): Vault<T> {
    return this.also { isUnlocked = true }
  }

  fun access(): Array<out T>? {
    return item.takeIf { isUnlocked }
  }

  fun <R> swap(trader: (Array<out T>) -> R): R? {
    return trader(item).takeIf { isUnlocked }
  }
}

data class Jewelry(
  override val value: Int, val name: String, val description: String = "") : Valuable

data class Cash(override val value: Int): Valuable

fun main(vararg args: String) {
  val vault1 = Vault(Cash(10_000_000))
  val vault2 = Vault(Jewelry(2_000_000, "Diamonds"), Jewelry(2_000_000, "Emeralds"))

  vault2.unlock().access()?.run {
    val names = toList().map { it.name }
    println("Opened the vault containing $names")
  }

  // Cash out the jewelry
  val cash = vault2.swap() {
    it.toList().map { it.value }.sum()
  }

  cash?.let { println("Sold the jewelry for $$it") }
}
