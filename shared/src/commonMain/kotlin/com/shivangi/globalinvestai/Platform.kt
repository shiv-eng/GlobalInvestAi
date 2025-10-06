package com.shivangi.globalinvestai

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform