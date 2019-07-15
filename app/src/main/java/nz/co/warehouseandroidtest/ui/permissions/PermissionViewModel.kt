package nz.co.warehouseandroidtest.ui.permissions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import nz.co.warehouseandroidtest.domain.utils.CoroutinesDispatcherProvider
import nz.co.warehouseandroidtest.domain.utils.PermissionHelper
import javax.inject.Inject

class PermissionViewModel @Inject constructor(
        private val permissionHelper: PermissionHelper,
        private val dispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

    private val parentJob = Job()
    private val scope = CoroutineScope(dispatcherProvider.main + parentJob)
    lateinit var permissions: Array<String>
    private var requireToCheckPermissions = true

    val shouldCheckPermissions: LiveData<Boolean>
        get() = _shouldCheckPermissions
    private val _shouldCheckPermissions = MutableLiveData<Boolean>()

    val isAllPermissionsGranted: LiveData<Boolean>
        get() = _isAllPermissionsGranted
    private val _isAllPermissionsGranted = MutableLiveData<Boolean>()

    override fun onCleared() {
        parentJob.cancel()
        super.onCleared()
    }

    fun checkForMissingPermissions() {
        if (requireToCheckPermissions) {
            _shouldCheckPermissions.value = permissionHelper.isLackOfPermissions(*permissions)
        } else {
            requireToCheckPermissions = true
        }
    }

    fun checkPermissionResults(results: IntArray) {
        val hasAllPermissionsGranted = permissionHelper.hasAllPermissionsGranted(results)
        requireToCheckPermissions = hasAllPermissionsGranted
        _isAllPermissionsGranted.value = hasAllPermissionsGranted
    }
}