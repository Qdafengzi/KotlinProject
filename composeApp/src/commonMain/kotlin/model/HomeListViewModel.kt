import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.input.key.Key.Companion.P
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import entity.HomeListEntity
import entity.HomeTabsEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class HomeListViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<List<HomeListEntity>> = MutableStateFlow(emptyList())
    val uiState: StateFlow<List<HomeListEntity>> = _uiState.asStateFlow()

    private val _homeTabList: MutableStateFlow<List<HomeTabsEntity>> = MutableStateFlow(emptyList())
    val homeTabList: StateFlow<List<HomeTabsEntity>> = _homeTabList.asStateFlow()


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
                homeList
            }
        }
    }

    fun changeSelectedStatus(index: Int) {
        val currentList = _uiState.value.toMutableList()
        if (index in currentList.indices) {
            val updatedItem = currentList[index].copy(selected = !currentList[index].selected)
            currentList[index] = updatedItem
            _uiState.update {
                currentList
            }
        }
    }

    fun initHomeTabList() {
        viewModelScope.launch {
            val list = mutableListOf<HomeTabsEntity>()
            (0..9).forEachIndexed { _, i ->
                list.add(HomeTabsEntity("TITLE${i}"))
            }
            list[0] = list[0].copy(selected = true)
            _homeTabList.update {
                list
            }
        }
    }

    fun updateTabClick(index: Int) {
        val newItem = _homeTabList.value[index].copy(selected = !_homeTabList.value[index].selected)
        val newList = _homeTabList.value.toMutableList()
        newList.forEachIndexed { i, homeTabsEntity ->
            if (i == index) {
                newList[i] = homeTabsEntity.copy(selected = !homeTabsEntity.selected)
            } else {
                newList[i] = homeTabsEntity.copy(selected = false)
            }
        }
        newList[index] = newItem
        _homeTabList.update {
            newList
        }
    }
}