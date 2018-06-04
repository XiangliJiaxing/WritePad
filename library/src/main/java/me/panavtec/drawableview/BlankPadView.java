package me.panavtec.drawableview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BlankPadView extends LinearLayout {

    private Context mContext;
    private TextView tv_save;
    private TextView tv_clear;
    private TextView tv_withdraw;
    private TextView tv_type;
    private DrawableView drawableView;
    private IDrawOverListener iDrawOverListener;

    public BlankPadView(Context context) {
        this(context, null);
    }

    public BlankPadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlankPadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
        setListener();
    }

    private void initView(){
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_blank_pad, this, true);
        tv_save = (TextView)contentView.findViewById(R.id.tv_save);
        tv_clear = (TextView)contentView.findViewById(R.id.tv_clear);
        tv_withdraw = (TextView)contentView.findViewById(R.id.tv_withdraw);
        tv_type = (TextView)contentView.findViewById(R.id.tv_type);

        drawableView = findViewById(R.id.paintView);
        DrawableViewConfig config = new DrawableViewConfig();
        config.setStrokeColor(getResources().getColor(android.R.color.black));
        config.setShowCanvasBounds(true); // If the view is bigger than canvas, with this the user will see the bounds (Recommended)
        config.setStrokeWidth(20.0f);
        config.setMinZoom(1.0f);
        config.setMaxZoom(1.0f);
//        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
//        displayMetrics.widthPixels;
//        displayMetrics.heightPixels;
        config.setCanvasHeight(1080);
        config.setCanvasWidth(1920);
        drawableView.setConfig(config);
    }

    private void setListener(){
        tv_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                drawableView.setDrawingCacheEnabled(true);
                Bitmap drawingCache = drawableView.getDrawingCache();
                File storageDirectory;
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                    storageDirectory = Environment.getExternalStorageDirectory();
                }else{
                    storageDirectory = getContext().getFilesDir();
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
                String format = simpleDateFormat.format(Calendar.getInstance().getTime());
                File resultFile = new File(storageDirectory, format +".png");
                try{
                FileOutputStream fileOutputStream = new FileOutputStream(resultFile);
                drawingCache.compress(Bitmap.CompressFormat.PNG, 80, fileOutputStream);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }finally {
                    if(resultFile.exists()){
                        if(iDrawOverListener != null){
                            iDrawOverListener.drawSucess(resultFile.getAbsolutePath());
                        }
                    }else{
                        if(iDrawOverListener != null){
                            iDrawOverListener.saveFail();
                        }
                    }
                }
            }
        });
        tv_clear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                drawableView.clear();
            }
        });

        tv_withdraw.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                drawableView.undo();
            }
        });

        tv_type.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

//    @TargetApi(21)
//    public BlankPadView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public interface IDrawOverListener{
        void drawSucess(String filePath);
        void saveFail();
    }

    public void setListener(IDrawOverListener listener){
        this.iDrawOverListener = listener;
    }

    public void removeListener(){
        this.iDrawOverListener = null;
    }
}
