package net.milanqiu.mimas.java.util;

import net.milanqiu.mimas.config.MimasJdkTrialProjectConfig;
import net.milanqiu.mimas.instrumentation.DebugUtils;
import net.milanqiu.mimas.junit.AssertExt;
import org.junit.Assert;
import org.junit.Test;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

/**
 * Creation Date: 2014-12-19
 * @author Milan Qiu
 */
public class ScannerTest {

    @Test
    public void test_hasNext_next() throws Exception {
        Scanner scanner = new Scanner("100 200");

        Assert.assertTrue(scanner.hasNext());
        Assert.assertFalse(scanner.hasNext("[a-z]+"));
        Assert.assertTrue(scanner.hasNext("[0-9]+"));

        Assert.assertEquals("100", scanner.next());
        try {
            scanner.next("[a-z]+");
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            AssertExt.assertClassification(InputMismatchException.class, e);
        }
        Assert.assertEquals("200", scanner.next("[0-9]+"));

        Assert.assertFalse(scanner.hasNext());
        Assert.assertFalse(scanner.hasNext("[a-z]+"));
        Assert.assertFalse(scanner.hasNext("[0-9]+"));

        try {
            scanner.next();
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            AssertExt.assertClassification(NoSuchElementException.class, e);
        }
    }

    @Test
    public void test_hasNextInt_nextInt() throws Exception {
        Scanner scanner = new Scanner("100 200 FF EE");

        Assert.assertTrue(scanner.hasNextInt());
        Assert.assertEquals(100, scanner.nextInt());
        Assert.assertEquals(0x200, scanner.nextInt(16));
        Assert.assertEquals(0xFF, scanner.nextInt(16));

        Assert.assertFalse(scanner.hasNextInt());
        Assert.assertTrue(scanner.hasNextInt(16));
        try {
            scanner.nextInt();
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            AssertExt.assertClassification(InputMismatchException.class, e);
        }
        Assert.assertEquals(0xEE, scanner.nextInt(16));

        Assert.assertFalse(scanner.hasNextInt());
        Assert.assertFalse(scanner.hasNextInt(16));
    }

    @Test
    public void test_hasNextLine_nextLine() throws Exception {
        Scanner scanner = new Scanner("100" + System.lineSeparator() + "abc 200");
        Assert.assertTrue(scanner.hasNextLine());
        Assert.assertEquals("100", scanner.nextLine());
        Assert.assertEquals("abc 200", scanner.nextLine());
        Assert.assertFalse(scanner.hasNextLine());
    }

    @Test
    public void test_match() throws Exception {
        Scanner scanner = new Scanner("abc100");
        Pattern pattern = Pattern.compile("([a-z]+)([0-9]+)");
        MatchResult mr;

        try {
            scanner.match();
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            AssertExt.assertClassification(IllegalStateException.class, e);
        }

        Assert.assertTrue(scanner.hasNext(pattern));
        mr = scanner.match();
        Assert.assertEquals(2, mr.groupCount());
        Assert.assertEquals("abc100", mr.group(0));
        Assert.assertEquals("abc", mr.group(1));
        Assert.assertEquals("100", mr.group(2));

        Assert.assertEquals("abc100", scanner.next(pattern));
        mr = scanner.match();
        Assert.assertEquals(2, mr.groupCount());
        Assert.assertEquals("abc100", mr.group(0));
        Assert.assertEquals("abc", mr.group(1));
        Assert.assertEquals("100", mr.group(2));

        Assert.assertFalse(scanner.hasNext(pattern));
        try {
            scanner.match();
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            AssertExt.assertClassification(IllegalStateException.class, e);
        }
    }

    @Test
    public void test_findInLine() throws Exception {
        {
            Scanner scanner = new Scanner("100 abc 200");
            Assert.assertEquals("abc", scanner.findInLine("[a-z]+"));
            Assert.assertTrue(scanner.hasNext());
            Assert.assertEquals(" 200", scanner.useDelimiter("$").next());
        }
        {
            Scanner scanner = new Scanner("100" + System.lineSeparator() + "abc 200");
            Assert.assertEquals(null, scanner.findInLine("[a-z]+"));
            Assert.assertTrue(scanner.hasNext());
            Assert.assertEquals("100" + System.lineSeparator() + "abc 200", scanner.useDelimiter("$").next());
        }
    }

    @Test
    public void test_findWithinHorizon() throws Exception {
        {
            Scanner scanner = new Scanner("100" + System.lineSeparator() + "abc 200");
            Assert.assertEquals("abc", scanner.findWithinHorizon("[a-z]+", 0));
            Assert.assertTrue(scanner.hasNext());
            Assert.assertEquals(" 200", scanner.useDelimiter("$").next());
        }
        {
            Scanner scanner = new Scanner("100" + System.lineSeparator() + "abc 200");
            Assert.assertEquals("ab", scanner.findWithinHorizon("[a-z]+", 3+System.lineSeparator().length()+2));
            Assert.assertTrue(scanner.hasNext());
            Assert.assertEquals("c 200", scanner.useDelimiter("$").next());
        }
        {
            Scanner scanner = new Scanner("100" + System.lineSeparator() + "abc 200");
            Assert.assertEquals(null, scanner.findWithinHorizon("[a-z]+", 3));
            Assert.assertTrue(scanner.hasNext());
            Assert.assertEquals("100" + System.lineSeparator() + "abc 200", scanner.useDelimiter("$").next());
        }
    }

    @Test
    public void test_delimiter() throws Exception {
        Scanner scanner = new Scanner("");
        MimasJdkTrialProjectConfig.getSingleton().writeFileInTestOutDir(scanner.delimiter().toString());
    }

    @Test
    public void test_useDelimiter() throws Exception {
        {
            Scanner scanner = new Scanner("100abc200");
            Assert.assertEquals("100abc200", scanner.next());
            Assert.assertFalse(scanner.hasNext());
        }
        {
            Scanner scanner = new Scanner("100abc200");
            Assert.assertSame(scanner, scanner.useDelimiter("[a-z]+"));
            Assert.assertEquals("100", scanner.next());
            Assert.assertEquals("200", scanner.next());
            Assert.assertFalse(scanner.hasNext());
        }
    }
}
