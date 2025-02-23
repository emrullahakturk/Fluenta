package com.yargisoft.fluenta.data.repository

import com.yargisoft.fluenta.data.model.Story
import com.yargisoft.fluenta.data.model.StoryDao
import javax.inject.Inject

class StoryRepository @Inject constructor(private val storyDao: StoryDao) {

    suspend fun getRandomStory(): Story {
        return storyDao.getRandomStory()
    }

    suspend fun getRandomStoryByLevel(level: String): Story {
        return when (level) {
            "A1-A2" -> storyDao.getRandomStoryA1A2()
            "B1-B2" -> storyDao.getRandomStoryB1B2()
            "C1-C2" -> storyDao.getRandomStoryC1C2()
            else -> storyDao.getRandomStory() // Varsayılan olarak herhangi bir seviye
        }
    }

    suspend fun insertStories(stories: List<Story>) {
        storyDao.insertAll(stories)
    }
}
