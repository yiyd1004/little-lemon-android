package com.study.littlelemon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.study.littlelemon.data.Repository
import com.study.littlelemon.database.MenuEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val menuList: List<MenuEntity> = emptyList(),
    val menuUiList: List<MenuEntity> = emptyList(),
    val categoryList: List<String> = emptyList(),
    val selectedCategories: List<String> = emptyList(),
    val searchTerm: String = "",
)

class HomeViewModel(
    private val repository: Repository
): ViewModel() {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getMenuList();
    }

    private fun getMenuList() {
        viewModelScope.launch {
            val menu: List<MenuEntity> = repository.getMenu()

            _uiState.update {
                it.copy(
                    menuList = menu,
                    menuUiList = menu
                )
            }

            getCategoryList(menu)
        }
    }

    private fun getCategoryList(list: List<MenuEntity>) {
        val categoryList = list.map {
            it.category
        }.distinct()

        _uiState.update {
            it.copy(categoryList = categoryList)
        }
    }

    fun handleSearch(input: String) {
        _uiState.update {
            it.copy(searchTerm = input)
        }
        updateUiMenuList()
    }

    fun handleFilter(filter: String) {
        val selectedFilter = _uiState.value.selectedCategories.toMutableList();
        toggleFilter(selectedFilter, filter)

        val newFilter = selectedFilter.toList()
        _uiState.update {
            it.copy(selectedCategories = newFilter)
        }
        updateUiMenuList()
    }

    private fun toggleFilter(
        selectedFilters: MutableList<String>,
        filter: String
    ) {
        val wasSelected = selectedFilters.contains(filter)
        if (wasSelected) {
            selectedFilters.remove(filter)
        } else {
            selectedFilters.add(filter)
        }
    }

    private fun updateUiMenuList() {
        with(_uiState.value) {
            val query = searchTerm.filterNot { it.isWhitespace() }
            val filteredList = menuList.filter { menu ->
                (searchTerm.isBlank() || menu.title.contains(query, ignoreCase = true)) &&
                        (selectedCategories.isEmpty() || selectedCategories.contains(menu.category))
            }

            _uiState.update {
                it.copy(menuUiList = filteredList)
            }
        }
    }
}