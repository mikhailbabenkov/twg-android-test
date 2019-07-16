package nz.co.warehouseandroidtest.domain.data

data class Pagination(
        var start: Int = 0,
        val count: Int = 20,
        var totalCount: Int = 0,
        var hasMore: Boolean = true
) {

    fun invalidate() {
        start = 0
        hasMore = true
    }
}