package com.dawan.multilingualcomparator

class UnicodeComparator : LanguageComparator {

    override fun isLanguage(char: Char): Boolean {
        return true
    }

    override fun compare(str1: String, str2: String): Int {
        return str1.compareTo(str2)
    }

}