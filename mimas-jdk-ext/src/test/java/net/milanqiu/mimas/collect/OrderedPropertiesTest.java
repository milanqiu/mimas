package net.milanqiu.mimas.collect;

import net.milanqiu.mimas.io.FileUtils;
import net.milanqiu.mimas.system.MimasJdkExtConvention;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Creation Date: 2014-12-20
 * @author Milan Qiu
 */
public class OrderedPropertiesTest {

    @Test
    public void test_propertyNames() throws Exception {
        Properties properties = new OrderedProperties();
        properties.setProperty("k1", "v1");
        properties.setProperty("k2", "v2\u0001\uEEEE");
        Enumeration propertyNames = properties.propertyNames();
        Assert.assertEquals("k1", propertyNames.nextElement());
        Assert.assertEquals("k2", propertyNames.nextElement());
        Assert.assertFalse(propertyNames.hasMoreElements());
    }

    @Test
    public void test_stringPropertyNames() throws Exception {
        Properties properties = new OrderedProperties();
        properties.setProperty("k1", "v1");
        properties.setProperty("k2", "v2\u0001\uEEEE");
        Set<String> stringPropertyNames = properties.stringPropertyNames();
        Iterator<String> itr = stringPropertyNames.iterator();
        Assert.assertEquals("k1", itr.next());
        Assert.assertEquals("k2", itr.next());
        Assert.assertFalse(itr.hasNext());
    }

    @Test
    public void test_store_load() throws Exception {
        File workDir = MimasJdkExtConvention.getSingleton().prepareWorkDirInTestTempDir(true);
        Properties properties = new OrderedProperties();
        properties.setProperty("k1", "v1");
        properties.setProperty("k2", "v2\u0001\uEEEE");

        // void store(OutputStream out, String comments)
        // void load(InputStream inStream)
        {
            File workFile = new File(workDir, "byte");
            try (FileOutputStream fos = new FileOutputStream(workFile)) {
                properties.store(fos, "comment");
            }

            Properties loadedProperties = new OrderedProperties();
            try (FileInputStream fis = new FileInputStream(workFile)) {
                loadedProperties.load(fis);
            }
            Assert.assertEquals("v1",             loadedProperties.getProperty("k1"));
            Assert.assertEquals("v2\u0001\uEEEE", loadedProperties.getProperty("k2"));

            String fileContent = FileUtils.readChars(workFile, StandardCharsets.ISO_8859_1);
            String[] fileLines = fileContent.split(System.lineSeparator());
            Assert.assertEquals("#comment",            fileLines[0]);
            Assert.assertEquals("k1=v1",               fileLines[2]);
            Assert.assertEquals("k2=v2\\u0001\\uEEEE", fileLines[3]);
        }

        // void store(Writer writer, String comments)
        // void load(Reader reader)
        {
            File workFile = new File(workDir, "char");
            try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(workFile), StandardCharsets.UTF_8)) {
                properties.store(osw, "comment");
            }

            Properties loadedProperties = new OrderedProperties();
            try (InputStreamReader isr = new InputStreamReader(new FileInputStream(workFile), StandardCharsets.UTF_8)) {
                loadedProperties.load(isr);
            }
            Assert.assertEquals("v1",             loadedProperties.getProperty("k1"));
            Assert.assertEquals("v2\u0001\uEEEE", loadedProperties.getProperty("k2"));

            String fileContent = FileUtils.readChars(workFile, StandardCharsets.UTF_8);
            String[] fileLines = fileContent.split(System.lineSeparator());
            Assert.assertEquals("#comment",          fileLines[0]);
            Assert.assertEquals("k1=v1",             fileLines[2]);
            Assert.assertEquals("k2=v2\u0001\uEEEE", fileLines[3]);
        }
    }

    @Test
    public void test_storeToXML_loadFromXML() throws Exception {
        File workDir = MimasJdkExtConvention.getSingleton().prepareWorkDirInTestTempDir(true);
        Properties properties = new OrderedProperties();
        properties.setProperty("k1", "v1");
        properties.setProperty("k2", "v2\uEEEE");

        File workFile = new File(workDir, "xml");
        try (FileOutputStream fos = new FileOutputStream(workFile)) {
            properties.storeToXML(fos, "comment");
        }

        Properties loadedProperties = new OrderedProperties();
        try (FileInputStream fis = new FileInputStream(workFile)) {
            loadedProperties.loadFromXML(fis);
        }
        Assert.assertEquals("v1",       loadedProperties.getProperty("k1"));
        Assert.assertEquals("v2\uEEEE", loadedProperties.getProperty("k2"));

        String fileContent = FileUtils.readChars(workFile, StandardCharsets.UTF_8);
        String[] fileLines = fileContent.split(System.lineSeparator());
        Assert.assertEquals("<properties>",                       fileLines[2]);
        Assert.assertEquals("<comment>comment</comment>",         fileLines[3]);
        Assert.assertEquals("<entry key=\"k1\">v1</entry>",       fileLines[4]);
        Assert.assertEquals("<entry key=\"k2\">v2\uEEEE</entry>", fileLines[5]);
        Assert.assertEquals("</properties>",                      fileLines[6]);
    }
}
