package com.study.littlelemon.ui.component


import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LittleLemonBtn(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onSurface,
            containerColor = MaterialTheme.colorScheme.primary
        ),
        onClick = onClick
    ){
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge
        )
    }
}