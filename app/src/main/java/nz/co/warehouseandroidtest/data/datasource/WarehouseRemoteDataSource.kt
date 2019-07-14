package nz.co.warehouseandroidtest.data.datasource

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

    suspend fun getProductDetails(barcode: String, userId: String): Result<String> {
        return try {
            Result.Success(service.getProductDetails(MACHINE_ID, BRANCH_ID, barcode, userId).UserID)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun searchProducts(query: String, userId: String, pagination: Pagination): Result<SearchResult> {
        return try {
            Result.Success(service.getSearchResult(query, userId, BRANCH_ID, pagination.start, pagination.count))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    companion object {
        private const val BRANCH_ID = "208"
        private const val MACHINE_ID = "1234567890"
    }
}