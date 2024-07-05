package entity

data class HomeUiState(
    val homeDataList: List<PagerData> = listOf(),
    val homeTabs: List<HomeTabsEntity> = listOf(),
    val currentPage: Int = 0,
)

data class PagerData(
    val index: Int = 0,
    val list: List<ListEntity> = listOf(),
)
