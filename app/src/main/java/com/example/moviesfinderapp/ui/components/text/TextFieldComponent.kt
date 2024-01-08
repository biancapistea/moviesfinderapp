package com.example.moviesfinderapp.ui.components.text

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movies.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldComponent(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholderTextResource: Int,
    errorText: String = "",
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onDoneActionClick: () -> Unit = {},
) {

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp
            )
            .then(modifier),
        value = value,
        isError = errorText.isNotEmpty(),
        textStyle = TextStyle(
            fontFamily = FontFamily(Font(R.font.graphik_regular)),
            fontSize = 12.sp,
            color = Color.DarkGray
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            errorBorderColor = Color.Red,
            unfocusedBorderColor = Color.Gray,
            focusedBorderColor = Color.Gray
        ),
        placeholder = { Text(text = stringResource(id = placeholderTextResource)) },
        onValueChange = onValueChange,
        singleLine = singleLine,
        supportingText = { Text(text = errorText) },
        keyboardActions = KeyboardActions(onDone = {
            onDoneActionClick()
        }),
        keyboardOptions = keyboardOptions,
        shape = RoundedCornerShape(8.dp),
        readOnly = readOnly
    )
}
