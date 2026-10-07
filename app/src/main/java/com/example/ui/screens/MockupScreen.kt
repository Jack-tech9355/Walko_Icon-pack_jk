package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.ui.WalkoViewModel
import com.example.ui.theme.CreamBorder
import com.example.ui.theme.CreamCanvas
import com.example.ui.theme.CreamCard
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.TextCocoa
import com.example.ui.theme.TextEspresso
import com.example.ui.theme.TextStone
import com.example.ui.theme.WarmAmber
import com.example.ui.theme.WarmCaramel

@Composable
fun MockupScreen(
    viewModel: WalkoViewModel,
    modifier: Modifier = Modifier
) {
    val selectedWp by viewModel.selectedWallpaper.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CreamCanvas)
    ) {
        // Top banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(CreamSurface)
                .border(1.dp, CreamBorder, RoundedCornerShape(24.dp))
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "Home Screen Mockup Tester",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextEspresso
                )
                Text(
                    text = "Preview icons on warm aesthetic wallpapers",
                    fontSize = 12.sp,
                    color = WarmCaramel
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Wallpaper selector chips
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(WalkoViewModel.MockupWallpaper.values()) { wp ->
                        val isSelected = selectedWp == wp
                        FilterChip(
                            selected = isSelected,
                            onClick = { viewModel.selectWallpaper(wp) },
                            label = { Text(wp.displayName, fontSize = 11.sp) },
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
            }
        }

        // SIMULATED PHONE FRAME
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            // Simulated phone chassis
            val wpColors = selectedWp.bgGradientColors.map { Color(it) }
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                shape = RoundedCornerShape(32.dp),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(CreamBorder)
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(32.dp))
                    .background(Brush.verticalGradient(wpColors))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Status bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "09:41", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Wifi, contentDescription = null, tint = Color.White, modifier = Modifier.size(13.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Default.BatteryFull, contentDescription = null, tint = Color.White, modifier = Modifier.size(13.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Minimal Clock & Weather Widget
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.45f)),
                        shape = RoundedCornerShape(22.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.92f)
                            .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(22.dp))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Thursday, Oct 15",
                                fontSize = 12.sp,
                                color = Color(0xFFF6E7DA),
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "09:41",
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.WbSunny, contentDescription = null, tint = Color(0xFFFFB300), modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("28°C • Warm & Sunny", fontSize = 11.sp, color = Color.White.copy(alpha = 0.8f))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Search Pill
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.92f)
                            .height(40.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.Black.copy(alpha = 0.45f))
                            .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(20.dp))
                            .padding(horizontal = 14.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Search, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Search apps, web, files…", fontSize = 11.sp, color = Color.White.copy(alpha = 0.6f))
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // 4x2 App Grid
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Row 1
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            MockupIconItem("WhatsApp", R.drawable.ic_walko_whatsapp)
                            MockupIconItem("Instagram", R.drawable.ic_walko_instagram)
                            MockupIconItem("YouTube", R.drawable.ic_walko_youtube)
                            MockupIconItem("Spotify", R.drawable.ic_walko_spotify)
                        }
                        // Row 2
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            MockupIconItem("Telegram", R.drawable.ic_walko_telegram)
                            MockupIconItem("Chrome", R.drawable.ic_walko_chrome)
                            MockupIconItem("GPay", R.drawable.ic_walko_gpay)
                            MockupIconItem("Netflix", R.drawable.ic_walko_netflix)
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Dock Bar
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.5f)),
                        shape = RoundedCornerShape(26.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.96f)
                            .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(26.dp))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 12.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            MockupDockIcon(R.drawable.ic_walko_phone)
                            MockupDockIcon(R.drawable.ic_walko_messages)
                            MockupDockIcon(R.drawable.ic_walko_camera)
                            MockupDockIcon(R.drawable.ic_walko_settings)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MockupIconItem(name: String, iconRes: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(58.dp)
    ) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color.Black.copy(alpha = 0.45f))
                .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(14.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = name,
                tint = Color.Unspecified,
                modifier = Modifier.size(28.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = name,
            fontSize = 9.sp,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MockupDockIcon(iconRes: Int) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White.copy(alpha = 0.12f)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(26.dp)
        )
    }
}
