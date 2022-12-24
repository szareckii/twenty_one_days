package com.zareckii.twentyonedays

interface Now {
    fun time(): Long

    class Base() : Now {
        override fun time() = System.currentTimeMillis()
    }
}