package uz.coderodilov.kattabozortest.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.coderodilov.kattabozortest.api.ApiConfig
import uz.coderodilov.kattabozortest.api.ApiService
import uz.coderodilov.kattabozortest.utils.NetworkStateListener
import javax.inject.Singleton

/* 
* Created by Coder Odilov on 26/07/2023
*/

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideApiService() : ApiService {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideNetworkStateListener(@ApplicationContext context: Context):NetworkStateListener{
        return NetworkStateListener(context)
    }

}