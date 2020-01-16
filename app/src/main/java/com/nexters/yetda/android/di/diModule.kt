package com.nexters.yetda.android.di

import com.nexters.yetda.android.name.NameViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelPart = module {
    viewModel {
        NameViewModel()
    }
}

//var adapterPart = module {
//    factory {
//        MainSearchRecyclerViewAdapter()
//    }
//}
//
//var modelPart = module {
//    factory<DataModel> {
//        DataModelImpl(get())
//    }
//}
val diModule = listOf(viewModelPart)