package ru.ifmo.md.colloquium2;

/**
 * Created by izban on 11.11.14.
 */
public class Candidate {
    int id;
    String name;
    int count;

    Candidate() {

    }
    Candidate(int id, String name, int count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }

    @Override
    public String toString() {
        return "id: " + id + " name: " + name + " count: " + count;
    }
}
