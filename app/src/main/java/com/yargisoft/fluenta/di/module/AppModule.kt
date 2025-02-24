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
            MainPageMenuItem(R.id.oxfordWordsFragment, R.drawable.oxford),
            MainPageMenuItem(R.id.storyFragment, R.drawable.story_mode),
            MainPageMenuItem(R.id.mostCommonWordsFragment, R.drawable.most_common_c1_c2),
            MainPageMenuItem(R.id.mostCommonPhrasesFragment, R.drawable.most_common_phrases),
            MainPageMenuItem(R.id.listenAndLearnFragment, R.drawable.listen_and_learn),
            MainPageMenuItem(R.id.aiTutorFragment, R.drawable.ai_tutor),
            MainPageMenuItem(R.id.translatorFragment,  R.drawable.translator),
            MainPageMenuItem(R.id.favoritesFragment, R.drawable.favorites),
            MainPageMenuItem(R.id.myAccountFragment, R.drawable.account),

        )
    }

    @Provides
    fun provideMainPageAdapter(): MainPageMenuAdapter {
        return MainPageMenuAdapter()
    }

}
