package nz.co.warehouseandroidtest.data.service

import android.content.Context

class WarehousePrefServiceImpl(context: Context) : WarehousePrefService {

    private val sharedPref by lazy { context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE) }

    override suspend fun getUserId(): String? {
        return sharedPref.getString(PREF_USER_ID, null)
    }

    override suspend fun putUserId(id: String) {
        sharedPref.edit().putString(PREF_USER_ID, id).apply()
    }

    companion object {
        private const val PREF_USER_ID = "userId"
        private const val PREF_NAME = "warehouse-pref-name"
    }

}