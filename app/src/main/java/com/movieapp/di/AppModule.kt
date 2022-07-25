package com.movieapp.di

import com.movieapp.network.MoviesApi
import com.movieapp.repository.MoviesRepository
import com.movieapp.repository.MoviesRepositoryImpl
import com.movieapp.utils.Constants.API_BASE_URL
import com.movieapp.utils.ResponseHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideResponseHandler() = ResponseHandler()

    @Provides
    @Singleton
    fun provideMoviesApi(): MoviesApi = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MoviesApi::class.java)

    @Provides
    @Singleton
    fun providePopularMoviesRepository(
        responseHandler: ResponseHandler,
        api: MoviesApi
    ) : MoviesRepository = MoviesRepositoryImpl(responseHandler, api)

}