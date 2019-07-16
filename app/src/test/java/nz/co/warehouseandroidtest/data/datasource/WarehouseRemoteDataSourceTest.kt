package nz.co.warehouseandroidtest.data.datasource

import kotlinx.coroutines.runBlocking
import nz.co.warehouseandroidtest.FakeResponse
import nz.co.warehouseandroidtest.MockApiConfig
import nz.co.warehouseandroidtest.MockServerHelper
import nz.co.warehouseandroidtest.ServiceHelper.getRetrofitService
import nz.co.warehouseandroidtest.data.service.WarehouseService
import nz.co.warehouseandroidtest.domain.data.Pagination
import nz.co.warehouseandroidtest.domain.data.Result
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import retrofit2.HttpException

class WarehouseRemoteDataSourceTest {

    private val config = MockApiConfig()

    private val service = getRetrofitService(WarehouseService::class.java, config)
    private val dataSource = WarehouseRemoteDataSource(service)

    @Test
    fun testGetUserSuccess()  = runBlocking{
        MockServerHelper.mockAPIResponse(FakeResponse.NewUser)
        val result = dataSource.getNewUserId()
        assert(result is Result.Success)
        assert((result as Result.Success).data == "A350DC24-B5DB-410C-BA8E-37310F269C95")
    }

    @Test
    fun testGetUserFailed()  = runBlocking{
        MockServerHelper.mockAPIResponse(FakeResponse.Empty, 500)
        val result = dataSource.getNewUserId()
        assert((result as Result.Error).exception is  HttpException)
    }

    @Test
    fun testSearchSuccess()  = runBlocking{
        MockServerHelper.mockAPIResponse(FakeResponse.Search)
        val result = dataSource.searchProducts("any", "user", Pagination())
        assert(result is Result.Success)
        assert((result as Result.Success).data.get(1).Barcode == "9400988273815")
    }

    @Test
    fun testSearchFailed()  = runBlocking{
        MockServerHelper.mockAPIResponse(FakeResponse.Empty, 400)
        val result = dataSource.searchProducts("any", "user", Pagination())
        assert(result is Result.Error)
        assert(((result as Result.Error).exception as HttpException).code() == 400)
    }

    @Test
    fun testGetPriceSuccess()  = runBlocking{
        MockServerHelper.mockAPIResponse(FakeResponse.Price)
        val result = dataSource.getProductDetails("bar", "user")
        assert(result is Result.Success)
        assert((result as Result.Success).data.Product.BranchPrice == "349")
    }

    @Test
    fun testGetPriceFailed()  = runBlocking{
        MockServerHelper.mockAPIResponse(FakeResponse.Empty, 400)
        val result = dataSource.getProductDetails("bar", "user")
        assert(result is Result.Error)
        assert(((result as Result.Error).exception as HttpException).code() == 400)
    }

    companion object {
        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            MockServerHelper.start()
        }
        @AfterAll
        @JvmStatic
        fun afterAll() {
            MockServerHelper.stop()
        }
    }
}