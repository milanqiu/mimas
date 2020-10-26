package net.milanqiu.mimas.digester;

import java.util.ArrayList;
import java.util.List;

/**
 * Creation Date: 2018-09-07
 * @author Milan Qiu
 */
public class LessonList {

    private List<Lesson> lessons = new ArrayList<>();

    public int size() {
        return lessons.size();
    }
    public Lesson get(int index) {
        return lessons.get(index);
    }
    public void add(Lesson lesson) {
        lessons.add(lesson);
    }
}
