package paulusunexpendableturtle.rocketlabyrinth.activities;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import paulusunexpendableturtle.rocketlabyrinth.views.GameView;
import static paulusunexpendableturtle.rocketlabyrinth.statics.Strings.key_mode;

public class GameActivity extends AppCompatActivity {

    GameView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        view = new GameView(this);
        view.set(getIntent().getStringExtra(key_mode));
        setContentView(view);
    }

}
