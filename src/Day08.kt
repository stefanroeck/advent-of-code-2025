import kotlin.math.pow
import kotlin.math.sqrt

fun main() {

  data class JunctionBox(val x: Int, val y: Int, val z: Int)

  fun Pair<JunctionBox, JunctionBox>.distance(): Double = sqrt(
    (first.x - second.x).toDouble().pow(2) +
      (first.y - second.y).toDouble().pow(2) +
      (first.z - second.z).toDouble().pow(2)
  )

  fun junctionBoxTuples(input: List<String>): List<Pair<JunctionBox, JunctionBox>> {
    val boxes = input
      .map {
        val line = it.split(",")
        val (x, y, z) = line
        JunctionBox(x.toInt(), y.toInt(), z.toInt())
      }

    return boxes.flatMapIndexed { idx1, box1 ->
      IntRange(idx1 + 1, boxes.size - 1).map { idx2 ->
        val box2 = boxes[idx2]
        Pair(box1, box2)
      }
    }
  }

  fun MutableMap<JunctionBox, Int>.integrate(pair: Pair<JunctionBox, JunctionBox>) {
    val circuits = this
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

  fun part1(input: List<String>, numConnections: Int): Long {
    val tuples = junctionBoxTuples(input)
    // each circuit has a unique number
    val circuits = mutableMapOf<JunctionBox, Int>()
    tuples.map { it to it.distance() }
      .sortedBy { it.second }
      .take(numConnections)
      .forEach { (pair) ->
        circuits.integrate(pair)
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
    val tuples = junctionBoxTuples(input)
    val tuplesWithDistances = tuples.map { it to it.distance() }.sortedBy { it.second }

    val remainingJunctionBoxes = tuples.flatMap { it.toList() }.toMutableList()
    val circuits = mutableMapOf<JunctionBox, Int>()
    var shortestWire: Pair<JunctionBox, JunctionBox>? = null

    for ((pair) in tuplesWithDistances) {
      circuits.integrate(pair)
      remainingJunctionBoxes.removeAll(pair.toList())
      if (circuits.values.distinct().size == 1 && remainingJunctionBoxes.isEmpty()) {
        println("Last connection wire $pair")
        shortestWire = pair
        break
      }
    }

    return (shortestWire!!.first.x * shortestWire.second.x).toLong()
  }

  compareAndCheck(part1(readInput("Day08_test"), 10), 40)
  compareAndCheck(part2(readInput("Day08_test")), 25272)

  val input = readInput("Day08")
  part1(input, 1000).println()
  part2(input).println()
}

