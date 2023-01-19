package components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import buttonSizes
import components.lazytree.NoFiles
import components.textarea.TextActions
import org.jetbrains.compose.splitpane.*
import pad
import structs.Settings
import java.awt.Cursor
import java.nio.file.Path
import java.util.*

@OptIn(ExperimentalSplitPaneApi::class)
@Composable
fun FrameWindowScope.MainCodeLayout(
    settings: Settings,
    snackbarHostState: SnackbarHostState,
    directoryChosen: Optional<String>,
    requestSave: MutableState<Boolean>
) {
    val hSplitPanelState = rememberSplitPaneState(.8f, true)
    val selectedPath = remember { mutableStateOf<Optional<Path>>(Optional.empty()) }
    val padding = PaddingValues(start = pad + buttonSizes + 10.dp)
    HorizontalSplitPane(
        Modifier.padding(padding),
        splitPaneState = hSplitPanelState,
    ) {
        first {
            if (directoryChosen.isPresent) {
                Column(Modifier.fillMaxWidth()) {
                    TextActions(snackbarHostState, requestSave, selectedPath.value)
                    MiddlePanel(
                        selectedPath.value,
                        requestSave
                    )
                }
            }
        }
        panelSplitter()

        second(
            minSize = 50.dp
        ) {
            if (directoryChosen.isPresent) {
                SidePanel(rootPath = Path.of(directoryChosen.get()), selectedPath = selectedPath)
            } else {
                NoFiles()
            }
        }
    }
}


@OptIn(ExperimentalSplitPaneApi::class)
fun SplitPaneScope.panelSplitter() {
    splitter {
        visiblePart {
            VerticalDividerLessAlpha(
                modifier = Modifier.clip(CircleShape),
                thickness = 1.dp
            )
        }
        handle {
            VerticalDividerLessAlpha(
                Modifier
                    .markAsHandle()
                    .cursorForHorizontalResize(),
                alpha = 0.0f,
                thickness = 10.dp
            )
        }
    }
}

private fun Modifier.cursorForHorizontalResize(): Modifier =
    pointerHoverIcon(PointerIcon(Cursor(Cursor.E_RESIZE_CURSOR)))