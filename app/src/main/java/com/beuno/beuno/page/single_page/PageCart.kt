package com.beuno.beuno.page.single_page

import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.View
import android.widget.RadioButton
import com.beuno.beuno.R
import com.beuno.beuno.alpha.UnoPage
import com.beuno.beuno.page.base.UnoBackwardFragment
import com.beuno.beuno.page.base.UnoBasePresenter
import com.beuno.beuno.page.base.UnoBaseSimpleActivity
import com.beuno.beuno.page.base.UnoPresenterFactory
import com.beuno.beuno.shortcut.toFragment
import com.beuno.beuno.widgets.adapters.CartAdapter
import com.beuno.beuno.widgets.adapters.CartHolder
import com.beuno.beuno.widgets.adapters.UnoAdapters

/**
 * 购物车
 */
class CartActivity : UnoBaseSimpleActivity() {
    override fun defaultFragment() = CartFragment::class.java.toFragment()
}

class CartFragment : UnoBackwardFragment() {

    override fun explorer(pageID: Int) {
        when (pageID) {
            UnoPage.PageID.ID_EDIT  -> mActivity.invalidateOptionsMenu()
            UnoPage.PageID.ID_FINISH -> mActivity.invalidateOptionsMenu()
        }
    }

    override fun onBackward() {
        finish()
    }

    private lateinit var mCartList: RecyclerView
    private lateinit var mCartListAdapter: CartAdapter
    private val mPresenter = UnoPresenterFactory.instance(this, CartPresenter::class.java)

    override fun title(): String = mActivity.getString(R.string.label_cart)
    override fun layoutRes(): Int = R.layout.fragment_cart
    override fun menuRes() = R.menu.menu_cart

    override fun initViews(root: View) {
        mCartList = UnoAdapters.initLinearAdapter(root, R.id.list_cart,
                activity!!, RecyclerView.VERTICAL, CartAdapter::class.java)
        mCartListAdapter = mCartList.adapter as CartAdapter
        findViewById<RadioButton>(R.id.cart_select_all)?.setOnClickListener {
            (it as RadioButton).apply {
                isChecked = mPresenter.selectAll(mCartList)
            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        mPresenter.mModeSwitcher.changeMode()
    }
}

class CartPresenter(fragment: CartFragment): UnoBasePresenter<CartFragment>(fragment) {
    val mModeSwitcher = ModeSwitcher()

    /**
     * 全选
     * @return true-已经全选, false-全部取消选中.
     */
    fun selectAll(mCartList: RecyclerView): Boolean {
        // 先判定是否处于全选状态, 因为非全选状态比较普遍, 所以先假定处于全选状态, 查找非选中项命中概率比较大?
        var alreadySelectAll = true
        for (idxChild in 0 until mCartList.childCount) {
            val isSelected = idxChild.let { mCartList.getChildAt(it) }
                    .let { mCartList.getChildViewHolder(it) }
                    .let { it as CartHolder }.isSelected()
            if (isSelected) break
            alreadySelectAll = false
        }

        // 替换所有项的选中状态.
        for (idxChild in 0 until mCartList.childCount) {
            idxChild.let { mCartList.getChildAt(it) }
                    .let { mCartList.getChildViewHolder(it) }
                    .let { it as CartHolder }
                    .also { it.setRadioStatus(!alreadySelectAll) }
        }
        return !alreadySelectAll
    }

    /** 切换 常规模式 & 编辑模式 */
    inner class ModeSwitcher {

        /** 当前是否为编辑模式 */
        private var isModeEdit = true

        /** 切换模式 */
        fun changeMode() {
            if (isModeEdit) modeGeneral() else modeEdit()
            isModeEdit = !isModeEdit
        }

        /** 常规模式 */
        private fun modeGeneral() {
            val toolbar = fragment.mToolbar ?: return
            toolbar.menu.findItem(R.id.action_edit).isVisible = true
            toolbar.menu.findItem(R.id.action_finish).isVisible = false
            fragment.getView(R.id.bottom_bar_general).visibility = View.VISIBLE
            fragment.getView(R.id.bottom_bar_edit).visibility = View.GONE
        }

        /** 编辑模式 */
        private fun modeEdit() {
            val toolbar = fragment.mToolbar ?: return
            toolbar.menu.findItem(R.id.action_edit).isVisible = false
            toolbar.menu.findItem(R.id.action_finish).isVisible = true
            fragment.getView(R.id.bottom_bar_general).visibility = View.GONE
            fragment.getView(R.id.bottom_bar_edit).visibility = View.VISIBLE
        }
    }
}