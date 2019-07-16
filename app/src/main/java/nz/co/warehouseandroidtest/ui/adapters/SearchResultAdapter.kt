package nz.co.warehouseandroidtest.ui.adapters

import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import java.util.ArrayList

import nz.co.warehouseandroidtest.R
import nz.co.warehouseandroidtest.data.ProductWithoutPrice
import nz.co.warehouseandroidtest.ui.adapters.viewholder.FooterViewHolder
import nz.co.warehouseandroidtest.ui.adapters.viewholder.SearchResultViewHolder
import java.lang.UnsupportedOperationException

class SearchResultAdapter(private val callback: Callback) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data = ArrayList<ProductWithoutPrice>()

    private var loadingState = LOADING_COMPLETE

    fun addData(data: List<ProductWithoutPrice>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position + 1 == itemCount) {
            TYPE_FOOTER
        } else {
            TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ITEM -> SearchResultViewHolder.newInstance(parent, callback)
            TYPE_FOOTER -> FooterViewHolder.newInstance(parent)
            else -> throw UnsupportedOperationException("Not supported")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchResultViewHolder -> holder.bind(data[position])
            is FooterViewHolder -> holder.bind(loadingState)
        }
//        if (holder is SearchResultViewHolder) {
//            holder.bind(data[position])
//        } else if (holder is FooterViewHolder) {
//            when (currentLoadState) {
//                LOADING -> {
//                    holder.pbLoading.visibility = View.VISIBLE
//                    holder.tvLoading.visibility = View.VISIBLE
//                    holder.llEnd.visibility = View.GONE
//                }
//                LOADING_COMPLETE -> {
//                    holder.pbLoading.visibility = View.INVISIBLE
//                    holder.tvLoading.visibility = View.INVISIBLE
//                    holder.llEnd.visibility = View.GONE
//                }
//                LOADING_END -> {
//                    holder.pbLoading.visibility = View.GONE
//                    holder.tvLoading.visibility = View.GONE
//                    holder.llEnd.visibility = View.VISIBLE
//                }
//                else -> {
//                }
//            }
//        }
    }

    override fun getItemCount(): Int {
        return data.size + 1
    }

    fun hasMore(hasMore: Boolean?) {
        loadingState = hasMore?.let { if (it) LOADING else LOADING_END } ?: LOADING_COMPLETE
        notifyDataSetChanged()
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    interface Callback : SearchResultViewHolder.Callback

    companion object {
        private const val TYPE_ITEM = 1
        private const val TYPE_FOOTER = 2
        const val LOADING = 1
        const val LOADING_COMPLETE = 2
        const val LOADING_END = 3
    }
}
