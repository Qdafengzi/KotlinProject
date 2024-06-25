import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import entity.HomeListEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeListViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<List<HomeListEntity>> = MutableStateFlow(emptyList())
    val uiState: StateFlow<List<HomeListEntity>> = _uiState.asStateFlow()

    fun initData() {
        viewModelScope.launch {
            val homeList = mutableListOf<HomeListEntity>()
            (0..500).forEach {
                homeList.add(HomeListEntity(title = "TITLE $it", content = "THIS IS A COMPOSE MULTIPLATFORM UI $it"))
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

}