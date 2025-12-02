import kotlin.math.floor

fun main() {

  fun part1(input: List<String>): Long {
    val invalidIds = mutableListOf<String>()

    val ranges = input.first().split(",")
    ranges.forEach { range ->
      val (from, to) = range.split("-").map { it.toLong() }
      check(from < to) { "from must be smaller than two" }

      LongRange(from, to).forEach { number ->
        val asString = number.toString()
        if (asString.length % 2 == 0) {
          val half = floor(asString.length / 2.0).toInt()
          val firstPart = asString.substring(0..<half)
          val secondPart = asString.substring(half)
          if (firstPart == secondPart) {
            invalidIds.add(asString)
          }
        }
      }
    }
    println(invalidIds)

    return invalidIds.sumOf { it.toLong() }
  }

  fun part2(input: List<String>): Int {
    return 0
  }

  compareAndCheck(part1(readInput("Day02_test")), 1227775554)
  //compareAndCheck(part2(readInput("Day02_test")), 6)

  val input = readInput("Day02")
  part1(input).println()
//  part2(input).println()
}
