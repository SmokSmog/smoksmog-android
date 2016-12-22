package com.antyzero.smoksmog.eventbus

import rx.Observable
import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject
import rx.subjects.Subject

class RxBus {

    private val _bus = SerializedSubject(PublishSubject.create<Any>())

    fun send(o: Any) {
        _bus.onNext(o)
    }

    fun toObserverable(): Observable<Any> {
        return _bus
    }
}
