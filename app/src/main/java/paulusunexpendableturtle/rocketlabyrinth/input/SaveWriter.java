package paulusunexpendableturtle.rocketlabyrinth.input;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Set;

import paulusunexpendableturtle.rocketlabyrinth.game.Level;
import paulusunexpendableturtle.rocketlabyrinth.game.modes.Mode;

public class SaveWriter {

    private Context context;

    public SaveWriter(Context context){
        this.context = context;
    }

    public void write(Mode mode, Level level){
        try{

            HashMap<String, Object> mod = new HashMap<>(),
                    lev = new HashMap<>();

            put(mod, mode);
            put(lev, level);

            JSONObject res = new JSONObject();

            put(res, "mode", mod);
            put(res, "level", lev);

            res.put("field", jsonArray(level.getField()));

            File save = context.getFileStreamPath("save.json");

            if(!save.delete())
                Log.e("WTF", "File deletion failed");
            if(!save.createNewFile())
                Log.e("WTF", "File creation failed");

            OutputStreamWriter out = new OutputStreamWriter(context.openFileOutput("save.json", Context.MODE_PRIVATE));
            out.write(res.toString());
            Log.d("JSON", res.toString());
            out.close();

            Log.d("JSON Location", context.getFilesDir().getCanonicalPath());

        }catch(IOException|JSONException e){
            e.printStackTrace();
        }
    }

    private static JSONArray jsonArray(boolean[][] field) throws JSONException{
        JSONArray res = new JSONArray();

        JSONArray tmp;
        for(boolean[] t : field){
            tmp = new JSONArray();

            for(boolean b : t)
                tmp.put(b);

            res.put(tmp);
        }

        return res;
    }

    private static void put(HashMap<String, Object> map, Object obj) throws JSONException{
        if(obj instanceof Mode) {
            map.put("instanceOf", Mode.getStringName(obj.getClass().getName()));
            Mode.translateToMap(map, (Mode)obj);
        }
        else
            Level.translateToMap(map, (Level)obj);
    }

    private static void put(JSONObject res, String son_name, HashMap<String, Object> map) throws JSONException{
        JSONObject obj = new JSONObject();

        Set<HashMap.Entry<String, Object>> set = map.entrySet();
        for(HashMap.Entry<String, Object> e : set)
            obj.put(e.getKey(), e.getValue());

        res.put(son_name, obj);
    }

}
