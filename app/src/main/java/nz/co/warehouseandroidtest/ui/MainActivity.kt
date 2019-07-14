package nz.co.warehouseandroidtest.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import nz.co.warehouseandroidtest.R
import nz.co.warehouseandroidtest.domain.utils.viewModelProvider
import nz.co.warehouseandroidtest.ui.barscan.BarScanActivity
import nz.co.warehouseandroidtest.ui.permissions.PermissionActivity
import nz.co.warehouseandroidtest.ui.search.SearchActivity

class MainActivity : BaseActivity() {

    val REQUEST_PERMISSION_CODE = 0

    private var tvScan: TextView? = null
    private var tvSearch: TextView? = null

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = viewModelProvider(viewModelFactory)
        observeData()
        bindUi()
    }

    private fun observeData() {
        viewModel.showError.observe(this, Observer {
            if(it) {
                Toast.makeText(this@MainActivity, "Get User failed!", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.showPermissionActivity.observe(this, Observer {
            if(it) {
                PermissionActivity.startActivityForResult(this, REQUEST_PERMISSION_CODE, PERMISSIONS)
            }
        })
    }

    private fun bindUi() {
        tvScan = findViewById<TextView>(R.id.tv_scan_barcode) as TextView
        tvSearch = findViewById<View>(R.id.tv_search) as TextView

        tvScan!!.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@MainActivity, BarScanActivity::class.java)
            startActivity(intent)
        }

        tvSearch!!.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@MainActivity, SearchActivity::class.java)
            startActivity(intent)
        }
    }


    public override fun onResume() {
        super.onResume()
        viewModel.checkPermissions(*PERMISSIONS)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Activity.RESULT_OK != resultCode) {
            return
        }
        if (requestCode == REQUEST_PERMISSION_CODE && resultCode == PermissionActivity.PERMISSION_DENIED) {
            finish()
        }
    }

    companion object{
        private val PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}
