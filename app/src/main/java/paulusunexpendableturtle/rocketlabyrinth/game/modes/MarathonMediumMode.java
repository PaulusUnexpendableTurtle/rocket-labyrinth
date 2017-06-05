package paulusunexpendableturtle.rocketlabyrinth.game.modes;

class MarathonMediumMode extends Mode{

    MarathonMediumMode(long life, int vel){
        super(life, vel);
        ++vel;
    }

    @Override
    public Mode update(long life, int vel){
        curLife = startLife + life;
        ++level;

        if(startVel > 4) --startVel;

        return this;
    }

}
