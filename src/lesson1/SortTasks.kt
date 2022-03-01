@file:Suppress("UNUSED_PARAMETER")

package lesson1

import java.io.File

/**
 * Сортировка времён
 *
 * Простая
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
 * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
 *
 * Пример:
 *
 * 01:15:19 PM
 * 07:26:57 AM
 * 10:00:03 AM
 * 07:56:14 PM
 * 01:15:19 PM
 * 12:40:31 AM
 *
 * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
 * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
 *
 * 12:40:31 AM
 * 07:26:57 AM
 * 10:00:03 AM
 * 01:15:19 PM
 * 01:15:19 PM
 * 07:56:14 PM
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
/**
 * Ресурсоёмкость R = O(N)
 * Трудоёмкость T = O(N * logN)
 */
fun sortTimes(inputName: String, outputName: String) {
    val list = mutableListOf<Int>()
    val map = mutableMapOf<Int, String>()
    val regex = Regex("""((0[0-9]|1[0-2]):[0-5][0-9]:[0-5][0-9])\s(AM|PM)""")
    File(inputName).bufferedReader().useLines { inpLines ->
        inpLines.forEach { line ->
            if (regex.matches(line)) {
                val parts = line.split(" ")
                val times = parts[0].split(":").map { it.toInt() }
                var hour = times[0]
                val minutes = times[1]
                val seconds = times[2]
                if (hour == 12) hour = 0
                if (parts[1] == "PM") hour += 12
                val time = seconds + 60 * minutes + 3600 * hour
                list += time
                map += Pair(time, line)
            } else {
                throw IllegalArgumentException()
            }
        }
        File(outputName).bufferedWriter().use {
            list.sorted().forEach { line ->
                it.write(map[line]!!)
                it.newLine()
            }
        }
    }
}

/**
 * Сортировка адресов
 *
 * Средняя
 *
 * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
 * где они прописаны. Пример:
 *
 * Петров Иван - Железнодорожная 3
 * Сидоров Петр - Садовая 5
 * Иванов Алексей - Железнодорожная 7
 * Сидорова Мария - Садовая 5
 * Иванов Михаил - Железнодорожная 7
 *
 * Людей в городе может быть до миллиона.
 *
 * Вывести записи в выходной файл outputName,
 * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
 * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
 *
 * Железнодорожная 3 - Петров Иван
 * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
 * Садовая 5 - Сидоров Петр, Сидорова Мария
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun sortAddresses(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сортировка температур
 *
 * Средняя
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
 * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
 * Например:
 *
 * 24.7
 * -12.6
 * 121.3
 * -98.4
 * 99.5
 * -12.6
 * 11.0
 *
 * Количество строк в файле может достигать ста миллионов.
 * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
 * Повторяющиеся строки сохранить. Например:
 *
 * -98.4
 * -12.6
 * -12.6
 * 11.0
 * 24.7
 * 99.5
 * 121.3
 */
/**
 * Ресурсоёмкость R = O(N)
 * Трудоёмкость T = O(N * logN)
 */
fun sortTemperatures(inputName: String, outputName: String) {
    val map = mutableMapOf<Double, Int>()
    File(inputName).bufferedReader().useLines { inpLines ->
        inpLines.forEach { line ->
            val temp = line.toDouble()
            if (temp in map.keys) {
                val count = map[temp]!! + 1
                map[temp] = count
            } else map[temp] = 1
        }
    }
    File(outputName).bufferedWriter().use {
        while (map.isNotEmpty()) {
            val min = map.keys.minOrNull()
            for (el in 1..map[min]!!) {
                it.write(min.toString())
                it.newLine()
            }
            map.remove(min)
        }
    }
}

/**
 * Сортировка последовательности
 *
 * Средняя
 * (Задача взята с сайта acmp.ru)
 *
 * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
 *
 * 1
 * 2
 * 3
 * 2
 * 3
 * 1
 * 2
 *
 * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
 * а если таких чисел несколько, то найти минимальное из них,
 * и после этого переместить все такие числа в конец заданной последовательности.
 * Порядок расположения остальных чисел должен остаться без изменения.
 *
 * 1
 * 3
 * 3
 * 1
 * 2
 * 2
 * 2
 */
/**
 * Ресурсоёмкость R = O(N)
 * Трудоёмкость T = O(N)
 */
fun sortSequence(inputName: String, outputName: String) {
    val list = mutableListOf<Int>()
    val map = mutableMapOf<Int, Int>()
    File(inputName).bufferedReader().useLines { inpLines ->
        inpLines.forEach { line ->
            val num = line.toInt()
            list += num
            if (num in map.keys) {
                val count = map[num]!! + 1
                map[num] = count
            } else map[num] = 1
        }
    }
    File(outputName).bufferedWriter().use {
        val max = map.values.maxOrNull()
        var pair = Pair(Int.MAX_VALUE, 0)
        for ((num, numOfRepeats) in map)
            if ((num <= pair.first) && (numOfRepeats == max))
                pair = Pair(num, max)
        for (line in list)
            if (line != pair.first) {
                it.write(line.toString())
                it.newLine()
            }
        for (n in 0 until pair.second) {
            it.write(pair.first.toString())
            it.newLine()
        }
    }
}

/**
 * Соединить два отсортированных массива в один
 *
 * Простая
 *
 * Задан отсортированный массив first и второй массив second,
 * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
 * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
 *
 * first = [4 9 15 20 28]
 * second = [null null null null null 1 3 9 13 18 23]
 *
 * Результат: second = [1 3 4 9 9 13 15 20 23 28]
 */
fun <T : Comparable<T>> mergeArrays(first: Array<T>, second: Array<T?>) {
    TODO()
}

