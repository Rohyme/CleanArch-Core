package com.tripl3dev.presentation

import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.junit.Test
import java.util.concurrent.TimeUnit

class RxTest {

    var numList = listOf(1, 3, 2, 4, 5, 6, 7, 8, 9, 10, 11)

    @Test
    fun testOperators() {
        Observable.range(1, 20)
                .filter {
                    return@filter it % 2 == 0
                }
                .map {
                    return@map "Even number is $it"
                }
                .subscribe(object : Observer<String> {
                    override fun onComplete() {
                        println("Done")
                    }

                    override fun onSubscribe(d: Disposable) {
                        println("Start subscribing")
                    }

                    override fun onNext(t: String) {
                        println(t)
                    }

                    override fun onError(e: Throwable) {
                        println("Error happened ${e.message}")
                    }
                })
    }


    @Test
    fun testJustOperator() {
        Observable.just(1, 2, 3, 4)
                .repeat(3)
                .subscribe(object : Observer<Int> {
                    override fun onComplete() {
                        println("Done")
                    }

                    override fun onSubscribe(d: Disposable) {
                        println("Start subscribing")
                    }

                    override fun onNext(t: Int) {
                        println("just No $t")
                    }

                    override fun onError(e: Throwable) {
                        println("Error happened ${e.message}")
                    }
                })
    }


    @Test
    fun testBufferAndBounce() {
        Observable.just(1,2,3,4,5,6,7,8)
//                .buffer(3)
                .debounce(1,TimeUnit.NANOSECONDS)
                .subscribe(object : Observer<Int> {
                    override fun onComplete() {
                        println("Done")
                    }

                    override fun onSubscribe(d: Disposable) {
                        println("Start subscribing")
                    }

                    override fun onNext(t : Int) {
                        println("just No $t")
                    }

                    override fun onError(e: Throwable) {
                        println("Error happened ${e.message}")
                    }
                })
    }

    @Test
    fun testTakeAndSkip() {
        Observable.just(1,2,3,4,5,6,7,8 ,2 ,3)
//                .buffer(3)
                .distinct()
                .subscribe(object : Observer<Int> {
                    override fun onComplete() {
                        println("Done")
                    }

                    override fun onSubscribe(d: Disposable) {
                        println("Start subscribing")
                    }

                    override fun onNext(t : Int) {
                        println("just No $t")
                    }

                    override fun onError(e: Throwable) {
                        println("Error happened ${e.message}")
                    }
                })
    }
}

