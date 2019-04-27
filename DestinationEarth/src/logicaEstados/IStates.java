package logicaEstados;

import logicaJogo.*;
import logicaJogo.crewMembers.*;

public interface IStates {

    // AwaitBeginning
    public IStates start(); //Configured in GameData constructor

    //AwaitCrewMembersSelection
    public IStates selectCM(int x);

    //AwaitThirdTokenPlacementCM1
    public IStates thirdTokenCM1(int x);

    //AwaitThirdTokenPlacementCM2
    public IStates thirdTokenCM2(int x);

    //MoveJourneyTracker
    public IStates moveJourneyTacker();

    //AwaitScanningPhase
    //AwaitAlienSpawn
    public IStates alienPlacment();

    //AwaitCrewPhase
    public IStates attack();

    public IStates move();

    public IStates noAP();

    public IStates seal();

    public IStates trap();

    public IStates gameOver();

    //AwaitMove
    public IStates moveCM(Room r, int cm);

    //AwaitDieRoll
    public IStates attackAlien(CrewMember cm, int x);

    public IStates noAlien();

    //GameOver
    public IStates replay();
}
