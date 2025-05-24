// Complete binary tree with heap properties
class MinHeap { // 1 index based
    private val array: IntArray
    private var n: Int
    constructor(n: Int) { this.array = IntArray(n + 1); this.n = n }
    constructor(array: IntArray) { this.array = IntArray(array.size + 1).apply {
        this[0] = 0
        array.copyInto(this, destinationOffset = 1)
    }; this.n = array.size; makeHeap() }
    fun makeHeap() {
        for(i in n/2 downTo 1) sink(i)
    }
    fun sink(i: Int) {
        if(i > n/2) return
        val li = 2*i
        val ri = 2*i + 1
        val target = if(ri > n || array[li] <= array[ri]) li else ri
        if(array[target] < array[i]) {
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
    override fun toString(): String = array.slice(1..n).joinToString()
}
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
