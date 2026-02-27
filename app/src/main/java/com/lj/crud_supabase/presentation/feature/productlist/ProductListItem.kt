package com.lj.crud_supabase.presentation.feature.productlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.lj.crud_supabase.domain.models.Product
import com.lj.crud_supabase.presentation.utils.formatPriceToIDR
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListItem(product: Product, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                if (product.image.isNotEmpty()) {

                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = product.image,
                        contentDescription = product.name,
                    )
                    Timber.tag("SupabaseImg").d("Image URL = ${product.image}")


                } else {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "No image",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Timber.tag("ErrorSupabaseImg").e("Image URL = ${product.image}")

                }
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = formatPriceToIDR(product.price),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}