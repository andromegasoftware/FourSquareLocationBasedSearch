package com.andromega.interviewmvpproject

import com.andromega.interviewmvpproject.interfaces.PlaceContracts
import com.andromega.interviewmvpproject.modelclass.Venue

class SearchPresenter(
    var placeSearchListView: PlaceContracts.View,
    var placeListModel: PlaceSearchListModel = PlaceSearchListModel()
) : PlaceContracts.Presenter, PlaceContracts.Model.OnFinishedListener {


    override fun onDestroy() {

    }


    override fun requestDataFromServer(
        finishListener: PlaceContracts.Model.OnFinishedListener?,
        ll: String,
        queryWord: String,
        clientId: String,
        clientSecret: String
    ) {
        placeListModel.getPlaceSearchModelClassList(finishListener,ll, queryWord, clientId, clientSecret)
    }

    override fun onFinished(search: List<Venue>) {

    }

    override fun onFailure(t: Throwable?) {

    }

}
