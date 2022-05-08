package lesson4

import java.util.*

/**
 * Префиксное дерево для строк
 */
class KtTrie : AbstractMutableSet<String>(), MutableSet<String> {

    private class Node {
        val children: SortedMap<Char, Node> = sortedMapOf()
    }

    private val root = Node()

    override var size: Int = 0
        private set

    override fun clear() {
        root.children.clear()
        size = 0
    }

    private fun String.withZero() = this + 0.toChar()

    private fun findNode(element: String): Node? {
        var current = root
        for (char in element) {
            current = current.children[char] ?: return null
        }
        return current
    }

    override fun contains(element: String): Boolean =
        findNode(element.withZero()) != null

    override fun add(element: String): Boolean {
        var current = root
        var modified = false
        for (char in element.withZero()) {
            val child = current.children[char]
            if (child != null) {
                current = child
            } else {
                modified = true
                val newChild = Node()
                current.children[char] = newChild
                current = newChild
            }
        }
        if (modified) {
            size++
        }
        return modified
    }

    override fun remove(element: String): Boolean {
        val current = findNode(element) ?: return false
        if (current.children.remove(0.toChar()) != null) {
            size--
            return true
        }
        return false
    }

    /**
     * Итератор для префиксного дерева
     *
     * Спецификация: [java.util.Iterator] (Ctrl+Click по Iterator)
     *
     * Сложная
     */
    override fun iterator(): MutableIterator<String> = TrieIterator()

    inner class TrieIterator internal constructor() : MutableIterator<String> {
        private val stack = ArrayDeque<MutableIterator<Map.Entry<Char, Node>>>()
        private var nextStr = ""
        private val line = StringBuilder()
        private var count = 0

        init {
            stack.push(root.children.entries.iterator())
        }

        /**
         * Трудоёмкость: T = O(1)
         * Ресурсоёмкость: R = O(1)
         */
        override fun hasNext(): Boolean = size > count

        /**
         * Трудоемксость: T = O(N)
         * Ресурсоемкость: R = O(H)
         */
        override fun next(): String {
            if (!hasNext()) throw NoSuchElementException()
            proceed()
            return nextStr
        }

        /**
         * Трудоемксость: T = O(N), трудоемкость внешнего while - O(N), а внутреннего - O(1)
         * Ресурсоемкость: R = O(H), где H - длина самого длинного слова (размер стека)
         */
        private fun proceed() {
            var iterator = stack.peek()
            while (iterator != null) {
                while (iterator.hasNext()) {
                    val next = iterator.next() // следующий потомок
                    val key = next.key
                    val value = next.value
                    if (key == 0.toChar()) { // текущий путь - является словом
                        nextStr = line.toString()
                        count++
                        return
                    }
                    iterator = value.children.entries.iterator() // получаем потомков текущего элемента
                    stack.push(iterator)
                    line.append(key)
                }
                // перебрали всех потомков текущего узла - убираем со стека
                stack.pop()
                if (line.isNotEmpty()) {
                    line.deleteCharAt(line.length - 1)
                }
                iterator = stack.peek()
            }
        }

        /**
         * Трудоёмкость: T = O(1)
         * Ресурсоёмкость: R = O(1)
         */
        override fun remove() {
            if (nextStr == "") throw IllegalStateException()
            if (stack.peek() != null) {
                stack.peek().remove()
                count--
                size--
                nextStr = ""
            }
        }
    }
}