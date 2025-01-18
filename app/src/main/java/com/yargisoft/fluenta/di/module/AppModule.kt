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
            MainPageMenuItem("Option 1", R.drawable.ic_menu_line),
            MainPageMenuItem("Option 2", R.drawable.ic_menu_line),
            MainPageMenuItem("Option 3", R.drawable.ic_menu_line),
            MainPageMenuItem("Option 4", R.drawable.ic_menu_line),
            MainPageMenuItem("Option 1", R.drawable.ic_menu_line),
            MainPageMenuItem("Option 2", R.drawable.ic_menu_line),
            MainPageMenuItem("Option 3", R.drawable.ic_menu_line),MainPageMenuItem("Option 1", R.drawable.ic_menu_line),
            MainPageMenuItem("Option 2", R.drawable.ic_menu_line),
            MainPageMenuItem("Option 3", R.drawable.ic_menu_line),
            MainPageMenuItem("Option 4", R.drawable.ic_menu_line),
            MainPageMenuItem("Option 1", R.drawable.ic_menu_line),
            MainPageMenuItem("Option 2", R.drawable.ic_menu_line),
            MainPageMenuItem("Option 3", R.drawable.ic_menu_line),MainPageMenuItem("Option 1", R.drawable.ic_menu_line),
            MainPageMenuItem("Option 2", R.drawable.ic_menu_line),
            MainPageMenuItem("Option 3", R.drawable.ic_menu_line),
            MainPageMenuItem("Option 4", R.drawable.ic_menu_line),
            MainPageMenuItem("Option 1", R.drawable.ic_menu_line),
            MainPageMenuItem("Option 2", R.drawable.ic_menu_line),
            MainPageMenuItem("Option 3", R.drawable.ic_menu_line),
            MainPageMenuItem("Option 4", R.drawable.ic_menu_line)
        )
    }

    @Provides
    fun provideMainPageAdapter(): MainPageMenuAdapter {
        return MainPageMenuAdapter()
    }

}
