package com.study.littlelemon.network

import com.study.littlelemon.data.Menu
import com.study.littlelemon.data.MenuItem
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface MenuService {
    suspend fun getMenu(): List<MenuItem>
}

class MenuServiceImpl(
    private val httpClient: HttpClient
): MenuService {

    private val URL = "https://raw.githubusercontent.com/yiyd1004/little-lemon-android/main/api/menu.json"

    override suspend fun getMenu(): List<MenuItem> {
        return httpClient.get(urlString = URL).body<Menu>().menu
    }
}