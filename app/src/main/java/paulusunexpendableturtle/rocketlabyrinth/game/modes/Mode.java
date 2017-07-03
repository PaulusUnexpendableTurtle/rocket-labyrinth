package paulusunexpendableturtle.rocketlabyrinth.game.modes;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static paulusunexpendableturtle.rocketlabyrinth.statics.IconicConstants.iconicVel;
import static paulusunexpendableturtle.rocketlabyrinth.statics.IconicConstants.iconicStartLife;
import static paulusunexpendableturtle.rocketlabyrinth.statics.Strings.*;

public abstract class Mode implements Cloneable{

    int startVel;

    protected int level;

    long startLife, curLife;

    Mode(long life, int vel){
        startLife = life;
        curLife = life;

        startVel = vel;

        level = -1;
    }

    public void setArgs(long startLife, long curLife, int level, int startVel){
        this.startLife = startLife;
        this.curLife = curLife;
        this.level = level;
        this.startVel = startVel;
    }

    public long getStartLife() {return startLife;}

    public long getCurLife() {return curLife;}

    public int getStartVel() {return startVel;}

    public int getLevel() {return level;}

    public Mode update(long life, int vel) {
        ++level;
        return this;
    }

    public static String getStringName(String class_name){
        return classNameToTemplate.get(class_name);
    }

    private static HashMap<String, String> classNameToTemplate = new HashMap<>();
    static{
        classNameToTemplate.put(MarathonEasyMode.class.getName(), mode_marathon_easy);
        classNameToTemplate.put(MarathonMediumMode.class.getName(), mode_marathon_medium);
        classNameToTemplate.put(MarathonHardMode.class.getName(), mode_marathon_hard);
        classNameToTemplate.put(MarathonInsaneMode.class.getName(), mode_marathon_insane);
    }

    public static void translateToMap(Map<String, Object> map, Mode mode){
        try{
            mode = (Mode)mode.clone();

            String[] name = {"startLife", "curLife", "level", "startVel"};

            Field[] fields = new Field[4];
            for(int i = 0; i < 4; ++i)
                fields[i] = Mode.class.getDeclaredField(name[i]);

            for(Field f : fields)
                map.put(f.getName(), f.get(mode));

        }catch(IllegalAccessException|CloneNotSupportedException|NoSuchFieldException e){
            e.printStackTrace();
        }
    }

    public static class ModeFactory{
        public static Mode buildMode(String template){
            switch(template){
                case mode_marathon_easy:
                    return new MarathonEasyMode(iconicStartLife, iconicVel);
                case mode_marathon_medium:
                    return new MarathonMediumMode(iconicStartLife, iconicVel);
                case mode_marathon_hard:
                    return new MarathonHardMode(iconicStartLife, iconicVel);
                case mode_marathon_insane:
                    return new MarathonInsaneMode(iconicStartLife, iconicVel);
                default:
                    return null;
            }
        }
    }

}