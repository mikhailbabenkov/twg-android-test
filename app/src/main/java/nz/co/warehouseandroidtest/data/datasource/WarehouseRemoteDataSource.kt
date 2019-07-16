package nz.co.warehouseandroidtest.data.datasource

import nz.co.warehouseandroidtest.data.ProductDetail
import nz.co.warehouseandroidtest.data.ProductWithoutPrice
import nz.co.warehouseandroidtest.data.SearchResult
import nz.co.warehouseandroidtest.domain.data.Result
import nz.co.warehouseandroidtest.data.service.WarehouseService
import nz.co.warehouseandroidtest.domain.data.Pagination

class WarehouseRemoteDataSource(private val service: WarehouseService) {

    suspend fun getNewUserId(): Result<String> {
        return try {
            Result.Success(service.getNewUserId().UserID)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getProductDetails(barcode: String, userId: String): Result<ProductDetail> {
        return try {
            Result.Success(service.getProductDetails(MACHINE_ID, BRANCH_ID, barcode, userId))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun searchProducts(query: String, userId: String, pagination: Pagination): Result<List<ProductWithoutPrice>> {
        return try {
            val searchResult = service.getSearchResult(query, userId, BRANCH_ID, pagination.start, pagination.count)
            Result.Success(parseSearchResult(searchResult, pagination))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private fun parseSearchResult(searchResult: SearchResult, pagination: Pagination): List<ProductWithoutPrice> {
        return if (searchResult.Found == "Y") {
            pagination.totalCount = searchResult.HitCount.toInt()
            searchResult.Results.map { it.Products[0] }
        } else {
            pagination.totalCount = 0
            return listOf()
        }
    }

    companion object {
        private const val BRANCH_ID = "208"
        private const val MACHINE_ID = "1234567890"
    }
}