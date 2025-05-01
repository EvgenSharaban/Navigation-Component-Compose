package com.example.navigationcomponenttest.ui.screens.add

import androidx.lifecycle.ViewModel
import com.example.navigationcomponenttest.model.ItemsRepository
import com.example.navigationcomponenttest.ui.screens.action.ActionViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val itemsRepository: ItemsRepository,
) : ViewModel(), ActionViewModel.Delegate<AddItemViewModel.ScreenState, String> {

    override suspend fun loadState(): ScreenState {
        return ScreenState()
    }

    override fun showProgress(input: ScreenState): ScreenState {
        return input.copy(isProgressVisible = true)
    }

    override suspend fun execute(action: String) {
        itemsRepository.add(action)
    }

    data class ScreenState(
        val isProgressVisible: Boolean = false,
    )

}