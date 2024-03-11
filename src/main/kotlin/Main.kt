import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.compose.runtime.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@Composable
@Preview
fun App() {

    val mutex = Mutex()
    var text by remember { mutableStateOf("Hello, World!") }
    val scope = rememberCoroutineScope { Dispatchers.IO }
    val _documentState = MutableStateFlow(
        DocumentState(
            arrayOf(
                Document("Document")
            ), false
        )
    )

    var textList by remember { mutableStateOf("") }

    var count by remember { mutableStateOf(0) }

    MaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop!"
            scope.launch {
                _documentState.update {
                    delay(5000)
                    mutex.withLock {
                        val list = it.documents.map {
                            it
                        }.toTypedArray()
                        list[count % list.size] = list[count % list.size].copy(name = "Document ${count + 1}")
                        count++
                        it.copy(list, it.showVehicles).also {
                            println(it.toString())
                            textList = "$textList \n $it"
                        }
                    }
                }
            }
        }) {
            Text(text)
        }

        Text(text = textList)
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
