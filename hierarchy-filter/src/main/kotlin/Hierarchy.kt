

import kotlin.test.*

// The task:
// 1. Read and understand the Hierarchy data structure described in this file.
// 2. Implement filter() function.
// 3. Implement more test cases.
//
// The task should take 30-90 minutes.
//
// When assessing the submission, we will pay attention to:
// - correctness, efficiency, and clarity of the code;
// - the test cases.

/**
 * A `Hyierarchy` stores an arbitrar _forest_ (an ordered collection of ordered trees)
 * as an array of node IDs in the order of DFS traversal, combined with a parallel array of node depths.
 *
 * Parent-child relationships are identified by the position in the array and the associated depth.
 * Each tree root has depth 0, its children have depth 1 and follow it in the array, their children have depth 2 and follow them, etc.
 *
 * Example:
 * ```
 * nodeIds: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11
 * depths:  0, 1, 2, 3, 1, 0, 1, 0, 1, 1, 2
 * ```
 *
 * the forest can be visualized as follows:
 * ```
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
 *```
 * 1 is a parent of 2 and 5, 2 is a parent of 3, etc. Note that depth is equal to the number of hyphens for each node.
 *
 * Invariants on the depths array:
 *  * Depth of the first element is 0.
 *  * If the depth of a node is `D`, the depth of the next node in the array can be:
 *      * `D + 1` if the next node is a child of this node;
 *      * `D` if the next node is a sibling of this node;
 *      * `d < D` - in this case the next node is not related to this node.
 */
interface Hierarchy {
    /** The number of nodes in the hierarchy. */
    val size: Int

    /**
     * Returns the unique ID of the node identified by the hierarchy index. The depth for this node will be `depth(index)`.
     * @param index must be non-negative and less than [size]
     * */
    fun nodeId(index: Int): Int

    /**
     * Returns the depth of the node identified by the hierarchy index. The unique ID for this node will be `nodeId(index)`.
     * @param index must be non-negative and less than [size]
     * */
    fun depth(index: Int): Int

    fun formatString(): String {
        return (0 until size).joinToString(
            separator = ", ",
            prefix = "[",
            postfix = "]"
        ) { i -> "${nodeId(i)}:${depth(i)}" }
    }
}

/**
 * A node is present in the filtered hierarchy iff its node ID passes the predicate and all of its ancestors pass it as well.
 */
fun Hierarchy.filter(nodeIdPredicate: (Int) -> Boolean): Hierarchy {
    val filtered : Pair<IntArray, IntArray> = filterNode(ArrayList(), ArrayList(), 0, nodeIdPredicate)

    val (resultNodeIds,resultNodeDepths) = filtered

    return ArrayBasedHierarchy(resultNodeIds,resultNodeDepths)
}

/*
    filter the forest in recursive way
*/
fun Hierarchy.filterNode(resultNodeIds:ArrayList<Int>, resultNodeDepths:ArrayList<Int>, currentIndex : Int, nodeIdPredicate: (Int) -> Boolean) : Pair<IntArray, IntArray> {

    if(currentIndex < size){
        val currentDepth = depth(currentIndex)
        val currentNodeId = nodeId(currentIndex)

        if(nodeIdPredicate(currentNodeId)){
            resultNodeIds.add(currentNodeId)
            resultNodeDepths.add(currentDepth)

            return filterNode(resultNodeIds, resultNodeDepths, currentIndex + 1, nodeIdPredicate)
        }

        else{
            return filterNode(resultNodeIds, resultNodeDepths, currentIndex + skipNode(currentIndex,currentDepth), nodeIdPredicate)
        }
    }
    return Pair(resultNodeIds.toIntArray(),resultNodeDepths.toIntArray())
}


/*
    'prune' the node (skipping node and its children)
*/
fun Hierarchy.skipNode(index: Int, ancestorDepth : Int) : Int{
    var skipIndex = 0;
    var i = index+1;
    while(i <= size-1 && depth(i) > ancestorDepth ){
        skipIndex++
        i++
    }
    return skipIndex + 1;
}


class ArrayBasedHierarchy(
    private val myNodeIds: IntArray,
    private val myDepths: IntArray,
) : Hierarchy {
    override val size: Int = myDepths.size

    override fun nodeId(index: Int): Int = myNodeIds[index]

    override fun depth(index: Int): Int = myDepths[index]
}