package custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class LinearSeekBar extends LinearLayout {


    private OnTouchListener listener;

    public interface OnTouchListener{
        void actionDown(float progress);
        void actionMove(float progress);
        void actionUp(float progress);
    }

    private float width;
    private float height;
    float progressBarWidth;
    private int backgroundColor = Color.parseColor("#7f7f7f7f");
    private int progressColor = Color.parseColor("#000000");
    private int thumbColor = Color.parseColor("#000000");
    private float progress = 0;
    float margin;
    public LinearSeekBar(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public LinearSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    public LinearSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
    }

    public LinearSeekBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = getWidth();
        height = getHeight();

        margin = height/2;
        progressBarWidth = width - (2*margin);

        Paint paint = new Paint();
        paint.setColor(backgroundColor);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(height/3);
        paint.setAntiAlias(true);

        canvas.drawLine(margin,height/2,width-margin,height/2,paint);
        float centerX = progressBarWidth*(progress/100) + margin;

        paint.setColor(progressColor);
        canvas.drawLine(margin,height/2,centerX,height/2,paint);
        paint.setColor(thumbColor);
        paint.setStyle(Paint.Style.FILL);



        canvas.drawCircle(centerX,height/2,height/2,paint);
    }

    public void setBackgroundColor(int backgroundColor){
        this.backgroundColor = backgroundColor;
        postInvalidate();
    }
    public void setProgressColor(int progressColor){
        this.progressColor = progressColor;
        postInvalidate();
    }

    public void setThumbColor(int thumbColor){
        this.thumbColor = thumbColor;
        postInvalidate();
    }
    public void setProgress(float progress){
        if(progress<0){
            progress = 0;
        }else if(progress>100){
            progress = 100;
        }
        this.progress = progress;
        postInvalidate();
    }

    public void setOnTouchListener(OnTouchListener listener){
        this.listener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if(x>=margin && x<=width-margin){
            float percent = (x-margin)/(progressBarWidth);
            progress = percent*100;

        }
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(listener !=null){
                    listener.actionDown(progress);
                }
                break;
            case MotionEvent.ACTION_UP:
                if(listener != null){
                    listener.actionUp(progress);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(listener != null){
                    listener.actionMove(progress);
                }
                break;
        }
        postInvalidate();
        return true;
    }
}
