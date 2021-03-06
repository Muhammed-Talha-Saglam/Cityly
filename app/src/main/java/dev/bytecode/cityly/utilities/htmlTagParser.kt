package dev.bytecode.cityly.utilities

import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat

@Composable
fun HtmlText(html: String, modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context -> TextView(context) },
        update = { it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT) }
    )
}

fun removeTag(text: String?): String {
    if (text.isNullOrBlank()) return ""

    return text.replace("  ", "").replace("\n", "")
        .replace("<br>", "").replace("<p>", "").replace("<i>", "").replace("</p>", "").replace("</i>", "").replace("<b>", "").replace("</b>", "")
}