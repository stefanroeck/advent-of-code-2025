import kotlin.math.floor


fun main() {

  data class PositionWithZeroCounter(val pos: Int, val zeros: Int = 0)

  fun countZeros(input: List<String>, countTicks: Boolean = false): Int {
    return input.fold(PositionWithZeroCounter(50)) { dialPos, operation ->
      val direction = operation.first()
      var additionalTicks = 0
      val steps = operation.substring(1).toInt().also {
        additionalTicks += floor(it / 100.0).toInt()
      }.rem(100)
      
      val nextPos = when (direction) {
        'R' -> (dialPos.pos + steps).also {
          if (it > 100 /* ignore 100*/) additionalTicks++
        }.rem(100)

        'L' -> {
          (dialPos.pos - steps).let {
            if (it < 0) {
              (100 + it).also {
                if (dialPos.pos != 0)
                  additionalTicks++
              }
            } else it
          }
        }

        else -> error("Invalid direction: $direction")
      }
      val newZeroCount = (if (nextPos == 0) dialPos.zeros + 1 else dialPos.zeros).let {
        if (countTicks) it + additionalTicks else it
      }
      dialPos.copy(pos = nextPos, zeros = newZeroCount)
    }.zeros
  }

  fun part1(input: List<String>): Int {
    return countZeros(input, countTicks = false)
  }

  fun part2(input: List<String>): Int {
    return countZeros(input, countTicks = true)
  }

  compareAndCheck(part1(readInput("Day01_test")), 3)
  compareAndCheck(part2(readInput("Day01_test")), 6)

  val input = readInput("Day01")
  part1(input).println()
  part2(input).println()
}
