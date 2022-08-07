package com.navi.githubpulls.di

import com.navi.githubpulls.BuildConfig
import com.navi.githubpulls.network.GithubApiService
import com.navi.githubpulls.network.GithubNetworkSource
import com.navi.githubpulls.network.GithubNetworkSourceImpl
import com.navi.githubpulls.repository.GithubRepository
import com.navi.githubpulls.repository.GithubRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    private const val BASE_URL = "https://api.github.com"

    @Provides
    fun provideBaseUrl() = BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): GithubApiService = retrofit.create(GithubApiService::class.java)

    @Provides
    @Singleton
    fun provideNetworkSource(apiService: GithubApiService): GithubNetworkSource = GithubNetworkSourceImpl(apiService)

    @Provides
    @Singleton
    fun provideRepo(networkSource: GithubNetworkSource): GithubRepository = GithubRepositoryImpl(networkSource)

}