package nz.co.warehouseandroidtest.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_search_result.*

import nz.co.warehouseandroidtest.R
import nz.co.warehouseandroidtest.ui.adapters.SearchResultAdapter
import nz.co.warehouseandroidtest.domain.utils.viewModelProvider
import nz.co.warehouseandroidtest.ui.BaseActivity
import nz.co.warehouseandroidtest.ui.adapters.EndlessRecyclerOnScrollListener
import nz.co.warehouseandroidtest.ui.product.ProductDetailActivity
import java.lang.RuntimeException

class SearchResultActivity : BaseActivity(), SearchResultAdapter.Callback {

    private val searchResultAdapter by lazy { SearchResultAdapter(this) }
    private lateinit var viewModel: SearchResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        viewModel = viewModelProvider(viewModelFactory)

        intent.getStringExtra(FLAG_KEY_WORD)?.let {
            viewModel.initLoad(it)
        } ?: throw RuntimeException("no query passed to activity")
        observeData()
        bindUi()
    }

    private fun bindUi() {
        refresh_layout.setOnRefreshListener {
            viewModel.onPullToRefresh()
        }
        bindRecyclerView()
    }

    private fun bindRecyclerView() = with(recycler_view) {
        layoutManager = LinearLayoutManager(this@SearchResultActivity, RecyclerView.VERTICAL, false)
        addOnScrollListener(object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore() {
                viewModel.loadMore()
            }
        })
        adapter = searchResultAdapter
    }

    private fun observeData() {
        viewModel.uiModel.observe(this, Observer {
            searchResultAdapter.hasMore(it.hasMore)
            if (it.shouldRefreshAll) {
                searchResultAdapter.clear()
                refresh_layout.isRefreshing = false
            }
            if (it.data != null) {
                searchResultAdapter.addData(it.data)
            }
            if (it.error != null) {
                Toast.makeText(this, "Search failed!", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onProductClicked(barcode: String) {
        ProductDetailActivity.startActivity(this, barcode)
    }

    companion object {
        private const val FLAG_KEY_WORD = "keyWord"

        fun startActivity(context: Context, query: String) {
            val intent = Intent()
            intent.setClass(context, SearchResultActivity::class.java)
            intent.putExtra(FLAG_KEY_WORD, query)
            context.startActivity(intent)
        }
    }
}
