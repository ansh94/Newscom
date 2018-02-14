package com.example.ansh.modernnewsapp.data

import com.example.ansh.modernnewsapp.androidmanagers.NetManager
import com.example.ansh.modernnewsapp.ui.uimodels.News
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by ansh on 13/02/18.
 */

/*
The only thing that repository needs to know is that the data is coming from remote or local.
There is no need to know how we are getting those remote or local data.
 */
class NewsRepository @Inject constructor(var netManager: NetManager) {

    private val localDataSource = NewsLocalDataSource()
    private val remoteDataSource = NewsRemoteDataSource()

    fun getRepositories(): Observable<ArrayList<News>> {

        // Kotlin Note: operator let checks nullability and returns you a value inside it.
        netManager.isConnectedToInternet?.let {
            if (it) {

                /*
                Using .flatMap ,once remoteDataSource.getRepositories() emits item,
                that item will be mapped to new Observable that emits same item. That new
                Observable we created from Completable that saves the same emitted item in
                the local data store converting it to Single that emits the same emitted item.
                Cause we need to return Observable, we have to convert that Single to Observable.
                 */

                return remoteDataSource.getRepositories().flatMap {
                    return@flatMap localDataSource.saveRepositories(it)
                            .toSingleDefault(it)
                            .toObservable()
                }
            }
        }

        return localDataSource.getRepositories()
    }
}