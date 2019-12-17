package com.example.kids.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.kids.R

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.kids.adapters.CategoryViewRecyclerAdapter
import com.example.kids.adapters.ImageAdapter
import com.example.kids.adapters.ProductTypeRecylerAdapter
import com.example.kids.adapters.SectionsPagerAdapter
import com.example.kids.base.BaseActivity
import com.example.kids.base.BaseFragment
import com.example.kids.data.ProductData
import com.google.android.material.tabs.TabLayout

class HomeFragment : BaseFragment() {

    var productTypeAdapter: ProductTypeRecylerAdapter? = null
    var imageAdapter: ImageAdapter? = null;
    var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    var mViewPager: ViewPager? = null
    var categoryAdapter: CategoryViewRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.activity_main, container, false)
        val callBack = activity as CallBack
        val rootView1 = inflater.inflate(R.layout.content_main, null)
        val rootView2 = inflater.inflate(R.layout.activity_categories, null)
        val rootView3 = inflater.inflate(R.layout.activity_filter, null)
        val rvTypes = rootView1.findViewById<RecyclerView>(R.id.rv_types)
        val rvImages = rootView1.findViewById<RecyclerView>(R.id.rv_images)
        val rvCategories = rootView2.findViewById<RecyclerView>(R.id.category_view)
        rvCategories!!.layoutManager = LinearLayoutManager(callBack.getContext())
        rvTypes?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        productTypeAdapter = ProductTypeRecylerAdapter(callBack.getContext())
        categoryAdapter = CategoryViewRecyclerAdapter(callBack.getContext())
        rvTypes.adapter = productTypeAdapter
        rvCategories.adapter = categoryAdapter
        val tabLayout = view.findViewById<TabLayout>(R.id.tabs1)
        rvImages.layoutManager = GridLayoutManager(activity, 2)
        imageAdapter = ImageAdapter(callBack.getContext())
        rvImages.adapter = imageAdapter
        mSectionsPagerAdapter = SectionsPagerAdapter(activity?.supportFragmentManager,
                arrayOf(rootView1, rootView2, rootView3),
                arrayOf("PRODUCTS", "CATEGORIES", "FILTER"))
        mViewPager = view.findViewById(R.id.container1)
        mViewPager?.adapter = mSectionsPagerAdapter
        tabLayout.setupWithViewPager(mViewPager)
        if(callBack.getListProduct().isNotEmpty()){
            onProductsFetched(callBack.getListProduct())
        }
        return view
    }

    public fun onProductsFetched(productDataList: List<ProductData>) {
        imageAdapter?.updateProductList(productDataList)
        categoryAdapter?.updateProductDataList(productDataList)
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    interface CallBack {
        fun getContext(): BaseActivity

        fun getListProduct():List<ProductData>
    }
}
