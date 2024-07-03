package entity

data class HomeUiState(
    val homeList: List<HomeListEntity> = listOf(),
    val homeTabs: List<HomeTabsEntity> = listOf()
)
