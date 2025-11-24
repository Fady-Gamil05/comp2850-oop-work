// constant to avoid magic number
private const val MAX_ATTEMPTS = 6

fun main() {
    val game = Wordle()

    // Load the list of words
    val words = game.readWordList("data/words.txt")

    // Pick a random target word
    val target = game.pickRandomWord(words)

    println("Welcome to Wordle!")
    println("Try to guess the 5-letter word.")
    println("You have $MAX_ATTEMPTS attempts.")
    println()

    // Allow up to 6 attempts
    for (attempt in 1..MAX_ATTEMPTS) {
        // Get a valid guess from the user
        val guess = game.obtainGuess(attempt)

        // Compare the guess with the target
        val matches = game.evaluateGuess(guess, target)

        // Display the match pattern
        game.displayGuess(guess, matches)

        println()

        // Check for success
        if (guess.equals(target, ignoreCase = true)) {
            println("\nCongratulations! You guessed the word in $attempt attempts!")
            return
        }
    }

    // Failed after 6 attempts
    println("\nSorry, you've run out of guesses.")
    println("The correct word was: $target")
}
