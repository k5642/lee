package com.beuno.beuno.page.homepage

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.beuno.beuno.R
import com.beuno.beuno.alpha.UnoPage
import com.beuno.beuno.page.base.UnoBaseFragment
import com.beuno.beuno.page.base.UnoBasePresenter
import com.beuno.beuno.page.base.UnoPresenterFactory
import com.beuno.beuno.page.entry.TestActivity
import com.beuno.beuno.page.settings.SettingsActivity
import com.beuno.beuno.plugin.PluginActivity
import com.beuno.beuno.plugin.PluginImage
import com.beuno.beuno.shortcut.logger
import com.beuno.beuno.shortcut.toCircleDrawable
import com.beuno.beuno.shortcut.toSquare


class SelfFragment : UnoBaseFragment() {
    override fun explorer(pageID: Int) {
        when (pageID) {
            UnoPage.PageID.ID_TEST -> TestActivity::class.java.switchActivity()
            UnoPage.PageID.ID_SETTINGS -> SettingsActivity::class.java.startActivityForTest()
        }
    }
    private val mPresenter = UnoPresenterFactory.instance(this, SelfPresenter::class.java)

    override fun layoutRes(): Int = R.layout.fragment_self
    override fun menuRes() = R.menu.menu_main

    override fun initViews(root: View) {
        logger("hide toolbar title")
        PluginActivity.hideSupportActionBarTitle(mSupportActionBar)

        mPresenter.initialize()
    }
}

class SelfPresenter(fragment: SelfFragment): UnoBasePresenter<SelfFragment>(fragment) {
    override fun initialize() {
        val drawable = mImage.getResouce(R.mipmap.mine_avatar_defult).let { mImage.toCircle(it) }
        val avatar = fragment.getView(R.id.self_avatar) as ImageView
        avatar.setImageDrawable(drawable)
        val name = fragment.getView(R.id.self_name) as TextView
        name.text = "今年要发大财"

    }

    val mImage = Img()

    inner class Img {
        /** 资源文件转化为Bitmap图片 */
        fun getResouce(@DrawableRes resId: Int) = PluginImage.getBitmap(fragment.resources, resId)
        /** Bitmap图片转化为圆形Drawable图片 */
        fun toCircle(bmp: Bitmap): Drawable {
            return bmp.toSquare().toCircleDrawable(fragment.resources)
        }
    }
}
