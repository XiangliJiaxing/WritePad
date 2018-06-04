package me.panavtec.drawableview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import me.panavtec.drawableview.R;

public class PadActivity extends AppCompatActivity {

    public static final String EXTRA_FILE_PATH = "extraFilePath";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_pad);

        final BlankPadView blankPadView = findViewById(R.id.pad);
        BlankPadView.IDrawOverListener listener = new BlankPadView.IDrawOverListener() {
            @Override
            public void drawSucess(String filePath) {
                blankPadView.removeListener();
                Intent intent = getIntent();
                intent.putExtra(EXTRA_FILE_PATH, filePath);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void saveFail() {
                blankPadView.removeListener();
                Intent intent = getIntent();
//                intent.putExtra(EXTRA_FILE_PATH, filePath);
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        };
        blankPadView.setListener(listener);
    }
}
