package com.mutondo.weather4sureapp.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {
    fun setActionBarTitle(title: String) {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }

    fun showActionBar() {
        (activity as AppCompatActivity).supportActionBar?.show()
    }

    fun hideActionBar() {
        (activity as AppCompatActivity).supportActionBar?.hide()
    }


}
