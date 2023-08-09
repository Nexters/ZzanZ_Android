package com.example.zzanz_android.presentation.viewmodel

import androidx.paging.PagingData
import com.example.zzanz_android.domain.model.PlanModel
import com.example.zzanz_android.domain.model.SpendingModel
import com.example.zzanz_android.domain.usecase.category.GetSpendingListByCategoryUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryViewModel @Inject constructor(
    private val getSpendingListUseCase: GetSpendingListByCategoryUseCase
): BaseViewModel<CategoryEvent, CategoryState, CategoryEffect>() {
    override fun createInitialState(): CategoryState {
        // 넘겨 받은 planId argument
        // plan 정보 받아오는 api는 별도로 받을 수 없을까 ?
        // paging이랑 같이 처리하기 너무너무 번거롭다 ㅜ ㅜ
        // 대안으로 아예 홈에서 PlanModel을 넘겨받는 방법 ?
        // Parcelable argument 알아보기 + 데이터 동기화 문제는 괜찮을까 ?
        TODO("Not yet implemented")
    }

    override fun handleEvent(event: CategoryEvent) {
        TODO("Not yet implemented")
    }
}

sealed class CategoryEvent : UiEvent {

}

sealed class CategoryEffect : UiEffect {

}

data class CategoryState(
    val planInfo: PlanModel,
    val spendingList: SpendingListState
) : UiState

sealed class SpendingListState {
    object Loading: SpendingListState()
    object Error: SpendingListState()
    data class Success(val data: Flow<PagingData<SpendingModel>>): SpendingListState()
}