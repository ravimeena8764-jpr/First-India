package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.AppNotification
import com.example.data.model.NotificationPreferences
import com.example.ui.theme.BrandRedDark
import com.example.ui.theme.BrandRedPrimary
import com.example.ui.theme.MuktaFontFamily
import com.example.ui.theme.SaffronGold

@Composable
fun NotificationCenterDialog(
    notifications: List<AppNotification>,
    onNotificationClick: (AppNotification) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
                .testTag("notification_center_dialog")
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = null,
                            tint = BrandRedPrimary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "सूचनाएं (${notifications.size})",
                            fontFamily = MuktaFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "बंद करें",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (notifications.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "अभी कोई नई सूचना नहीं है",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(320.dp)
                    ) {
                        items(notifications, key = { it.id }) { notif ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onNotificationClick(notif) }
                                    .padding(vertical = 10.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = BrandRedPrimary.copy(alpha = 0.12f)
                                    ) {
                                        Text(
                                            text = notif.category,
                                            color = BrandRedPrimary,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }

                                    Text(
                                        text = "अभी",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = notif.title,
                                    fontFamily = MuktaFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                Text(
                                    text = notif.body,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationSettingsDialog(
    initialPreferences: NotificationPreferences,
    onSave: (NotificationPreferences) -> Unit,
    onTestNotification: () -> Unit,
    onDismiss: () -> Unit
) {
    var breaking by remember { mutableStateOf(initialPreferences.breakingNews) }
    var cityAlerts by remember { mutableStateOf(initialPreferences.cityAlerts) }
    var sports by remember { mutableStateOf(initialPreferences.cricketScores) }
    var horoscope by remember { mutableStateOf(initialPreferences.dailyHoroscope) }
    var finance by remember { mutableStateOf(initialPreferences.marketFinance) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
                .testTag("notification_settings_dialog")
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = null,
                            tint = BrandRedPrimary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "व्यक्तिगत पुश नोटिफिकेशन्स",
                            fontFamily = MuktaFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "बंद करें",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "अपनी पसंद की श्रेणियों के लिए लाइव अलर्ट प्राप्त करें:",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Toggles list
                NotificationToggleItem(
                    title = "🔴 ब्रेकिंग न्यूज़ अलर्ट्स",
                    subtitle = "देश-दुनिया की सबसे बड़ी और अहम खबरें तुरंत",
                    checked = breaking,
                    onCheckedChange = { breaking = it }
                )

                NotificationToggleItem(
                    title = "📍 मेरा शहर अलर्ट्स",
                    subtitle = "स्थानीय अपराध, नगर निगम व ट्रैफिक अपडेट्स",
                    checked = cityAlerts,
                    onCheckedChange = { cityAlerts = it }
                )

                NotificationToggleItem(
                    title = "🏏 क्रिकेट व खेल स्कोर",
                    subtitle = "लाइव मैच स्कोर और टूर्नामेंट अपडेट्स",
                    checked = sports,
                    onCheckedChange = { sports = it }
                )

                NotificationToggleItem(
                    title = "✨ दैनिक राशिफल",
                    subtitle = "सुबह 7 बजे आपकी राशि का दैनिक भविष्यफल",
                    checked = horoscope,
                    onCheckedChange = { horoscope = it }
                )

                NotificationToggleItem(
                    title = "📈 बिज़नेस व शेयर बाजार",
                    subtitle = "सेंसेक्स, निफ्टी और सोने-चांदी के ताज़ा भाव",
                    checked = finance,
                    onCheckedChange = { finance = it }
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Action Buttons: Test Notification + Save
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onTestNotification,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("टेस्ट अलर्ट", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                    }

                    Button(
                        onClick = {
                            onSave(
                                NotificationPreferences(
                                    breakingNews = breaking,
                                    cityAlerts = cityAlerts,
                                    cricketScores = sports,
                                    dailyHoroscope = horoscope,
                                    marketFinance = finance
                                )
                            )
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = BrandRedPrimary),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("सहेजें", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationToggleItem(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontFamily = MuktaFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = BrandRedPrimary
            )
        )
    }
}
