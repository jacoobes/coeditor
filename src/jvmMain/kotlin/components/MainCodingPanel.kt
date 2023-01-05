package components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import components.textarea.TextArea
import jetbrains
import structs.Code
import java.nio.file.Path
import java.util.*
import kotlin.io.path.name

fun getExtension(path: Path): String {
    var extension = ""

    val i: Int = path.name.lastIndexOf('.')
    if (i > 0) {
        extension = path.name.substring(i + 1)
    }
    return extension
}

@Composable
fun MiddlePanel(path: Optional<Path>) {
    if (path.isPresent) {
        val p = path.get()
        val code = Code(p, getExtension(p))
        VerticalDividerLessAlpha(thickness = 2.dp)
        TextArea(
            code,
            style = TextStyle(
                fontFamily = jetbrains(),
                color = MaterialTheme.colorScheme.onSurface
            ),
        )
    }
}