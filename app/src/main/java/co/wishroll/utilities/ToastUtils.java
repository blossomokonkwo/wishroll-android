package co.wishroll.utilities;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import co.wishroll.R;
import co.wishroll.WishRollApplication;

public class ToastUtils {

    public static void showToast( Context context, String message){

        Toast toast = new Toast(WishRollApplication.getContext());
        View view = LayoutInflater.from(context).inflate(R.layout.toast_banner_layout, null);
        TextView toastTextView = view.findViewById(R.id.textViewToast);
        toastTextView.setText(message);
        toast.setView(view);
        toast.setGravity(Gravity.FILL_HORIZONTAL, 0, 0);
        toast.setMargin(0,-1);

        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();

    }
}
