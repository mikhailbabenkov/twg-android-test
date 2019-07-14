package nz.co.warehouseandroidtest.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import nz.co.warehouseandroidtest.data.datasource.WarehouseLocalDataSource
import nz.co.warehouseandroidtest.data.datasource.WarehouseRemoteDataSource
import nz.co.warehouseandroidtest.data.repo.WarehouseRepository
import nz.co.warehouseandroidtest.data.service.WarehousePrefService
import nz.co.warehouseandroidtest.data.service.WarehouseService
import nz.co.warehouseandroidtest.domain.usecase.UpdateUserUseCase
import nz.co.warehouseandroidtest.domain.utils.ApiConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, PlatformSpecificModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideInterceptor(apiConfig: ApiConfig): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
                    .addHeader("Ocp-Apim-Subscription-Key", apiConfig.subscriptionKey).build()
            chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun provideOkHttp(interceptor: Interceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideWarehouseService(
            apiConfig: ApiConfig,
            okHttpClient: OkHttpClient
    ): WarehouseService {
        return Retrofit.Builder()
                .baseUrl(apiConfig.apiUrl)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .client(okHttpClient)
                .build()
                .create(WarehouseService::class.java)
    }

    @Provides
    @Singleton
    fun provideWarehouseLocalDataSource(
            service: WarehousePrefService
    ): WarehouseLocalDataSource {
        return WarehouseLocalDataSource(service)
    }

    @Provides
    @Singleton
    fun provideWarehouseRemoteDataSource(
            service: WarehouseService
    ): WarehouseRemoteDataSource {
        return WarehouseRemoteDataSource(service)
    }

    @Provides
    @Singleton
    fun provideWarehouseRepository(
            localSource: WarehouseLocalDataSource,
            remoteSource: WarehouseRemoteDataSource
    ): WarehouseRepository {
        return WarehouseRepository(remoteSource, localSource)
    }

    @Provides
    @Singleton
    fun provideUpdateUserUseCase(
            repo: WarehouseRepository
    ): UpdateUserUseCase {
        return UpdateUserUseCase(repo)
    }
}