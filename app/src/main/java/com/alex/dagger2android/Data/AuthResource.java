package com.alex.dagger2android.Data;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AuthResource<T> {

    @NonNull
    public final Status status;

    @Nullable
    public final T data;

    @Nullable
    public final String message;

    public AuthResource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> AuthResource<T> authenticated(@Nullable T data){
        return new AuthResource<>(Status.AUTHENTICATED, data, null);
    }

    public static <T> AuthResource<T> error(String msg, @Nullable T data){
        return new AuthResource<>(Status.ERROR, data, msg);
    }

    public static <T> AuthResource<T> loading(@Nullable T data){
        return new AuthResource<>(Status.LOADING, data,null);
    }
    public static <T> AuthResource<T> logout(){
        return new AuthResource<>(Status.NOT_AUTHENTICATED, null,null);
    }


    public enum Status{
        AUTHENTICATED,
        ERROR,
        LOADING,
        NOT_AUTHENTICATED
    }
}
