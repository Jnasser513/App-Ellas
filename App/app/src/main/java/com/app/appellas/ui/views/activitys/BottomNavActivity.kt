package com.app.appellas.ui.views.activitys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.app.appellas.R
import com.app.appellas.databinding.BottomNavActivityBinding

class BottomNavActivity: AppCompatActivity() {

    private lateinit var mBinding: BottomNavActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = BottomNavActivityBinding.inflate(layoutInflater)

        setContentView(mBinding.root)
    }

    override fun onStart() {
        super.onStart()

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container) as NavHostFragment

        val navController = navHostFragment.navController

        mBinding.bottomNavMenu.setOnClickListener {
            navController.navigate(R.id.bottomSheetMenuFragment)
        }

        mBinding.bottomNavProfile.setOnClickListener {
            navController.navigate(R.id.profileFragment)
        }

        mBinding.bottomNavActionAlert.setOnClickListener {
            navController.navigate(R.id.dialogAlertFragment)
        }
    }

}