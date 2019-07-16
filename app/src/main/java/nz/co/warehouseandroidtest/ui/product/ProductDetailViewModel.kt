package nz.co.warehouseandroidtest.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nz.co.warehouseandroidtest.data.ProductDetail
import nz.co.warehouseandroidtest.domain.data.Result
import nz.co.warehouseandroidtest.domain.usecase.GetProductDetailsUseCase
import nz.co.warehouseandroidtest.domain.utils.CoroutinesDispatcherProvider
import javax.inject.Inject

class ProductDetailViewModel @Inject constructor(
        private val getProductDetails: GetProductDetailsUseCase,
        private val dispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {
    private val parentJob = Job()
    private val scope = CoroutineScope(dispatcherProvider.main + parentJob)

    val uiModel: LiveData<ProductDetailUiModel>
        get() = _uiModel
    private val _uiModel = MutableLiveData<ProductDetailUiModel>()

    override fun onCleared() {
        parentJob.cancel()
        super.onCleared()
    }

    fun initLoad(barcode: String) = scope.launch(dispatcherProvider.io) {
        withContext(dispatcherProvider.main) {
            _uiModel.value = ProductDetailUiModel(true)
        }
        val result = getProductDetails.invoke(barcode)
        withContext(dispatcherProvider.main) {
            handleResult(result)
        }
    }

    private fun handleResult(result: Result<ProductDetail>) {
        when (result) {
            is Result.Success -> _uiModel.value = ProductDetailUiModel(false, result.data)
            is Result.Error -> _uiModel.value = ProductDetailUiModel(false, null, result.exception)
        }
    }

    data class ProductDetailUiModel(
            val isLoading: Boolean,
            val data: ProductDetail? = null,
            val error: Exception? = null
    )
}