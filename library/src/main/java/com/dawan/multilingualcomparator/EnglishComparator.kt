package com.dawan.multilingualcomparator

class EnglishComparator : LanguageComparator {
    private val EnglishUpperCaseRange = 'A'..'Z'
    private val EnglishLowerCaseRange = 'a'..'z'
    var ignoreCase = true

    override fun isLanguage(char: Char): Boolean {
        return char in EnglishUpperCaseRange || char in EnglishLowerCaseRange
    }

    override fun compare(str1: String, str2: String): Int {
        return str1.compareTo(str2, ignoreCase)
    }

}