package paulusunexpendableturtle.rocketlabyrinth.game.modes;

class MarathonInsaneMode extends Mode{

    MarathonInsaneMode(long life, int vel){
        super(life, vel);
        ++vel;
    }

    @Override
    public Mode update(long life, int vel){
        curLife = startLife;
        ++level;

        if(startVel > 4) --startVel;

        return this;
    }

}
