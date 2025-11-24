// Implement the six required functions here

import java.io.File
import kotlin.random.Random

// constant for word length to avoid magic numbers
private const val WORD_LENGTH = 5

class Wordle {
    // Checks if a word is valid: must be 5 letters and alphabetic only
    fun isValid(word: String): Boolean {
        val guess = word.lowercase()

        // check length
        val correctLength = guess.length == WORD_LENGTH

        // check letters only
        val allLetters = guess.all { it.isLetter() }

        // one combined return to satisfy detekt
        return correctLength && allLetters
    }

    // Reads a list of words from a text file
    fun readWordList(filename: String): MutableList<String> {
        val file = File(filename)
        val words = file.readLines()
        return words.toMutableList()
    }

    // Picks a random word and removes it from the list
    fun pickRandomWord(words: MutableList<String>): String {
        val index = Random.nextInt(words.size)
        val selectedWord = words[index]
        words.removeAt(index)
        return selectedWord
    }

    // Keeps asking until the user enters a valid guess
    fun obtainGuess(attempt: Int): String {
        while (true) {
            // prompt user
            println("Attempt $attempt: ")

            val guess = readln()

            // return if valid
            if (isValid(guess)) {
                return guess
            }

            // otherwise show error
            println("Invalid guess. Please try again.")
        }
    }

    // Compares the guess with the target and returns match results
    fun evaluateGuess(guess: String, target: String): List<Int> {
        // stores results: 0=wrong, 1=wrong spot, 2=correct spot
        val results = MutableList(WORD_LENGTH) { 0 }

        // make uppercase for easier comparison
        val g = guess.uppercase()
        val t = target.uppercase()

        // count letters in target
        val letterCounts = mutableMapOf<Char, Int>()
        for (ch in t) {
            letterCounts[ch] = letterCounts.getOrDefault(ch, 0) + 1
        }

        // mark exact matches (2)
        for (i in 0 until WORD_LENGTH) {
            if (g[i] == t[i]) {
                results[i] = 2
                letterCounts[g[i]] = letterCounts[g[i]]!! - 1
            }
        }

        // mark wrong-place matches (1)
        for (i in 0 until WORD_LENGTH) {
            if (results[i] == 0) {
                val ch = g[i]
                if (letterCounts.getOrDefault(ch, 0) > 0) {
                    results[i] = 1
                    letterCounts[ch] = letterCounts[ch]!! - 1
                }
            }
        }

        return results
    }

    // Displays guess letters with colours (green/yellow/grey)
    fun displayGuess(guess: String, matches: List<Int>) {
        // Add a blank line before display
        println()

        // Loop through each of the 5 letters in the guess
        for (i in 0 until WORD_LENGTH) {
            val ch = guess[i].uppercase()

            // Check the match result for this position
            when (matches[i]) {
                2 -> print("\u001B[32m$ch\u001B[0m") // green
                1 -> print("\u001B[33m$ch\u001B[0m") // yellow
                else -> print("\u001B[90m$ch\u001B[0m") // grey
            }
        }

        println()
    }
}
