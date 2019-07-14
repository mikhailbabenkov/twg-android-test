package nz.co.warehouseandroidtest

import android.app.Activity
import android.app.Application

import com.google.gson.GsonBuilder
import com.uuzuche.lib_zxing.activity.ZXingLibrary

import java.io.IOException

import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import nz.co.warehouseandroidtest.data.WarehouseService
import nz.co.warehouseandroidtest.di.AppInjector
import nz.co.warehouseandroidtest.domain.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class WarehouseTestApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    var warehouseService: WarehouseService? = null
        private set

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
        val builder = OkHttpClient.Builder()
        builder.addInterceptor { chain ->
            val request = chain.request().newBuilder().addHeader("Ocp-Apim-Subscription-Key", Constants.SUBSCRIPTION_KEY).build()
            chain.proceed(request)
        }

        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.HTTP_URL_ENDPOINT)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
        warehouseService = retrofit.create(WarehouseService::class.java)

        ZXingLibrary.initDisplayOpinion(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }
}
