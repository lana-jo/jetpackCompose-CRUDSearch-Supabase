package com.lj.crud_supabase.presentation.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

/**
 * Custom Button dengan Gradient Green Press Effect:
 * 1. Normal (Default) - Hijau Terang (#4CAF50)
 * 2. Press - Hijau Tua saat sedang ditekan (#388E3C)
 * 3. Click - Hijau Gelap setelah dilepas (#1B5E20), lalu kembali ke normal
 */
@Composable
fun TrueButtonStyle(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    sizeShape: Int ,
    content: @Composable () -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }
    var trueButtonState by remember { mutableStateOf(TrueButtonState.NORMAL) }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    // PRESS STATE - Hijau Tua
                    trueButtonState = TrueButtonState.PRESSED
                }
                is PressInteraction.Release -> {
                    // CLICK STATE - Hijau Gelap (setelah dilepas)
                    trueButtonState = TrueButtonState.CLICKED
                    // Tunggu 300ms baru kembali ke NORMAL
                    delay(300)
                    trueButtonState = TrueButtonState.NORMAL
                }
                is PressInteraction.Cancel -> {
                    // CANCEL - kembali ke NORMAL
                    trueButtonState = TrueButtonState.NORMAL
                }
            }
        }
    }

    // Tentukan warna hijau berdasarkan state
    val containerColor = when {
        !enabled -> Color(0xFF070000)  // Hijau Sangat Terang - Disabled state
        trueButtonState == TrueButtonState.PRESSED -> Color(0xFF015901)  // Hijau Tua saat ditekan
        trueButtonState == TrueButtonState.CLICKED -> Color(0xFF194B1D)  // Hijau Gelap setelah dilepas
        else -> Color.Green  // Hijau Normal (terang) - default state
    }

    val contentColor = when {
        !enabled -> Color(0xFF558B2F)
        else -> Color.Black  // Text selalu putih
    }

    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = Color(0x4F000000),
            disabledContentColor = Color(0x1EB0B0B0)
        ),
        shape = RoundedCornerShape(sizeShape.dp)

    ) {
        content()
    }
    Spacer(modifier = Modifier.height(8.dp))
}

/**
 * Custom Outlined Button dengan Gradient Green Press Effect
 */
@Composable
fun TrueOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    sizeShape: Int ,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    var trueButtonState by remember { mutableStateOf(TrueButtonState.NORMAL) }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    // PRESS STATE - Hijau Tua
                    trueButtonState = TrueButtonState.PRESSED
                }
                is PressInteraction.Release -> {
                    // CLICK STATE - Hijau Gelap (setelah dilepas)
                    trueButtonState = TrueButtonState.CLICKED
                    // Tunggu 300ms baru kembali ke NORMAL
                    delay(300)
                    trueButtonState = TrueButtonState.NORMAL
                }
                is PressInteraction.Cancel -> {
                    // CANCEL - kembali ke NORMAL
                    trueButtonState = TrueButtonState.NORMAL
                }
            }
        }
    }

    val containerColor = when (trueButtonState) {
        TrueButtonState.PRESSED -> Color(0xFF105611).copy(alpha = 0.25f)  // Hijau Tua transparan
        TrueButtonState.CLICKED -> Color(0xFF04520A).copy(alpha = 0.25f)   // Hijau Gelap transparan
        else -> Color.Transparent
    }

    val contentColor = when (trueButtonState) {
        TrueButtonState.PRESSED -> Color(0xFF105611)  // Hijau Tua
        TrueButtonState.CLICKED -> Color(0xFF04520A)  // Hijau Gelap
        else -> Color(0xFF4CAF50)  // Hijau Normal
    }

    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(sizeShape.dp)
    ) {
        content()
    }
}

/**
 * Enum untuk menentukan state button
 */
enum class TrueButtonState {
    NORMAL,      // State awal/default - Hijau Terang
    PRESSED,     // Saat sedang ditekan - Hijau Tua
    CLICKED      // Setelah dilepas - Hijau Gelap, sebelum kembali ke NORMAL
}