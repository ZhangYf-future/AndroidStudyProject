package com.project.tvapp.fragment.brabded

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.leanback.app.BrandedSupportFragment
import com.project.tvapp.R
import kotlinx.android.synthetic.main.fragment_my_branded.*

class MyBrandedSupportFragment : BrandedSupportFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_branded, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        installTitleView(LayoutInflater.from(requireContext()), fl_add_title, null)
    }
}