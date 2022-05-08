@file:Suppress("UNUSED_PARAMETER")

package lesson7

/**
 * Наибольшая общая подпоследовательность.
 * Средняя
 *
 * Дано две строки, например "nematode knowledge" и "empty bottle".
 * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
 * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
 * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
 * Если общей подпоследовательности нет, вернуть пустую строку.
 * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
 * При сравнении подстрок, регистр символов *имеет* значение.
 *
 * Трудоемкость T = O(m*n)
 * Ресурсоемкость R = O(m*n)
 */
fun longestCommonSubSequence(first: String, second: String): String {
    val m = first.length
    val n = second.length
    val array = Array(m + 1) { IntArray(n + 1) }
    for (i in m downTo 0)
        for (j in n downTo 0) {
            if (i == m || j == n) {
                array[i][j] = 0
            } else if (first[i] == second[j]) {
                array[i][j] = array[i + 1][j + 1] + 1
            } else {
                array[i][j] = array[i][j + 1].coerceAtLeast(array[i + 1][j])
            }
        }
    val answer = StringBuilder()
    var i = 0
    var j = 0
    while (i < m && j < n) {
        if (first[i] == second[j]) {
            answer.append(first[i])
            i++
            j++
        } else if (array[i + 1][j] >= array[i][j + 1]) {
            i++
        } else {
            j++
        }
    }
    return answer.toString()
}

/**
 * Наибольшая возрастающая подпоследовательность
 * Сложная
 *
 * Дан список целых чисел, например, [2 8 5 9 12 6].
 * Найти в нём самую длинную возрастающую подпоследовательность.
 * Элементы подпоследовательности не обязаны идти подряд,
 * но должны быть расположены в исходном списке в том же порядке.
 * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
 * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
 * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
 *
 * Трудоёмкость T = O(N*logN)
 * Ресурсоёмкость R = O(N)
 */
fun longestIncreasingSubSequence(list: List<Int>): List<Int> {
    val prev = IntArray(list.size)
    val pos = IntArray(list.size + 1)
    var length = 0
    for (i in list.indices.reversed()) {
        var l = 1
        var r = length
        while (l <= r) {
            val m = (l + r) / 2
            if (list[pos[m]] < list[i]) {
                r = m - 1
            } else {
                l = m + 1
            }
        }
        prev[i] = pos[l - 1]
        pos[l] = i
        if (l > length) {
            length = l
        }
    }
    val answer = mutableListOf<Int>()
    var p = pos[length]
    for (i in length - 1 downTo 0) {
        answer.add(list[p])
        p = prev[p]
    }
    return answer
}

/**
 * Самый короткий маршрут на прямоугольном поле.
 * Средняя
 *
 * В файле с именем inputName задано прямоугольное поле:
 *
 * 0 2 3 2 4 1
 * 1 5 3 4 6 2
 * 2 6 2 5 1 3
 * 1 4 3 2 6 2
 * 4 2 3 1 5 0
 *
 * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
 * В каждой клетке записано некоторое натуральное число или нуль.
 * Необходимо попасть из верхней левой клетки в правую нижнюю.
 * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
 * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
 *
 * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
 */
fun shortestPathOnField(inputName: String): Int {
    TODO()
}
