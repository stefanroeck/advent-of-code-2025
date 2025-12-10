fun main() {

  data class Machine(val indicators: String, val buttonWirings: List<List<Int>>)

  fun String.applyWiring(buttonWiring: List<Int>): String {
    val chars = this.toCharArray()
    buttonWiring.forEach {
      chars[it] = when (chars[it]) {
        '.' -> '#'
        '#' -> '.'
        else -> error("Unexpected char ${chars[it]}")
      }
    }
    return chars.joinToString("")
  }


  fun parseInput(input: List<String>): List<Machine> = input.map { line ->
    val values = Regex("""^\[([.#]+)] ([(\d,) ]+) .*$""").matchEntire(line)?.groupValues
    Machine(values!![1], values[2].split(" ").map {
      it
        .replace(Regex("[()]"), "")
        .split(",").map { it.toInt() }
    })
  }

  val maxPresses = 6

  fun Machine.applyWiringAndReturnPresses(input: String, previousPresses: Int, pressCounts: MutableSet<Int>) {
    if (pressCounts.isNotEmpty() && previousPresses >= pressCounts.min()) return

    buttonWirings.forEach { wiring ->
      val newInput = input.applyWiring(wiring)
      check(newInput.length == input.length)
      if (newInput == indicators) {
        pressCounts.add(previousPresses + 1)
      } else if (previousPresses < maxPresses) {
        applyWiringAndReturnPresses(newInput, previousPresses + 1, pressCounts)
      }
    }
  }

  fun part1(input: List<String>): Long {
    val machines = parseInput(input)

    return machines.sumOf { machine ->
      val startState = ".".repeat(machine.indicators.length)
      val pressCounts = mutableSetOf<Int>()
      machine.applyWiringAndReturnPresses(startState, 0, pressCounts)
      pressCounts.min().also { println("Machine $machine solved after $it button presses") }
    }.toLong()
  }


  fun part2(input: List<String>): Long {
    return 0
  }

  compareAndCheck(part1(readInput("Day10_test")), 7)
  //compareAndCheck(part2(readInput("Day10_test.txt")), 24)

  val input = readInput("Day10")
  part1(input).println()
  //part2(input).println()
}

