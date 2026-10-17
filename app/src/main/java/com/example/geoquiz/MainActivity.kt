package com.example.geoquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
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
fun QuizScreen(modifier: Modifier = Modifier,
               viewModel: QuizViewModel = viewModel()){
    var answered by remember { mutableStateOf(false) }
    var showResultDialog by remember { mutableStateOf(false) }
    LaunchedEffect(viewModel.currentIndex) {
        answered = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        //horizontalAlignment = Alignment.CenterHorizontally,
       // verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = viewModel.currentQuestion.text,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        if (!answered) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = {
                    viewModel.checkAnswer(true)
                    answered = true
                }) { Text("True") }

                Button(onClick = {
                    viewModel.checkAnswer(false)
                    answered = true
                }) { Text("False") }
            }

        } else {
            Text("Ответ принят", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (!viewModel.isLastQuestion) {
            Button(
                onClick = { viewModel.moveToNext() },
                enabled = answered,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Next") }

        } else if (answered) {
            Button(
                onClick = { showResultDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Show result") }
        }
    }




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