package paulusunexpendableturtle.rocketlabyrinth.game.modes;

import static paulusunexpendableturtle.rocketlabyrinth.statics.IconicConstants.iconicVel;
import static paulusunexpendableturtle.rocketlabyrinth.statics.IconicConstants.iconicStartLife;
import static paulusunexpendableturtle.rocketlabyrinth.statics.Strings.*;

public abstract class Mode{

    int startVel;

    protected int level;

    long startLife, curLife;

    public Mode(long life, int vel){
        startLife = life;
        curLife = life;

        startVel = vel;

        level = -1;
    }

    public long getStartLife() {return startLife;}

    public long getCurLife() {return curLife;}

    public int getStartVel() {return startVel;}

    public int getLevel() {return level;}

    public Mode update(long life, int vel) {
        ++level;
        return this;
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