import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import entity.ListEntity
import entity.HomeTabsEntity
import entity.HomeUiState
import entity.PagerData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch





class HomeListViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()


    fun getHomePageData(index:Int){
        viewModelScope.launch {
            val list = mutableListOf<ListEntity>()
            (0..20).forEach {
                list.add(
                    ListEntity(
                        title = "PAGE $index TITLE $it",
                        content = "THIS IS A COMPOSE MULTIPLATFORM UI $it"
                    )
                )
            }
            val pagerData = PagerData(index = index, list)
            val homeDataList = _uiState.value.homeDataList.toMutableList()
            homeDataList.add(index, pagerData)
            _uiState.update {
                it.copy(homeDataList = homeDataList)
            }
        }
    }

    fun changeSelectedStatus(pageIndex:Int,index: Int) {
        val homeDataList = _uiState.value.homeDataList.toMutableList()
        val pagerData = homeDataList[pageIndex].list.toMutableList()

        if (index in pagerData.indices) {
            val updatedItem = pagerData[index].copy(selected = !pagerData[index].selected)
            pagerData[index] = updatedItem
        }
        homeDataList[pageIndex] = PagerData(index = pageIndex, pagerData)
        _uiState.update {
            it.copy(homeDataList = homeDataList)
        }
    }

    fun initHomeTabList() {
        viewModelScope.launch {
            val list = mutableListOf<HomeTabsEntity>()
            val homeData = mutableListOf<PagerData>()
            (0..9).forEachIndexed { index, i ->
                list.add(HomeTabsEntity("TITLE${i}"))
                homeData.add(PagerData(index = index, listOf()))
            }
            list[0] = list[0].copy(selected = true)
            _uiState.update {
                it.copy(homeTabs = list, homeDataList = homeData)
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