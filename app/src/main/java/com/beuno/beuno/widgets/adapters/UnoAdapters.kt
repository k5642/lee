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
import android.widget.TextView
import com.beuno.beuno.R
import com.beuno.beuno.alpha.UnoConstants
import com.beuno.beuno.shortcut.bold

object UnoAdapters {

    /** 快捷方式, 初始化RecyclerView的网格适配器 */
    fun <T: RecyclerView.Adapter<*>> initGridAdapter(root: View, @IdRes itemId: Int,
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
    fun <T: RecyclerView.Adapter<*>> initLinearAdapter(root: View, @IdRes itemId: Int,
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
//                                          homepage
// --------------------------------------------------------------------------------------------

/** 商品分类 */
class HomepageCategoryHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
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
class HomepageBrandHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
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
class HomepageRecommendHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
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
            holder.price.bold()
        }
    }
}

/** 分类 */
class CategoryHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val txt: TextView by lazy { itemView.findViewById(R.id.item_txt) as TextView }
    val img: ImageView by lazy { itemView.findViewById(R.id.item_img) as ImageView }
    val engName: TextView by lazy { itemView.findViewById(R.id.item_eng_name) as TextView }
}
class CategoryAdapter(private val mContext: Context) : RecyclerView.Adapter<CategoryHolder>() {
    private val mList = UnoConstants.CATEGORY_LIST
    override fun getItemCount(): Int = mList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        return LayoutInflater.from(mContext)
                .run { inflate(R.layout.item_adapter_category, parent, false) }
                .let { CategoryHolder(it) }
    }
    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        mList[position].apply {
            holder.txt.bold().text = name
            holder.img.setImageResource(pic)
            holder.engName.text = engName
        }
        holder.itemView.apply {
            isClickable = true
            setOnClickListener { mOnItemClickListener?.invoke() }
        }
    }

    private var mOnItemClickListener: (() -> Unit)? = null
    fun setOnItemClickListener(onItemClick: () -> Unit) {
        mOnItemClickListener = onItemClick
    }

}