package com.dawan.multilingualcomparator

class ArabicNumeralsComparator : LanguageComparator {
    private val ArabicNumeralsRange = '0'..'9'

    override fun isLanguage(char: Char): Boolean {
        return char in ArabicNumeralsRange
    }

    override fun compare(str1: String, str2: String): Int {
        // 去除前置0
        val numberString1 = getRemoveFrontZeroString(str1)
        val numberString2 = getRemoveFrontZeroString(str2)
        // 先判断长度，长度长的大
        var result = numberString1.length.compareTo(numberString2.length)
        // 长度相等，判断内容
        if (result == 0) result = numberString1.compareTo(numberString2)
        // 内容相等，判断带前置0的字符串长度
        if (result == 0) result = str1.length.compareTo(str2.length)

        return result
    }

    /**
     * 移除字符串的前置0，例如传入numberString = "00001234400"，返回“1234400”
     * @param numberString 该字符串必须全部为阿拉伯数字
     * @return 移除参数numberString字符串的前置0后生成的字符串
     */
    private fun getRemoveFrontZeroString(numberString: String): String {
        var startPosition = 0
        var subString = ""
        var char: Char
        while (startPosition < numberString.length) {
            char = numberString[startPosition]
            if ('0' == char) {
                startPosition++
            } else {
                subString = numberString.substring(startPosition, numberString.length)
                break
            }
        }
        return subString
    }
}