package com.anshdeep.newsly.di

import com.anshdeep.newsly.api.NewsService
import com.anshdeep.newsly.data.NewsRemoteDataSource
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.inject.Singleton
import javax.net.ssl.*

@Module
class NetworkModule {

    companion object {
        private const val NAME_BASE_URL = "https://newsapi.org/v2/"
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient = getUnsafeOkHttpClient().build()


    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().client(okHttpClient).baseUrl(NAME_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun providesApiInterface(retrofit: Retrofit): NewsService = retrofit.create(
            NewsService::class.java)

    @Provides
    @Singleton
    fun providesRemoteDataSource(apiInterface: NewsService): NewsRemoteDataSource = NewsRemoteDataSource(apiInterface)

    private fun getUnsafeOkHttpClient(): OkHttpClient.Builder {

        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }


                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                }
            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier(object : HostnameVerifier {
                override fun verify(hostname: String, session: SSLSession): Boolean {
                    return true
                }
            })
            return builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}