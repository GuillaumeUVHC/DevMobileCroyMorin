package com.example.devmobilecroymorin.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.devmobilecroymorin.fragments.DevFragment
import com.example.devmobilecroymorin.fragments.FormFragment
import com.example.devmobilecroymorin.fragments.ResultFragment
import com.example.devmobilecroymorin.fragments.ServicesFragment

class PageAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return 4
    }

    override fun getItem(p0: Int): Fragment {
        when (p0) {
            0 -> return ServicesFragment().newInstance(0)
            1 -> return FormFragment().newInstance(1)
            2 -> return ResultFragment().newInstance(2)
            3 -> return DevFragment().newInstance(3)
        }
        return ServicesFragment().newInstance(p0)

    }


}