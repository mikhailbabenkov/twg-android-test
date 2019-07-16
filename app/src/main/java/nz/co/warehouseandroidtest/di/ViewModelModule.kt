package nz.co.warehouseandroidtest.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import nz.co.warehouseandroidtest.domain.utils.TwgViewModelFactory
import nz.co.warehouseandroidtest.ui.MainViewModel
import nz.co.warehouseandroidtest.ui.permissions.PermissionViewModel
import nz.co.warehouseandroidtest.ui.product.ProductDetailViewModel
import nz.co.warehouseandroidtest.ui.search.SearchResultViewModel
import javax.inject.Singleton

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchResultViewModel::class)
    abstract fun bindSearchResultViewModel(viewModel: SearchResultViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PermissionViewModel::class)
    abstract fun bindPermissionViewModel(viewModel: PermissionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductDetailViewModel::class)
    abstract fun bindProductDetailsViewModel(viewModel: ProductDetailViewModel): ViewModel

    @Binds
    @Singleton
    abstract fun bindViewModelFactory(factory: TwgViewModelFactory): ViewModelProvider.Factory
}
