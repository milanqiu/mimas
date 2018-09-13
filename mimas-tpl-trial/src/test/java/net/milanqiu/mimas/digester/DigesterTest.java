package net.milanqiu.mimas.digester;

import net.milanqiu.mimas.config.MimasTplTrialProjectConfig;
import org.apache.commons.digester3.Digester;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static net.milanqiu.mimas.instrumentation.TestDataConsts.*;

/**
 * Creation Date: 2018-09-06
 * @author Milan Qiu
 */
public class DigesterTest {

    private List<Teacher> teachers;
    
    private void load() throws IOException, SAXException {
        Digester digester = new Digester();
        digester.setValidating(false);

        digester.addObjectCreate("Teachers", "java.util.ArrayList");

        digester.addObjectCreate("Teachers/Teacher", "net.milanqiu.mimas.digester.Teacher");
        digester.addSetProperties("Teachers/Teacher");
        digester.addBeanPropertySetter("Teachers/Teacher/name");
        digester.addCallMethod("Teachers/Teacher", "setGender", 1, new Class<?>[]{Integer.class});
        digester.addCallParam("Teachers/Teacher/gender", 0);
        digester.addSetNext("Teachers/Teacher", "add", "net.milanqiu.mimas.digester.Teacher");

        digester.addObjectCreate("Teachers/Teacher/Lessons/Lesson", Lesson.class);
        digester.addSetNestedProperties("Teachers/Teacher/Lessons/Lesson");
        digester.addCallMethod("Teachers/Teacher/Lessons/Lesson", "setPassRate", 1);
        digester.addCallParam("Teachers/Teacher/Lessons/Lesson", 0, "passRate");
        digester.addSetNext("Teachers/Teacher/Lessons/Lesson", "addLesson");

        teachers = digester.parse(new File(MimasTplTrialProjectConfig.getSingleton().getFilesDir(), "Samples/Sample.xml"));
    }

    @Test
    public void test_Digester() throws Exception {
        load();

        Assert.assertEquals(TEACHER_COUNT, teachers.size());
        for (int i = 0; i < TEACHER_COUNT; i++) {
            Assert.assertEquals(TEACHER_IDS[i], teachers.get(i).getId());
            Assert.assertEquals(TEACHER_NAMES[i], teachers.get(i).getName());
            Assert.assertEquals(TEACHER_GENDER_STRS[i], teachers.get(i).getGender().toString());
            Assert.assertEquals(TEACHER_LESSONS[i].length, teachers.get(i).getLessons().size());
            for (int j = 0; j < TEACHER_LESSONS[i].length; j++) {
                Assert.assertEquals(LESSON_IDS[TEACHER_LESSONS[i][j]], teachers.get(i).getLessons().get(j).getId());
                Assert.assertEquals(LESSON_NAMES[TEACHER_LESSONS[i][j]], teachers.get(i).getLessons().get(j).getName());
                Assert.assertEquals(LESSON_PASS_RATE_STRS[TEACHER_LESSONS[i][j]], teachers.get(i).getLessons().get(j).getPassRate().toString());
            }
        }
    }
}
