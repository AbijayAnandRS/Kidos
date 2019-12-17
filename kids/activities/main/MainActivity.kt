package com.example.kids.activities.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater


import android.view.View


import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager

import com.example.kids.R
import com.example.kids.adapters.CategoryViewRecyclerAdapter
import com.example.kids.adapters.ImageAdapter
import com.example.kids.adapters.ProductTypeRecylerAdapter
import com.example.kids.adapters.SectionsPagerAdapter
import com.example.kids.base.BaseActivity
import com.example.kids.data.ProductData
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout


class MainActivity : BaseActivity(),  NavigationView.OnNavigationItemSelectedListener , MainContract.View{

    var productTypeAdapter : ProductTypeRecylerAdapter ? =null;
    var imageAdapter : ImageAdapter ? =null;
    var presenter = MainPresenter()
    var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    var mViewPager: ViewPager? = null
    var categoryAdapter :CategoryViewRecyclerAdapter ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rootView1 = inflater.inflate(R.layout.content_main, null)
        val rootView2 = inflater.inflate(R.layout.activity_categories, null)
        val rootView3 = inflater.inflate(R.layout.activity_filter, null)
        val rvTypes = rootView1.findViewById<RecyclerView>(R.id.rv_types)
        val rvImages = rootView1.findViewById<RecyclerView>(R.id.rv_images)
        val rvCategories = rootView2.findViewById<RecyclerView>(R.id.category_view)
        rvCategories!!.layoutManager = LinearLayoutManager(this)
        rvTypes?.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        productTypeAdapter = ProductTypeRecylerAdapter(this)
        categoryAdapter = CategoryViewRecyclerAdapter(this)
        rvTypes.adapter = productTypeAdapter
        rvCategories.adapter = categoryAdapter
        val tabLayout = findViewById<TabLayout>(R.id.tabs1)
        rvImages.layoutManager = GridLayoutManager(applicationContext, 2)
        imageAdapter =  ImageAdapter(this)
        rvImages.adapter = imageAdapter
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager,
                arrayOf(rootView1, rootView2, rootView3),
                arrayOf("PRODUCTS", "CATEGORIES", "OFFERS"))
        mViewPager = findViewById(R.id.container1)
        mViewPager?.adapter = mSectionsPagerAdapter
        tabLayout.setupWithViewPager(mViewPager)

        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
        presenter.attachView(this)
        presenter.getProducts()
    }

    override fun onBackPressed() {
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onProductsFetched(productDataList: MutableList<ProductData>?) {
        imageAdapter?.updateProductList(productDataList)
        categoryAdapter?.updateProductDataList(productDataList)
    }
}
