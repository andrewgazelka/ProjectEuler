package com.github.andrewgazelka.eulerproject.test

import com.github.andrewgazelka.eulerproject.problems.Problem88
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Problem 88")
class Problem88Test {

    @Test
    fun `test problem 88`() {
        assertEquals(7_587_457, Problem88().solve())
    }
}
