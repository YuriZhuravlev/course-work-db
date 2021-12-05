package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import base.DBColors
import screen.NavState

@Composable
fun MenuView(modifier: Modifier, onClick: (NavState) -> Unit) {
    Column(modifier) {
        Box(Modifier.fillMaxWidth().height(2.dp).background(DBColors().primary))
        Box(Modifier.fillMaxWidth().height(8.dp))
        TextMenu(Modifier.clickable { onClick(NavState.Category) }, "Категории")
        TextMenu(Modifier.clickable { onClick(NavState.Cathedra) }, "Кафедры")
        TextMenu(Modifier.clickable { onClick(NavState.Council) }, "Научные советы")
        TextMenu(Modifier.clickable { onClick(NavState.Protection) }, "Отчеты по защитам")
    }
}

@Composable
private fun TextMenu(modifier: Modifier, text: String) {
    Text(
        modifier = modifier.fillMaxWidth().padding(2.dp),
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight(600)
    )
}