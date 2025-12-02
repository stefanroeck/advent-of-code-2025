import java.util.function.Predicate
import kotlin.math.floor

fun main() {

  fun checkInvalidIdPart1(asString: String): Boolean {
    if (asString.length % 2 == 0) {
      val half = floor(asString.length / 2.0).toInt()
      val firstPart = asString.substring(0..<half)
      val secondPart = asString.substring(half)
      if (firstPart == secondPart) {
        return true
      }
    }
    return false
  }

  fun searchForInvalidIds(input: List<String>, predicate: Predicate<String>): Long {
    val invalidIds = mutableListOf<String>()

    val ranges = input.first().split(",")
    ranges.forEach { range ->
      val (from, to) = range.split("-").map { it.toLong() }
      check(from < to) { "from must be smaller than two" }

      LongRange(from, to).forEach { number ->
        val asString = number.toString()
        if (predicate.test(asString)) {
          invalidIds.add(asString)
        }

      }
    }
    println(invalidIds)

    return invalidIds.sumOf { it.toLong() }
  }

  fun checkInvalidIdPart2(asString: String): Boolean {
    asString.repeatableSequences().forEach { sequence ->
      if (asString.replace(sequence, "") == "") {
        return true
      }
    }

    return false
  }

  fun part1(input: List<String>): Long {
    return searchForInvalidIds(input, ::checkInvalidIdPart1)
  }

  fun part2(input: List<String>): Long {
    return searchForInvalidIds(input, ::checkInvalidIdPart2)
  }

  compareAndCheck(part1(readInput("Day02_test")), 1227775554)
  compareAndCheck(part2(readInput("Day02_test")), 4174379265)

  val input = readInput("Day02")
  part1(input).println()
  part2(input).println()
}

private fun String.repeatableSequences(): Set<String> {
  return (1..floor(length / 2.0).toInt()).map {
    substring(0, it)
  }.toSet()
}
