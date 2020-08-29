package co.wishroll.utilities;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class StateData<T> {

    @NonNull
    public final AuthStatus status;

    @Nullable
    public final T data;

    @Nullable
    public final String message;


    public StateData(@NonNull AuthStatus status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> StateData<T> authenticated (@Nullable T data) {
        return new StateData<>(AuthStatus.AUTHENTICATED, data, null);
    }

    public static <T> StateData<T> error(@NonNull String msg, @Nullable T data) {
        return new StateData<>(AuthStatus.ERROR, data, msg);
    }

    public static <T> StateData<T> loading(@Nullable T data) {
        return new StateData<>(AuthStatus.LOADING, data, null);
    }

    public static <T> StateData<T> logout () {
        return new StateData<>(AuthStatus.NOT_AUTHENTICATED, null, null);
    }

    public enum AuthStatus { AUTHENTICATED, ERROR, LOADING, NOT_AUTHENTICATED}

}