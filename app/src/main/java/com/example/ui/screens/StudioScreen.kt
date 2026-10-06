package com.example.ui.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.InstalledAppInfo
import com.example.data.model.SavedCustomIcon
import com.example.ui.BackgroundPreset
import com.example.ui.IconShapeMask
import com.example.ui.WalkoViewModel
import com.example.ui.components.StyledIconCanvas
import com.example.ui.components.renderStyledIconBitmap
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
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudioScreen(
    viewModel: WalkoViewModel,
    modifier: Modifier = Modifier
) {
    val subTab by viewModel.studioSubTab.collectAsStateWithLifecycle()
    val installedApps by viewModel.installedApps.collectAsStateWithLifecycle()
    val selectedApp by viewModel.selectedTargetApp.collectAsStateWithLifecycle()
    val customIconName by viewModel.customIconName.collectAsStateWithLifecycle()
    val pickedBitmap by viewModel.pickedBitmap.collectAsStateWithLifecycle()
    val shapeMask by viewModel.selectedShapeMask.collectAsStateWithLifecycle()
    val bgPreset by viewModel.selectedBgPreset.collectAsStateWithLifecycle()
    val borderWidth by viewModel.borderWidth.collectAsStateWithLifecycle()
    val borderColorHex by viewModel.borderColorHex.collectAsStateWithLifecycle()
    val scalePercent by viewModel.scalePercent.collectAsStateWithLifecycle()
    val rotationDeg by viewModel.rotationDeg.collectAsStateWithLifecycle()
    val badgeSymbol by viewModel.selectedBadge.collectAsStateWithLifecycle()
    val savedCreations by viewModel.savedCreations.collectAsStateWithLifecycle()

    // Photo picker launcher (Android Photo Picker, zero permission)
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        viewModel.setPickedImageUri(uri)
    }

    var showAppPickerModal by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AmoledBlack)
    ) {
        // Tab Row: Studio Creator vs My Creations
        TabRow(
            selectedTabIndex = subTab,
            containerColor = DeepObsidian,
            contentColor = NeonCyan,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[subTab]),
                    color = NeonCyan
                )
            }
        ) {
            Tab(
                selected = subTab == 0,
                onClick = { viewModel.setStudioSubTab(0) },
                text = { Text("Icon Studio", fontWeight = FontWeight.Bold, fontSize = 13.sp) },
                icon = { Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = subTab == 1,
                onClick = { viewModel.setStudioSubTab(1) },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("My Creations", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        if (savedCreations.isNotEmpty()) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(ElectricViolet)
                                    .padding(horizontal = 6.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = "${savedCreations.size}",
                                    fontSize = 10.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                },
                icon = { Icon(Icons.Default.Bookmark, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
        }

        if (subTab == 0) {
            // STUDIO CREATOR VIEW
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // LIVE HIGH RES PREVIEW CARD
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = DeepObsidian),
                        shape = RoundedCornerShape(24.dp),
                        border = CardDefaults.outlinedCardBorder().copy(
                            brush = Brush.linearGradient(
                                listOf(BorderSubtle, NeonCyan.copy(alpha = 0.4f))
                            )
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("studio_preview_card")
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "LIVE 4K CANVAS PREVIEW",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = NeonCyan,
                                letterSpacing = 1.2.sp
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Canvas preview
                            Box(
                                modifier = Modifier
                                    .size(140.dp)
                                    .padding(4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                val borderColor = try {
                                    Color(android.graphics.Color.parseColor(borderColorHex))
                                } catch (e: Exception) {
                                    NeonCyan
                                }

                                StyledIconCanvas(
                                    modifier = Modifier.fillMaxSize(),
                                    shapeMask = shapeMask,
                                    bgPreset = bgPreset,
                                    borderColor = borderColor,
                                    borderWidthDp = borderWidth,
                                    scalePercent = scalePercent,
                                    rotationDeg = rotationDeg,
                                    bitmap = pickedBitmap,
                                    badgeSymbol = badgeSymbol
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = customIconName,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )

                            Text(
                                text = "Target: ${selectedApp?.label ?: "Select Target App"}",
                                fontSize = 12.sp,
                                color = TextSecondary
                            )
                        }
                    }
                }

                // 1. CHOOSE PHOTO BUTTON
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ElectricViolet,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .testTag("pick_photo_button")
                        ) {
                            Icon(Icons.Default.AddPhotoAlternate, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(if (pickedBitmap == null) "Pick Photo" else "Change Photo", fontWeight = FontWeight.Bold)
                        }

                        // App Linker Button
                        OutlinedButton(
                            onClick = { showAppPickerModal = true },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = NeonCyan),
                            border = ButtonDefaults.outlinedButtonBorder().copy(brush = Brush.linearGradient(listOf(NeonCyan, ElectricViolet))),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .testTag("link_app_button")
                        ) {
                            Icon(Icons.Default.Apps, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Link Target App", fontWeight = FontWeight.Bold, maxLines = 1)
                        }
                    }
                }

                // ICON NAME INPUT
                item {
                    OutlinedTextField(
                        value = customIconName,
                        onValueChange = { viewModel.setCustomIconName(it) },
                        label = { Text("Custom Icon Name", color = TextSecondary, fontSize = 12.sp) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonCyan,
                            unfocusedBorderColor = BorderSubtle,
                            focusedContainerColor = DeepObsidian,
                            unfocusedContainerColor = DeepObsidian,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("custom_icon_name_input")
                    )
                }

                // 2. SHAPE MASKS
                item {
                    Column {
                        Text(
                            text = "Shape Mask",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(IconShapeMask.values()) { mask ->
                                val isSelected = shapeMask == mask
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { viewModel.setShapeMask(mask) },
                                    label = { Text(mask.displayName, fontSize = 12.sp) },
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
                    }
                }

                // 3. BACKGROUND FILL
                item {
                    Column {
                        Text(
                            text = "Background Style",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(BackgroundPreset.values()) { preset ->
                                val isSelected = bgPreset == preset
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { viewModel.setBgPreset(preset) },
                                    label = { Text(preset.displayName, fontSize = 12.sp) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = ElectricViolet,
                                        selectedLabelColor = Color.White,
                                        containerColor = DeepObsidian,
                                        labelColor = TextSecondary
                                    ),
                                    border = FilterChipDefaults.filterChipBorder(
                                        enabled = true,
                                        selected = isSelected,
                                        borderColor = BorderSubtle,
                                        selectedBorderColor = ElectricViolet
                                    ),
                                    shape = RoundedCornerShape(12.dp)
                                )
                            }
                        }
                    }
                }

                // 4. BORDER THICKNESS & COLOR
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = DeepObsidian),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Border Stroke", fontSize = 13.sp, color = TextPrimary)
                                Text("${borderWidth.toInt()} dp", fontSize = 13.sp, color = NeonCyan)
                            }
                            Slider(
                                value = borderWidth,
                                onValueChange = { viewModel.setBorderWidth(it) },
                                valueRange = 0f..12f,
                                colors = SliderDefaults.colors(
                                    thumbColor = NeonCyan,
                                    activeTrackColor = NeonCyan,
                                    inactiveTrackColor = MidnightSurface
                                )
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text("Border Color Accent", fontSize = 12.sp, color = TextSecondary)
                            Spacer(modifier = Modifier.height(8.dp))
                            val colorPalette = listOf(
                                "#00E5FF", "#7C4DFF", "#FF3366", "#00E676", "#FFB300", "#FFFFFF", "#1E2A3E"
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                colorPalette.forEach { hex ->
                                    val isColorSelected = borderColorHex.equals(hex, ignoreCase = true)
                                    Box(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .clip(CircleShape)
                                            .background(Color(android.graphics.Color.parseColor(hex)))
                                            .border(
                                                if (isColorSelected) 2.5.dp else 1.dp,
                                                if (isColorSelected) Color.White else BorderSubtle,
                                                CircleShape
                                            )
                                            .clickable { viewModel.setBorderColorHex(hex) },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (isColorSelected) {
                                            Icon(
                                                Icons.Default.Done,
                                                contentDescription = null,
                                                tint = if (hex == "#FFFFFF") Color.Black else Color.White,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // 5. SCALE & ROTATION SLIDERS
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = DeepObsidian),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            // Zoom / Scale
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Zoom & Scale", fontSize = 13.sp, color = TextPrimary)
                                Text("${scalePercent.toInt()}%", fontSize = 13.sp, color = NeonCyan)
                            }
                            Slider(
                                value = scalePercent,
                                onValueChange = { viewModel.setScalePercent(it) },
                                valueRange = 50f..200f,
                                colors = SliderDefaults.colors(
                                    thumbColor = NeonCyan,
                                    activeTrackColor = NeonCyan,
                                    inactiveTrackColor = MidnightSurface
                                )
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // Rotation
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Image Rotation", fontSize = 13.sp, color = TextPrimary)
                                Text("${rotationDeg.toInt()}°", fontSize = 13.sp, color = ElectricViolet)
                            }
                            Slider(
                                value = rotationDeg,
                                onValueChange = { viewModel.setRotationDeg(it) },
                                valueRange = -180f..180f,
                                colors = SliderDefaults.colors(
                                    thumbColor = ElectricViolet,
                                    activeTrackColor = ElectricViolet,
                                    inactiveTrackColor = MidnightSurface
                                )
                            )
                        }
                    }
                }

                // 6. MINI BADGE OVERLAY
                item {
                    Column {
                        Text(
                            text = "Mini Overlay Badge",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        val badges = listOf(null, "⚡", "💬", "📸", "▶️", "⭐", "❤️", "🔥", "🎵")
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(badges) { b ->
                                val isSelected = badgeSymbol == b
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(if (isSelected) NeonCyan.copy(alpha = 0.2f) else DeepObsidian)
                                        .border(
                                            1.dp,
                                            if (isSelected) NeonCyan else BorderSubtle,
                                            RoundedCornerShape(10.dp)
                                        )
                                        .clickable { viewModel.setBadge(b) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = b ?: "None",
                                        fontSize = if (b == null) 10.sp else 16.sp,
                                        color = if (isSelected) NeonCyan else TextSecondary
                                    )
                                }
                            }
                        }
                    }
                }

                // 7. PRIMARY ACTION BUTTONS: PIN TO HOME & SAVE
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Button(
                            onClick = {
                                val borderColorInt = try {
                                    android.graphics.Color.parseColor(borderColorHex)
                                } catch (e: Exception) {
                                    android.graphics.Color.CYAN
                                }
                                val rendered = renderStyledIconBitmap(
                                    bitmap = pickedBitmap,
                                    shapeMask = shapeMask,
                                    bgPreset = bgPreset,
                                    borderColorInt = borderColorInt,
                                    borderWidthPx = borderWidth * 3f,
                                    scalePercent = scalePercent,
                                    rotationDeg = rotationDeg,
                                    badgeSymbol = badgeSymbol
                                )
                                viewModel.pinShortcutToHomeScreen(rendered)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = NeonCyan,
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp)
                                .testTag("pin_shortcut_button")
                        ) {
                            Icon(Icons.Default.PushPin, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Pin Shortcut to Home Screen", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
                        }

                        Button(
                            onClick = {
                                val borderColorInt = try {
                                    android.graphics.Color.parseColor(borderColorHex)
                                } catch (e: Exception) {
                                    android.graphics.Color.CYAN
                                }
                                val rendered = renderStyledIconBitmap(
                                    bitmap = pickedBitmap,
                                    shapeMask = shapeMask,
                                    bgPreset = bgPreset,
                                    borderColorInt = borderColorInt,
                                    borderWidthPx = borderWidth * 3f,
                                    scalePercent = scalePercent,
                                    rotationDeg = rotationDeg,
                                    badgeSymbol = badgeSymbol
                                )
                                viewModel.saveCurrentCreation(rendered)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MidnightSurface,
                                contentColor = TextPrimary
                            ),
                            border = ButtonDefaults.outlinedButtonBorder().copy(brush = Brush.linearGradient(listOf(BorderSubtle, ElectricViolet))),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("save_creation_button")
                        ) {
                            Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(20.dp), tint = ElectricViolet)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Save Design to 'My Creations'", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
            }
        } else {
            // MY CREATIONS SUB-TAB VIEW
            if (savedCreations.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = null,
                            tint = TextMuted,
                            modifier = Modifier.size(56.dp)
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "No Saved Creations Yet",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Design a custom icon with your photo and tap 'Save Design'",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(savedCreations, key = { it.id }) { item ->
                        CreationCard(
                            item = item,
                            onPin = {
                                if (item.imageFilePath != null && File(item.imageFilePath).exists()) {
                                    val bmp = BitmapFactory.decodeFile(item.imageFilePath)
                                    if (bmp != null) {
                                        viewModel.pinShortcutToHomeScreen(bmp)
                                    }
                                }
                            },
                            onDelete = { viewModel.deleteCreation(item.id) }
                        )
                    }
                }
            }
        }
    }

    // Modal to pick installed target app
    if (showAppPickerModal) {
        AlertDialog(
            onDismissRequest = { showAppPickerModal = false },
            containerColor = DeepObsidian,
            title = {
                Text("Select App to Link", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Scanned ${installedApps.size} installed apps on your device",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(340.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(installedApps) { app ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (selectedApp?.packageName == app.packageName) MidnightSurface else Color.Transparent)
                                    .clickable {
                                        viewModel.selectTargetApp(app)
                                        showAppPickerModal = false
                                    }
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(MidnightSurface),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Apps, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(20.dp))
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = app.label,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = app.packageName,
                                        fontSize = 10.sp,
                                        color = TextMuted,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showAppPickerModal = false }) {
                    Text("Cancel", color = NeonCyan)
                }
            }
        )
    }
}

@Composable
fun CreationCard(
    item: SavedCustomIcon,
    onPin: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = DeepObsidian),
        shape = RoundedCornerShape(16.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(BorderSubtle, BorderSubtle.copy(alpha = 0.5f)))),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon thumbnail
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(MidnightSurface),
                contentAlignment = Alignment.Center
            ) {
                if (item.imageFilePath != null && File(item.imageFilePath).exists()) {
                    val bmp = remember(item.imageFilePath) { BitmapFactory.decodeFile(item.imageFilePath) }
                    if (bmp != null) {
                        Image(
                            bitmap = bmp.asImageBitmap(),
                            contentDescription = item.name,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Icon(Icons.Default.Edit, contentDescription = null, tint = NeonCyan)
                    }
                } else {
                    Icon(Icons.Default.Edit, contentDescription = null, tint = NeonCyan)
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = TextPrimary
                )
                Text(
                    text = item.targetPackage,
                    fontSize = 10.sp,
                    color = TextSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Shape: ${item.shapeMask}",
                    fontSize = 10.sp,
                    color = NeonCyan
                )
            }

            // Pin button
            IconButton(
                onClick = onPin,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(MidnightSurface)
            ) {
                Icon(
                    Icons.Default.PushPin,
                    contentDescription = "Pin to Home",
                    tint = NeonCyan,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(6.dp))

            // Delete button
            IconButton(
                onClick = onDelete,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(MidnightSurface)
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = CyberPink,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
