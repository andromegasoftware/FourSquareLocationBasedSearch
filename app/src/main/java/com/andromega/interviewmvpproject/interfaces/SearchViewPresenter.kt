package com.andromega.interviewmvpproject.interfaces

import com.andromega.interviewmvpproject.modelclass.Venue

interface SearchViewPresenter {

    fun onDataCompleteFromApi(search: List<Venue>)
    fun onDataErrorFromApi(throwable: Throwable)
}