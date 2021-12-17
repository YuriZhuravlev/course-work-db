package ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

@Composable
fun BigText(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = 18.sp,
        fontWeight = FontWeight(500)
    )
}

@Composable
fun NormalText(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight(400)
    )
}

@Composable
fun UnderlineText(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight(400),
        textDecoration = TextDecoration.Underline
    )
}

@Composable
fun BoldText(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight(700)
    )
}