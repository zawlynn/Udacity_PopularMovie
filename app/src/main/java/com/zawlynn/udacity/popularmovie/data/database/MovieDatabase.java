package com.zawlynn.udacity.popularmovie.data.database;


import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.zawlynn.udacity.popularmovie.data.database.dao.MovieDao;
import com.zawlynn.udacity.popularmovie.data.database.entity.Movie;

@Database(entities = {Movie.class}, version = 2,exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();

    private static MovieDatabase instance;

    public static synchronized MovieDatabase getInstance(Application application) {
        if (instance == null) {
            instance = Room.databaseBuilder(application,
                    MovieDatabase.class, "movie_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
