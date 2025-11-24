// Implement the six required functions here

import java.io.File
import kotlin.random.Random

class Wordle{
        
    fun isValid(word:String): Boolean {
        val guess=word.lowercase()
        
        // must be 5 letters
        if (guess.length!=5) return false

        // check letters only
        for (c in guess){
            if(!c.isLetter()) return false
        }

        return true
    }

    fun readWordList(filename: String): MutableList<String> {
        // Create a File object
        val file = File(filename)

        // Read all lines in the file
        val words = file.readLines()

        // Convert the List into a MutableList
        return words.toMutableList()
    }

    fun pickRandomWord(words: MutableList<String>): String {
        // random index
        val index = Random.nextInt(words.size)

        // Get the word
        val selectedWord = words[index]

        // Remove it from the list
        words.removeAt(index)

        return selectedWord
    }

    fun obtainGuess(attempt: Int): String {
    while (true) {

        // Print prompt
        println("Attempt $attempt: ")

        // Read user input
        val guess = readln()

        // Check if it's valid
        if (isValid(guess)) {
            return guess
        }

        // If invalid, print mesage and ask again
        println("Invalid guess.Please try again.")
    }
}


    fun evaluateGuess(guess: String, target: String): List<Int> {
        
        // Create a list to store the results
        val results = MutableList(5) { 0 }

        // Convert both guess and target to uppercase
            val g = guess.uppercase()
            val t = target.uppercase()

        // Count how many times each letter appears in the target word
        val letterCounts = mutableMapOf<Char, Int>()
        for (ch in t) {
            letterCounts[ch] = letterCounts.getOrDefault(ch, 0) + 1
        }

        // correct position matches (2)
            for (i in 0 until 5) {
                if (g[i] == t[i]) {
                    results[i] = 2
                    letterCounts[g[i]] = letterCounts[g[i]]!! - 1
                }
            }

         // wrong position matches (1)
        for (i in 0 until 5) {
            if (results[i] == 0) {   // only check letters NOT already matched
                val ch = g[i]
                if (letterCounts.getOrDefault(ch, 0) > 0) {
                    results[i] = 1
                    letterCounts[ch] = letterCounts[ch]!! - 1
                    }
                }
            }

        return results
    }

    
    
    fun displayGuess(guess: String, matches: List<Int>) {

        println()

        // Loop through each of the 5 letters in the guess
        for (i in 0 until 5) {
            val ch = guess[i].uppercase()

        // Check the match result for this position
            when (matches[i]) {

                // 2 → correct position → green
                2 -> print("\u001B[32m$ch\u001B[0m")

                // 1 → wrong position but letter exists → yellow
                1 -> print("\u001B[33m$ch\u001B[0m")

                // 0 → letter not in target → grey
                else -> print("\u001B[90m$ch\u001B[0m")
            }
        }

    }



}
