package com.github.andrewgazelka.problems

import com.github.andrewgazelka.eulerproject.problems.Problem33
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class Problem31Test {

    @Test
    fun `right answer`(){
        println(measureTimeMillis {
            println(Problem33().solve())
        })
    }
}
