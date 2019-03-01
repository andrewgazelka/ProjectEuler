package com.github.andrewgazelka.eulerproject.problems

import com.github.andrewgazelka.eulerproject.util.Problem
import com.github.andrewgazelka.eulerproject.util.cmap
import kotlinx.coroutines.runBlocking
import java.util.*

data class Node(val currentMoney: Int, val index: Int)

class Problem33 : Problem<Long> {

    override fun solve(): Long {

        val from = listOf(1, 2, 5, 10, 20, 50, 100, 200)
        val totalMoney = 200

        fun Node.children(): List<Node> {
            val nextIndex = index + 1
            if (nextIndex >= from.size) return emptyList()

            val amount = from[nextIndex]
            return generateSequence(0) { it + amount }
                .map { this.currentMoney - it }
                .takeWhile { it >= 0 }
                .map { Node(it, nextIndex) }
                .toList()
        }

        val root = Node(totalMoney, -1)
        return runBlocking {
            root.children()
                .cmap {
                    var counter = 0L
                    val toCheck = Stack<Node>()
                    toCheck.add(it)

                    while (!toCheck.isEmpty()) {
                        val currentNode = toCheck.pop()
                        val children = currentNode.children()
                        if (children.isEmpty() && currentNode.currentMoney == 0) {
                            counter++
                        } else {
                            toCheck.addAll(children)
                        }
                    }
                    counter
                }
                .sum()
        }
    }

}
