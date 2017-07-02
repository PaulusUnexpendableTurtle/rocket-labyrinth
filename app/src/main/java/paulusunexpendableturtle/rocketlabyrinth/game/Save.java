package paulusunexpendableturtle.rocketlabyrinth.game;

import android.content.res.Resources;

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

    //TODO: use it somewhere

    public static Mode getSavedMode(){
        return mode;
    }

    public static Level getSavedLevel(){
        return level;
    }

    public static void readFromFile(Resources resources){
        SaveReader reader = new SaveReader(resources);
        loadSave(reader.readMode(), reader.readLevel());
    }

    public static void writeToFile(Resources resources) {
        (new SaveWriter(resources)).write(mode, level);
    }

}
