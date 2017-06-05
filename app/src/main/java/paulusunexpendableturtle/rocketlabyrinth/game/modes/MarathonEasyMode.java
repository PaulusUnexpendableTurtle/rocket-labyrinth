package paulusunexpendableturtle.rocketlabyrinth.game.modes;

class MarathonEasyMode extends Mode{

    MarathonEasyMode(long life, int vel){
        super(life, vel);
    }

    @Override
    public Mode update(long life, int vel){
        curLife = startLife + life;
        ++level;
        return this;
    }

}
