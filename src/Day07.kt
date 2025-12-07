fun main() {


  fun processBeamDown(
    map: MapOfPoints,
    position: ValuePoint,
    splitCount: Long,
    beamStartingPoints: MutableSet<ValuePoint>
  ): Long {
    var downBeam: ValuePoint? = position
    do {
      check(downBeam != null)
      downBeam = map.pointAt(downBeam.x, downBeam.y + 1)
    } while (downBeam != null && downBeam.value == ".")

    if (downBeam == null) {
      return splitCount
    }
    check(downBeam.value == "^")

    val leftBeam = map.pointAt(downBeam.x - 1, downBeam.y)
    val rightBeam = map.pointAt(downBeam.x + 1, downBeam.y)
    println("Splitting at $downBeam")

    var additionalSplits = 0
    val splitCountLeft = leftBeam?.let {
      if (!beamStartingPoints.contains(it)) {
        additionalSplits = 1
        beamStartingPoints.add(it)
        processBeamDown(map, it, splitCount, beamStartingPoints)
      } else 0
    } ?: 0
    val splitCountRight = rightBeam?.let {
      if (!beamStartingPoints.contains(it)) {
        additionalSplits = 1
        beamStartingPoints.add(it)
        processBeamDown(map, it, splitCount, beamStartingPoints)
      } else 0
    } ?: 0
    return splitCountLeft + splitCountRight + additionalSplits
  }

  fun part1(input: List<String>): Long {
    val map = MapOfPoints(input, "")
    val start = map.pointsWithValue("S").single()
    var beamStartingPoints = mutableSetOf<ValuePoint>()

    return processBeamDown(map, start, 0, beamStartingPoints)
  }


  fun part2(input: List<String>): Long {
    return 0
  }

  compareAndCheck(part1(readInput("Day07_test")), 21)
  //compareAndCheck(part2(readInput("Day07_test")), 3263827)

  val input = readInput("Day07")
  part1(input).println()
  //part2(input).println()
}

