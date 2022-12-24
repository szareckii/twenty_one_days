package com.zareckii.twentyonedays

import android.content.SharedPreferences

interface MainRepository {

    fun days(): Int

    fun reset()

    class Base(
        private val cacheDateSource: CacheDataSource,
        private val now: Now
    ) : MainRepository {

        override fun days(): Int {
            val saved = cacheDateSource.time(-1)
            if (saved == -1L) {
                reset()
                return 0
            } else {
                val diff = now.time() - saved
                return (diff / DAY_MILLIS).toInt()
            }
        }

        override fun reset() {
            cacheDateSource.save(now.time())
        }

        companion object {
            private const val DAY_MILLIS = 24 * 3600 * 1000
        }

    }
}

interface CacheDataSource {

    fun save(time: Long)
    fun time(default: Long): Long

    class Base(private val sharedPreferences: SharedPreferences) : CacheDataSource {

        override fun save(time: Long) =
            sharedPreferences.edit().putLong(KEY, time).apply()

        override fun time(default: Long): Long =
            sharedPreferences.getLong(KEY, default)

        companion object {
            private const val KEY = "savedTimeKey"
        }
    }
}

interface Now {
    fun time(): Long

    class Base() : Now {
        override fun time() = System.currentTimeMillis()
    }
}