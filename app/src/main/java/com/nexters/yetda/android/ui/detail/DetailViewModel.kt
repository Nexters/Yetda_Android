package com.nexters.yetda.android.ui.detail


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.nexters.yetda.android.domain.database.dao.HistoryDao
import com.nexters.yetda.android.util.SingleLiveEvent
import io.realm.Realm


class DetailViewModel : ViewModel() {

    private val TAG = javaClass.simpleName
    private val realm by lazy {
        Realm.getDefaultInstance()
    }

    private val _backBeforeActivityEvent = SingleLiveEvent<Any>()
    val backBeforeActivityEvent: LiveData<Any>
        get() = _backBeforeActivityEvent

    fun clickBackButton() {
        _backBeforeActivityEvent.call()
    }


    private var clickCount = 0

    private val _deleteBeforeActivityEvent = SingleLiveEvent<Any>()
    val deleteBeforeActivityEvent: LiveData<Any>
        get() = _deleteBeforeActivityEvent

    private val _toastBeforeActivityEvent = SingleLiveEvent<Any>()
    val toastBeforeActivityEvent: LiveData<Any>
        get() = _toastBeforeActivityEvent

    fun clickDeleteButton(id: Int) {
        when (clickCount) {
            0 -> {
                clickCount = 1
                _toastBeforeActivityEvent.call()
            }
            else -> {
                // 삭제
                HistoryDao(realm).deleteHistory(id)
                _deleteBeforeActivityEvent.call()
            }
        }
    }

}