package com.hotel.ui.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.hotel.ui.theme.subtitleLight

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: @Composable () -> Unit,
    label: @Composable () -> Unit,
    keyboardOptions: KeyboardOptions,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = false,
    isError: Boolean = false,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        label = label,
        isError = isError,
        keyboardOptions = keyboardOptions,
        singleLine = singleLine,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF6F6F9),
            unfocusedContainerColor = Color(0xFFF6F6F9),
            errorContainerColor = Color(0xFFEB5757).copy(alpha = 0.15f),
            focusedTextColor = Color(0xFF14142B),
            unfocusedTextColor = Color(0xFF14142B),
            errorTextColor = Color(0xFF14142B),
            cursorColor = Color(0xFF14142B),
            focusedPlaceholderColor = Color(0xFFA9ABB7),
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedLabelColor = Color(0xFFA9ABB7),
            unfocusedLabelColor = Color(0xFFA9ABB7),
            errorLabelColor = Color(0xFFA9ABB7),
            unfocusedPlaceholderColor = Color(0xFFA9ABB7)
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier,
        textStyle = subtitleLight.copy(color = Color.Black),
        interactionSource = interactionSource,
        visualTransformation = visualTransformation
    )
}

fun String.isValidEmail() =
    isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

class PhoneVisualTransformation(val mask: String, val maskNumber: Char) : VisualTransformation {

    private val maxLength = mask.count { it == maskNumber }

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.length > maxLength) text.take(maxLength) else text

        val annotatedString = buildAnnotatedString {
            if (trimmed.isEmpty()) return@buildAnnotatedString

            var maskIndex = 0
            var textIndex = 0
            while (textIndex < trimmed.length && maskIndex < mask.length) {
                if (mask[maskIndex] != maskNumber) {
                    val nextDigitIndex = mask.indexOf(maskNumber, maskIndex)
                    append(mask.substring(maskIndex, nextDigitIndex))
                    maskIndex = nextDigitIndex
                }
                append(trimmed[textIndex++])
                maskIndex++
            }
        }

        return TransformedText(annotatedString, PhoneOffsetMapper(mask, maskNumber))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PhoneVisualTransformation) return false
        if (mask != other.mask) return false
        if (maskNumber != other.maskNumber) return false
        return true
    }

    override fun hashCode(): Int {
        return mask.hashCode()
    }
}

private class PhoneOffsetMapper(val mask: String, val numberChar: Char) : OffsetMapping {

    override fun originalToTransformed(offset: Int): Int {
        var noneDigitCount = 0
        var i = 0
        while (i < offset + noneDigitCount) {
            if (mask[i++] != numberChar) noneDigitCount++
        }
        return offset + noneDigitCount
    }

    override fun transformedToOriginal(offset: Int): Int =
        offset - mask.take(offset).count { it != numberChar }
}