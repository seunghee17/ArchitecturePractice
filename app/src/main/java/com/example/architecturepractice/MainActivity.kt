package com.example.architecturepractice

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.architecturepractice.ui.theme.ArchitecturePracticeTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArchitecturePracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()){
    val state by viewModel.uiState.collectAsState()
    Column (
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(text = state.data.toString())
        Button(onClick = { viewModel.setEvent(MainContract.Event.Increase) }) {
            Text("Increase")
        }
        Button(onClick = { viewModel.setEvent(MainContract.Event.Decrease) }) {
            Text("Decrease")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ArchitecturePracticeTheme {
        MainScreen()
    }
}