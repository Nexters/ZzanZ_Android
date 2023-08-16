package com.zzanz.swip_android.presentation.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zzanz.swip_android.R
import com.zzanz.swip_android.common.ui.theme.ZzanZColorPalette
import com.zzanz.swip_android.common.ui.util.ImageViewWithXml

@Composable
fun BackIcon() {
    Icon(painter = painterResource(id = R.drawable.icon_left), contentDescription = null)
}

@Composable
fun MoreIcon() {
    Icon(painterResource(id = R.drawable.icon_right), contentDescription = null)
}

@Composable
fun AddIcon() {
    ImageViewWithXml(modifier = Modifier.size(36.dp), resId = R.drawable.icon_add)
}

@Composable
fun InfoIcon(color: Color) {
    Icon(
        modifier = Modifier.size(16.dp),
        painter = painterResource(id = R.drawable.alert_information_circle),
        tint = color,
        contentDescription = null
    )
}

@Composable
fun AlarmIcon() {
    ImageViewWithXml(modifier = Modifier.size(32.dp), resId = R.drawable.ic_alarm)
}

@Composable
fun FeedbackIcon() {
    ImageViewWithXml(modifier = Modifier.size(32.dp), resId = R.drawable.ic_feedback)
}

@Composable
fun CommunityIcon() {
    ImageViewWithXml(modifier = Modifier.size(32.dp), resId = R.drawable.ic_community)
}

@Composable
fun TermsIcon(){
    ImageViewWithXml(modifier = Modifier.size(32.dp), resId = R.drawable.ic_terms)
}

@Composable
fun PlusIcon() {
    Column(
        modifier = Modifier
            .size(32.dp)
            .background(color = ZzanZColorPalette.current.Gray02, shape = CircleShape),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ImageViewWithXml(
            modifier = Modifier.size(20.dp),
            resId = R.drawable.icon_plus
        )
    }
}

@Composable
fun CategoryIcon(
    modifier: Modifier = Modifier,
    resId: Int
) {
    ImageViewWithXml(
        modifier = modifier,
        resId = resId
    )
}