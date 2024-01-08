package com.example.countryapp.ui.components.customgriditems

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun <T> LazyListScope.gridItems(
    data: List<T>,
    columnCount: Int,
    modifier: Modifier,
    horizontalArrangement: Arrangement.Horizontal,
    itemContent: @Composable BoxScope.(T) -> Unit,
    hasMultipleLines: Boolean = false,
    onItemClick: (Int) -> Unit,
) {
    val size = data.count()
    val rows = if (size == 0) 0 else 1 + (size - 1) / columnCount
    items(rows, key = { it.hashCode() }) { rowIndex ->
        Row(
            horizontalArrangement = horizontalArrangement,
            modifier = modifier
        ) {
            val boxModifier = if (hasMultipleLines) {
                Modifier
                    .weight(1F, fill = true)
            } else {
                Modifier.wrapContentSize()
            }
            for (columnIndex in 0 until columnCount) {
                val itemIndex = rowIndex * columnCount + columnIndex
                if (itemIndex < size) {
                    Box(
                        modifier = Modifier
                            .clickable(indication = null,
                                interactionSource = remember { MutableInteractionSource() }) {
                                onItemClick(itemIndex)
                            }.then(boxModifier),
                        propagateMinConstraints = true
                    ) {
                        itemContent(data[itemIndex])
                    }
                } else {
                    EmptyGridItem()
                }
            }
        }
    }
}

@Composable
fun EmptyGridItem() {
    Box(
        modifier = Modifier.wrapContentSize()
    ) {
        Column {
            Spacer(
                modifier = Modifier
                    .width(142.dp)
                    .height(142.dp)
            )
        }
    }
}
