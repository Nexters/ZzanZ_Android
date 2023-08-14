package com.example.zzanz_android.presentation.view.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zzanz_android.R
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.presentation.view.setting.BudgetCategory
import com.example.zzanz_android.presentation.viewmodel.BudgetViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryBottomSheet(
    budgetViewModel: BudgetViewModel = hiltViewModel(),
    coroutineScope: CoroutineScope,
    sheetState: SheetState,
) {
    if (sheetState.isVisible) {
        ModalBottomSheet(modifier = Modifier.wrapContentHeight(),
            sheetState = sheetState,
            containerColor = ZzanZColorPalette.current.White,
            scrimColor = Color(0x66000000),
            onDismissRequest = {
                coroutineScope.launch {
                    sheetState.hide()
                }
            }) {
            BudgetCategory(
                budgetViewModel = budgetViewModel,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 8.dp),
                categoryModifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp),
                titleText = stringResource(R.string.add_category_explain_title)
            )
            Spacer(modifier = Modifier.height(8.dp))
            GreenRoundButton(
                modifier = Modifier.padding(horizontal = 24.dp),
                text = stringResource(id = R.string.complete),
                onClick = {
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                },
                enabled = true
            )
            Spacer(modifier = Modifier.height(56.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun BottomSheetPreview() {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    CategoryBottomSheet(
        coroutineScope = coroutineScope,
        sheetState = sheetState
    )
}