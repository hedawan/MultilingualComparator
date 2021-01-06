package com.dawan.multilingualcomparator

import java.util.*

class MultilingualismComparator : Comparator<String> {
    private val comparatorList = LinkedList<LanguageComparator>()

    override fun compare(str1: String?, str2: String?): Int {
        var result = 0
        // 判空处理
        when {
            str1 == null && str2 != null -> result = -1
            str1 != null && str2 == null -> result = 1
            str1 == null && str2 == null -> result = 0
        }
        if (str1 == null || str2 == null) return result

        var startPosition1 = 0
        var startPosition2 = 0

        var endPosition1: Int
        var endPosition2: Int

        var substring1: String
        var substring2: String

        var sameLanguageComparator: LanguageComparator
        while (true) {
            endPosition1 = getSameLanguageEndPosition(str1, startPosition1)
            endPosition2 = getSameLanguageEndPosition(str2, startPosition2)

            substring1 = str1.substring(startPosition1, endPosition1)
            substring2 = str2.substring(startPosition2, endPosition2)

            sameLanguageComparator =
                getSameLanguageComparator(substring1.first(), substring2.first())
            result = sameLanguageComparator.compare(substring1, substring2)

            if (result != 0) {
                break
            } else if (endPosition1 == str1.length || endPosition2 == str2.length) {
                result = str1.length.compareTo(str2.length)
                break
            } else {
                startPosition1 = endPosition1
                startPosition2 = endPosition2
            }
        }

        return result
    }

    private fun getSameLanguageEndPosition(string: String, startPosition: Int): Int {
        var endPosition = startPosition
        var char: Char
        var languagesComparator1: LanguageComparator? = null
        var languagesComparator2: LanguageComparator
        val handleRange = startPosition until string.length
        for (i in handleRange) {
            char = string[i]
            if (languagesComparator1 == null) {
                languagesComparator1 = getLanguageComparator(char)
            } else {
                languagesComparator2 = getLanguageComparator(char)
                if (languagesComparator1 != languagesComparator2) {
                    break
                }
            }
            endPosition = i
        }
        return ++endPosition
    }

    private fun getLanguageComparator(char: Char): LanguageComparator {
        var targetComparator: LanguageComparator? = null
        var comparator: LanguageComparator
        val listIterator = comparatorList.listIterator(comparatorList.size)
        while (listIterator.hasPrevious()) {
            comparator = listIterator.previous()
            if (comparator.isLanguage(char)) {
                targetComparator = comparator
                break
            }
        }
        if (targetComparator == null) {
            throw RuntimeException("无法处理当前语言，nowLanguage=${char}，请添加相应的MultilingualComparator。")
        }
        return targetComparator
    }

    private fun getSameLanguageComparator(char1: Char, char2: Char): LanguageComparator {
        var targetComparator: LanguageComparator? = null
        var comparator: LanguageComparator
        val listIterator = comparatorList.listIterator(comparatorList.size)
        while (listIterator.hasPrevious()) {
            comparator = listIterator.previous()
            if (comparator.isLanguage(char1) && comparator.isLanguage(char2)) {
                targetComparator = comparator
                break
            }
        }
        if (targetComparator == null) {
            throw RuntimeException("无法处理当前两种语言，nowLanguage1=${char1},nowLanguage2=${char2}，请添加相应的MultilingualComparator。")
        }
        return targetComparator
    }

    fun addLanguageComparator(comparator: LanguageComparator): MultilingualismComparator {
        comparatorList.offerLast(comparator)
        return this
    }

    fun removeLanguageComparator(comparator: LanguageComparator): Boolean {
        return comparatorList.removeLastOccurrence(comparator)
    }
}