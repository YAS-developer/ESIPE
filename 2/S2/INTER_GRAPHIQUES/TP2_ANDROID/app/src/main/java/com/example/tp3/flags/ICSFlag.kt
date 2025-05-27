package com.example.tp3.flags

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

sealed class ICSFlag(val codeWord: String) {
    /** Letter of the flag (first letter of the code word in uppercase) */
    val letter = codeWord[0].uppercaseChar()

    /** Meaning of the flag if raised on a ship */
    abstract val message: String

    /** Implementation of a component to display the flag.
     */
    @Composable
    abstract fun Flag(modifier: Modifier)

    companion object {
        /**
         * This property will lazily get all the defined flags.
         * It can work only if you have added the Kotlin reflection library in your dependencies section
         * of the build.gradle file of the app:
         *
         * implementation "org.jetbrains.kotlin:kotlin-reflect:$kotlin_version"
         */
        val allFlags by lazy { ICSFlag::class.sealedSubclasses.mapNotNull { it.objectInstance }.associateBy{ it.letter } }

        /** Find a flag for a given letter */
        fun findFlag(letter: Char) = allFlags[letter]
    }
}