
val digits = listOf(9,8,7,6,5,4,3,2,1,0).map { it.toString() }

fun main() {

  fun String.largestDigitPosition(startIndex: Int = 0): Int {
    digits.forEach { digit ->
      indexOf(digit, startIndex).let { index ->
        if (index != -1) {
          if (startIndex != 0 || index != length - 1)
            return index
        }
      }
    }
    error("Must not happen")
  }

  fun part1(input: List<String>): Long {
    return input.filterNot { it.isEmpty() }.map { bank ->
      val firstDigitPos = bank.largestDigitPosition()
      val secondDigitPos = bank.largestDigitPosition(firstDigitPos + 1)
      ("${bank[firstDigitPos]}${bank[secondDigitPos]}").toInt()
    }.sumOf { it.toLong() }
  }

  fun part2(input: List<String>): Long {
    return 0
  }

  compareAndCheck(part1(readInput("Day03_test")), 357)
  //compareAndCheck(part2(readInput("Day03_test")), 4174379265)

  val input = readInput("Day03")
  part1(input).println()
  //part2(input).println()
}
