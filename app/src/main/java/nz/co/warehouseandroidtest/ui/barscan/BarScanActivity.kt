package nz.co.warehouseandroidtest.ui.barscan

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.uuzuche.lib_zxing.activity.CaptureFragment
import com.uuzuche.lib_zxing.activity.CodeUtils
import kotlinx.android.synthetic.main.activity_bar_scan.*
import nz.co.warehouseandroidtest.R
import nz.co.warehouseandroidtest.ui.product.ProductDetailActivity

class BarScanActivity : AppCompatActivity() {

    private val captureFragment = CaptureFragment()

    private var isOpen = false

    private val analyzeCallback = object : CodeUtils.AnalyzeCallback {

        override fun onAnalyzeSuccess(mBitmap: Bitmap, result: String) {
            ProductDetailActivity.startActivity(this@BarScanActivity, result)
        }

        override fun onAnalyzeFailed() {
            Toast.makeText(this@BarScanActivity, "Oops, bar code analysis failed!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    public override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)

        setContentView(R.layout.activity_bar_scan)

        iv_flashlight.setImageDrawable(getDrawable(R.mipmap.ic_flash_off_black_24dp))
        iv_flashlight.setOnClickListener { toggleFlashlight() }

        CodeUtils.setFragmentArgs(captureFragment, R.layout.fragment_zxing_scan)

        captureFragment.analyzeCallback = analyzeCallback

        supportFragmentManager.beginTransaction().replace(R.id.fl_zxing_container, captureFragment).commit()
    }

    private fun toggleFlashlight() {
        isOpen = if (!isOpen) {
            CodeUtils.isLightEnable(true)
            iv_flashlight.setImageDrawable(getDrawable(R.mipmap.ic_flash_on_black_24dp))
            true
        } else {
            CodeUtils.isLightEnable(false)
            iv_flashlight.setImageDrawable(getDrawable(R.mipmap.ic_flash_off_black_24dp))
            false
        }
    }

}
