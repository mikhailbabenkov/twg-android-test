package nz.co.warehouseandroidtest.di

import nz.co.warehouseandroidtest.WarehouseTestApp

object AppInjector {
    fun init(application: WarehouseTestApp) {
        DaggerAppComponent.builder().application(application)
            .build().inject(application)
    }
}
