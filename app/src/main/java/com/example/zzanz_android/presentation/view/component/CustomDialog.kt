package com.example.zzanz_android.presentation.view.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.zzanz_android.R
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.theme.ZzanZTypo

@Composable
fun PopupSheetDialog(
    onDismiss:() -> Unit,
    onClickChangeAlarm: () -> Unit,
    onClickSendFeedback: () -> Unit,
    onClickJoinCommunity: () -> Unit,
) {
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = { onDismiss() }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 10.dp),
            color = ZzanZColorPalette.current.White,
            shape = RoundedCornerShape(20.dp),

        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DialogTitle(title = stringResource(id = R.string.more_dialog_title))
                DialogComponent(icon = { AlarmIcon() }, title = stringResource(id = R.string.change_noti_time), onClickChangeAlarm)
                DialogComponent(icon = { FeedbackIcon() }, title = stringResource(id = R.string.send_feedback), onClickSendFeedback)
                DialogComponent(icon = { CommunityIcon() }, title = stringResource(id = R.string.join_community), onClickJoinCommunity)
            }
        }
    }
}

@Composable
fun DialogTitle(title: String){
    Text(text = title, style = ZzanZTypo.current.H2, color = ZzanZColorPalette.current.Gray07)
}

@Composable
fun DialogComponent(icon: @Composable () -> Unit, title: String, onClick: () -> Unit){
    Row(Modifier.fillMaxWidth().height(56.dp).clickable { onClick() }, verticalAlignment = Alignment.CenterVertically){
        icon()
        Spacer(Modifier.width(16.dp))
        Text(text = title, style = ZzanZTypo.current.Body01, color = ZzanZColorPalette.current.Gray07)
    }
}

@Preview
@Composable
fun DialogPreview() {
    DialogComponent(icon = { CommunityIcon() }, title = stringResource(id = R.string.join_community)){}
}