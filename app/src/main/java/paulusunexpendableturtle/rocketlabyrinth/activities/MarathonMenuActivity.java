package paulusunexpendableturtle.rocketlabyrinth.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import paulusunexpendableturtle.rocketlabyrinth.R;

import static paulusunexpendableturtle.rocketlabyrinth.statics.Strings.*;

public class MarathonMenuActivity extends AppCompatActivity {

    SparseArray<String> nameMap = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(nameMap.size() == 0)
            fillMap();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_marathon_menu);
    }

    private void fillMap(){
        nameMap.put(R.id.marathon_easy_button, mode_marathon_easy);
        nameMap.put(R.id.marathon_medium_button, mode_marathon_medium);
        nameMap.put(R.id.marathon_hard_button, mode_marathon_hard);
        nameMap.put(R.id.marathon_insane_button, mode_marathon_insane);
    }

    public void onClick(View view){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(key_mode, nameMap.get(view.getId()));
        startActivity(intent);
    }

    @Override
    protected void onDestroy(){
        Log.d("Destroy", "MarathonMenu");
        super.onDestroy();
    }

}
