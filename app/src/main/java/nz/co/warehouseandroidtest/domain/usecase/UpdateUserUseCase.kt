package nz.co.warehouseandroidtest.domain.usecase

import nz.co.warehouseandroidtest.data.repo.WarehouseRepository
import nz.co.warehouseandroidtest.domain.data.Result

class UpdateUserUseCase(private val repo: WarehouseRepository) {
    suspend operator fun invoke(): Result<Boolean> {
        return when(repo.getUserId(true)) {
            is Result.Success -> Result.Success(true)
            is Result.Error -> Result.Success(false)
        }
    }
}