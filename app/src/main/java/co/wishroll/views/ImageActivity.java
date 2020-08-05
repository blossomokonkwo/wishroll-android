package co.wishroll.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import co.wishroll.R;

public class ImageActivity extends AppCompatActivity {

    ImageView backButton;
    ImageView moreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        backButton = findViewById(R.id.backImageView);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        moreButton = findViewById(R.id.moreImageView);
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        final BottomSheetDialog concerns = new BottomSheetDialog(
                                ImageActivity.this, R.style.BottomSheetDialogTheme
                        );

                        final View bottomSheetConcernView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet_concerns,
                                (LinearLayout)findViewById(R.id.bottomSheetConcernsContainer));

                        bottomSheetConcernView.findViewById(R.id.reportConcernButton).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(ImageActivity.this, "Post has been reported", Toast.LENGTH_LONG).show();
                                concerns.dismiss();


                            }
                        });

                        bottomSheetConcernView.findViewById(R.id.cancelConcernButton).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                concerns.dismiss();
                            }
                        });


                    }
                });


            }




}