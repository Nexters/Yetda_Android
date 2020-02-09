package com.nexters.yetda.android.di

import com.nexters.yetda.android.birthday.BirthdayViewModel
import com.nexters.yetda.android.database.PersonDao
import com.nexters.yetda.android.gender.GenderViewModel
import com.nexters.yetda.android.name.NameViewModel
import com.nexters.yetda.android.price.PriceViewModel
import com.nexters.yetda.android.question.QuestionViewModel
import com.nexters.yetda.android.result.ResultViewModel
import com.nexters.yetda.android.home.HomeViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelPart = module {
    viewModel {
        HomeViewModel()
    }
    viewModel {
        NameViewModel()
    }
    viewModel {
        GenderViewModel()
    }
    viewModel {
        BirthdayViewModel()
    }
    viewModel {
        PriceViewModel()
    }
    viewModel {
        QuestionViewModel()
    }
    viewModel {
        ResultViewModel()
    }
}

var daoPart = module {
    factory<PersonDao> {
        PersonDao(get())
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
val diModule = listOf(viewModelPart, daoPart)