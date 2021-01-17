package custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class WaterProgressBar extends LinearLayout {

    private OnProgressChangeListener listener;

    public interface OnProgressChangeListener{
        void onTouch(boolean isTouch);
        void onProgressChange(float progress , boolean isTouch);
    }

    private float progress = 0;
    float width;
    float height;

    public WaterProgressBar(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public WaterProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    public WaterProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
    }

    public WaterProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setWillNotDraw(false);
    }

    public void setProgress(float progress) {
        this.progress = progress;
        postInvalidate();
    }

    public void setOnProgressChangeListener(OnProgressChangeListener listener){
        this.listener = listener;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int strokeWidth = 3;

        Paint progressPaint = new Paint();
        progressPaint.setColor(Color.parseColor("#7f661111"));
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.FILL);
        progressPaint.setStrokeWidth(strokeWidth);



         width = getWidth();
         height = getHeight();
        int margin = strokeWidth / 2;

        float top = (height - height*progress/100.0f);


        RectF rectF = new RectF(
                0,
                0,
                width,
                height
        );

        float sweepAngle = 360.0f*progress/100.0f;

        canvas.drawArc(rectF,(float)90.0f-sweepAngle/2.0f,sweepAngle,false,progressPaint);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        if(y < 0 || y>height){
            return false;
        }
        float percent = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                listener.onTouch(true);
                break;

            case MotionEvent.ACTION_MOVE:
                percent = 1- y/height;
                progress = 100 * percent;
                postInvalidate();
                listener.onProgressChange(progress,true);
                break;

            case MotionEvent.ACTION_UP:
                percent = 1- y/height;
                progress = 100 * percent;
                postInvalidate();
                listener.onProgressChange(progress,false);
                break;
        }
        return true;
    }

    private String convertToTimeFormat(int time) {
        time = time / 1000;
        String stringTime = "";
        int minutes = time / 60;
        int seconds = time % 60;
        if (minutes < 10) {
            stringTime += "0";
        }
        stringTime += minutes;
        stringTime += ":";

        if (seconds < 10) {
            stringTime += "0";
        }
        stringTime += seconds;
        return stringTime;
    }
}
