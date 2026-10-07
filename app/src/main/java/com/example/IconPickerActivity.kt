package com.example

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.data.model.ThemedIconItem
import com.example.data.repository.IconCatalog
import com.example.ui.theme.CreamBorder
import com.example.ui.theme.CreamCanvas
import com.example.ui.theme.CreamCard
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.TextEspresso
import com.example.ui.theme.TextStone
import com.example.ui.theme.WarmCaramel
import com.example.ui.theme.WalkoIconsTheme

class IconPickerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            WalkoIconsTheme {
                IconPickerScreen(
                    onIconSelected = { item ->
                        returnIconResult(item)
                    },
                    onDismiss = {
                        setResult(Activity.RESULT_CANCELED)
                        finish()
                    }
                )
            }
        }
    }

    private fun returnIconResult(item: ThemedIconItem) {
        val resultIntent = Intent()

        // 1. Resource ID info for launchers expecting ICON_RESOURCE
        val iconRes = Intent.ShortcutIconResource.fromContext(this, item.drawableResId)
        resultIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes)

        // 2. Direct Bitmap return for launchers expecting Bitmap
        try {
            val drawable = ContextCompat.getDrawable(this, item.drawableResId)
            val bitmap = drawable?.toBitmap(192, 192, Bitmap.Config.ARGB_8888)
            if (bitmap != null) {
                resultIntent.putExtra("icon", bitmap)
                resultIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, bitmap)
            }
        } catch (e: Exception) {
            // Resource fallback already attached
        }

        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconPickerScreen(
    onIconSelected: (ThemedIconItem) -> Unit,
    onDismiss: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    val filteredIcons = remember(searchQuery, selectedCategory) {
        IconCatalog.allIcons.filter { icon ->
            val matchesQuery = searchQuery.isBlank() ||
                    icon.name.contains(searchQuery, ignoreCase = true) ||
                    icon.tags.any { it.contains(searchQuery, ignoreCase = true) }
            val matchesCategory = (selectedCategory == "All") || (icon.category == selectedCategory)
            matchesQuery && matchesCategory
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Pick an Icon",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = TextEspresso
                        )
                        Text(
                            text = "Walko Icons • ${IconCatalog.allIcons.size} Available",
                            fontSize = 11.sp,
                            color = WarmCaramel
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextEspresso)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CreamSurface
                )
            )
        },
        containerColor = CreamCanvas
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Search input
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search icon name or tag…", color = TextStone, fontSize = 13.sp) },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null, tint = WarmCaramel)
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = WarmCaramel,
                    unfocusedBorderColor = CreamBorder,
                    focusedContainerColor = CreamCard,
                    unfocusedContainerColor = CreamCard,
                    focusedTextColor = TextEspresso,
                    unfocusedTextColor = TextEspresso
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .testTag("picker_search_input")
            )

            // Category row
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                items(IconCatalog.categories) { category ->
                    val isSelected = selectedCategory == category
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = category },
                        label = { Text(category, fontSize = 11.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = WarmCaramel,
                            selectedLabelColor = Color.White,
                            containerColor = CreamCard,
                            labelColor = TextEspresso
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = CreamBorder,
                            selectedBorderColor = WarmCaramel
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }

            // Icons Grid
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 76.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("picker_icons_grid")
            ) {
                items(filteredIcons, key = { it.id }) { item ->
                    Card(
                        onClick = { onIconSelected(item) },
                        colors = CardDefaults.cardColors(containerColor = CreamCard),
                        shape = RoundedCornerShape(16.dp),
                        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CreamBorder)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(CreamSurface),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = item.drawableResId),
                                    contentDescription = item.name,
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = item.name,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                color = TextEspresso,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}
