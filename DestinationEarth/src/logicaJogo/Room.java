package logicaJogo;

import java.util.*;

public class Room {

    private final String name;
    private final HashMap<Integer, Room> exits;

    public Room(String name) {
        exits = new HashMap<>();
        this.name = name;
    }

    public HashMap<Integer, Room> getExits() {
        return exits;
    }

    public void setExits(int num, Room neighbor) {
        exits.put(num, neighbor);
    }

    public String getName() {
        return name;
    }
}