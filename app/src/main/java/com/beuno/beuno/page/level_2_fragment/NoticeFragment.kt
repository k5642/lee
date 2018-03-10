package com.beuno.beuno.page.level_2_fragment

import android.view.View
import com.beuno.beuno.R
import com.beuno.beuno.page.base.UnoBaseFragment
import kotlinx.android.synthetic.main.fragment_notice.*

class NoticeFragment : UnoBaseFragment() {
    override fun topViewToAdjust(): View = tmp
    override fun layoutRes(): Int = R.layout.fragment_notice

    override fun initViews(root: View) {

    }
}
