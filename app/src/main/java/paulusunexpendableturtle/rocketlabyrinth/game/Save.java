package paulusunexpendableturtle.rocketlabyrinth.game;

import android.content.Context;

import paulusunexpendableturtle.rocketlabyrinth.game.modes.Mode;
import paulusunexpendableturtle.rocketlabyrinth.input.SaveReader;
import paulusunexpendableturtle.rocketlabyrinth.input.SaveWriter;

public abstract class Save {

    private static Mode mode;
    private static Level level;

    public static void loadSave(Mode m, Level l){
        mode = m;
        level = l;
    }

    public static Mode getSavedMode(){
        return mode;
    }

    public static Level getSavedLevel(){
        return level;
    }

    public static void readFromFile(Context context){
        SaveReader reader = new SaveReader(context);
        loadSave(reader.readMode(), reader.readLevel());
    }

    public static void writeToFile(Context context) {
        (new SaveWriter(context)).write(mode, level);
    }

}
