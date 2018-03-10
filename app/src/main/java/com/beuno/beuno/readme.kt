import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations

/*
Google架构扩展包官方文档:
    https://developer.android.com/topic/libraries/architecture/guide.html#recommended_app_architecture
LiveData:
    使用场景: Model层初始化完成, 通知UI
    解说: 丫是一个数据集, 在数据变动时, 触发监听, 通知UI组件.
         也可以用于监听组件的生命周期, 及时释放资源.
    使用: 与RxJava2共用, 引用库 android.arch.lifecycle:reactivestreams

Agera:
    Google的响应库.


 */

object IdLiveData : LiveData<Int>()
object NameLiveData : LiveData<String>()

fun foo() {
    val ld = NameLiveData
    Transformations.map(ld) {
        input: String? ->
        5
    }
    Transformations.switchMap(ld) {
        input: String? ->
        IdLiveData
    }
}