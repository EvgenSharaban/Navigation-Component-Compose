package com.example.navigationcomponenttest.ui.screens.edit

import androidx.lifecycle.ViewModel
import com.example.navigationcomponenttest.model.ItemsRepository
import com.example.navigationcomponenttest.ui.screens.action.ActionViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = EditItemViewModel.Factory::class)
class EditItemViewModel @AssistedInject constructor(
    @Assisted private val index: Int,
    private val itemsRepository: ItemsRepository,
) : ViewModel(), ActionViewModel.Delegate<EditItemViewModel.ScreenState, String> {

    override suspend fun loadState(): ScreenState {
        return ScreenState(loadedItem = itemsRepository.getByIndex(index))
    }

    override fun showProgress(input: ScreenState): ScreenState {
        return input.copy(isEditInProgress = true)
    }

    override suspend fun execute(action: String) {
        itemsRepository.update(index, action)
    }

    data class ScreenState(
        val loadedItem: String,
        val isEditInProgress: Boolean = false,
    )

    @AssistedFactory
    interface Factory {
        fun create(index: Int): EditItemViewModel
    }
}