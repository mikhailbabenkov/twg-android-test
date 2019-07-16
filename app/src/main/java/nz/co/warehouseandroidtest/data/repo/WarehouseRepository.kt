package nz.co.warehouseandroidtest.data.repo

import nz.co.warehouseandroidtest.data.ProductDetail
import nz.co.warehouseandroidtest.data.ProductWithoutPrice
import nz.co.warehouseandroidtest.data.SearchResultItem
import nz.co.warehouseandroidtest.data.datasource.WarehouseLocalDataSource
import nz.co.warehouseandroidtest.data.datasource.WarehouseRemoteDataSource
import nz.co.warehouseandroidtest.domain.data.Pagination
import nz.co.warehouseandroidtest.domain.data.Result
import java.lang.NullPointerException

class WarehouseRepository(private val remoteDataSource: WarehouseRemoteDataSource,
                          private val localDataSource: WarehouseLocalDataSource) {
    suspend fun getUserId(tryCache: Boolean): Result<String> {
        return if(tryCache) {
            localDataSource.getUserId()?.let {
                Result.Success(it)
            } ?: remoteDataSource.getNewUserId()
        } else {
            remoteDataSource.getNewUserId()
        }
    }

    suspend fun search(query: String, pagination: Pagination): Result<List<ProductWithoutPrice>> {
        return getUserId(true).let {
            if(it is Result.Success) {
                remoteDataSource.searchProducts(query, it.data, pagination)
            } else {
                Result.Error(NullPointerException("UserId not found"))
            }
        }
    }

    suspend fun getProductDetails(barcode: String): Result<ProductDetail> {
        return getUserId(true).let {
            if(it is Result.Success) {
                remoteDataSource.getProductDetails(barcode, it.data)
            } else {
                Result.Error(NullPointerException("UserId not found"))
            }
        }
    }
}