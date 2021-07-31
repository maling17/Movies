package com.kresna.movies.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.kresna.movies.view.popular.PopularFragment
import com.kresna.movies.view.upcoming.UpcomingFragment

class MoviesPagerAdapter(fm:FragmentManager): FragmentPagerAdapter(fm) {

    private val pages = listOf(
        UpcomingFragment(),
        PopularFragment(),
    )
    override fun getCount(): Int {
        return pages.size
    }

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }
    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Upcoming"
            else -> "Popular"
        }
    }
}