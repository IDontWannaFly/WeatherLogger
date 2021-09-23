package com.mark.weatherlogger.extensions

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

operator fun CompositeDisposable.plusAssign(item: Disposable){
    add(item)
}