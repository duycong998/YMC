

package com.example.myapplication.db;

import android.content.Context;
import android.provider.ContactsContract;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CongViec.class}, version =  1, exportSchema = false)
public abstract class CongViecDatabase extends RoomDatabase {

    public abstract CongViecDao congviecDao();

    private static volatile CongViecDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static CongViecDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CongViecDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CongViecDatabase.class, "congviec")
                            .allowMainThreadQueries()
                            .build();
                }
            }

        }
            return INSTANCE;
        }

}
