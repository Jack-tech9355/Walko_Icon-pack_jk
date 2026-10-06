package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.ThemedIconItem
import com.example.data.repository.IconCatalog
import com.example.ui.WalkoViewModel
import com.example.ui.theme.AmoledBlack
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CyberPink
import com.example.ui.theme.DeepObsidian
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.MidnightSurface
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: WalkoViewModel,
    modifier: Modifier = Modifier
) {
    val icons by viewModel.filteredIcons.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val showOnlyFavorites by viewModel.showOnlyFavorites.collectAsStateWithLifecycle()
    val selectedIconDetail by viewModel.selectedIconDetail.collectAsStateWithLifecycle()
    val detailBgMode by viewModel.detailBgMode.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AmoledBlack)
    ) {
        // Hero Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            MidnightSurface,
                            DeepObsidian,
                            Color(0xFF131D2E)
                        )
                    )
                )
                .border(1.dp, BorderSubtle, RoundedCornerShape(20.dp))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Walko Icons",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = NeonCyan
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(ElectricViolet.copy(alpha = 0.3f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "4K ULTRA",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = NeonCyan
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "80+ Handcrafted Icons • Pure Vector Fidelity",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }

                // Fav Filter Button
                IconButton(
                    onClick = { viewModel.toggleFavoritesFilter() },
                    modifier = Modifier
                        .testTag("filter_favorites_button")
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(if (showOnlyFavorites) CyberPink.copy(alpha = 0.2f) else DeepObsidian)
                        .border(1.dp, if (showOnlyFavorites) CyberPink else BorderSubtle, CircleShape)
                ) {
                    Icon(
                        imageVector = if (showOnlyFavorites) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorites",
                        tint = if (showOnlyFavorites) CyberPink else TextSecondary
                    )
                }
            }
        }

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.setSearchQuery(it) },
            placeholder = { Text("Search 80+ icons by app name or tag…", color = TextMuted, fontSize = 13.sp) },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = NeonCyan)
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { viewModel.setSearchQuery("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear", tint = TextSecondary)
                    }
                }
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = NeonCyan,
                unfocusedBorderColor = BorderSubtle,
                focusedContainerColor = DeepObsidian,
                unfocusedContainerColor = DeepObsidian,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .testTag("search_icon_input")
        )

        // Category Chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
        ) {
            items(IconCatalog.categories) { category ->
                val isSelected = selectedCategory == category
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.selectCategory(category) },
                    label = {
                        Text(
                            text = category,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = NeonCyan,
                        selectedLabelColor = Color.Black,
                        containerColor = DeepObsidian,
                        labelColor = TextSecondary
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = isSelected,
                        borderColor = BorderSubtle,
                        selectedBorderColor = NeonCyan
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }

        // Icon Grid
        if (icons.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Empty",
                        tint = TextMuted,
                        modifier = Modifier.size(56.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No icons found for '$searchQuery'",
                        color = TextSecondary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Try searching for WhatsApp, Chrome, or YouTube",
                        color = TextMuted,
                        fontSize = 12.sp
                    )
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 82.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("icon_pack_grid")
            ) {
                items(icons, key = { it.id }) { iconItem ->
                    IconGridCard(
                        item = iconItem,
                        onClick = { viewModel.openIconDetail(iconItem) }
                    )
                }
            }
        }
    }

    // Detail Dialog
    selectedIconDetail?.let { item ->
        IconDetailDialog(
            item = item,
            bgMode = detailBgMode,
            onDismiss = { viewModel.closeIconDetail() },
            onCycleBg = { viewModel.cycleDetailBgMode() },
            onToggleFavorite = { viewModel.toggleFavorite(item) },
            onPinShortcut = { context, icon ->
                val drawable = ContextCompat.getDrawable(context, icon.drawableResId)
                val bmp = drawable?.toBitmap(256, 256)
                if (bmp != null) {
                    viewModel.pinShortcutToHomeScreen(bmp)
                }
            }
        )
    }
}

@Composable
fun IconGridCard(
    item: ThemedIconItem,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = DeepObsidian
        ),
        shape = RoundedCornerShape(18.dp),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = Brush.linearGradient(
                colors = listOf(
                    BorderSubtle,
                    BorderSubtle.copy(alpha = 0.3f)
                )
            )
        ),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("icon_item_${item.id}")
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(MidnightSurface)
                    .border(
                        1.dp,
                        Color(item.accentColor).copy(alpha = 0.35f),
                        RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = item.drawableResId),
                    contentDescription = item.name,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(34.dp)
                )

                if (item.isFavorite) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(2.dp)
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(CyberPink)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = item.name,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun IconDetailDialog(
    item: ThemedIconItem,
    bgMode: Int,
    onDismiss: () -> Unit,
    onCycleBg: () -> Unit,
    onToggleFavorite: () -> Unit,
    onPinShortcut: (Context, ThemedIconItem) -> Unit
) {
    val context = LocalContext.current

    val bgBrush = when (bgMode) {
        1 -> Brush.linearGradient(listOf(Color(0xFFE2E8F0), Color(0xFFCBD5E1))) // Light preview
        2 -> Brush.radialGradient(listOf(Color(item.accentColor).copy(alpha = 0.3f), AmoledBlack)) // Neon glow
        else -> Brush.linearGradient(listOf(Color(0xFF07090E), Color(0xFF0F141F))) // Amoled
    }

    val bgLabel = when (bgMode) {
        1 -> "Light Mode"
        2 -> "Neon Glow"
        else -> "AMOLED Black"
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DeepObsidian,
        shape = RoundedCornerShape(24.dp),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = item.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = item.category,
                        fontSize = 12.sp,
                        color = NeonCyan
                    )
                }
                IconButton(onClick = onToggleFavorite) {
                    Icon(
                        imageVector = if (item.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (item.isFavorite) CyberPink else TextSecondary
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Large 4K Icon Preview Area
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(bgBrush)
                        .border(1.5.dp, BorderSubtle, RoundedCornerShape(24.dp))
                        .clickable { onCycleBg() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = item.drawableResId),
                        contentDescription = item.name,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(80.dp)
                    )

                    // Small background switch badge
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 6.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color.Black.copy(alpha = 0.6f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = bgLabel,
                            fontSize = 9.sp,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Component info
                Card(
                    colors = CardDefaults.cardColors(containerColor = MidnightSurface),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "Package ID", fontSize = 10.sp, color = TextMuted)
                            Text(
                                text = item.packageName,
                                fontSize = 11.sp,
                                color = TextPrimary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        IconButton(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("Package Name", item.packageName)
                                clipboard.setPrimaryClip(clip)
                            },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                Icons.Default.ContentCopy,
                                contentDescription = "Copy",
                                tint = NeonCyan,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onPinShortcut(context, item)
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = Color.Black),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.PushPin, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Pin to Home", fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Close", color = TextSecondary)
            }
        }
    )
}
