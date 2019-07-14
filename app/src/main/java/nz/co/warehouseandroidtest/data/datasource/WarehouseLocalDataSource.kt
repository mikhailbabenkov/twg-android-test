package nz.co.warehouseandroidtest.data.datasource

import nz.co.warehouseandroidtest.data.service.WarehousePrefService

class WarehouseLocalDataSource(private val service: WarehousePrefService) {
    suspend fun getUserId(): String? {
        return service.getUserId()
    }

    suspend fun putUserId(userId: String) {
        return service.putUserId(userId)
    }
}