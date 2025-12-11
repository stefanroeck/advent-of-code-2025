

fun main() {

  fun findPathes(directions: Map<String, List<String>>, currentPath: List<String>, foundPathes: MutableSet<List<String>>) {
    val currentToken = currentPath.last()
    if (currentToken == "out") {
      foundPathes.add(currentPath)
      return
    }

    directions[currentToken]!!
      .forEach { nextToken ->
        if (!currentPath.contains(nextToken)){
          findPathes(directions,  currentPath + nextToken, foundPathes)

        }
      }
  }

  fun part1(input: List<String>): Long {
    val directions = input.map { line ->
      line.split(":").let { (first, second) ->
        first to second.split(" ").filterNot { it.isEmpty() }
      }
    }.toMap()

    val foundPaths = mutableSetOf<List<String>>()
    findPathes(directions, listOf("you"), foundPaths)
    return foundPaths.size.toLong();
  }

  fun part2(input: List<String>): Long {
    return 0
  }

  compareAndCheck(part1(readInput("Day11_test")), 5)
  //compareAndCheck(part2(readInput("Day11_test")), 4174379265)

  val input = readInput("Day11")
  part1(input).println()
  //part2(input).println()
}
