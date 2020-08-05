package co.wishroll.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckedTextView;
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



                        /*bottomSheetConcernView.findViewById(R.id.concernChecking).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()){
                                    case (R.id.concern_1):
                                        CheckedTextView concern = findViewById(R.id.concern_1);
                                        concern.toggle();
                                        break;

                                    case (R.id.concern_2):
                                        CheckedTextView concern2 = findViewById(R.id.concern_2);
                                        concern2.toggle();
                                        break;

                                    case (R.id.concern_3):
                                        CheckedTextView concern3 = findViewById(R.id.concern_3);
                                        concern3.toggle();
                                        break;

                                    case (R.id.concern_4):
                                        CheckedTextView concern4 = findViewById(R.id.concern_4);
                                        concern4.toggle();
                                        break;

                                    case (R.id.concern_5):
                                        CheckedTextView concern5 = findViewById(R.id.concern_5);
                                        concern5.toggle();
                                        break;

                                    case (R.id.concern_6):
                                        CheckedTextView concern6 = findViewById(R.id.concern_6);
                                        concern6.toggle();
                                        break;
                                    case (R.id.concern_7):
                                        CheckedTextView concern7 = findViewById(R.id.concern_7);
                                        concern7.toggle();
                                        break;
                                    case (R.id.concern_8):
                                        CheckedTextView concern8 = findViewById(R.id.concern_8);
                                        concern8.toggle();
                                        break;
                                    default:

                                        break;
                                }
                            }
                        });*/


                    }
                });


            }




}