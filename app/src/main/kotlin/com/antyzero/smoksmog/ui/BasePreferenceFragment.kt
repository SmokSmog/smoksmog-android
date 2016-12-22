package com.antyzero.smoksmog.ui

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceFragment
import android.view.View

import com.trello.rxlifecycle.FragmentEvent
import com.trello.rxlifecycle.FragmentLifecycleProvider
import com.trello.rxlifecycle.RxLifecycle

import rx.Observable
import rx.subjects.BehaviorSubject

abstract class BasePreferenceFragment : PreferenceFragment(), FragmentLifecycleProvider {

    private val lifecycleSubject = BehaviorSubject.create<FragmentEvent>()

    override fun lifecycle(): Observable<FragmentEvent> {
        return lifecycleSubject.asObservable()
    }

    override fun <T> bindUntilEvent(event: FragmentEvent): Observable.Transformer<T, T> {
        return RxLifecycle.bindUntilFragmentEvent<T>(lifecycleSubject, event)
    }

    override fun <T> bindToLifecycle(): Observable.Transformer<T, T> {
        return RxLifecycle.bindFragment<T>(lifecycleSubject)
    }

    override fun onAttach(activity: android.app.Activity) {
        super.onAttach(activity)
        lifecycleSubject.onNext(FragmentEvent.ATTACH)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycleSubject.onNext(FragmentEvent.ATTACH)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleSubject.onNext(FragmentEvent.CREATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW)
    }

    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(FragmentEvent.START)
    }

    override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(FragmentEvent.RESUME)
    }

    override fun onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE)
        super.onPause()
    }

    override fun onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP)
        super.onStop()
    }

    override fun onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW)
        super.onDestroyView()
    }

    override fun onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY)
        super.onDestroy()
    }

    override fun onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH)
        super.onDetach()
    }
}
