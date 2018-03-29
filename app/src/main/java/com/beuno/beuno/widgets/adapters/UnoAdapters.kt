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
import com.beuno.beuno.shortcut.logger
import com.beuno.beuno.shortcut.snack

object UnoAdapters {

    /** 快捷方式, 初始化RecyclerView的网格适配器 */
    fun <T : RecyclerView.Adapter<*>> initGridAdapter(root: View, @IdRes itemId: Int,
                                                      ctx: Context, spanCount: Int, orientation: Int,
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
                                                        ctx: Context, orientation: Int,
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
 *
 */
private object ItemStatus {
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

// --------------------------------------------------------------------------------------------
//                                          homepage
// --------------------------------------------------------------------------------------------

/** 商品分类 */
class HomepageCategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val txt: TextView by lazy { itemView.findViewById(R.id.item_txt) as TextView }
    val img: ImageView by lazy { itemView.findViewById(R.id.item_img) as ImageView }
}

class HomepageCategoryAdapter(private val mContext: Context) : RecyclerView.Adapter<HomepageCategoryHolder>() {
    private val mList = UnoConstants.HOMEPAGE_CATEGORY_LIST
    override fun getItemCount(): Int = mList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomepageCategoryHolder {
        return LayoutInflater.from(mContext)
                .run { inflate(R.layout.item_adapter_homepage_category, parent, false) }
                .let { HomepageCategoryHolder(it) }
    }

    override fun onBindViewHolder(holder: HomepageCategoryHolder, position: Int) {
        mList[position].apply {
            holder.txt.text = second
            holder.img.setImageResource(first)
        }
    }
}

/** 品牌精选 */
class HomepageBrandHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val txt: TextView by lazy { itemView.findViewById(R.id.item_txt) as TextView }
    val img: ImageView by lazy { itemView.findViewById(R.id.item_img) as ImageView }
}

class HomepageBrandAdapter(private val mContext: Context) : RecyclerView.Adapter<HomepageBrandHolder>() {
    private val mList = UnoConstants.HOMEPAGE_BRAND_LIST
    override fun getItemCount(): Int = mList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomepageBrandHolder {
        return LayoutInflater.from(mContext)
                .run { inflate(R.layout.item_adapter_homepage_brand, parent, false) }
                .let { HomepageBrandHolder(it) }
    }

    override fun onBindViewHolder(holder: HomepageBrandHolder, position: Int) {
        mList[position].apply {
            holder.txt.text = second
            holder.img.setImageResource(first)
        }
    }
}

/** 个性推荐 */
class HomepageRecommendHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val txt: TextView by lazy { itemView.findViewById(R.id.item_txt) as TextView }
    val img: ImageView by lazy { itemView.findViewById(R.id.item_img) as ImageView }
    val price: TextView by lazy { itemView.findViewById(R.id.item_price) as TextView }
    val charge: TextView by lazy { itemView.findViewById(R.id.item_charge) as TextView }
    val discount: TextView by lazy { itemView.findViewById(R.id.item_discount) as TextView }
}

class HomepageRecommendAdapter(private val mContext: Context) : RecyclerView.Adapter<HomepageRecommendHolder>() {
    private val mList = UnoConstants.HOMEPAGE_RECOMMEND_LIST
    override fun getItemCount(): Int = mList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomepageRecommendHolder {
        return LayoutInflater.from(mContext)
                .run { inflate(R.layout.item_adapter_homepage_recommend, parent, false) }
                .let { HomepageRecommendHolder(it) }
    }

    override fun onBindViewHolder(holder: HomepageRecommendHolder, position: Int) {
        mList[position].apply {
            //            holder.txt.text = second
//            holder.img.setImageResource(first)
        }
    }
}

/** 分类 */
class CategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val txt: TextView by lazy { itemView.findViewById(R.id.item_txt) as TextView }
    val img: ImageView by lazy { itemView.findViewById(R.id.item_img) as ImageView }
    val engName: TextView by lazy { itemView.findViewById(R.id.item_eng_name) as TextView }
}

class CategorySubHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val list: RecyclerView by lazy { itemView.findViewById(R.id.list_category_sub) as RecyclerView }
}

class CategoryAdapter(private val mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mList = UnoConstants.CATEGORY_LIST

    init {
        ItemStatus.reset()
    }

    override fun getItemCount(): Int {
        val subItemCount = if (ItemStatus.noChild()) 0 else 1
        return mList.size + subItemCount
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ItemStatus.VIEW_TYPE_PARENT ->
                LayoutInflater.from(mContext)
                        .run { inflate(R.layout.item_adapter_category, parent, false) }
                        .let { CategoryHolder(it) }
            ItemStatus.VIEW_TYPE_CHILD ->
                LayoutInflater.from(mContext)
                        .run { inflate(R.layout.item_adapter_category_sub, parent, false) }
                        .let { CategorySubHolder(it) }
            else -> object : RecyclerView.ViewHolder(null) {}
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // 因为有中间插入项, 索引好特么恶心.
        if (holder is CategoryHolder) {
            val positionParent = if (ItemStatus.hasChild() && position > ItemStatus.indexFather()) position - 1
            else position
            mList[positionParent].apply {
                holder.txt.text = name
                holder.img.setImageResource(pic)
                holder.engName.text = engName
                holder.itemView.setBackgroundColor(bg)
            }
            holder.itemView.apply {
                isClickable = true
                setOnClickListener {
                    ItemStatus.trigger(this@CategoryAdapter, positionParent)
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
            ItemStatus.noChild() -> ItemStatus.VIEW_TYPE_PARENT
            ItemStatus.isChild(position) -> ItemStatus.VIEW_TYPE_CHILD
            else -> ItemStatus.VIEW_TYPE_PARENT
        }.also { logger("type -- pos: $position, status $it, prt 0 chd 1") }
    }

}

/** 分类二级列表 */
class CategoryLevel2Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val txt: TextView by lazy { itemView.findViewById(R.id.item_txt) as TextView }
    val img: ImageView by lazy { itemView.findViewById(R.id.item_img) as ImageView }
}

class CategoryLevel2Adapter(private val mContext: Context, private val mList: List<UnoCategorySub>) : RecyclerView.Adapter<CategoryLevel2Holder>() {
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
class CartHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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

class CartAdapter(private val mContext: Context) : RecyclerView.Adapter<CartHolder>() {
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
class NoticeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val timestamp: TextView by lazy { itemView.findViewById(R.id.item_timestamp) as TextView }
    val title: TextView by lazy { itemView.findViewById(R.id.item_title) as TextView }
    val content: TextView by lazy { itemView.findViewById(R.id.item_content) as TextView }
}

class NoticeAdapter(private val mContext: Context) : RecyclerView.Adapter<NoticeHolder>() {
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