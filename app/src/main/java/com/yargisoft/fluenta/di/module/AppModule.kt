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
            MainPageMenuItem("Option 1", R.drawable.oxford_a1),
            MainPageMenuItem("Option 1", R.drawable.most_common_c1_c2),
            MainPageMenuItem("Option 1", R.drawable.most_common_phrases),
            MainPageMenuItem("Option 1", R.drawable.listen_and_learn),
            MainPageMenuItem("Option 1", R.drawable.ai_tutor),
            MainPageMenuItem("Option 1", R.drawable.translator),
            MainPageMenuItem("Option 1", R.drawable.favorites),
            MainPageMenuItem("Option 1", R.drawable.upgrade_pro),
            MainPageMenuItem("Option 1", R.drawable.account),
            MainPageMenuItem("Option 1", R.drawable.settings),
            MainPageMenuItem("Option 1", R.drawable.feedback),
            MainPageMenuItem("Option 1", R.drawable.about_us),
        )
    }

    @Provides
    fun provideMainPageAdapter(): MainPageMenuAdapter {
        return MainPageMenuAdapter()
    }

}
