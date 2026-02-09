package com.lj.crud_supabase.presentation.feature.productlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.rememberAsyncImagePainter
import com.lj.crud_supabase.data.dto.Product

@OptIn(ExperimentalMaterialApi::class, ExperimentalCoilApi::class)
@Composable
fun ProductListItem(
    modifier: Modifier = Modifier,
    product: Product,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(),
        elevation = 8.dp,
        onClick = onClick
    ) {
        Row(
            modifier = modifier.padding(10.dp),
            horizontalArrangement =  Arrangement.SpaceBetween
        ) {
            Image(
                contentDescription = null,
                painter = rememberAsyncImagePainter(product.image),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(16.dp, 8.dp)
                    .size(64.dp)
            )
            Text(
                text = product.name,
                modifier = modifier.weight(1.0f),
                color = Color.Red
            )
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

        }
    }
}
