class MapOfPoints(input: List<String>) {

  private val data: List<ValuePoint> = input.flatMapIndexed { y, line ->
    line.split(" ").filter { it.isNotBlank() }.mapIndexed { x, token -> ValuePoint(x, y, token) }
  }

  fun allPoints(): List<ValuePoint> {
    return data
  }

  fun columns(): List<List<ValuePoint>> {
    return allPoints().groupBy { it.x }.map { it.value }
  }
}

data class ValuePoint(val x: Int, val y: Int, val value: String)