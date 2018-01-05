package xyz.yshj.treasure.utils


import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.functions.Function

import java.lang.reflect.Type


/**
 * 把返回的数据转换为对象
 */

class ToModel<T>(private val _debug: Boolean, private val type: Type?) : Function<String, Observable<T>> {
    private val gson = Gson()
    private val TAG = "ToModel"

    override fun apply(decodeData: String): Observable<T> {
        if (_debug) {
//            println("返回的数据.." + decodeData)
        }

        return if (type?.typeName == "java.lang.String") {
            Observable.just(decodeData as T)
        } else {
            val data = gson.fromJson<T>(decodeData, type)
            Observable.just(data)
        }
    }
}
