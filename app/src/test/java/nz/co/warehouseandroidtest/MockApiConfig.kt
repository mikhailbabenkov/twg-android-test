package nz.co.warehouseandroidtest

import nz.co.warehouseandroidtest.domain.utils.ApiConfig

class MockApiConfig: ApiConfig {
    override val apiUrl: String
        get() = "http://localhost:21000"
    override val subscriptionKey: String
        get() = ""
}