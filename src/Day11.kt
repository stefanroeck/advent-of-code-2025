import java.util.concurrent.atomic.AtomicLong
import java.util.function.Predicate


fun main() {

  fun findPaths(directions: Map<String, List<String>>, currentPath: List<String>, foundPathes: AtomicLong, pathPredicate: Predicate<List<String>>) {
    val currentToken = currentPath.last()
    if (currentToken == "out") {
      if (pathPredicate.test(currentPath)) {
        foundPathes.incrementAndGet()
      }
      return
    }

    directions[currentToken]!!.let {
      it.forEach { nextToken ->
        if (!currentPath.contains(nextToken)) {
          findPaths(directions, currentPath + nextToken, foundPathes, pathPredicate)
        }
      }
    }
  }

  fun parseInput(input: List<String>): Map<String, List<String>> = input.associate { line ->
    line.split(":").let { (first, second) ->
      first to second.split(" ").filterNot { it.isEmpty() }
    }
  }


  fun part1(input: List<String>): Long {
    val directions = parseInput(input)

    val foundPaths = AtomicLong(0)
    findPaths(directions, listOf("you"), foundPaths) {true}
    return foundPaths.toLong();
  }

  fun part2(input: List<String>): Long {
    val directions = parseInput(input)

    val foundPaths = AtomicLong(0)
    findPaths(directions, listOf("svr"), foundPaths) { it.contains("dac") && it.contains("fft") }
    return foundPaths.toLong();
  }

  compareAndCheck(part1(readInput("Day11_test")), 5)
  compareAndCheck(part2(readInput("Day11_test2")), 2)

  val input = readInput("Day11")
  part1(input).println()
  part2(input).println()
}
