package nz.co.warehouseandroidtest.domain.usecase

import nz.co.warehouseandroidtest.data.ProductWithoutPrice
import nz.co.warehouseandroidtest.data.repo.WarehouseRepository
import nz.co.warehouseandroidtest.domain.data.Result
import nz.co.warehouseandroidtest.domain.data.Pagination

class GetProductsUseCase(private val repository: WarehouseRepository) {
    suspend operator fun invoke(
            query: String,
            pagination: Pagination = Pagination()
    ): Result<List<ProductWithoutPrice>> {
        return when (val result = repository.search(query, pagination)) {
            is Result.Success -> {
                if (result.data.isEmpty()) pagination.hasMore = false
                pagination.start += result.data.size
                Result.Success(result.data)
            }
            is Result.Error -> result
        }
    }
}