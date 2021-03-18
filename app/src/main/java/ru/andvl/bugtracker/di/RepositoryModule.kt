package ru.andvl.bugtracker.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ru.andvl.bugtracker.network.ApiHelper
import ru.andvl.bugtracker.persistence.UserDao
import ru.andvl.bugtracker.presentation.datastore.DataStoreManager
import ru.andvl.bugtracker.repository.MainRepository

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun providesMainRepository(
        apiHelper: ApiHelper,
        userDao: UserDao,
        dataStoreManager: DataStoreManager,
    ) = MainRepository(
        apiHelper,
        userDao,
        dataStoreManager,
    )
}
