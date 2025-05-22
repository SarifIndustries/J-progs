val inf = 9000
val a: Array<IntArray> = arrayOf(
  intArrayOf(0,   1,   12,  inf, inf, inf),
  intArrayOf(inf, 0,   9,   3,   inf, inf),
  intArrayOf(inf, inf, 0,   inf, 5,   inf),
  intArrayOf(inf, inf, 4,   0,   13,  15),
  intArrayOf(inf, inf, inf, inf, 0,   4),
  intArrayOf(inf, inf, inf, inf, inf, 0)
)
println(a.joinToString("\n") { it.joinToString() })
val dis: IntArray = IntArray(6).apply { a[0].copyInto(this) }
println(dis.joinToString())
val book: BooleanArray = BooleanArray(6).apply { this[0] = true }
for(i in 1 until a.size) {
  var fix = 0
  var minDist = inf
  // Find new nearest point
  dis.forEachIndexed { j, v ->
    if(book[j]) return@forEachIndexed
    if(v < minDist) minDist = v.also { fix = j }
  }
  book[fix] = true
  // Shortcuts
  for(v in 0 until 6) {
    if(a[fix][v] >= inf) continue
    val shortcut = dis[fix] + a[fix][v]
    if(dis[v] > shortcut) dis[v] = shortcut
  }
  println(book.joinToString())
  println(dis.joinToString())
}
