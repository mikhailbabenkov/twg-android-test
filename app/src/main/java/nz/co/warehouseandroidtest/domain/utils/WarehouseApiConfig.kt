package nz.co.warehouseandroidtest.domain.utils

class WarehouseApiConfig: ApiConfig {
    override val apiUrl: String
        get() = "https://twg.azure-api.net/"
    override val subscriptionKey: String
        get() = ""//subscription code here
}
