package com.skylightapp.MapAndLoc;

import com.google.android.datatransport.runtime.dagger.Provides;

import org.jetbrains.annotations.NotNull;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityRetainedComponent;
import dagger.hilt.android.scopes.ActivityRetainedScoped;

@Module
@InstallIn(ActivityRetainedComponent.class)
public class RepositoryModule {
    @NotNull
    public static final RepositoryModule INSTANCE;

    @Provides
    @ActivityRetainedScoped
    public MapRepository provideMapRepository(RemoteService remoteService){
        return new MapRepositoryImpl(remoteService);
    }

    public RepositoryModule(){
        //Empty constructor.
    }

    static {
        RepositoryModule repositoryModule = new RepositoryModule();
        INSTANCE = repositoryModule;
    }
}
