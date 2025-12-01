fun main() {

    data class PositionWithZeroCounter(val pos: Int, val zeros: Int = 0)

    fun part1(input: List<String>): Int {
        val result = input.fold(PositionWithZeroCounter(50)) { dialPos, operation ->
            val direction = operation.first()
            val steps = operation.substring(1).toInt().rem(100)
            val nextPos = when (direction) {
                'R' -> (dialPos.pos + steps).rem(100)
                'L' -> {
                    (dialPos.pos - steps).let {
                        if (it < 0) (100 + it) else it
                    }
                }

                else -> error("Invalid direction: $direction")
            }
            dialPos.copy(pos = nextPos, zeros = if (nextPos == 0) dialPos.zeros + 1 else dialPos.zeros)
        }
        return result.zeros
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Or read a large test input from the `src/Day01.txt` file:
    check(part1(readInput("Day01_test")) == 3)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    //part2(input).println()
}
