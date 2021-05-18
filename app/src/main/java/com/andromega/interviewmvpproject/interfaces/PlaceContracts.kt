package com.andromega.interviewmvpproject.interfaces

import com.andromega.interviewmvpproject.modelclass.PlaceSearchModelClass
import com.andromega.interviewmvpproject.modelclass.Venue

interface PlaceContracts {

    interface Model {
        interface OnFinishedListener {
            fun onFinished(search: List<Venue>)
            fun onFailure(t: Throwable?)
        }

        fun getPlaceSearchModelClassList(
            onFinishedListener: OnFinishedListener?,
            ll: String,
            queryWord: String,
            clientId: String,
            clientSecret: String
        )
    }

    interface View {
        fun setDataToRecyclerView(placeArrayList: List<PlaceSearchModelClass>)
        fun onResponseFailure(throwable: Throwable?)
    }

    interface Presenter {
        fun onDestroy()

        fun requestDataFromServer(
            finishListener: Model.OnFinishedListener?,
            ll: String,
            queryWord: String,
            clientId: String,
            clientSecret: String
        )
    }
}