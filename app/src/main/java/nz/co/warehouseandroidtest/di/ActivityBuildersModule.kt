package nz.co.warehouseandroidtest.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import nz.co.warehouseandroidtest.ui.MainActivity
import nz.co.warehouseandroidtest.ui.permissions.PermissionActivity
import nz.co.warehouseandroidtest.ui.product.ProductDetailActivity
import nz.co.warehouseandroidtest.ui.search.SearchActivity

@Suppress("unused")
@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
    @ContributesAndroidInjector
    abstract fun contributePermissionActivity(): PermissionActivity
    @ContributesAndroidInjector
    abstract fun contributeStartupActivity(): SearchActivity
    @ContributesAndroidInjector
    abstract fun contributePdfActivity(): ProductDetailActivity
}