package com.example.geoquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.geoquiz.ui.theme.GeoquizTheme
data class Question(
    val text: String,
    val answer: Boolean
)

class QuizViewModel : ViewModel() {

    private val questionBank = listOf(
        Question("Canberra is the capital of Australia.", true),
        Question("The Pacific Ocean is larger than the Atlantic Ocean.", true),
        Question("The Suez Canal connects the Red Sea and the Indian Ocean.", false),
        Question("The source of the Nile River is in Egypt.", false),
        Question("The Amazon River is the longest river in the Americas.", true),
        Question("Lake Baikal is the world's oldest and deepest freshwater lake.", true)
    )

    var currentIndex by mutableStateOf(0)
        private set

    var correctAnswers by mutableStateOf(0)
        private set
    val currentQuestion: Question get() = questionBank[currentIndex]
    val questionCount: Int get() = questionBank.size
    val isLastQuestion: Boolean get() = currentIndex == questionBank.lastIndex

    fun moveToNext() {
        if (!isLastQuestion) currentIndex++
    }

    fun checkAnswer(userAnswer: Boolean): Boolean {
        val isCorrect = userAnswer == currentQuestion.answer
        if (isCorrect) correctAnswers++
        return isCorrect
    }

    fun reset() {
        currentIndex = 0
        correctAnswers = 0
    }

}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeoquizTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuizScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun QuizScreen(modifier: Modifier = Modifier){
    var answered by remember { mutableStateOf(false) }
    var showResultDialog by remember { mutableStateOf(false) }



}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GeoquizTheme {
        QuizScreen()
    }
}