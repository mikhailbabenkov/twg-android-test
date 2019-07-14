package nz.co.warehouseandroidtest.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import nz.co.warehouseandroidtest.domain.utils.TwgViewModelFactory
import javax.inject.Singleton

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @Singleton
    abstract fun bindViewModelFactory(factory: TwgViewModelFactory): ViewModelProvider.Factory
}
