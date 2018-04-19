/*
 ViewModel 用于在内存中驻留要展示的数据.
 Repository 作为ViewModel的代理类, 用于处理数据: 网络请求, 本地持久化.
            !!! 不要在主线程操作数据库.

 MediatorLiveData & Transformations 自定义LiveData获取公式.
 */

/*
UserInfo 用户信息, 卖家需要资质, 买东西随便.

    Seller 卖家, 上架商品的功能让后台给个网页实现?
        SellerRecommend 推荐商户
        Goods 商品
            GoodsSpec 商品规格

    Buyer 买家, 基本相当于用户, 当前App是买家用的
        Taker 收货人, 关联买家, 买家可以存多个收货人信息
        Cart 购物车, 目前没用
            CartItem

    Message & Notice 消息通知
    Address 地址
    Cache 文件缓存, 比如图片文件夹路径之类的.
 */