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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zzanz_android.R
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.theme.ZzanZDimen
import com.example.zzanz_android.common.ui.theme.ZzanZTypo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreBottomSheet(
    scope: CoroutineScope,
    state: SheetState,
    onClickChangeAlarm: () -> Unit,
    onClickSendFeedback: () -> Unit,
    onClickJoinCommunity: () -> Unit
) {
    if (state.isVisible) {
        ModalBottomSheet(modifier = Modifier.wrapContentHeight(),
            sheetState = state,
            containerColor = ZzanZColorPalette.current.White,
            scrimColor = Color(0x66000000),
            onDismissRequest = {
                scope.launch {
                    state.hide()
                }
            }) {
            SheetContent(
                onClickChangeAlarm,
                onClickSendFeedback,
                onClickJoinCommunity
            )
        }
    }
}

@Composable
fun SheetContent(
    onClickChangeAlarm: () -> Unit,
    onClickSendFeedback: () -> Unit,
    onClickJoinCommunity: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
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
            Spacer(modifier = Modifier.height(ZzanZDimen.current.defaultHorizontal))
            Spacer(modifier = Modifier.height(ZzanZDimen.current.defaultHorizontal))
        }
    }
}

@Composable
fun DialogTitle(title: String){
    Text(text = title, style = ZzanZTypo.current.H2, color = ZzanZColorPalette.current.Gray07)
}

@Composable
fun DialogComponent(icon: @Composable () -> Unit, title: String, onClick: () -> Unit){
    Row(
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable { onClick() }, verticalAlignment = Alignment.CenterVertically){
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