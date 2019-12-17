package com.example.kids.activities.productpage


import android.Manifest.permission.READ_SMS
import android.Manifest.permission.RECEIVE_SMS
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast

import com.example.kids.R
import com.example.kids.adapters.ProductPageAdapter
import com.example.kids.adapters.TabproductpageAdapter
import com.google.android.material.tabs.TabLayout


import androidx.viewpager.widget.ViewPager
import com.example.kids.base.BaseActivity
import com.example.kids.data.ProductData
import com.example.kids.utils.PermissionUtils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.paytm.pgsdk.PaytmOrder
import com.paytm.pgsdk.PaytmPGService
import com.paytm.pgsdk.PaytmPaymentTransactionCallback
import kotlinx.android.synthetic.main.activity_product_page.*
import kotlinx.android.synthetic.main.toolbar_product_page.*
import me.relex.circleindicator.CircleIndicator
import java.util.jar.Manifest

class ProductpageActivity : BaseActivity(), ProductContract.View {

    private var v1: ViewPager? = null
    private var a: ProductPageAdapter? = null
    private val indicator: CircleIndicator? = null

    var presenter = ProductPagePresenter()

    internal var favimg1: ImageView? = null

    private var viewpager1: ViewPager? = null

    private var tablayout1: TabLayout? = null
    private var adapter: TabproductpageAdapter? = null

    private val TEZ_REQUEST_CODE = 123

    private val GOOGLE_TEZ_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user"

    private val PAY_TM_PERMISSION_REQUEST_CODE = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_page)
        v1 = findViewById(R.id.v1)
        val indicator = findViewById<View>(R.id.indicator) as CircleIndicator
        var productData = kiddiesDataHolder.get(ProductData::class.java)
        a = ProductPageAdapter(supportFragmentManager, productData.imgUrl.split(","))
        v1!!.adapter = a
        indicator.setViewPager(v1)
        a!!.registerDataSetObserver(indicator.dataSetObserver)

        tablayout1 = findViewById(R.id.tablayout1)

        viewpager1 = findViewById(R.id.viewpager1)
        tablayout1?.setTabGravity(TabLayout.GRAVITY_FILL)

        tablayout1?.addTab(tablayout1?.newTab()!!.setText("INFO"))
        tablayout1?.addTab(tablayout1?.newTab()!!.setText("SIZE CHART"))

        adapter = TabproductpageAdapter(supportFragmentManager, tablayout1!!.tabCount)
        viewpager1?.adapter = adapter
        viewpager1?.offscreenPageLimit = 2
        setListenerForViews()
        presenter.attachView(this)
    }

    private fun setListenerForViews() {
        viewpager1?.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tablayout1))
        tablayout1?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                viewpager1?.currentItem = tab.position

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
        back.setOnClickListener {
            finish()
        }
        pay_check_out.setOnClickListener {
            createBottomSheetDialog()
        }
    }

    private fun createBottomSheetDialog() {
        var bottomSheetDialog = BottomSheetDialog(this)
        val view = this.layoutInflater.inflate(R.layout.bottom_dialog, null)
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
        val googlePay: LinearLayout = view.findViewById(R.id.google_pay)
        val otherPay: LinearLayout = view.findViewById(R.id.other_payment)
        googlePay.setOnClickListener {
            bottomSheetDialog.cancel()
            startGooglePayTransaction()
        }
        otherPay.setOnClickListener {
            bottomSheetDialog.cancel()
            startPayTmService()
        }
    }

    private fun startPayTmService() {
        if(PermissionUtils.checkAndAskPermission(this, PAY_TM_PERMISSION_REQUEST_CODE, READ_SMS, RECEIVE_SMS)) {
            presenter.getPayTmCheckSumHash("7777777777");
        }
    }

    private fun startGooglePayTransaction() {
        val uri = Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .appendQueryParameter("pa", "test@axisbank")
                .appendQueryParameter("pn", "Test Merchant")
                .appendQueryParameter("mc", "1234")
                .appendQueryParameter("tr", "123456789")
                .appendQueryParameter("tn", "For product payment")
                .appendQueryParameter("am", "10.01")
                .appendQueryParameter("cu", "INR")
                .appendQueryParameter("url", "https://test.merchant.website")
                .build()
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = uri
        intent.setPackage(GOOGLE_TEZ_PACKAGE_NAME)
        startActivityForResult(intent, TEZ_REQUEST_CODE)
    }

    override fun paramMapReceived(paramMap: MutableMap<String, String>) {
        val paytmPGService = PaytmPGService.getStagingService()
        val payTmOrder = PaytmOrder(HashMap(paramMap))
        paytmPGService.initialize(payTmOrder, null)
        paytmPGService.startPaymentTransaction(this, true, true, object : PaytmPaymentTransactionCallback {
            /*Call Backs*/
            override fun someUIErrorOccurred(inErrorMessage: String) {}

            override fun onTransactionResponse(inResponse: Bundle) {}
            override fun networkNotAvailable() {}
            override fun clientAuthenticationFailed(inErrorMessage: String) {
                Toast.makeText(applicationContext, inErrorMessage, Toast.LENGTH_SHORT).show()
            }
            override fun onErrorLoadingWebPage(iniErrorCode: Int, inErrorMessage: String, inFailingUrl: String) {
                Toast.makeText(applicationContext, inErrorMessage, Toast.LENGTH_SHORT).show()
            }
            override fun onBackPressedCancelTransaction() {}
            override fun onTransactionCancel(inErrorMessage: String, inResponse: Bundle) {
                Toast.makeText(applicationContext, inErrorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
