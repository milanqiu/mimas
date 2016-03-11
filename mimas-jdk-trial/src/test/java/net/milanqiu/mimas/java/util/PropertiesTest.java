package net.milanqiu.mimas.java.util;

import net.milanqiu.mimas.collect.CollectionUtils;
import net.milanqiu.mimas.config.MimasJdkTrialProjectConfig;
import net.milanqiu.mimas.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-12-20
 * @author Milan Qiu
 */
public class PropertiesTest {

    @Test
    public void test_setProperty_getProperty() throws Exception {
        Properties properties = new Properties();
        Assert.assertEquals(null, properties.setProperty(STR_0, STR_OF_INT_0));
        Assert.assertEquals(null, properties.setProperty(STR_1, STR_OF_INT_1));
        Assert.assertEquals(STR_OF_INT_1, properties.setProperty(STR_1, STR_OF_INT_4));
        /*
            Then the properties will be:
            STR_0 = STR_OF_INT_0
            STR_1 = STR_OF_INT_4
         */
        Assert.assertEquals(STR_OF_INT_0, properties.getProperty(STR_0));
        Assert.assertEquals(STR_OF_INT_4, properties.getProperty(STR_1));
        Assert.assertEquals(null, properties.getProperty(STR_2));
        Assert.assertEquals(STR_OF_INT_2, properties.getProperty(STR_2, STR_OF_INT_2));
    }

    @Test
    public void test_DefaultProperties() throws Exception {
        Properties defaultProperties = new Properties();
        defaultProperties.setProperty(STR_0, STR_OF_INT_0);
        defaultProperties.setProperty(STR_1, STR_OF_INT_1);
        Properties properties = new Properties(defaultProperties);
        properties.setProperty(STR_2, STR_OF_INT_2);
        properties.setProperty(STR_1, STR_OF_INT_4);
        /*
            Then the properties will be:
            STR_0 = STR_OF_INT_0
            STR_1 = STR_OF_INT_4
            STR_2 = STR_OF_INT_2
         */
        Assert.assertEquals(STR_OF_INT_0, properties.getProperty(STR_0));
        Assert.assertEquals(STR_OF_INT_4, properties.getProperty(STR_1));
        Assert.assertEquals(STR_OF_INT_2, properties.getProperty(STR_2));
        Assert.assertEquals(null, properties.getProperty(STR_3));
    }

    @Test
    public void test_propertyNames() throws Exception {
        Properties defaultProperties = new Properties();
        defaultProperties.setProperty(STR_0, STR_OF_INT_0);
        defaultProperties.setProperty(STR_1, STR_OF_INT_1);
        Properties properties = new Properties(defaultProperties);
        properties.setProperty(STR_2, STR_OF_INT_2);
        properties.setProperty(STR_1, STR_OF_INT_4);
        /*
            Then the properties will be:
            STR_0 = STR_OF_INT_0
            STR_1 = STR_OF_INT_4
            STR_2 = STR_OF_INT_2
         */
        Enumeration propertyNames = properties.propertyNames();
        List<Object> propertyNamesList = new ArrayList<>();
        while (propertyNames.hasMoreElements())
            propertyNamesList.add(propertyNames.nextElement());
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(Arrays.asList(STR_0, STR_1, STR_2), propertyNamesList));
    }

    @Test
    public void test_stringPropertyNames() throws Exception {
        Properties defaultProperties = new Properties();
        defaultProperties.setProperty(STR_0, STR_OF_INT_0);
        defaultProperties.setProperty(STR_1, STR_OF_INT_1);
        Properties properties = new Properties(defaultProperties);
        properties.setProperty(STR_2, STR_OF_INT_2);
        properties.setProperty(STR_1, STR_OF_INT_4);
        /*
            Then the properties will be:
            STR_0 = STR_OF_INT_0
            STR_1 = STR_OF_INT_4
            STR_2 = STR_OF_INT_2
         */
        Set<String> stringPropertyNames = properties.stringPropertyNames();
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(Arrays.asList(STR_0, STR_1, STR_2), stringPropertyNames));
    }

    @Test
    public void test_store_load() throws Exception {
        File testDir = MimasJdkTrialProjectConfig.getSingleton().prepareDirInTestTempDir();
        Properties defaultProperties = new Properties();
        defaultProperties.setProperty("fake", "will not be stored");
        Properties properties = new Properties(defaultProperties);
        properties.setProperty("k1", "v1");
        properties.setProperty("k2", "v2\u0001\uEEEE");

        // void store(OutputStream out, String comments)
        // void load(InputStream inStream)
        {
            File testFile = new File(testDir, "byte");
            try (FileOutputStream fos = new FileOutputStream(testFile)) {
                properties.store(fos, "comment");
            }

            Properties loadedProperties = new Properties();
            try (FileInputStream fis = new FileInputStream(testFile)) {
                loadedProperties.load(fis);
            }
            Assert.assertEquals("v1",             loadedProperties.getProperty("k1"));
            Assert.assertEquals("v2\u0001\uEEEE", loadedProperties.getProperty("k2"));

            String testFileContent = FileUtils.readChars(testFile, StandardCharsets.ISO_8859_1);
            String[] testFileLines = testFileContent.split(System.lineSeparator());
            Assert.assertEquals("#comment",            testFileLines[0]);
            Assert.assertEquals("k2=v2\\u0001\\uEEEE", testFileLines[2]);
            Assert.assertEquals("k1=v1",               testFileLines[3]);
        }

        // void store(Writer writer, String comments)
        // void load(Reader reader)
        {
            File testFile = new File(testDir, "char");
            try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(testFile), StandardCharsets.UTF_8)) {
                properties.store(osw, "comment");
            }

            Properties loadedProperties = new Properties();
            try (InputStreamReader isr = new InputStreamReader(new FileInputStream(testFile), StandardCharsets.UTF_8)) {
                loadedProperties.load(isr);
            }
            Assert.assertEquals("v1",             loadedProperties.getProperty("k1"));
            Assert.assertEquals("v2\u0001\uEEEE", loadedProperties.getProperty("k2"));

            String testFileContent = FileUtils.readChars(testFile, StandardCharsets.UTF_8);
            String[] testFileLines = testFileContent.split(System.lineSeparator());
            Assert.assertEquals("#comment",          testFileLines[0]);
            Assert.assertEquals("k2=v2\u0001\uEEEE", testFileLines[2]);
            Assert.assertEquals("k1=v1",             testFileLines[3]);
        }

        FileUtils.deleteRecursively(testDir);
        Assert.assertFalse(testDir.exists());
    }

    @Test
    public void test_storeToXML_loadFromXML() throws Exception {
        File testDir = MimasJdkTrialProjectConfig.getSingleton().prepareDirInTestTempDir();
        Properties defaultProperties = new Properties();
        defaultProperties.setProperty("fake", "will not be stored");
        Properties properties = new Properties(defaultProperties);
        properties.setProperty("k1", "v1");
        properties.setProperty("k2", "v2\uEEEE");

        File testFile = new File(testDir, "xml");
        try (FileOutputStream fos = new FileOutputStream(testFile)) {
            properties.storeToXML(fos, "comment", StandardCharsets.UTF_8.name());
        }

        Properties loadedProperties = new Properties();
        try (FileInputStream fis = new FileInputStream(testFile)) {
            loadedProperties.loadFromXML(fis);
        }
        Assert.assertEquals("v1",       loadedProperties.getProperty("k1"));
        Assert.assertEquals("v2\uEEEE", loadedProperties.getProperty("k2"));

        String testFileContent = FileUtils.readChars(testFile, StandardCharsets.UTF_8);
        String[] testFileLines = testFileContent.split(System.lineSeparator());
        Assert.assertEquals("<properties>",                       testFileLines[2]);
        Assert.assertEquals("<comment>comment</comment>",         testFileLines[3]);
        Assert.assertEquals("<entry key=\"k2\">v2\uEEEE</entry>", testFileLines[4]);
        Assert.assertEquals("<entry key=\"k1\">v1</entry>",       testFileLines[5]);
        Assert.assertEquals("</properties>",                      testFileLines[6]);

        FileUtils.deleteRecursively(testDir);
        Assert.assertFalse(testDir.exists());
    }

    @Test
    public void test_list() throws Exception {
        File testDir = MimasJdkTrialProjectConfig.getSingleton().prepareDirInTestTempDir();
        Properties defaultProperties = new Properties();
        defaultProperties.setProperty("k0", "v0");
        defaultProperties.setProperty("k1", "vvv1");
        Properties properties = new Properties(defaultProperties);
        properties.setProperty("k2", "v2\u0001\uEEEE");
        properties.setProperty("k1", "v1");

        // void list(PrintStream out)
        {
            File testFile = new File(testDir, "byte");
            try (PrintStream ps = new PrintStream(testFile, StandardCharsets.UTF_8.name())) {
                properties.list(ps);
            }

            String testFileContent = FileUtils.readChars(testFile, StandardCharsets.UTF_8);
            String[] testFileLines = testFileContent.split(System.lineSeparator());
            Assert.assertEquals("-- listing properties --", testFileLines[0]);
            Assert.assertEquals("k0=v0",             testFileLines[1]);
            Assert.assertEquals("k2=v2\u0001\uEEEE", testFileLines[2]);
            Assert.assertEquals("k1=v1",             testFileLines[3]);
        }

        // void list(PrintWriter out)
        {
            File testFile = new File(testDir, "char");
            try (PrintWriter pw = new PrintWriter(testFile, StandardCharsets.UTF_8.name())) {
                properties.list(pw);
            }

            String testFileContent = FileUtils.readChars(testFile, StandardCharsets.UTF_8);
            String[] testFileLines = testFileContent.split(System.lineSeparator());
            Assert.assertEquals("-- listing properties --", testFileLines[0]);
            Assert.assertEquals("k0=v0"            , testFileLines[1]);
            Assert.assertEquals("k2=v2\u0001\uEEEE", testFileLines[2]);
            Assert.assertEquals("k1=v1",             testFileLines[3]);
        }

        FileUtils.deleteRecursively(testDir);
        Assert.assertFalse(testDir.exists());
    }
}
