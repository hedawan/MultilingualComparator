package com.dawan.multilingualcomparator

import com.github.promeg.pinyinhelper.Pinyin

class ChinesePinyinComparator : LanguageComparator {
    private val ChinesePinyinRange = '\u4e00'..'\u9fa5'

    override fun isLanguage(char: Char): Boolean {
        return char in ChinesePinyinRange
    }

    override fun compare(str1: String, str2: String): Int {
        var startPosition1 = 0
        var startPosition2 = 0

        var char1: Char
        var char2: Char
        var pinyin1: String
        var pinyin2: String
        var result: Int
        while (true) {
            char1 = str1[startPosition1]
            char2 = str2[startPosition2]
            pinyin1 = getPinyin(char1)
            pinyin2 = getPinyin(char2)
            // 转换拼音失败时将失败的排到最后
            when {
                pinyin1.indexOf(char1) == -1 && pinyin2.indexOf(char2) != -1 -> {
                    result = -1
                    break
                }
                pinyin1.indexOf(char1) != -1 && pinyin2.indexOf(char2) == -1 -> {
                    result = 1
                    break
                }
                pinyin1.indexOf(char1) != -1 && pinyin2.indexOf(char2) != -1 -> {
                    result = char1.compareTo(char2)
                    break
                }
            }
            // 转换拼音成功时处理逻辑
            result = pinyin1.compareTo(pinyin2)
            startPosition1++
            startPosition2++
            if (result != 0) {
                break
            } else if (str1.length == startPosition1 || str2.length == startPosition2) {
                // 字符串前面部分完全相同，但一个字符串比较长，根据长度比较
                result = str1.length.compareTo(str2.length)
                break
            }
        }
        return result
    }

    private fun getPinyin(char: Char): String {
        return Pinyin.toPinyin(char)
    }

}