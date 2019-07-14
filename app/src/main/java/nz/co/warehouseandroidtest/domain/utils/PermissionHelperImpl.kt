package nz.co.warehouseandroidtest.domain.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class PermissionHelperImpl(private val context: Context) : PermissionHelper {
    override fun isLackOfPermissions(vararg permissions: String): Boolean {
        var i = 0
        while (i < permissions.size) {
            if (ifLackPermission(permissions[i])) {
                return true
            }
            i++
        }
        return false
    }

    private fun ifLackPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED
    }
}