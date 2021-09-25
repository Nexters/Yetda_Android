package com.nexters.yetda.android.domain.database.dao

import androidx.lifecycle.LiveData
import com.nexters.yetda.android.domain.database.RealmUtil
import com.nexters.yetda.android.domain.database.model.Question
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlin.random.Random

class QuestionDao(private val mRealm: Realm) {

    private val TAG = javaClass.simpleName

    fun findAllQuestions(): LiveData<RealmResults<Question>> {
        return RealmUtil.asLiveData(
            mRealm.where<Question>(Question::class.java)
                .findAllAsync()
        )
    }

    fun findQuestionById(id: Int): Question? {
        return mRealm.where<Question>()
            .equalTo("id", id)
            .findFirst()

    }

    fun initAskedStatus() {
        mRealm.executeTransaction {
            val result = it.where<Question>()
                .equalTo("isAsked", true)
                .findAll()
            for (q in result) {
                q.isAsked = false
            }
        }
    }

    fun findQuestion(tags: ArrayList<String>): Question? {
        val tagList = arrayOfNulls<String>(tags.size)
        tags.toArray(tagList)
        val results: RealmResults<Question> =
            mRealm.where<Question>()
                .equalTo("isAsked", false)
                .not()
                .beginGroup()
                .`in`("tag", tagList)
                .endGroup()
                .findAll()

        //Randomly pick one
        val r = Random(System.nanoTime())
        // todo : error ::: Random range is empty: [0, 0).
        val id = r.nextInt(results.size)

        mRealm.executeTransaction {
            results[id]?.isAsked = true
        }
        return results[id]
    }

    fun deleteAll() {
        val result = mRealm.where<Question>().findAll()
        mRealm.executeTransaction {
            result.deleteAllFromRealm()
        }
    }

    fun addQuestion(
        _id: Int,
        _question: String,
        _tag: String
    ) {
        mRealm.executeTransaction {
            val currentId = it.where<Question>(Question::class.java).max("id")
            val nextId = if (currentId == null || currentId == 0) 1 else currentId.toInt() + 1

            val item = it.createObject<Question>(nextId)
            item.question = _question
            item.tag = _tag
            it.copyToRealm(item)
        }
    }
}