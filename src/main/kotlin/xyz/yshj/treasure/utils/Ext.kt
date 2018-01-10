package xyz.yshj.treasure.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import io.reactivex.schedulers.Schedulers
import io.reactivex.Observable
import xyz.yshj.treasure.utils.HttpConfig.K_COOKIE
import xyz.yshj.treasure.utils.HttpConfig.K_HEADER


fun Any.json(): String {
    return Gson().toJson(this)
}

inline fun <reified T> String.toModel(): T {
    return Gson().fromJson(this, object : TypeToken<T>() {}.type)
}


inline fun <reified T> String.get(): Observable<T> {
    val type = object : TypeToken<T>() {}.type
    return Observable
            .just("")
            .map {

                val res = khttp.get(url = this, cookies = K_COOKIE)
                if (K_COOKIE.isEmpty()) {
                    K_COOKIE = res.cookies
                }
                res.text
            }
            .flatMap(ToModel<T>(false, type))
            .subscribeOn(Schedulers.io())
}

inline fun <reified T> String.post(data: Map<String, String> = HashMap()): Observable<T> {
    val type = object : TypeToken<T>() {}.type

    return Observable
            .just("")
            .map {
                val res = khttp.post(url = this, params = data, headers = K_HEADER, cookies = K_COOKIE, allowRedirects = true)

                if (K_COOKIE.isEmpty()) {
                    K_COOKIE = res.cookies
                }

                res.text
            }
            .flatMap(ToModel<T>(true, type))
            .subscribeOn(Schedulers.newThread())
//            .observeOn(JavaFxScheduler.platform())

}



