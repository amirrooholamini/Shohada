package activities;

import android.support.v7.app.AppCompatActivity;

import com.i3center.android.shohada.App;

public class ActivityParent extends AppCompatActivity {
    @Override
    protected void onResume() {
        App.setCurrentActivity(this);
        super.onResume();
    }
}
