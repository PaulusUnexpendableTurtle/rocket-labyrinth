package paulusunexpendableturtle.rocketlabyrinth.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import paulusunexpendableturtle.rocketlabyrinth.R;
import paulusunexpendableturtle.rocketlabyrinth.game.Save;
import paulusunexpendableturtle.rocketlabyrinth.statics.Bitmaps;

import static paulusunexpendableturtle.rocketlabyrinth.statics.Strings.from_save;
import static paulusunexpendableturtle.rocketlabyrinth.statics.Strings.key_mode;

public class MainMenuActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Bitmaps.tryDecodeResources(getResources());
        Save.readFromFile(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main_menu);
    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.button_new_game:
                startActivity(new Intent(this, MarathonMenuActivity.class));
                break;
            case R.id.button_continue:
                Intent intent = new Intent(this, GameActivity.class);
                intent.putExtra(key_mode, from_save);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy(){
        Log.d("Destroy", "MainMenu");
        super.onDestroy();
    }

}
