package nz.co.warehouseandroidtest

import android.app.Activity
import android.app.Application
import com.uuzuche.lib_zxing.activity.ZXingLibrary
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import nz.co.warehouseandroidtest.di.AppInjector
import javax.inject.Inject

class WarehouseTestApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
        ZXingLibrary.initDisplayOpinion(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }
}
