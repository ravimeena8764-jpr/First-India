package com.example.ui.components

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HowToVote
import androidx.compose.material.icons.filled.Poll
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PollOption
import com.example.data.model.PublicPoll
import com.example.ui.theme.BrandRedPrimary
import com.example.ui.theme.HindFontFamily
import com.example.ui.theme.MuktaFontFamily
import java.text.NumberFormat
import java.util.Locale

@Composable
fun PublicPollWidget(
    poll: PublicPoll,
    onVote: (String) -> Unit,
    onResetVote: () -> Unit,
    fontScale: Float = 1.0f,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedOptionId by remember(poll.userSelectedOptionId) {
        mutableStateOf(poll.userSelectedOptionId)
    }

    val isVoted = poll.userSelectedOptionId != null
    val numberFormatter = remember { NumberFormat.getNumberInstance(Locale("hi", "IN")) }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = BrandRedPrimary.copy(alpha = 0.2f),
                shape = RoundedCornerShape(16.dp)
            )
            .testTag("public_poll_card")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .animateContentSize()
        ) {
            // Header: Poll badge + Category + Live Indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(BrandRedPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Poll,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "जनमत सर्वेक्षण / पोल",
                                fontFamily = MuktaFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = BrandRedPrimary
                            ) {
                                Text(
                                    text = "LIVE",
                                    color = Color.White,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = poll.subtitleHindi,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 11.sp
                        )
                    }
                }

                // Share button
                IconButton(
                    onClick = {
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(
                                Intent.EXTRA_TEXT,
                                "【फर्स्ट इंडिया जनमत सर्वेक्षण】\n\nसवाल: ${poll.questionHindi}\n\nअपनी राय दर्ज करें और लाइव परिणाम देखें: https://firstindianews.com/poll/${poll.id}"
                            )
                            type = "text/plain"
                        }
                        context.startActivity(Intent.createChooser(sendIntent, "पोल शेयर करें"))
                    },
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("share_poll_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "पोल शेयर करें",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Question
            Text(
                text = poll.questionHindi,
                fontFamily = MuktaFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = (16 * fontScale).sp,
                lineHeight = (23 * fontScale).sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.testTag("poll_question_text")
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Options List
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                poll.options.forEach { option ->
                    if (isVoted) {
                        // Result State with animated progress bar
                        PollResultOptionItem(
                            option = option,
                            percentage = poll.getPercentageFor(option.id),
                            isSelectedByUser = poll.userSelectedOptionId == option.id,
                            fontScale = fontScale
                        )
                    } else {
                        // Voting selection state
                        PollVotingOptionItem(
                            option = option,
                            isSelected = selectedOptionId == option.id,
                            onSelect = { selectedOptionId = option.id },
                            fontScale = fontScale
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Footer Actions & Total Votes
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "कुल वोट्स: ${numberFormatter.format(poll.totalVotes)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp
                    )
                    if (isVoted) {
                        Text(
                            text = "मत दर्ज किया जा चुका है ✔",
                            color = Color(0xFF2E7D32),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                if (!isVoted) {
                    Button(
                        onClick = {
                            selectedOptionId?.let { onVote(it) }
                        },
                        enabled = selectedOptionId != null,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BrandRedPrimary,
                            disabledContainerColor = BrandRedPrimary.copy(alpha = 0.4f)
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.testTag("submit_vote_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.HowToVote,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "वोट करें",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                } else {
                    OutlinedButton(
                        onClick = onResetVote,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.testTag("change_vote_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "राय बदलें",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PollVotingOptionItem(
    option: PollOption,
    isSelected: Boolean,
    onSelect: () -> Unit,
    fontScale: Float = 1.0f
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = if (isSelected) BrandRedPrimary.copy(alpha = 0.08f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        border = androidx.compose.foundation.BorderStroke(
            width = if (isSelected) 1.5.dp else 1.dp,
            color = if (isSelected) BrandRedPrimary else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .testTag("poll_option_${option.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = onSelect,
                colors = RadioButtonDefaults.colors(
                    selectedColor = BrandRedPrimary,
                    unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = option.textHindi,
                fontFamily = HindFontFamily,
                fontSize = (13.5 * fontScale).sp,
                lineHeight = (19 * fontScale).sp,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                color = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun PollResultOptionItem(
    option: PollOption,
    percentage: Int,
    isSelectedByUser: Boolean,
    fontScale: Float = 1.0f
) {
    val animatedProgress by animateFloatAsState(
        targetValue = percentage / 100f,
        animationSpec = tween(durationMillis = 600),
        label = "poll_progress_${option.id}"
    )

    Surface(
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
        border = androidx.compose.foundation.BorderStroke(
            width = if (isSelectedByUser) 1.5.dp else 1.dp,
            color = if (isSelectedByUser) Color(0xFF2E7D32) else MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .testTag("poll_result_${option.id}")
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // Animated percentage fill background
            Box(
                modifier = Modifier
                    .fillMaxWidth(animatedProgress)
                    .matchParentSize()
                    .background(
                        if (isSelectedByUser)
                            Color(0xFF2E7D32).copy(alpha = 0.18f)
                        else
                            BrandRedPrimary.copy(alpha = 0.12f)
                    )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isSelectedByUser) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "आपकी पसंद",
                            tint = Color(0xFF2E7D32),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                    }

                    Text(
                        text = option.textHindi,
                        fontFamily = HindFontFamily,
                        fontSize = (13 * fontScale).sp,
                        lineHeight = (18 * fontScale).sp,
                        fontWeight = if (isSelectedByUser) FontWeight.Bold else FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "$percentage%",
                    fontFamily = MuktaFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = if (isSelectedByUser) Color(0xFF2E7D32) else BrandRedPrimary
                )
            }
        }
    }
}
