package nz.co.warehouseandroidtest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nz.co.warehouseandroidtest.domain.usecase.UpdateUserUseCase
import nz.co.warehouseandroidtest.domain.utils.CoroutinesDispatcherProvider
import nz.co.warehouseandroidtest.domain.utils.PermissionHelper
import nz.co.warehouseandroidtest.domain.data.Result
import javax.inject.Inject

class MainViewModel @Inject constructor(
        private val updateUser: UpdateUserUseCase,
        private val permissionHelper: PermissionHelper,
        private val dispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {
    private val parentJob = Job()
    private val scope = CoroutineScope(dispatcherProvider.main + parentJob)

    val showPermissionActivity: LiveData<Boolean>
        get() = _showPermissionActivity
    private val _showPermissionActivity = MutableLiveData<Boolean>()

    val showError: LiveData<Boolean>
        get() = _showError
    private val _showError = MutableLiveData<Boolean>()

    init {
        scope.launch(dispatcherProvider.io) {
            val result = updateUser.invoke()
            withContext(dispatcherProvider.main) { handleResult(result) }
        }
    }

    private fun handleResult(result: Result<Boolean>) {
        if(result is Result.Success) {
            _showError.value = !result.data
        }
    }

    fun checkPermissions(vararg permissions: String) {
        if(permissionHelper.isLackOfPermissions(*permissions)) {
            _showPermissionActivity.value = true
        }
    }

    override fun onCleared() {
        parentJob.cancel()
        super.onCleared()
    }
}