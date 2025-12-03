fun main() {

  fun String.largestDigitPosition(startIndex: Int = 0, endingPositionsToIgnore: Int = 0): Int {
    for (digit in 9 downTo 0) {
      val stringToInvestigate = dropLast(endingPositionsToIgnore)
      stringToInvestigate.indexOf(digit.toString(), startIndex).let { index ->
        if (index != -1) {
          return index
        }
      }
    }
    error("Must not happen")
  }

  fun part1(input: List<String>): Long {
    return input.filterNot { it.isEmpty() }.map { bank ->
      val firstDigitPos = bank.largestDigitPosition(0, 1)
      val secondDigitPos = bank.largestDigitPosition(firstDigitPos + 1)
      ("${bank[firstDigitPos]}${bank[secondDigitPos]}").toInt()
    }.sumOf { it.toLong() }
  }

  fun part2(input: List<String>): Long {
    return input.filterNot { it.isEmpty() }.map { bank ->
      var lastPosition = -1
      var joltage = ""
      for (digit in 11 downTo 0) {
        lastPosition = bank.largestDigitPosition(lastPosition + 1, digit)
        joltage += bank[lastPosition]
      }
      println("bank $bank -> joltage $joltage")
      joltage.toLong()
    }.sumOf { it }
  }

  compareAndCheck(part1(readInput("Day03_test")), 357)
  compareAndCheck(part2(readInput("Day03_test")), 3121910778619)

  val input = readInput("Day03")
  part1(input).println()
  part2(input).println()
}
