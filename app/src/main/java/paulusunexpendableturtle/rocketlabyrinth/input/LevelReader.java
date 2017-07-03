package paulusunexpendableturtle.rocketlabyrinth.input;

import android.content.res.Resources;

import java.util.Scanner;

import paulusunexpendableturtle.rocketlabyrinth.game.Level;
import paulusunexpendableturtle.rocketlabyrinth.game.modes.Mode;

import static paulusunexpendableturtle.rocketlabyrinth.R.raw.*;

public class LevelReader {

    private Resources res;

    public LevelReader(Resources resources){
        res = resources;
    }

    public Level readLevel(Mode mode){

        int ind = getIndex(mode);
        if(ind < 0) return null;

        Scanner in = new Scanner(res.openRawResource(ind));

        int x = in.nextInt(), y = in.nextInt();
        boolean[][] field = new boolean[y][x];

        in.nextLine();

        for(int i = 0; i < y; i++){
            char[] t = in.nextLine().toCharArray();

            for(int j = 0; j < x; j++)
                field[i][j] = t[j] == '1';
        }

        in.close();

        return new Level(field, x, y, mode.getStartLife(), mode.getCurLife(), mode.getStartVel());

    }

    private static final int[] levelIds = {
        simple,
        exit,
        pixel,
        japanese,
        randlevel,
        levelx,
        levelzeta,
        randlevel1,
        randlevel2,
        chess,
        brandnew,
        platforms
    };

    private int getIndex(Mode mode){
        if(mode.getLevel() >= levelIds.length) return -1;
        return levelIds[mode.getLevel()];
    }

}
