package com.example.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Launch
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.LauncherInfo
import com.example.ui.WalkoViewModel
import com.example.ui.theme.AmoledBlack
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CyberGreen
import com.example.ui.theme.DeepObsidian
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.MidnightSurface
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun ApplyScreen(
    viewModel: WalkoViewModel,
    modifier: Modifier = Modifier
) {
    val launchers by viewModel.launchers.collectAsStateWithLifecycle()
    val selectedTutorial by viewModel.selectedLauncherTutorial.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadLaunchers()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AmoledBlack)
    ) {
        // Top Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(MidnightSurface, DeepObsidian)
                    )
                )
                .border(1.dp, BorderSubtle, RoundedCornerShape(20.dp))
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(NeonCyan.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Launch,
                        contentDescription = null,
                        tint = NeonCyan,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = "1-Click Launcher Applier",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "Auto-detects Nova, Lawnchair, Smart, Niagara & more",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }
            }
        }

        // Launcher List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(launchers, key = { it.id }) { launcher ->
                LauncherCard(
                    launcher = launcher,
                    onApply = { viewModel.applyLauncher(launcher) },
                    onTutorial = { viewModel.openLauncherTutorial(launcher) },
                    onGetLauncher = {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${launcher.packageName}"))
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=${launcher.packageName}"))
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(intent)
                        }
                    }
                )
            }
        }
    }

    // Tutorial Dialog
    selectedTutorial?.let { launcher ->
        AlertDialog(
            onDismissRequest = { viewModel.closeLauncherTutorial() },
            containerColor = DeepObsidian,
            shape = RoundedCornerShape(20.dp),
            title = {
                Text(
                    text = "${launcher.name} Tutorial",
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    fontSize = 18.sp
                )
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Follow these simple steps to activate Walko Icons:",
                        fontSize = 13.sp,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    launcher.tutorialSteps.forEachIndexed { index, step ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(NeonCyan),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${index + 1}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = Color.Black
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = step,
                                fontSize = 13.sp,
                                color = TextPrimary,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { viewModel.closeLauncherTutorial() },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = Color.Black),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Got it", fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}

@Composable
fun LauncherCard(
    launcher: LauncherInfo,
    onApply: () -> Unit,
    onTutorial: () -> Unit,
    onGetLauncher: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = DeepObsidian),
        shape = RoundedCornerShape(18.dp),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = Brush.linearGradient(
                if (launcher.isInstalled) listOf(CyberGreen.copy(alpha = 0.6f), BorderSubtle)
                else listOf(BorderSubtle, BorderSubtle.copy(alpha = 0.4f))
            )
        ),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("launcher_card_${launcher.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = launcher.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    if (launcher.isInstalled) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(CyberGreen.copy(alpha = 0.2f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "INSTALLED",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = CyberGreen
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (launcher.isDirectApplySupported) "1-Tap Direct Apply Supported" else "Manual System Theming",
                    fontSize = 11.sp,
                    color = if (launcher.isDirectApplySupported) NeonCyan else TextMuted
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            if (launcher.isInstalled) {
                Button(
                    onClick = onApply,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CyberGreen,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                    modifier = Modifier.testTag("apply_launcher_${launcher.id}")
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Apply", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            } else if (launcher.isDirectApplySupported) {
                OutlinedButton(
                    onClick = onGetLauncher,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = NeonCyan),
                    border = ButtonDefaults.outlinedButtonBorder().copy(brush = Brush.linearGradient(listOf(NeonCyan, BorderSubtle))),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Get", fontSize = 12.sp)
                }
            } else {
                IconButton(
                    onClick = onTutorial,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(MidnightSurface)
                ) {
                    Icon(Icons.Default.HelpOutline, contentDescription = "Tutorial", tint = NeonCyan)
                }
            }
        }
    }
}
