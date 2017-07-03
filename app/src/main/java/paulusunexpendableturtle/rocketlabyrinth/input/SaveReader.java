package paulusunexpendableturtle.rocketlabyrinth.input;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import paulusunexpendableturtle.rocketlabyrinth.game.Level;
import paulusunexpendableturtle.rocketlabyrinth.game.modes.Mode;

public class SaveReader {

    private Context context;
    private JSONObject jsonObject;

    public SaveReader(Context context){
        this.context = context;
    }

    private void readJSON(){
        try{
            String json = "";

            if(!context.getFileStreamPath("save.json").exists()) {
                jsonObject = null;
                return;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(context.openFileInput("save.json")));

            String t;
            while((t = in.readLine()) != null)
                json += t;

            in.close();

            jsonObject = new JSONObject(json);

        }catch(JSONException|IOException e) {
            e.printStackTrace();
        }
    }

    public Mode readMode(){

        if(jsonObject == null)
            readJSON();
        if(jsonObject == null)
            return null;

        try {

            JSONObject jsonMode = jsonObject.getJSONObject("mode");

            Log.d("on mode reading", jsonMode.toString());

            Mode mode = Mode.ModeFactory.buildMode(jsonMode.getString("instanceOf").intern());

            if(mode == null) {
                Log.e("JSON Mode parse", "Factory returned null");
                return null;
            }

            mode.setArgs(jsonMode.getLong("startLife"), jsonMode.getLong("curLife"),
                    jsonMode.getInt("level"), jsonMode.getInt("startVel"));

            return mode;

        }catch(JSONException e){
            e.printStackTrace();
        }

        return null;

    }

    public Level readLevel(){

        if(jsonObject == null)
            readJSON();
        if(jsonObject == null)
            return null;

        try{

            JSONObject jsonLevel = jsonObject.getJSONObject("level");

            Log.d("on level reading", jsonLevel.toString());

            if(jsonLevel.getDouble("x") == -1)
                return null;

            Level level = new Level(translate(jsonObject.getJSONArray("field")),
                    jsonLevel.getInt("sizeX"), jsonLevel.getInt("sizeY"),
                    jsonLevel.getLong("startlife"), jsonLevel.getLong("life"),
                    jsonLevel.getInt("vel"));

            level.setCoords((float)jsonLevel.getDouble("x"), (float)jsonLevel.getDouble("y"));
            level.updateDirection(jsonLevel.getInt("dirx"), jsonLevel.getInt("dirx"));
            level.setSideAndStep(jsonLevel.getInt("side"), (float)jsonLevel.getDouble("step"));
            level.setTime(jsonLevel.getLong("firstTime"), jsonLevel.getLong("lastTime"));
            level.setComplete(jsonLevel.getBoolean("complete"));
            level.setSteps(jsonLevel.getInt("iterations"), jsonLevel.getInt("steps"));

            return level;

        }catch(JSONException e){
            e.printStackTrace();
        }

        return null;

    }

    private static boolean[][] translate(JSONArray array){
        try{

            boolean[][] ans = new boolean[array.length()][array.getJSONArray(0).length()];

            for(int i = 0; i < ans.length; ++i)
                for(int j = 0; j < ans[0].length; ++j)
                    ans[i][j] = array.getJSONArray(i).getBoolean(j);

            return ans;

        }catch(JSONException e){
            e.printStackTrace();
        }

        return null;
    }

}
