/*
 ViewModel 用于在内存中驻留要展示的数据.
 Repository 作为ViewModel的代理类, 用于处理数据: 网络请求, 本地持久化.
            !!! 不要在主线程操作数据库.

 MediatorLiveData & Transformations 自定义LiveData获取公式.
 */