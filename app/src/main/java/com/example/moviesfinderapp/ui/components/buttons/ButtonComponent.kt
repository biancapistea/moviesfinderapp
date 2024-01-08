package com.example.moviesfinderapp.ui.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movies.R

@Composable
fun ButtonComponent(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        disabledContainerColor = colorResource(id = R.color.green_chips),
        disabledContentColor = Color.White,
        containerColor = colorResource(id = R.color.green_chips),
        contentColor = Color.White
    ),
    paddingValues: PaddingValues = PaddingValues(
        start = 16.dp,
        end = 16.dp,
        bottom = 20.dp,
        top = 20.dp
    ),
    border: BorderStroke? = null
) {
    Button(
        modifier = Modifier
            .wrapContentHeight()
            .padding(paddingValues)
            .height(IntrinsicSize.Min)
            .then(modifier),
        onClick = onClick,
        border = border,
        enabled = enabled,
        colors = colors,
        shape = RoundedCornerShape(10)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp, bottom = 12.dp),
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.graphik_regular))
        )
    }
}