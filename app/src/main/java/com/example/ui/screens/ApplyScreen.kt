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
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.HelpOutline
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.LauncherInfo
import com.example.ui.WalkoViewModel
import com.example.ui.theme.CreamBorder
import com.example.ui.theme.CreamCanvas
import com.example.ui.theme.CreamCard
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.SageGreen
import com.example.ui.theme.TextCocoa
import com.example.ui.theme.TextEspresso
import com.example.ui.theme.TextStone
import com.example.ui.theme.WarmCaramel
import com.example.ui.theme.WarmCaramelLight

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
            .background(CreamCanvas)
    ) {
        // Top Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(CreamSurface)
                .border(1.dp, CreamBorder, RoundedCornerShape(24.dp))
                .padding(18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(WarmCaramelLight),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Launch,
                        contentDescription = null,
                        tint = WarmCaramel,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = "1-Click Launcher Themer",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextEspresso
                    )
                    Text(
                        text = "Nova, Lawnchair, Smart, Niagara, Hyperion & Microsoft",
                        fontSize = 12.sp,
                        color = TextCocoa
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
            containerColor = CreamSurface,
            shape = RoundedCornerShape(26.dp),
            title = {
                Text(
                    text = "${launcher.name} Tutorial",
                    fontWeight = FontWeight.Bold,
                    color = TextEspresso,
                    fontSize = 18.sp
                )
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Follow these simple steps to activate Walko Icons:",
                        fontSize = 13.sp,
                        color = TextCocoa
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
                                    .background(WarmCaramel),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${index + 1}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = step,
                                fontSize = 13.sp,
                                color = TextEspresso,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { viewModel.closeLauncherTutorial() },
                    colors = ButtonDefaults.buttonColors(containerColor = WarmCaramel, contentColor = Color.White),
                    shape = RoundedCornerShape(14.dp)
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
        colors = CardDefaults.cardColors(containerColor = CreamCard),
        shape = RoundedCornerShape(20.dp),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(
                if (launcher.isInstalled) SageGreen.copy(alpha = 0.5f)
                else CreamBorder
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
                        color = TextEspresso
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    if (launcher.isInstalled) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(SageGreen.copy(alpha = 0.15f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "INSTALLED",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = SageGreen
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (launcher.isDirectApplySupported) "1-Tap Dedicated Intent Supported" else "Manual System Theming",
                    fontSize = 11.sp,
                    color = if (launcher.isDirectApplySupported) WarmCaramel else TextStone
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            if (launcher.isInstalled) {
                Button(
                    onClick = onApply,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SageGreen,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(14.dp),
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
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = WarmCaramel),
                    border = ButtonDefaults.outlinedButtonBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CreamBorder)),
                    shape = RoundedCornerShape(14.dp),
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
                        .background(CreamSurface)
                ) {
                    Icon(Icons.Default.HelpOutline, contentDescription = "Tutorial", tint = WarmCaramel)
                }
            }
        }
    }
}
