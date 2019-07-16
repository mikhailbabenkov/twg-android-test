package nz.co.warehouseandroidtest.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nz.co.warehouseandroidtest.data.ProductWithoutPrice
import nz.co.warehouseandroidtest.domain.data.Pagination
import nz.co.warehouseandroidtest.domain.data.Result
import nz.co.warehouseandroidtest.domain.usecase.GetProductsUseCase
import nz.co.warehouseandroidtest.domain.utils.CoroutinesDispatcherProvider
import java.lang.Exception
import javax.inject.Inject

class SearchResultViewModel @Inject constructor(
        private val getProducts: GetProductsUseCase,
        private val dispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

    private val parentJob = Job()
    private val scope = CoroutineScope(dispatcherProvider.main + parentJob)
    private lateinit var query: String
    private val pagination = Pagination()

    val uiModel: LiveData<SearchResultUiModel>
        get() = _uiModel
    private val _uiModel = MutableLiveData<SearchResultUiModel>()

    override fun onCleared() {
        parentJob.cancel()
        super.onCleared()
    }

    fun initLoad(query: String) {
        this@SearchResultViewModel.query = query
        loadProducts(false)
    }

    fun loadMore() {
        loadProducts(false)
    }

    private fun loadProducts(shouldInvalidate: Boolean) = scope.launch(dispatcherProvider.io) {
        withContext(dispatcherProvider.main) {
            _uiModel.value = SearchResultUiModel(pagination.hasMore, shouldInvalidate)
        }
        val result = getProducts.invoke(query, pagination)
        withContext(dispatcherProvider.main) {
            handleResult(result)
        }
    }

    fun onPullToRefresh() {
        pagination.invalidate()
        loadProducts(true)
    }

    private fun handleResult(result: Result<List<ProductWithoutPrice>>) {
        when(result) {
            is Result.Success-> {
                _uiModel.value = SearchResultUiModel(pagination.hasMore, false, result.data)
            }
            is Result.Error-> _uiModel.value = SearchResultUiModel(null, false,null, result.exception)
        }
    }

    data class SearchResultUiModel(
            val hasMore: Boolean? = null,
            val shouldRefreshAll: Boolean = false,
            val data: List<ProductWithoutPrice>? = null,
            val error: Exception? = null
    )
}