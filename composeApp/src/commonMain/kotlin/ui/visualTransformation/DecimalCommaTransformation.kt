package ui.visualTransformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class DecimalCommaTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val transformedText =text.text.replace(".", ",")
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return offset
            }

            override fun transformedToOriginal(offset: Int): Int {
                return offset
            }}
        return TransformedText(AnnotatedString(transformedText), offsetMapping)
    }
}