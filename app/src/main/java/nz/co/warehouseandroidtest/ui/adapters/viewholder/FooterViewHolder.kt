package nz.co.warehouseandroidtest.ui.adapters.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nz.co.warehouseandroidtest.R
import nz.co.warehouseandroidtest.ui.adapters.SearchResultAdapter.Companion.LOADING
import nz.co.warehouseandroidtest.ui.adapters.SearchResultAdapter.Companion.LOADING_COMPLETE
import nz.co.warehouseandroidtest.ui.adapters.SearchResultAdapter.Companion.LOADING_END

class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val pbLoading: View = view.findViewById(R.id.pb_loading)
    private val tvLoading: View = view.findViewById(R.id.tv_loading)
    private val llEnd: View = view.findViewById(R.id.ll_end)
    fun bind(state: Int) {
        when (state) {
            LOADING -> showLoading()
            LOADING_COMPLETE -> hideLoading()
            LOADING_END -> showNoMore()
        }
    }

    private fun hideLoading() {
        pbLoading.visibility = View.INVISIBLE
        tvLoading.visibility = View.INVISIBLE
        llEnd.visibility = View.GONE
    }

    private fun showNoMore() {
        pbLoading.visibility = View.GONE
        tvLoading.visibility = View.GONE
        llEnd.visibility = View.VISIBLE
    }

    private fun showLoading() {
        pbLoading.visibility = View.VISIBLE
        tvLoading.visibility = View.VISIBLE
        llEnd.visibility = View.GONE
    }

    companion object {
        fun newInstance(parent: ViewGroup): FooterViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return FooterViewHolder(
                    inflater.inflate(R.layout.layout_refresh_footer, parent, false)
            )
        }
    }
}
