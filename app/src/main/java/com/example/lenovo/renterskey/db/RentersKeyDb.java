package com.example.lenovo.renterskey.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.lenovo.renterskey.vo.Products;

/**
 * Created by lenovo on 27-12-2017.
 */

@Database(entities = {Products.class}, version = 3)
public abstract class RentersKeyDb extends RoomDatabase{
     public static final String DB_NAME="renterskey_db";
     private static RentersKeyDb INSTANCE;
     private static Object LOCK=new Object();

     public static RentersKeyDb getINSTANCE(Context context){
          if(INSTANCE==null){
               synchronized (LOCK){
                    if(INSTANCE==null){
                         INSTANCE= Room.databaseBuilder(context.getApplicationContext()
                         ,RentersKeyDb.class,RentersKeyDb.DB_NAME).build();
                    }
               }
          }
          return INSTANCE;
     }
     public abstract ProductDao productDao();
}
