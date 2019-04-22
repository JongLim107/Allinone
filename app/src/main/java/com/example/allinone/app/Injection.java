package com.example.allinone.app;


import android.content.Context;

import com.example.allinone.data.login.LoginRepository;

import androidx.annotation.NonNull;

import static androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions.checkNotNull;

/**
 * Created by Jong Lim on 21/4/19.
 */
public class Injection {

    public static LoginRepository provideLoginRepository(@NonNull Context context) {
        checkNotNull(context);
        ToDoDatabase database = ToDoDatabase.getInstance(context);
        return LoginRepository.getInstance(
                FakeTasksRemoteDataSource.getInstance(),
                TasksLocalDataSource.getInstance(
                        new AppExecutors(),
                        database.taskDao()
                )
        );
    }
}
}
