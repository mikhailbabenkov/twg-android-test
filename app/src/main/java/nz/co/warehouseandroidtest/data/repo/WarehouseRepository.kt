package nz.co.warehouseandroidtest.data.repo

import nz.co.warehouseandroidtest.data.datasource.WarehouseLocalDataSource
import nz.co.warehouseandroidtest.data.datasource.WarehouseRemoteDataSource
import nz.co.warehouseandroidtest.domain.data.Result

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
}