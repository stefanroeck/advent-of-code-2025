import kotlin.math.pow
import kotlin.math.sqrt

fun main() {

  data class JunctionBox(val x: Int, val y: Int, val z: Int)

  fun Pair<JunctionBox, JunctionBox>.distance(): Double = sqrt(
    (first.x - second.x).toDouble().pow(2) +
      (first.y - second.y).toDouble().pow(2) +
      (first.z - second.z).toDouble().pow(2)
  )

  fun part1(input: List<String>, numConnections: Int): Long {
    val boxes = input
      .map {
        val line = it.split(",")
        val (x, y, z) = line
        JunctionBox(x.toInt(), y.toInt(), z.toInt())
      }

    val tuples = boxes.flatMapIndexed { idx1, box1 ->
      IntRange(idx1 + 1, boxes.size - 1).map { idx2 ->
        val box2 = boxes[idx2]
        Pair(box1, box2)
      }
    }

    // each circuit has a unique number
    val circuits = mutableMapOf<JunctionBox, Int>()
    val distances = tuples.map { it to it.distance() }
    distances
      .sortedBy { it.second }
      .take(numConnections)
      .forEach { (pair) ->
        val circuitBox1 = circuits[pair.first]
        val circuitBox2 = circuits[pair.second]
        if (circuitBox1 == null && circuitBox2 == null) {
          val newCircuitNumber = if (circuits.isEmpty()) 1 else circuits.maxOf { it.value } + 1
          circuits[pair.first] = newCircuitNumber
          circuits[pair.second] = newCircuitNumber
        } else if (circuitBox1 != null && circuitBox2 != null) {
          val newCircuitNumber = circuitBox1
          circuits[pair.first] = newCircuitNumber
          circuits[pair.second] = newCircuitNumber
          circuits.filter { it.value == circuitBox2 }.forEach { (key) ->
            circuits[key] = newCircuitNumber
          }
        } else if (circuitBox1 == null && circuitBox2 != null) {
          circuits[pair.first] = circuitBox2
        } else if (circuitBox1 != null && circuitBox2 == null) {
          circuits[pair.second] = circuitBox1
        } else error("Must not happen")
      }

    return circuits.entries
      .groupBy { it.value }
      .toList()
      .sortedByDescending { it.second.size }
      .take(3)
      .map {
        println("circuit ${it.first} with ${it.second.size} entries")
        it
      }.fold(1) { sum, row -> sum * row.second.size }

  }


  fun part2(input: List<String>): Long {
    return 0
  }

  compareAndCheck(part1(readInput("Day08_test"), 10), 40)
  //compareAndCheck(part2(readInput("Day08_test")), 40)

  val input = readInput("Day08")
  part1(input, 1000).println()
  //part2(input).println()
}

