package com.study.littlelemon.ui.component

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@Composable
fun TextInputField(
    modifier: Modifier = Modifier,
    value: String = "",
    label: String? = null,
    placeHolder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    shape: Shape = MaterialTheme.shapes.small,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall,
    keyboardType: KeyboardType = KeyboardType.Text,
    scrollState: ScrollState? = null,
    imeState: State<Boolean>? = null,
    onValueChange: (String) -> Unit,
) {
    var scrollToPosition: Float by remember { mutableFloatStateOf(0F) }
    var focusedState: Boolean by remember { mutableStateOf(false) }

    if (imeState != null && scrollState != null) {
        LaunchedEffect(key1 = imeState.value, key2 = focusedState) {
            if (imeState.value && focusedState) {
                scrollState.animateScrollTo(scrollToPosition.roundToInt())
            }
        }
    }

    Column(modifier = modifier) {
        label?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
            )
        }
        OutlinedTextField(
            modifier = Modifier
                .background(color = Color.Transparent)
                .fillMaxWidth()
                .onGloballyPositioned {
                    if (imeState != null && scrollState != null) {
                        scrollToPosition = it.positionInRoot().y
                    }
                }
                .onFocusChanged {
                    focusedState = it.isFocused
                },
            value = value,
            onValueChange = { newText ->
                onValueChange(newText)
            },
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.onSurface,
                focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledBorderColor = MaterialTheme.colorScheme.onSurface,
                focusedContainerColor = backgroundColor,
                unfocusedContainerColor = backgroundColor,
                disabledContainerColor = backgroundColor,
            ),
            shape = shape,
            textStyle = textStyle,
            placeholder = {
                Text(
                    text = placeHolder.orEmpty(),
                    style = textStyle,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            leadingIcon = leadingIcon,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
        )
    }
}