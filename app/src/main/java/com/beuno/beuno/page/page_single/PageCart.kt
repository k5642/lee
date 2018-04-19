package com.beuno.beuno.page.page_single

import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.beuno.beuno.R
import com.beuno.beuno.alpha.UnoPage
import com.beuno.beuno.page.base.UnoBackwardFragment
import com.beuno.beuno.page.base.UnoBasePresenter
import com.beuno.beuno.page.base.UnoBaseSimpleActivity
import com.beuno.beuno.page.base.UnoPresenterFactory
import com.beuno.beuno.page.page_detail.DetailGoodsActivity
import com.beuno.beuno.plugin.PluginInitialState
import com.beuno.beuno.plugin.PluginTransition
import com.beuno.beuno.shortcut.*
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
            UnoPage.PageID.ID_EDIT -> mActivity.invalidateOptionsMenu()
            UnoPage.PageID.ID_FINISH -> mActivity.invalidateOptionsMenu()
        }
    }

    private val mPluginInitialState = PluginInitialState()
    private val mPresenter = UnoPresenterFactory.instance(this, CartPresenter::class.java)

    override fun title(): String = mActivity.getString(R.string.label_cart)
    override fun layoutRes(): Int = R.layout.fragment_cart
    override fun menuRes() = R.menu.menu_cart

    override fun initViews(root: View) {
        mPresenter.initialize()
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        mPluginInitialState.onLady {
            mPresenter.mModeSwitcher.changeMode()
        }
    }
}

class CartPresenter(fragment: CartFragment) : UnoBasePresenter<CartFragment>(fragment) {
    val mRecyclerController = RecyclerController()
    val mBottomBarController = BottomBarController()
    val mModeSwitcher = ModeSwitcher()

    override fun initialize() {
        mRecyclerController.init()
        mBottomBarController.init()
    }

    /** RecyclerView 控制器 */
    inner class RecyclerController {
        lateinit var recycler: RecyclerView
        fun init() {
            recycler = UnoAdapters.initLinearAdapter(fragment.mRoot, R.id.list_cart, fragment.mActivity, RecyclerView.VERTICAL, CartAdapter::class.java)
            recycler.onItemClick {
                DetailGoodsActivity::class.java.switchActivity(fragment.mActivity)
            }
        }
    }

    /** 底部菜单控制器 */
    inner class BottomBarController {
        fun init() {
            // select all
            val radioButton = fragment.getView(R.id.cart_select_all) as RadioButton
            radioButton.setOnClickListener {
                radioButton.isChecked = mBottomBarController.selectAll()
            }
        }
        /**
         * 全选
         * @return true-已经全选, false-全部取消选中.
         */
        fun selectAll(): Boolean {
            val cartList: RecyclerView = mRecyclerController.recycler
            // 先判定是否处于全选状态, 因为非全选状态比较普遍, 所以先假定处于全选状态, 查找非选中项命中概率比较大?
            var alreadySelectAll = false
            for (idxChild in 0 until cartList.childCount) {
                val isSelected = idxChild.let { cartList.getChildAt(it) }
                        .let { cartList.getChildViewHolder(it) }
                        .let { it as CartHolder }.isSelected()
                if (isSelected) break
                alreadySelectAll = false
            }

            // 替换所有项的选中状态.
            for (idxChild in 0 until cartList.childCount) {
                idxChild.let { cartList.getChildAt(it) }
                        .let { cartList.getChildViewHolder(it) }
                        .let { it as CartHolder }
                        .also { it.setRadioStatus(!alreadySelectAll) }
            }
            return !alreadySelectAll
        }
    }

    /** 切换 常规模式 & 编辑模式 */
    inner class ModeSwitcher {

        /** 当前是否为编辑模式 */
        private var isModeEdit = true

        /** 切换模式 */
        fun changeMode() {
            PluginTransition.beginDelayedTransition(fragment.mRoot as ViewGroup)
            if (isModeEdit) modeGeneral() else modeEdit()
            isModeEdit = !isModeEdit
        }

        /** 常规模式 */
        private fun modeGeneral() {
            val toolbar = fragment.mToolbar ?: return
            toolbar.menu.findItem(R.id.action_edit).show()
            toolbar.menu.findItem(R.id.action_finish).hide()
            fragment.getView(R.id.bottom_bar_general).show()
            fragment.getView(R.id.bottom_bar_edit).hide()
        }

        /** 编辑模式 */
        private fun modeEdit() {
            val toolbar = fragment.mToolbar ?: return
            toolbar.menu.findItem(R.id.action_edit).hide()
            toolbar.menu.findItem(R.id.action_finish).show()
            fragment.getView(R.id.bottom_bar_general).hide()
            fragment.getView(R.id.bottom_bar_edit).show()
        }
    }
}