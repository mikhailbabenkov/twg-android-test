package nz.co.warehouseandroidtest.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import nz.co.warehouseandroidtest.data.service.WarehousePrefService
import nz.co.warehouseandroidtest.data.service.WarehousePrefServiceImpl
import nz.co.warehouseandroidtest.domain.utils.*
import javax.inject.Singleton

@Module
class PlatformSpecificModule {
    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }
    @Singleton
    @Provides
    fun provideApiConfig(): ApiConfig {
        return WarehouseApiConfig()
    }

    @Singleton
    @Provides
    fun providePermissionHelper(context: Context): PermissionHelper {
        return PermissionHelperImpl(context)
    }

    @Singleton
    @Provides
    fun provideWarehousePrefService(context: Context): WarehousePrefService {
        return WarehousePrefServiceImpl(context)
    }

    @Provides
    fun provideCoroutinesDispatcherProvider() = CoroutinesDispatcherProvider(
            Dispatchers.Main,
            Dispatchers.Default,
            Dispatchers.IO
    )
}