package com.yargisoft.fluenta.data.repository

import com.yargisoft.fluenta.data.model.OxfordWord
import com.yargisoft.fluenta.data.model.OxfordWordDao
import javax.inject.Inject


class OxfordWordRepository @Inject constructor(
    private val oxfordWordDao: OxfordWordDao
) {
    suspend fun getRandomOxfordWord(): OxfordWord {
        return oxfordWordDao.getRandomOxfordWord()
    }
}
