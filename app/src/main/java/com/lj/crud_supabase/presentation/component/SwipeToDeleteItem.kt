package com.lj.crud_supabase.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.lj.crud_supabase.domain.models.Product
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeToDeleteItem(
    item: T,
    onDeleteConfirmed: (T) -> Unit,
    modifier: Modifier = Modifier,
    dialogTitle: String = "Hapus Item?",
    dialogMessage: (T) -> String = { "Apakah kamu yakin ingin menghapus item ini?" },
    confirmText: String = "Hapus",
    dismissText: String = "Batal",
    enableDialog: Boolean = true,
    backgroundColor: Color = MaterialTheme.colorScheme.error,
    icon: ImageVector = Icons.Default.Delete,
    content: @Composable () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                if (enableDialog) {
                    showDialog = true
                    false
                } else {
                    onDeleteConfirmed(item)
                    true
                }
            } else false
        }
    )

    SwipeToDismissBox(
        modifier = modifier,
        state = dismissState,
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = true,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.onError
                )
            }
        },
        content = { content() }
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                scope.launch { dismissState.reset() }
            },
            title = { Text(dialogTitle) },
            text = { Text(dialogMessage(item)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        onDeleteConfirmed(item)
                    }
                ) {
                    Text(
                        confirmText,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        scope.launch { dismissState.reset() }
                    }
                ) {
                    Text(dismissText)
                }
            }
        )
    }
}

/*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDeleteItem(
    item: Product,
    onDeleteConfirmed: (Product) -> Unit,
    content: @Composable () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                showDialog = true
                false
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = true,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.error)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.onError
                )
            }
        },
        content = { content() }
    )

    if (showDialog) {
        val scope = rememberCoroutineScope()
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                scope.launch { dismissState.reset() }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null
                )
            },
            title = { Text("Hapus Item?") },
            text = { Text("Apakah kamu yakin ingin menghapus \"${item.name}\"?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        onDeleteConfirmed(item)
                    }
                ) {
                    Text(
                        "Hapus",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        scope.launch { dismissState.reset() }
                    }
                ) {
                    Text("Batal")
                }
            }
        )
    }
}*/
