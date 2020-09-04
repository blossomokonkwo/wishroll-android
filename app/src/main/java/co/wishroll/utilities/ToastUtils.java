package co.wishroll.utilities;

import android.content.Context;

import com.muddzdev.styleabletoast.StyleableToast;

import co.wishroll.R;

public class ToastUtils {

    public static void showToast( Context context, String message){

        StyleableToast.makeText(context, message, R.style.wishrollToast).show();

    }
}
