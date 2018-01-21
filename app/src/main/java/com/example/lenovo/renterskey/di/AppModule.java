package com.example.lenovo.renterskey.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.lenovo.renterskey.Networking.ApiInterface;
import com.example.lenovo.renterskey.db.ProductDao;
import com.example.lenovo.renterskey.db.RentersKeyDb;
import com.example.lenovo.renterskey.util.LiveDataCallAdapterFactory;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * Created by lenovo on 28-12-2017.
 */

@Module(includes = ViewModelModule.class)
public class AppModule {

    @Singleton
    @Provides
    ApiInterface provideApiInterface(){
        OkHttpClient.Builder okhttpBuilder=new OkHttpClient.Builder();
        okhttpBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request=chain.request();
                Request newRequest=newRequest=request.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type","application/json")
                        .build();

                return chain.proceed(newRequest);
            }
        });
        return  new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(okhttpBuilder.build())
                .build()
                .create(ApiInterface.class);
    }

    @Singleton
    @Provides
    RentersKeyDb provideDb(Application app){
        return Room.databaseBuilder(app,RentersKeyDb.class,"renterskey.db").build();
    }

    @Singleton
    @Provides
    ProductDao provideProductDao(RentersKeyDb db){
        return db.productDao();
    }
}
