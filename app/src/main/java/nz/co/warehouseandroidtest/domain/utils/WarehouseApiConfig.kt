package nz.co.warehouseandroidtest.domain.utils

class WarehouseApiConfig: ApiConfig {
    override val apiUrl: String
        get() = "https://twg.azure-api.net/"
    override val subscriptionKey: String
        get() = "73bdaf91a43d45e7a1a45ac043c4a16b"//subscription code here
}