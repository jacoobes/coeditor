package components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import com.wakaztahir.codeeditor.model.CodeLang
import com.wakaztahir.codeeditor.prettify.PrettifyParser
import com.wakaztahir.codeeditor.theme.CodeTheme
import com.wakaztahir.codeeditor.utils.parseCodeAsAnnotatedString
import components.textarea.TextArea
import jetbrains
import structs.Code
import structs.deriveMonochrome
import java.nio.file.Files
import java.nio.file.Path
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
fun MiddlePanel(path: Path) {
    val theme = MaterialTheme.colorScheme.deriveMonochrome()
    val code =  Code(Files.readString(path), getExtension(path))
    val textFieldValue =
        mutableStateOf(
            TextFieldValue(
                annotatedString = parseCodeAsAnnotatedString(
                    parser = PrettifyParser(),
                    theme = object : CodeTheme(theme) {},
                    lang = code.lang ?: CodeLang.Default,
                    code = code.content
                )
            )
        )
    TextArea(
        textFieldValue,
        style = TextStyle(
            fontFamily = jetbrains(),
            color = MaterialTheme.colorScheme.onSurface
        ),
    )
}