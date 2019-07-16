package nz.co.warehouseandroidtest.ui.product

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View

import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_product_detail.*

import java.util.HashMap

import nz.co.warehouseandroidtest.R
import nz.co.warehouseandroidtest.data.ProductDetail
import nz.co.warehouseandroidtest.domain.utils.viewModelProvider
import nz.co.warehouseandroidtest.ui.BaseActivity
import java.lang.RuntimeException

class ProductDetailActivity : BaseActivity() {

    private lateinit var viewModel: ProductDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        viewModel = viewModelProvider(viewModelFactory)

        intent.getStringExtra(EXTRA_BAR_CODE)?.let {
            viewModel.initLoad(it)
        } ?: throw RuntimeException("cant launch without barcode extra")

        observeData()
    }

    private fun observeData() {
        viewModel.uiModel.observe(this, Observer {
            if (it.isLoading) {
                //currently no loading support
            }
            if (it.data != null) {
                bindProductDetails(it.data)
            }
            if (it.error != null) {
                Toast.makeText(this, "Get product detail failed!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun bindProductDetails(data: ProductDetail) {
        Glide.with(this).load(data.Product.ImageURL).into(iv_product)
        tv_product.text = data.Product.ItemDescription;
        tv_price.text = getString(R.string.price_value, data.Product.Price.Price)
        tv_barcode.run { setText(data.Product.Barcode) }
        if (data.Product.Price.Type == "CLR") {
            iv_clearance.visibility = View.VISIBLE;
        } else {
            iv_clearance.visibility = View.GONE;
        }
    }

    companion object {
        private const val EXTRA_BAR_CODE = "extra-bar-code"

        fun startActivity(context: Context, barCode: String) {
            val intent = Intent()
            intent.setClass(context, ProductDetailActivity::class.java)
            intent.putExtra(EXTRA_BAR_CODE, barCode)
            context.startActivity(intent)
        }
    }
}
