import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.ln
import kotlin.test.*

class FilterTests {

    @Test
    fun `nodeId is not divisible by 3`() {
        /*
              * 1
              * - 2
              * - - 3
              * - - - 4
              * - 5
              * 6
              * - 7
              * 8
              * - 9
              * - 10
              * - - 11
        */
        val unfiltered: Hierarchy = ArrayBasedHierarchy(
            intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11),
            intArrayOf(0, 1, 2, 3, 1, 0, 1, 0, 1, 1, 2))
        val filteredActual: Hierarchy = unfiltered.filter { nodeId -> nodeId % 3 != 0 }
        val filteredExpected: Hierarchy = ArrayBasedHierarchy(
            intArrayOf(1, 2, 5, 8, 10, 11),
            intArrayOf(0, 1, 1, 0, 1, 2))
        assertEquals(filteredExpected.formatString(), filteredActual.formatString())
    }

    @Test
    fun `nodeId is divisible by 2`() {
        /*
             * 1
             * 2
             * - 3
             * - 4
             * - - 5
             * - - - 6
             * 7
             * - 8
             * - 9
             * - 10
             * - - 11
             * - - 12
             * - - - 13
             * 14
             * - 15
             * - 16
             * - - 17
             * - - 18
             * - 19
        */
        val unfiltered : Hierarchy = ArrayBasedHierarchy(
            intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19),
            intArrayOf(0, 0, 1, 1, 2, 3, 0, 1, 1, 1, 2, 2, 3, 0, 1, 1, 2, 2, 1))
        val filteredActual : Hierarchy = unfiltered.filter { nodeId -> nodeId % 2 == 0  }
        val filteredExpected : Hierarchy = ArrayBasedHierarchy(
            intArrayOf(2, 4, 14, 16, 18),
            intArrayOf(0, 1, 0, 1, 2))
        assertEquals(filteredExpected.formatString(), filteredActual.formatString())
    }

    @Test
    fun `nodeId is a factor of 24`() {
        /*
             * 1
             * - 2
             * - - 3
             * - - - 4
             * - - 5
             * - - - 6
             * - 7
             * 8
             * - 9
             * - 10
             * - - 11
             * - - 12
             * - - - 13
             * 14
        */
        val unfiltered : Hierarchy = ArrayBasedHierarchy(
            intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
            intArrayOf(0, 1, 2, 3, 2, 3, 1, 0, 1, 1, 2, 2, 3, 0))
        val filteredActual : Hierarchy = unfiltered.filter { nodeId -> 24 % nodeId == 0  }
        val filteredExpected : Hierarchy = ArrayBasedHierarchy(
            intArrayOf(1, 2, 3, 4, 8),
            intArrayOf(0, 1, 2, 3, 0))
        assertEquals(filteredExpected.formatString(), filteredActual.formatString())
    }

    @Test
    fun `nodeId is divisible by 3 or 4`() {
        /*
             * 1
             * - 2
             * 3
             * - 4
             * - - 5
             * - - - 6
             * - 7
             * 8
             * - 9
             * - 10
             * - - 11
             * - - 12
             * - - - 13
             * - 14
        */
        val unfiltered : Hierarchy = ArrayBasedHierarchy(
            intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
            intArrayOf(0, 1, 0, 1, 2, 3, 1, 0, 1, 1, 2, 2, 3, 1))
        val filteredActual : Hierarchy = unfiltered.filter { nodeId -> nodeId % 3 == 0 || nodeId % 4 == 0}
        val filteredExpected : Hierarchy = ArrayBasedHierarchy(
            intArrayOf(3, 4, 8, 9),
            intArrayOf(0, 1, 0, 1))
        assertEquals(filteredExpected.formatString(), filteredActual.formatString())
    }

    @Test
    fun `nodeId is a power of 2`() {
        /*
             * 1
             * - 2
             * - 3
             * - - 4
             * - - 5
             * - - - 6
             * 7
             * 8
             * - 9
             * - 10
             * - - 11
             * - - 12
             * - - 13
             * 14
             * - 15
             * - 16
             * - - 17
             * - - 18
             * - 19
        */
        val unfiltered : Hierarchy = ArrayBasedHierarchy(
            intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19),
            intArrayOf(0, 1, 1, 2, 2, 3, 0, 0, 1, 1, 2, 2, 2, 0, 1, 1, 2, 2, 1))
        val filteredActual : Hierarchy = unfiltered.filter { nodeId -> powerOf2(nodeId)  }
        val filteredExpected : Hierarchy = ArrayBasedHierarchy(
            intArrayOf(1, 2, 8),
            intArrayOf(0, 1, 0))
        assertEquals(filteredExpected.formatString(), filteredActual.formatString())
    }

    private fun powerOf2 (x : Int) : Boolean = ceil((ln(x.toDouble()) / ln(2.0))) == floor((ln(x.toDouble()) / ln(2.0)))
}