package paulusunexpendableturtle.rocketlabyrinth.game.modes;

class MarathonHardMode extends Mode{

    MarathonHardMode(long life, int vel){
        super(life, vel);
    }

    @Override
    public Mode update(long life, int vel){
        curLife = startLife;
        ++level;
        return this;
    }

}
