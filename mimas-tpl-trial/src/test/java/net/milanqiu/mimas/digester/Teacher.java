package net.milanqiu.mimas.digester;

/**
 * Creation Date: 2018-09-06
 * @author Milan Qiu
 */
public class Teacher {

    private int id;
    private String name;
    private Gender gender;
    private LessonList lessons = new LessonList();

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public void setGender(int gender) {
        if (gender == 1)
            this.gender = Gender.MALE;
        else if (gender == 2)
            this.gender = Gender.FEMALE;
        else
            this.gender = Gender.OTHER;
    }
    public LessonList getLessons() {
        return lessons;
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }
}
