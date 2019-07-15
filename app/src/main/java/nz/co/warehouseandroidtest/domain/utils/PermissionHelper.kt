package nz.co.warehouseandroidtest.domain.utils

interface PermissionHelper {
    fun isLackOfPermissions(vararg permissions: String): Boolean
    fun hasAllPermissionsGranted(results: IntArray): Boolean
}