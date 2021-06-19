package ru.andvl.bugtracker.di

import android.content.Context
import android.os.Build
import coil.ImageLoader
import coil.util.CoilUtils
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.andvl.bugtracker.BuildConfig
import ru.andvl.bugtracker.network.ApiHelper
import ru.andvl.bugtracker.network.ApiHelperImpl
import ru.andvl.bugtracker.network.ApiService
import ru.andvl.bugtracker.network.RequestInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor())
            .cache(CoilUtils.createDefaultCache(context))
            .build()
    }

    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context,
        okHttpClient: OkHttpClient,
    ): ImageLoader {
        return ImageLoader.Builder(context)
            .okHttpClient { okHttpClient }
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(if (BuildConfig.DEBUG) "http://10.0.2.2:5001" else "")
//            .baseUrl(if (BuildConfig.DEBUG) "https://dbe024cca2ee8f.localhost.run" else "")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper
}
