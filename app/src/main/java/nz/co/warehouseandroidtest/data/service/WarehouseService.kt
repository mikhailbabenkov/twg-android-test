package nz.co.warehouseandroidtest.data.service

import nz.co.warehouseandroidtest.data.ProductDetail
import nz.co.warehouseandroidtest.data.SearchResult
import nz.co.warehouseandroidtest.data.User
import retrofit2.http.GET
import retrofit2.http.Query

interface WarehouseService {

    @GET("bolt/newuser.json")
    suspend fun getNewUserId(): User

    @GET("bolt/price.json")
    suspend fun getProductDetails(@Query("MachineID") machineId: String,
                                  @Query("Branch") branch: String,
                                  @Query("BarCode") barcode: String,
                                  @Query("UserID") userId: String): ProductDetail

    @GET("bolt/search.json")
    suspend fun getSearchResult(@Query("Search") query: String,
                                @Query("UserID") userId: String,
                                @Query("Branch") branch: String,
                                @Query("Start") start: Int,
                                @Query("Limit") limit: Int): SearchResult
}
