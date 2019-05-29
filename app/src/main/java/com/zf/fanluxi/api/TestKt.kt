package com.zf.fanluxi.api

import java.util.*


fun main(args: Array<String>) {

    /**
     * 栈和队列的区别
     * 栈：后进先出
     * 队列：先进先出
     */

    val urlTxt = "&lt;p&gt;&lt;img src=&quot;https://mobile.zhifengwangluo.c3w.cc/public/upload/goods/2019/03-16/68eb9fe414499db3a35189ae98b6a28b.png&quot;/&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=&quot;https://mobile.zhifengwangluo.c3w.cc/public/upload/goods/2019/03-16/f3490163655c39ec7de2916aa6a8c528.png&quot;/&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=&quot;https://mobile.zhifengwangluo.c3w.cc/public/upload/goods/2019/03-16/9c532305143b3a9a7d49e4d7265e83be.png&quot;/&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=&quot;https://mobile.zhifengwangluo.c3w.cc/public/upload/goods/2019/03-16/02e782dba0fd4bbdb9b896bcb11c6cf1.png&quot;/&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=&quot;https://mobile.zhifengwangluo.c3w.cc/public/upload/goods/2019/03-16/57171d02d130039140f95b39c4820935.png&quot;/&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=&quot;https://mobile.zhifengwangluo.c3w.cc/public/upload/goods/2019/03-16/ea95c060a48190807adcfd4726c23eb8.png&quot;/&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=&quot;https://mobile.zhifengwangluo.c3w.cc/public/upload/goods/2019/03-16/fbea54101deb9661e70a676701f9471b.png&quot;/&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=&quot;https://mobile.zhifengwangluo.c3w.cc/public/upload/goods/2019/03-16/7074bdefdc72af614830fbf1f18b50e9.png&quot;/&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=&quot;https://mobile.zhifengwangluo.c3w.cc/public/upload/goods/2019/03-16/9a2684ceb3d737d585384a1fefe695d1.png&quot;/&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=&quot;https://mobile.zhifengwangluo.c3w.cc/public/upload/goods/2019/03-16/2af62d4bb4591810c932f8079b9190bd.png&quot;/&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=&quot;https://mobile.zhifengwangluo.c3w.cc/public/upload/goods/2019/03-16/3fae8f4d4a2e7f7de5c0a152e6fdd371.png&quot;/&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=&quot;https://mobile.zhifengwangluo.c3w.cc/public/upload/goods/2019/03-16/06689f3b3b1e8b96718c1edc393b6e11.png&quot;/&gt;&lt;/p&gt;"


    /**
     * 数组
     */


//    initWhen()

}

private fun initWhen() {
    val a = "c"
    when (a) {
        "c" -> {
            println(">>>a")
        }
        "b" -> {
            println(">>>b")
        }
    }
    println(">>>?")
}

/**
 * 队列
 */
private fun dequeTest() {
    val deque = LinkedList<Int>()
    //尾部入队，区别在于如果失败了
    deque.offer(122)
    deque.add(122)
    var head = deque.poll()
    head = deque.remove()
    head = deque.peek()
    head = deque.element()

}

/**
 * 链表
 */
private fun linkListTest() {
    val linkedList = LinkedList<String>()
    linkedList.add("abc")
    linkedList.add("def")
    println(linkedList[0])
    linkedList[0] = "e" //一定要确保linkedList中已经有第0个元素
    val s = linkedList[0]
    println(s)
    val a = linkedList.contains("e")
    println(a)
    val b = linkedList.contains("b")
    println(b)
    linkedList.remove("e")
    println(linkedList.contains("e"))
}