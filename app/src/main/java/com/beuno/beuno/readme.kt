import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations

/**
 * todo 设计需求
 * 登录方式, 传统登录, 短信验证码, 微信登录
 * 折扣方式,
 *
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