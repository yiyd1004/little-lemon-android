package com.study.littlelemon.network

import com.study.littlelemon.data.MenuItem


interface NetworkDataManager {
    suspend fun getMenu(): List<MenuItem>
}

class NetworkDataManagerImpl(
    private val service: MenuService
): NetworkDataManager {
    override suspend fun getMenu(): List<MenuItem> {
        return service.getMenu()
    }
}