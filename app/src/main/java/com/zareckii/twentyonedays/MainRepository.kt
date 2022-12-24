package com.zareckii.twentyonedays

interface MainRepository {

    fun days(): Int

    fun reset()

    class Base(
        private val cacheDateSource: CacheDataSource,
        private val now: Now
    ) : MainRepository {

        override fun days(): Int {
            val saved = cacheDateSource.time(-1)
            return if (saved == -1L) {
                reset()
                0
            } else {
                val diff = now.time() - saved
                (diff / DAY_MILLIS).toInt()
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
