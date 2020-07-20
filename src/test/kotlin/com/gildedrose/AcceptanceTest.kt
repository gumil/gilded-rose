package com.gildedrose

import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream

class AcceptanceTest {

    @Test
    fun `test text fixture for 30 days`() {
        val lines = File("src/test/resources/sample.txt").readLines()

        val originalOut = System.out

        val baos = ByteArrayOutputStream()
        System.setOut(PrintStream(baos))

        main(emptyArray())

        System.out.flush()
        System.setOut(originalOut)

        val mainOut = baos.toString()
        val outputLines = mainOut.split(System.getProperty("line.separator"))
        for (i in 0 until minOf(lines.size, outputLines.size)) {
            assertEquals(lines[i], outputLines[i])
        }
    }
}
