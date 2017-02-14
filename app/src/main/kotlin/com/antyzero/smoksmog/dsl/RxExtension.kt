package com.antyzero.smoksmog.dsl

import rx.Observable

/**
 * Rx related
 */

fun <T> T.observable() = Observable.just(this)
