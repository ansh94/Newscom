package com.example.ansh.modernnewsapp.utilities.extensions

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by ansh on 13/02/18.
 */
operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}