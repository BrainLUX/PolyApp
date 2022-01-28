package com.brainlux.polyapp.ui.main

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.brainlux.polyapp.R
import com.brainlux.polyapp.base.BaseFragment
import com.brainlux.polyapp.databinding.FragmentMainBinding
import com.brainlux.polyapp.extensions.viewBinding

class MainFragment : BaseFragment(R.layout.fragment_main) {

    override val binding by viewBinding(FragmentMainBinding::bind)

    override fun setupViews() {
        val navHost = childFragmentManager.findFragmentById(R.id.frg_main__fcv_container) as NavHostFragment
        val navController = navHost.navController
        binding.frgMainBnvMain.setupWithNavController(navController)
        binding.frgMainBnvMain.setOnNavigationItemSelectedListener { item ->
            NavigationUI.onNavDestinationSelected(item, navController)
        }
    }

}