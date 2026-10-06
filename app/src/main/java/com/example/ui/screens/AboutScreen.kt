package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.WalkoViewModel
import com.example.ui.theme.CreamBorder
import com.example.ui.theme.CreamCanvas
import com.example.ui.theme.CreamCard
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.CreamSurfaceVariant
import com.example.ui.theme.SageGreen
import com.example.ui.theme.TextCocoa
import com.example.ui.theme.TextEspresso
import com.example.ui.theme.TextStone
import com.example.ui.theme.WarmAmber
import com.example.ui.theme.WarmCaramel
import com.example.ui.theme.WarmCaramelLight
import com.example.ui.theme.WarmCinnamon

@Composable
fun AboutScreen(
    viewModel: WalkoViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showLicenseDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CreamCanvas)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // App Branding Hero
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CreamCard),
                shape = RoundedCornerShape(26.dp),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(CreamBorder)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .clip(RoundedCornerShape(22.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(WarmCaramel, WarmAmber)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "W",
                            fontSize = 42.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Walko Icons",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = TextEspresso
                    )

                    Text(
                        text = "Ultra-HD Custom Icon Studio & Launcher Themer",
                        fontSize = 12.sp,
                        color = WarmCaramel,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(CreamSurface)
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Version 1.1.0 (Smooth Cream Edition)",
                            fontSize = 11.sp,
                            color = TextCocoa,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // MANDATORY DEVELOPER CARD
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                shape = RoundedCornerShape(22.dp),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(CreamBorder)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("developer_card")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(WarmCaramelLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = WarmCaramel,
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Developer: ",
                                fontSize = 13.sp,
                                color = TextCocoa
                            )
                            Text(
                                text = "JackTech",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = WarmCaramel
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Made with ❤️ in India",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextEspresso
                        )
                        Text(
                            text = "Love from India 🇮🇳",
                            fontSize = 12.sp,
                            color = SageGreen,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // App Features Stats Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(title = "Icons", value = "80+", subtitle = "Real Vectors", modifier = Modifier.weight(1f))
                StatCard(title = "Masks", value = "7", subtitle = "DIY Shapes", modifier = Modifier.weight(1f))
                StatCard(title = "Launchers", value = "9+", subtitle = "Direct Apply", modifier = Modifier.weight(1f))
            }
        }

        // Quick Action Buttons: Rate, Share, Contact
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${context.packageName}"))
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=${context.packageName}"))
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(intent)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = WarmCaramel, contentColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("rate_app_button")
                ) {
                    Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Rate 5 Stars on Google Play", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, "Check out Walko Icons - Ultra 4K Icon Pack & DIY Studio by JackTech! Download now.")
                                type = "text/plain"
                            }
                            context.startActivity(Intent.createChooser(sendIntent, "Share Walko Icons"))
                        },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextEspresso),
                        border = ButtonDefaults.outlinedButtonBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CreamBorder)),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp), tint = WarmCaramel)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Share App", fontSize = 12.sp)
                    }

                    OutlinedButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("mailto:jacktech.devs@gmail.com")
                                putExtra(Intent.EXTRA_SUBJECT, "Walko Icons Feedback / Support")
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            }
                            try {
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                // Handled
                            }
                        },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextEspresso),
                        border = ButtonDefaults.outlinedButtonBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CreamBorder)),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(16.dp), tint = WarmAmber)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Contact Us", fontSize = 12.sp)
                    }
                }

                // License & Privacy Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(onClick = { showLicenseDialog = true }) {
                        Icon(Icons.Default.Shield, contentDescription = null, modifier = Modifier.size(14.dp), tint = TextStone)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Open Source License & Privacy", fontSize = 11.sp, color = TextStone)
                    }
                }
            }
        }
    }

    if (showLicenseDialog) {
        AlertDialog(
            onDismissRequest = { showLicenseDialog = false },
            containerColor = CreamSurface,
            shape = RoundedCornerShape(26.dp),
            title = {
                Text("Open Source & Privacy", fontWeight = FontWeight.Bold, color = TextEspresso, fontSize = 18.sp)
            },
            text = {
                Column {
                    Text(
                        text = "Walko Icons Smooth Cream Edition is developed by JackTech.",
                        fontSize = 13.sp,
                        color = TextEspresso
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "• 100% Offline Capable: All custom shortcuts and icon packs are stored locally on your device using Room Database.\n" +
                               "• Privacy First: No personal data or user images are transmitted to external servers.\n" +
                               "• Licensed under the Apache License, Version 2.0.",
                        fontSize = 12.sp,
                        color = TextCocoa
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { showLicenseDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = WarmCaramel, contentColor = Color.White),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("OK", fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CreamCard),
        shape = RoundedCornerShape(18.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CreamBorder)),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Black, color = WarmCaramel)
            Text(text = title, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextEspresso)
            Text(text = subtitle, fontSize = 9.sp, color = TextStone)
        }
    }
}
