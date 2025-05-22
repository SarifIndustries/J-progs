val inf = 9000
val n = 5
val u = intArrayOf(2, 1, 1, 4, 3).map { it-1 }
val v = intArrayOf(3, 2, 5, 5, 4).map { it-1 }
val w = intArrayOf(2, -3, 5, 2, 3)
val dis: IntArray = IntArray(n) { if(it == 0) 0 else inf }
repeat(n) {
    for(i in 0..<u.size) {
        val source = u[i]
        val target = v[i]
        val shortcut = dis[source] + w[i]
        if (shortcut < dis[target]) dis[target] = shortcut
    }
    println(dis.joinToString())
}
