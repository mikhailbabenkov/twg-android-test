package nz.co.warehouseandroidtest.ui.adapters.viewholder

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import nz.co.warehouseandroidtest.R
import nz.co.warehouseandroidtest.data.ProductWithoutPrice

class SearchResultViewHolder(private val view: View, private val callback: Callback) : RecyclerView.ViewHolder(view) {
    private val mIvProduct: ImageView = view.findViewById(R.id.iv_product)
    private val mTvProductName: TextView = view.findViewById(R.id.tv_product_name)

    fun bind(product: ProductWithoutPrice) {

        if (!TextUtils.isEmpty(product.Description)) {
            mTvProductName.text = product.Description
        }
        if (!TextUtils.isEmpty(product.ImageURL)) {
            Glide.with(mIvProduct.context).load(product.ImageURL).into(mIvProduct)
        }

        view.setOnClickListener {
            callback.onProductClicked(product.Barcode)
        }
    }


    companion object {
        fun newInstance(parent: ViewGroup, callback: Callback): SearchResultViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return SearchResultViewHolder(
                    inflater.inflate(R.layout.item_product, parent, false), callback)
        }
    }

    interface Callback {
        fun onProductClicked(barcode: String)
    }
}
