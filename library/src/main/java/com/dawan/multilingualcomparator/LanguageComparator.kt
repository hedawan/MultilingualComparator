package com.dawan.multilingualcomparator

import java.util.*

interface LanguageComparator : Comparator<String> {
    fun isLanguage(char: Char): Boolean
    override fun compare(str1: String, str2: String): Int
}