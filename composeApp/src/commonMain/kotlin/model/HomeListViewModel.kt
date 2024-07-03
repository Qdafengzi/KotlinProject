import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.input.key.Key.Companion.P
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import entity.HomeListEntity
import entity.HomeTabsEntity
import entity.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch





class HomeListViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun initData() {
        viewModelScope.launch {
            val homeList = mutableListOf<HomeListEntity>()
            (0..500).forEach {
                homeList.add(
                    HomeListEntity(
                        title = "TITLE $it",
                        content = "THIS IS A COMPOSE MULTIPLATFORM UI $it"
                    )
                )
            }
            _uiState.update {
                it.copy(homeList = homeList)
            }
        }
    }

    fun changeSelectedStatus(index: Int) {
        val currentList = _uiState.value.homeList.toMutableList()
        if (index in currentList.indices) {
            val updatedItem = currentList[index].copy(selected = !currentList[index].selected)
            currentList[index] = updatedItem

        }
        _uiState.update {
            it.copy(homeList = currentList)
        }
    }

    fun initHomeTabList() {
        viewModelScope.launch {
            val list = mutableListOf<HomeTabsEntity>()
            (0..9).forEachIndexed { _, i ->
                list.add(HomeTabsEntity("TITLE${i}"))
            }
            list[0] = list[0].copy(selected = true)
            _uiState.update {
                it.copy(homeTabs = list)
            }
        }
    }

    fun updateTabClick(index: Int) {
        val newItem = _uiState.value.homeTabs[index].copy(selected = !_uiState.value.homeTabs[index].selected)
        val newList = _uiState.value.homeTabs.toMutableList()
        newList.forEachIndexed { i, homeTabsEntity ->
            if (i == index) {
                newList[i] = homeTabsEntity.copy(selected = !homeTabsEntity.selected)
            } else {
                newList[i] = homeTabsEntity.copy(selected = false)
            }
        }
        newList[index] = newItem
        _uiState.update {
            it.copy(homeTabs = newList)
        }
    }
}