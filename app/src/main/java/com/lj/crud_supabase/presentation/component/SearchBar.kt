package com.lj.crud_supabase.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search...",
    leadingIcon: ImageVector = Icons.Default.Search,
    showClearButton: Boolean = true,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    shape: Shape = RoundedCornerShape(24.dp),
    colors: TextFieldColors = TextFieldDefaults.colors()
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier,
        placeholder = { Text(text = placeholder) },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = "Search Icon"
            )
        },
        trailingIcon = {
            if (showClearButton && query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear Search"
                    )
                }
            }
        },
        shape = shape,
        singleLine = singleLine,
        enabled = enabled,
        readOnly = readOnly,
        colors = colors
    )
}

/*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier.Companion
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text(text = "Search products...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
        shape = RoundedCornerShape(24.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline
        )
    )
}*/
