package nz.co.warehouseandroidtest.data.service

interface WarehousePrefService {
    suspend fun getUserId(): String?
    suspend fun putUserId(id: String)
}