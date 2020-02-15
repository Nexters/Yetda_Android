package com.nexters.yetda.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.nexters.yetda.android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MainContact.View {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        var vm = ViewModelProviders.of(this)[MainViewModel::class.java]
        binding.setVariable(BR.vm, vm)

        vm.getFirebaseSampleData()

//        Crashlytics.getInstance().crash() // Force a crash

    }
}
