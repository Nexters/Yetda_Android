package com.nexters.yetda.android.di

import com.nexters.yetda.android.ui.birthday.BirthdayViewModel
import com.nexters.yetda.android.database.PersonDao
import com.nexters.yetda.android.ui.detail.DetailViewModel
import com.nexters.yetda.android.ui.gender.GenderViewModel
import com.nexters.yetda.android.ui.name.NameViewModel
import com.nexters.yetda.android.ui.price.PriceViewModel
import com.nexters.yetda.android.ui.question.QuestionViewModel
import com.nexters.yetda.android.ui.result.ResultViewModel
import com.nexters.yetda.android.ui.home.HomeViewModel
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
    viewModel {
        DetailViewModel()
    }
}

val daoPart = module {
    factory {
        PersonDao(get())
    }
}

val diModule = listOf(viewModelPart, daoPart)