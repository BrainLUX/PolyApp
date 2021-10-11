package com.polytech.application.ui.main

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.polytech.application.R
import com.polytech.application.base.BaseFragment
import com.polytech.application.databinding.FragmentMainBinding
import com.polytech.application.extensions.viewBinding

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