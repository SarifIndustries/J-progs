import kotlin.math.*
abstract class Heap { // 1 index based abstract Heap
    protected abstract infix fun Int.cmp(o: Int): Boolean // should sink down?
    companion object Settings { const val capacity = 100 }
    private val array: IntArray
    var n: Int private set
    constructor(n: Int) { this.array = IntArray(capacity); this.n = n }
    constructor(array: IntArray) { this.array = IntArray(capacity).apply {
        this[0] = 0
        array.copyInto(this, destinationOffset = 1)
    }; this.n = array.size; makeHeap() }
    private fun makeHeap() {
        for(i in n/2 downTo 1) sink(i)
    }
    fun sink(i: Int) {
        if(i > n/2) return
        val li = 2*i
        val ri = 2*i + 1
        val target = if(ri > n || array[ri] cmp array[li]) li else ri
        if(array[i] cmp array[target]) {
            array[target] = array[i].also { array[i] = array[target] }
            sink(target)
        }
    }
    val root: Int get() = array[1]
    val last: Int get() = array[n]
    fun replaceTopAndSink(value: Int) {
        array[1] = value
        sink(1)
    }
    fun removeTop() {
        val t = last
        n--
        replaceTopAndSink(t)
    }
    fun insertLast(k: Int) {
        n++
        array[n] = k
        siftUp(n)
    }
    private fun siftUp(i: Int) {
        if(i == 1) return
        val current = array[i]
        val parent = array[i/2]
        if(parent cmp current) {
            array[i] = array[i/2].also { array[i/2] = array[i] }
            siftUp(i/2)
        }
    }
    override fun toString(): String = array.slice(1..n).joinToString()
}
class MinHeap: Heap {
    constructor(n: Int): super(n)
    constructor(array: IntArray): super(array)
    override infix fun Int.cmp(o: Int): Boolean = this > o
}
class MaxHeap: Heap {
    constructor(n: Int): super(n)
    constructor(array: IntArray): super(array)
    override infix fun Int.cmp(o: Int): Boolean = this < o
}
// heap fun
val h = MinHeap(intArrayOf(10,12,3,4,5,6))
val g = MinHeap(6)
println(h)
println(g)
h.replaceTopAndSink(23)
println(h)
fun IntArray.heapSort() {
    val minHeap = MinHeap(this)
    println(minHeap)
    repeat(this.size) { i ->
        this[i] = minHeap.root
        minHeap.removeTop()
        println(minHeap)
    }
}
val a = intArrayOf(3,4,1,23,6,7,45,3,1,34,6,8,3)
a.heapSort()
println(a.joinToString())
println(a.apply{sort()}.joinToString())
println("Sift Up")
val x = MinHeap(intArrayOf(3,4,1,23,6,7,45,3,1,34,6,8,3))
println(x)
x.removeTop()
x.removeTop()
x.removeTop()
println(x)
x.insertLast(50)
println(x)
x.insertLast(13)
println(x)
x.insertLast(0)
println(x)
x.removeTop()
x.insertLast(2)
println(x)
println("===")
fun IntArray.heapSort2() {
    // using max heap, 0 index based
    var n = this.size
    val sinkLambda: (Int) -> Unit = { j ->
        var i = j
        var flag = false
        while(!flag && i <= (n-1)/2) {
            val left = 2*i + 1
            val right = 2*i + 2
            var greatest = i
            if(left < n && this[left] > this[greatest]) greatest = left
            if(right < n && this[right] > this[greatest]) greatest = right
            if(greatest != i) {
                this[greatest] = this[i].also { this[i] = this[greatest] }
                i = greatest
            } else flag = true
        }
    }
    val lastNonLeaf = (n-1)/2
    for(i in lastNonLeaf downTo 0) sinkLambda(i)
    while(n > 1) {
        this[0] = this[n-1].also{ this[n-1] = this[0] }
        n--
        sinkLambda(0)
    }
}
val q = intArrayOf(3,4,1,23,6,7,45,3,1,34,6,8,3)
q.heapSort2()
println(q.joinToString())
val y = intArrayOf(3, 23,126,337,4145,123,421,2334,236,238,323)
val yHeap = MinHeap(y)
buildList {
    repeat(3) {
        add(yHeap.root)
        yHeap.removeTop()
    }
}.also{ println(it) }
// Double Heap Median value
val dataStream = intArrayOf(3, 8, 9, 2, 1, 4, 6, 9, 19, 12, 14, 11, 2, 4, 14, 7)
val leftHeap:  Heap = MaxHeap(0)
val rightHeap: Heap = MinHeap(0)
var median = 0
val process: (Int) -> Unit = proc@ {
    println("Processing: $it")
    /* Phase 1 */ (if(it < median) leftHeap else rightHeap).insertLast(it)
    println("Phase 1 L>[$leftHeap]\tM>|$median|\tR>[$rightHeap]")
    /* Check */ if (abs(leftHeap.n - rightHeap.n) < 2) return@proc
    /* Phase 2 */ val (source, target) = if(leftHeap.n > rightHeap.n) leftHeap to rightHeap else rightHeap to leftHeap
    target.insertLast(median)
    median = source.root.also { source.removeTop() }
    println("Phase 2 L>[$leftHeap]\tM>|$median|\tR>[$rightHeap]")
}
dataStream.forEach {
    process(it)
}
