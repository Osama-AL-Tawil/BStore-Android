package com.os_tec.store.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.os_tec.store.Fragments.*
import com.os_tec.store.R
import com.os_tec.store.databinding.ActivityNavigation2Binding

class Navigation2Activity : AppCompatActivity() {
    lateinit var binding: ActivityNavigation2Binding
    var cartFragment = CartFragment()
    var addressFragment = AddressFragment()
    var paymentFragment = PaymentFragment()
    var checkOutFragment = CheckoutFragment()
    var confirmationFragment = ConfirmationFragment()
    var activeFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigation2Binding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val nv = intent.getStringExtra("nv")

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        when (nv) {
            "cart" -> {
                startFragment(cartFragment)
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun startFragment(fragment: Fragment) {
         if (fragment.isAdded) {
            supportFragmentManager.beginTransaction().remove(activeFragment!!).hide(activeFragment!!).commit()
            supportFragmentManager.beginTransaction().show(fragment).commit()
            activeFragment=fragment

        } else {
            supportFragmentManager.beginTransaction().add(R.id.container2, fragment).commit()
            if (activeFragment != null) {
                supportFragmentManager.beginTransaction().hide(activeFragment!!).commit()
            }
            activeFragment=fragment

        }
    }
}