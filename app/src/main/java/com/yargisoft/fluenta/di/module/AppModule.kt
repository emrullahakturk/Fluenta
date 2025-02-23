package com.yargisoft.fluenta.di.module

import com.yargisoft.fluenta.R
import com.yargisoft.fluenta.data.model.MainPageMenuItem
import com.yargisoft.fluenta.views.adapter.MainPageMenuAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMainPageItems(): List<MainPageMenuItem> {
        return listOf(
            MainPageMenuItem("main_to_oxford", R.drawable.oxford),
            MainPageMenuItem("main_to_story", R.drawable.story_mode),
            MainPageMenuItem("main_to_most_common_words", R.drawable.most_common_c1_c2),
            MainPageMenuItem("main_to_most_common_phrases", R.drawable.most_common_phrases),
            MainPageMenuItem("main_to_listen_and_learn", R.drawable.listen_and_learn),
            MainPageMenuItem("main_to_ai_tutor", R.drawable.ai_tutor),
            MainPageMenuItem("main_to_translator", R.drawable.translator),
            MainPageMenuItem("main_to_favorites", R.drawable.favorites),
            MainPageMenuItem("main_to_my_account", R.drawable.account),

        )
    }

    @Provides
    fun provideMainPageAdapter(): MainPageMenuAdapter {
        return MainPageMenuAdapter()
    }

}
