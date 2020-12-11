package clock

import com.intellij.openapi.wm.StatusBar
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.util.Consumer
import java.awt.Component
import java.awt.event.MouseEvent
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.swing.Timer

class ClockWidget : StatusBarWidget {
    private var statusBar: StatusBar? = null
    private val timer = Timer(60_000) { statusBar?.updateWidget(ID()) }

    override fun install(statusBar: StatusBar) {
        this.statusBar = statusBar
        statusBar.updateWidget(ID())
        timer.start()
    }

    override fun dispose() {
        timer.stop()
        statusBar = null
    }

    override fun getPresentation() = object: StatusBarWidget.TextPresentation {
        override fun getTooltipText(): String? = null
        override fun getClickConsumer(): Consumer<MouseEvent>? = null

        override fun getAlignment(): Float = Component.CENTER_ALIGNMENT

        override fun getText(): String {
            val pattern = "MMMdd E HH:mm"
            val locale = Locale.getDefault()
            val formatter = DateTimeFormatter.ofPattern(pattern, locale)
            return LocalDateTime.now().format(formatter)
        }
    }

    override fun ID() = ID

    companion object {
        const val ID = "intellij-clock"
    }
}
