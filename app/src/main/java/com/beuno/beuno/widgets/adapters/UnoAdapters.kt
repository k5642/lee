package com.beuno.beuno.widgets.adapters

import android.content.Context
import android.support.annotation.IdRes
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import com.beuno.beuno.R
import com.beuno.beuno.alpha.UnoConstants
import com.beuno.beuno.bean.UnoCategorySub
import com.beuno.beuno.plugin.PluginImage
import com.beuno.beuno.shortcut.deselect
import com.beuno.beuno.shortcut.logger
import com.beuno.beuno.shortcut.select
import com.beuno.beuno.shortcut.snack

object UnoAdapters {

    /** 快捷方式, 初始化RecyclerView的网格适配器 */
    fun <T : RecyclerView.Adapter<*>> initGridAdapter(root: View, @IdRes itemId: Int,
                                                      ctx: Context?, spanCount: Int, orientation: Int,
                                                      cls: Class<T>): RecyclerView {
        return root.findViewById<RecyclerView>(itemId)
                .apply {
                    layoutManager = GridLayoutManager(ctx, spanCount, orientation, false)
                    adapter = cls.constructors[0].newInstance(ctx) as RecyclerView.Adapter<*>?
                }
    }

    /**
     * 快捷方式, 初始化RecyclerView的线性适配器
     * @param orientation RecyclerView.HORIZONTAL / VERTICAL
     */
    fun <T : RecyclerView.Adapter<*>> initLinearAdapter(root: View, @IdRes itemId: Int,
                                                        ctx: Context?, orientation: Int,
                                                        cls: Class<T>): RecyclerView {
        return root.findViewById<RecyclerView>(itemId)
                .apply {
                    layoutManager = LinearLayoutManager(ctx, orientation, false)
                    adapter = cls.constructors[0].newInstance(ctx) as RecyclerView.Adapter<*>?
                }
    }
}

// --------------------------------------------------------------------------------------------
//                                     2nd level
// --------------------------------------------------------------------------------------------

/**
 * 二级列表的状态指示.
 */
private object ItemLevel {
    const val VIEW_TYPE_PARENT = 0
    const val VIEW_TYPE_CHILD = 1
    const val INDEX_PARENT_DEFAULT = -1
    /** 二级项所属父项的索引 */
    private var indexParent: Int = INDEX_PARENT_DEFAULT

    /** 点击父项时, 关闭当前打开的子项, 开启新的子项 */
    fun trigger(adapter: RecyclerView.Adapter<*>, indexTriggered: Int) {
        logger("trigger $indexTriggered")
        if (indexTriggered == indexParent) {
            adapter.notifyItemRemoved(indexParent + 1)
            indexParent = INDEX_PARENT_DEFAULT
            return
        }
        if (hasChild())
            adapter.notifyItemRemoved(indexParent + 1)
        adapter.notifyItemInserted(indexTriggered + 1)
        indexParent = indexTriggered
    }

    /** 重置, Adapter启动时调用, parent指针清零 */
    fun reset() {
        indexParent = INDEX_PARENT_DEFAULT
    }

    /** 当前没有正在显示的child */
    fun noChild() = indexParent < 0

    /** 当前有正在显示的child */
    fun hasChild() = indexParent >= 0

    /** 判定当前项是否为child */
    fun isChild(index: Int) = indexParent + 1 == index

    /** 当前展开项的的父项 */
    fun indexFather() = indexParent
}

/**
 * 多个项, 只有一个可以被选中的指示器
 */
class ItemSelector {
    /** 被选中的项的索引 */
    var itemLast: View? = null
    /** 触发事件 */
    fun trigger(itemTriggered: View) {
        itemLast?.deselect()
        itemTriggered.select()
        itemLast = itemTriggered
    }
}

// --------------------------------------------------------------------------------------------
//                                          adapters
// --------------------------------------------------------------------------------------------
abstract class UnoBasicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

abstract class UnoBasicAdapter<VH : UnoBasicViewHolder> : RecyclerView.Adapter<VH>() {
    /** 设置点击事件 */
    var itemListener: ((position: Int) -> Unit)? = null
    /** 启用整项点击事件 */
    internal fun enableItemViewClick(holder: UnoBasicViewHolder, position: Int) {
        holder.itemView.apply {
            isClickable = true
            setOnClickListener {
                itemListener?.invoke(position)
            }
        }
    }
}

/** 通用商品列表. 供多个页面使用 */
class GeneralGoodsHolder(itemView: View) : UnoBasicViewHolder(itemView) {
    val txt: TextView by lazy { itemView.findViewById(R.id.item_txt) as TextView }
    val img: ImageView by lazy { itemView.findViewById(R.id.item_img) as ImageView }
    val price: TextView by lazy { itemView.findViewById(R.id.item_price) as TextView }
    val charge: TextView by lazy { itemView.findViewById(R.id.item_charge) as TextView }
    val discount: TextView by lazy { itemView.findViewById(R.id.item_discount) as TextView }
}

class GeneralGoodsAdapter(private val mContext: Context) : UnoBasicAdapter<GeneralGoodsHolder>() {
    private val mList = UnoConstants.HOMEPAGE_RECOMMEND_LIST
    override fun getItemCount(): Int = mList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralGoodsHolder {
        return LayoutInflater.from(mContext)
                .run { inflate(R.layout.item_adapter_general_goods, parent, false) }
                .let { GeneralGoodsHolder(it) }
    }

    override fun onBindViewHolder(holder: GeneralGoodsHolder, position: Int) {
        enableItemViewClick(holder, position)
        mList[position].apply {
            //            holder.txt.text = second
//            holder.img.setImageResource(first
        }
    }
}

/** 商品分类 */
class HomepageCategoryHolder(itemView: View) : UnoBasicViewHolder(itemView) {
    val txt: TextView by lazy { itemView.findViewById(R.id.item_txt) as TextView }
    val img: ImageView by lazy { itemView.findViewById(R.id.item_img) as ImageView }
}

class HomepageCategoryAdapter(private val mContext: Context) : UnoBasicAdapter<HomepageCategoryHolder>() {
    private val mList = UnoConstants.HOMEPAGE_CATEGORY_LIST
    override fun getItemCount(): Int = mList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomepageCategoryHolder {
        return LayoutInflater.from(mContext)
                .run { inflate(R.layout.item_adapter_homepage_category, parent, false) }
                .let { HomepageCategoryHolder(it) }
    }

    override fun onBindViewHolder(holder: HomepageCategoryHolder, position: Int) {
        enableItemViewClick(holder, position)
        mList[position].apply {
            holder.txt.text = second
            holder.img.setImageResource(first)
        }
    }
}

/** 品牌精选 */
class HomepageBrandHolder(itemView: View) : UnoBasicViewHolder(itemView) {
    val txt: TextView by lazy { itemView.findViewById(R.id.item_txt) as TextView }
    val img: ImageView by lazy { itemView.findViewById(R.id.item_img) as ImageView }
}

class HomepageBrandAdapter(private val mContext: Context) : UnoBasicAdapter<HomepageBrandHolder>() {
    private val mList = UnoConstants.HOMEPAGE_BRAND_LIST
    override fun getItemCount(): Int = mList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomepageBrandHolder {
        return LayoutInflater.from(mContext)
                .run { inflate(R.layout.item_adapter_homepage_brand, parent, false) }
                .let { HomepageBrandHolder(it) }
    }

    override fun onBindViewHolder(holder: HomepageBrandHolder, position: Int) {
        enableItemViewClick(holder, position)
        mList[position].apply {
            holder.txt.text = second
            holder.img.setImageResource(first)
        }
    }
}

/** 分类 */
class CategoryHolder(itemView: View) : UnoBasicViewHolder(itemView) {
    val txt: TextView by lazy { itemView.findViewById(R.id.item_txt) as TextView }
    val img: ImageView by lazy { itemView.findViewById(R.id.item_img) as ImageView }
    val engName: TextView by lazy { itemView.findViewById(R.id.item_eng_name) as TextView }
}

class CategorySubHolder(itemView: View) : UnoBasicViewHolder(itemView) {
    val list: RecyclerView by lazy { itemView.findViewById(R.id.list_category_sub) as RecyclerView }
}

class CategoryAdapter(private val mContext: Context) : UnoBasicAdapter<UnoBasicViewHolder>() {
    private val mList = UnoConstants.CATEGORY_LIST

    init {
        ItemLevel.reset()
    }

    override fun getItemCount(): Int {
        val subItemCount = if (ItemLevel.noChild()) 0 else 1
        return mList.size + subItemCount
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnoBasicViewHolder {
        return when (viewType) {
            ItemLevel.VIEW_TYPE_PARENT ->
                LayoutInflater.from(mContext)
                        .run { inflate(R.layout.item_adapter_category, parent, false) }
                        .let { CategoryHolder(it) }
            ItemLevel.VIEW_TYPE_CHILD ->
                LayoutInflater.from(mContext)
                        .run { inflate(R.layout.item_adapter_category_sub, parent, false) }
                        .let { CategorySubHolder(it) }
            else -> object : UnoBasicViewHolder(parent) {}
        }
    }

    override fun onBindViewHolder(holder: UnoBasicViewHolder, position: Int) {
        // 因为有中间插入项, 索引好特么恶心.
        if (holder is CategoryHolder) {
            val positionParent = if (ItemLevel.hasChild() && position > ItemLevel.indexFather()) position - 1
            else position
            mList[positionParent].apply {
                holder.txt.text = name
                holder.img.setImageResource(pic)
                holder.engName.text = engName
                holder.itemView.setBackgroundColor(PluginImage.genColorfulBg())
            }
            holder.itemView.apply {
                isClickable = true
                setOnClickListener {
                    ItemLevel.trigger(this@CategoryAdapter, positionParent)
                }
            }
        } else if (holder is CategorySubHolder) {
            holder.list.apply {
                layoutManager = LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false)
                adapter = CategoryLevel2Adapter(mContext, mList[position - 1].subCategory)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        // child只会位于parent下一项.
        return when {
            ItemLevel.noChild() -> ItemLevel.VIEW_TYPE_PARENT
            ItemLevel.isChild(position) -> ItemLevel.VIEW_TYPE_CHILD
            else -> ItemLevel.VIEW_TYPE_PARENT
        }
    }

}

/** 分类二级列表 */
class CategoryLevel2Holder(itemView: View) : UnoBasicViewHolder(itemView) {
    val txt: TextView by lazy { itemView.findViewById(R.id.item_txt) as TextView }
    val img: ImageView by lazy { itemView.findViewById(R.id.item_img) as ImageView }
}

class CategoryLevel2Adapter(private val mContext: Context, private val mList: List<UnoCategorySub>) : UnoBasicAdapter<CategoryLevel2Holder>() {
    override fun getItemCount(): Int = mList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryLevel2Holder {
        return LayoutInflater.from(mContext)
                .run { inflate(R.layout.item_adapter_category_level_2, parent, false) }
                .let { CategoryLevel2Holder(it) }
    }

    override fun onBindViewHolder(holder: CategoryLevel2Holder, position: Int) {
        mList[position].apply {
            holder.txt.text = name
            holder.img.setImageResource(pic)
        }
        holder.itemView.apply {
            isClickable = true
            setOnClickListener {
                // todo 根据id跳转
                snack(holder.itemView, holder.toString())
            }
        }
    }
}

/** 购物车 */
class CartHolder(itemView: View) : UnoBasicViewHolder(itemView) {
    val radio: RadioButton by lazy { itemView.findViewById(R.id.item_radio) as RadioButton }
    val name: TextView by lazy { itemView.findViewById(R.id.item_name) as TextView }
    val type: TextView by lazy { itemView.findViewById(R.id.item_type) as TextView }
    val img: ImageView by lazy { itemView.findViewById(R.id.item_img) as ImageView }
    val price: TextView by lazy { itemView.findViewById(R.id.item_price) as TextView }
    val count: TextView by lazy { itemView.findViewById(R.id.item_count) as TextView }

    /** 判定当前项是否已被选中 */
    fun isSelected(): Boolean = radio.isChecked

    /** 设置当前项是否选中 */
    fun setRadioStatus(check: Boolean) {
        if (radio.isChecked != check) radio.isChecked = check
    }
}

class CartAdapter(private val mContext: Context) : UnoBasicAdapter<CartHolder>() {
    private val mList = UnoConstants.CART_LIST

    override fun getItemCount(): Int = mList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        return LayoutInflater.from(mContext)
                .run { inflate(R.layout.item_adapter_cart, parent, false) }
                .let { CartHolder(it) }
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        mList[position].apply {
            //            holder.txt.text = second
            holder.img.setImageResource(pic)
            holder.type.text = type
            // 滚动字幕
            holder.name.text = name
            holder.price.text = "￥$price"
            holder.count.text = "x$count"
        }
        holder.itemView.apply {
            isClickable = true
            setOnClickListener {
                holder.radio.isChecked = !holder.radio.isChecked
            }
        }
    }
}

/** 通知 */
class NoticeHolder(itemView: View) : UnoBasicViewHolder(itemView) {
    val timestamp: TextView by lazy { itemView.findViewById(R.id.item_timestamp) as TextView }
    val title: TextView by lazy { itemView.findViewById(R.id.item_title) as TextView }
    val content: TextView by lazy { itemView.findViewById(R.id.item_content) as TextView }
}

class NoticeAdapter(private val mContext: Context) : UnoBasicAdapter<NoticeHolder>() {
    private val mList = UnoConstants.NOTICE_LIST
    override fun getItemCount(): Int = mList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeHolder {
        return LayoutInflater.from(mContext)
                .run { inflate(R.layout.item_adapter_notice, parent, false) }
                .let { NoticeHolder(it) }
    }

    override fun onBindViewHolder(holder: NoticeHolder, position: Int) {
        mList[position].apply {
            holder.timestamp.text = timestamp
            holder.title.text = title
            holder.content.text = content
        }
    }
}

/** 通用商品列表. 供多个页面使用 */
class GoodsSpecHolder(itemView: View) : UnoBasicViewHolder(itemView) {
    val txt: TextView by lazy { itemView.findViewById(R.id.item_txt) as TextView }
}

class GoodsSpecAdapter(private val mContext: Context) : UnoBasicAdapter<GoodsSpecHolder>() {
    private val mList = UnoConstants.GOODS_SPEC_LIST
    private val itemSelector = ItemSelector()
    override fun getItemCount(): Int = mList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsSpecHolder {
        return LayoutInflater.from(mContext)
                .run { inflate(R.layout.item_adapter_goods_spec, parent, false) }
                .let { GoodsSpecHolder(it) }
    }

    override fun onBindViewHolder(holder: GoodsSpecHolder, position: Int) {
        logger("on bind view holder pos: $position")
        holder.txt.text = mList[position]
        enableItemViewClick(holder, position)
        holder.itemView.setOnClickListener {
            itemSelector.trigger(it)
            itemListener?.invoke(position)
        }
        itemSelector.itemLast?.also { itemSelector.trigger(it) }
    }
}