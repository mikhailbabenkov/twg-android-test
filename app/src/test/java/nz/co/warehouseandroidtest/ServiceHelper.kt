package nz.co.warehouseandroidtest

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.google.gson.GsonBuilder
import nz.co.warehouseandroidtest.domain.utils.ApiConfig

object ServiceHelper {

    private val gson = GsonBuilder().create()

    private fun buildOkHttpClient(config: ApiConfig): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.SECONDS)
                .readTimeout(1, TimeUnit.SECONDS)
                .writeTimeout(1, TimeUnit.SECONDS)
                .build()
    }

    fun <T> getRetrofitService(clazz: Class<T>, config: ApiConfig): T = Retrofit.Builder()
            .baseUrl(config.apiUrl).client(
                    buildOkHttpClient(config)
            )
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(clazz)
}