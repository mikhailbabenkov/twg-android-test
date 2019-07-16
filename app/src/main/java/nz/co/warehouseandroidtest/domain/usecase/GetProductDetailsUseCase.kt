package nz.co.warehouseandroidtest.domain.usecase

import nz.co.warehouseandroidtest.data.ProductDetail
import nz.co.warehouseandroidtest.data.repo.WarehouseRepository
import nz.co.warehouseandroidtest.domain.data.Result

class GetProductDetailsUseCase(private val repository: WarehouseRepository) {

    suspend operator fun invoke(barcode: String): Result<ProductDetail> {
        return repository.getProductDetails(barcode)
    }
}