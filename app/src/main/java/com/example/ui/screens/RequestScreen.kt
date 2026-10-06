package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SelectAll
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.InstalledAppInfo
import com.example.ui.WalkoViewModel
import com.example.ui.theme.AmoledBlack
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.DeepObsidian
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.MidnightSurface
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun RequestScreen(
    viewModel: WalkoViewModel,
    modifier: Modifier = Modifier
) {
    val unthemedApps by viewModel.unthemedApps.collectAsStateWithLifecycle()
    val searchQuery by viewModel.requestSearchQuery.collectAsStateWithLifecycle()
    val selectedPackages by viewModel.selectedRequestApps.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AmoledBlack)
    ) {
        // Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Brush.horizontalGradient(listOf(MidnightSurface, DeepObsidian)))
                .border(1.dp, BorderSubtle, RoundedCornerShape(20.dp))
                .padding(16.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Smart Icon Request",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "${unthemedApps.size} unthemed installed apps detected",
                            fontSize = 12.sp,
                            color = NeonCyan
                        )
                    }

                    // Selection counter badge
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (selectedPackages.isNotEmpty()) NeonCyan else MidnightSurface)
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "${selectedPackages.size} Selected",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (selectedPackages.isNotEmpty()) Color.Black else TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { viewModel.selectAllUnthemedApps() },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = NeonCyan),
                        border = ButtonDefaults.outlinedButtonBorder().copy(brush = Brush.linearGradient(listOf(NeonCyan, BorderSubtle))),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.SelectAll, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Select All", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = { viewModel.clearSelectedRequestApps() },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary),
                        border = ButtonDefaults.outlinedButtonBorder().copy(brush = Brush.linearGradient(listOf(BorderSubtle, BorderSubtle))),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Clear", fontSize = 11.sp)
                    }
                }
            }
        }

        // Search Input
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.setRequestSearchQuery(it) },
            placeholder = { Text("Filter unthemed apps…", color = TextMuted, fontSize = 13.sp) },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = NeonCyan)
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { viewModel.setRequestSearchQuery("") }) {
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
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        )

        // List of unthemed apps
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(unthemedApps, key = { it.packageName }) { app ->
                val isSelected = selectedPackages.contains(app.packageName)
                Card(
                    onClick = { viewModel.toggleAppForRequest(app.packageName) },
                    colors = CardDefaults.cardColors(containerColor = if (isSelected) MidnightSurface else DeepObsidian),
                    shape = RoundedCornerShape(14.dp),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = Brush.linearGradient(
                            if (isSelected) listOf(NeonCyan, ElectricViolet) else listOf(BorderSubtle, BorderSubtle.copy(alpha = 0.5f))
                        )
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isSelected,
                            onCheckedChange = { viewModel.toggleAppForRequest(app.packageName) },
                            colors = CheckboxDefaults.colors(
                                checkedColor = NeonCyan,
                                checkmarkColor = Color.Black,
                                uncheckedColor = TextSecondary
                            )
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = app.label,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = app.packageName,
                                fontSize = 11.sp,
                                color = TextMuted,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "Component: ${app.activityName}",
                                fontSize = 9.sp,
                                color = TextSecondary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }

        // Floating send request button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Button(
                onClick = {
                    sendIconRequestEmail(context, unthemedApps.filter { selectedPackages.contains(it.packageName) })
                },
                enabled = selectedPackages.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = NeonCyan,
                    contentColor = Color.Black,
                    disabledContainerColor = MidnightSurface,
                    disabledContentColor = TextMuted
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("send_icon_request_button")
            ) {
                Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (selectedPackages.isEmpty()) "Select Apps to Request" else "Send Request (${selectedPackages.size} Apps)",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

fun sendIconRequestEmail(context: Context, apps: List<InstalledAppInfo>) {
    if (apps.isEmpty()) return

    val subject = "[Walko Icons Request] ${apps.size} New Icons Requested"
    val bodyBuilder = StringBuilder()
    bodyBuilder.append("Developer: JackTech (Walko Icons)\n")
    bodyBuilder.append("Device: ${Build.MANUFACTURER} ${Build.MODEL} (Android ${Build.VERSION.RELEASE})\n\n")
    bodyBuilder.append("Requested Apps (${apps.size}):\n")
    bodyBuilder.append("---------------------------------------\n")
    apps.forEachIndexed { index, app ->
        bodyBuilder.append("${index + 1}. App: ${app.label}\n")
        bodyBuilder.append("   Package: ${app.packageName}\n")
        bodyBuilder.append("   Activity: ${app.activityName}\n")
        bodyBuilder.append("   Component: ComponentInfo{${app.packageName}/${app.activityName}}\n\n")
    }

    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:jacktech.devs@gmail.com")
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, bodyBuilder.toString())
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    try {
        context.startActivity(Intent.createChooser(intent, "Send Icon Request via Email"))
    } catch (e: Exception) {
        val fallback = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("jacktech.devs@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, bodyBuilder.toString())
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(Intent.createChooser(fallback, "Send Icon Request"))
    }
}
