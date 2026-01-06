package com.proyectoPdm.seashellinc.di

import android.content.Context
import com.proyectoPdm.seashellinc.data.database.SeaShellChemistryDatabase
import com.proyectoPdm.seashellinc.data.database.daos.CompoundDao
import com.proyectoPdm.seashellinc.data.database.daos.UserDao
import com.proyectoPdm.seashellinc.data.remote.ApiService
import com.proyectoPdm.seashellinc.data.repository.UserRepository
import com.proyectoPdm.seashellinc.data.database.CompoundDatabase
import com.proyectoPdm.seashellinc.utils.ConnectivityHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CompoundModule {
    private const val BASE_URL = "http://137.184.61.16:80/api/SeaShellCalculator/" //TODO: Agregar la url de la api (investigar como aplicar variables de entorno)

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSeaShellChemistryApiService(retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSeaShellChemistryRepository(userDao : UserDao, molarMassDao : CompoundDao, apiService: ApiService) : UserRepository {
        return UserRepository(userDao, molarMassDao, apiService)
    }

    @Provides
    @Singleton
    fun provideConnectivityHelper(@ApplicationContext context: Context) : ConnectivityHelper{
        return ConnectivityHelper(context)
    }
}
