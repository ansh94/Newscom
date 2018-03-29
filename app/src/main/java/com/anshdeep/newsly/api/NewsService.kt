package com.anshdeep.newsly.api

import com.anshdeep.newsly.BuildConfig
import com.anshdeep.newsly.model.NewsResult
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*


/**
 * Created by ansh on 08/03/18.
 */
interface NewsService {

    @GET("top-headlines?country=in&apiKey=" + BuildConfig.NEWS_API_KEY)
    fun getTopHeadlines(): Observable<NewsResult>

    @GET("top-headlines?country=in&apiKey=" + BuildConfig.NEWS_API_KEY)
    fun getHeadlinesByCategory(@Query("category") category: String): Observable<NewsResult>

    @GET("top-headlines?language=en&apiKey=" + BuildConfig.NEWS_API_KEY)
    fun getHeadlinesByKeyword(@Query("q") keyword: String): Observable<NewsResult>


    /**
     * Kinda like a static block in Java
     */
    companion object Factory {
        private const val BASE_URL = "https://newsapi.org/v2/"

        // TODO: Move to DI.
        fun getNewsService(): NewsService {
            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getUnsafeOkHttpClient().build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(NewsService::class.java)
        }

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


}