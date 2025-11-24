import io.kotest.assertions.withClue
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

@Suppress("unused")
class WordleTest : StringSpec({

    "valid words should return true" {
        val w = Wordle()
        w.isValid("break") shouldBe true
    }

    "words not 5 letters return false" {
        val w = Wordle()
        w.isValid("bre") shouldBe false
        w.isValid("breaks") shouldBe false
    }

    "words with non letters return false" {
        val w = Wordle()
        w.isValid("br3ak") shouldBe false
        w.isValid("he!!o") shouldBe false
    }

    "readWordList should load words from file" {
        val w = Wordle()
        val words = w.readWordList("data/words.txt")

        words.isNotEmpty() shouldBe true
        words.contains("AGENT") shouldBe true

    }

    "pickRandomWord should return and remove a word" {
        val w = Wordle()
        val words = mutableListOf("break", "doors", "light")

        val picked = w.pickRandomWord(words)

        listOf("break", "doors", "light").contains(picked) shouldBe true
        words.size shouldBe 2
        words.contains(picked) shouldBe false
    }

    "evaluateGuess should return correct match pattern" {
        val w = Wordle()
        val result = w.evaluateGuess("broke", "break")

        result shouldBe listOf(2, 2, 0, 1, 1)
    }

})
