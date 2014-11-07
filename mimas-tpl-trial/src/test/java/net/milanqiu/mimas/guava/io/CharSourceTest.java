package net.milanqiu.mimas.guava.io;

import com.google.common.io.CharSource;
import com.google.common.io.CharStreams;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.Reader;

import static net.milanqiu.mimas.guava.io.GuavaIoTestUtils.*;

/**
 * <p>Creation Date: 2014-10-29
 * @author Milan Qiu
 */
public class CharSourceTest {

    @Test
    public void test_wrap() throws Exception {
        CharSource cs = CharSource.wrap(STR_OF_CHAR_ARR);
        Assert.assertEquals(STR_OF_CHAR_ARR, cs.read());
    }

    @Test
    public void test_concat() throws Exception {
        CharSource cs = CharSource.concat(CharSource.wrap(STR_OF_CHAR_ARR), CharSource.wrap(STR_OF_CHAR_ARR));
        Assert.assertEquals(STR_OF_CHAR_ARR+STR_OF_CHAR_ARR, cs.read());
    }

    @Test
    public void test_openStream() throws Exception {
        CharSource cs = CharSource.wrap(STR_OF_CHAR_ARR);
        Reader r = cs.openStream();
        Assert.assertEquals(STR_OF_CHAR_ARR, CharStreams.toString(r));
    }

    @Test
    public void test_openBufferedStream() throws Exception {
        CharSource cs = CharSource.wrap(STR_OF_CHAR_ARR);
        BufferedReader br = cs.openBufferedStream();
        Assert.assertEquals(STR_OF_CHAR_ARR, CharStreams.toString(br));
    }

    @Test
    public void test_read() throws Exception {
        CharSource cs = CharSource.wrap(STR_OF_CHAR_ARR);
        Assert.assertEquals(STR_OF_CHAR_ARR, cs.read());
    }

    @Test
    public void test_readLines() throws Exception {
        CharSource cs = CharSource.wrap(STR_MULTILINE);
        Assert.assertEquals(LIST_MULTILINE, cs.readLines());
    }

    @Test
    public void test_readFirstLine() throws Exception {
        CharSource cs = CharSource.wrap(STR_MULTILINE);
        Assert.assertEquals(LIST_MULTILINE.get(0), cs.readFirstLine());
    }

    @Test
    public void test_copyTo() throws Exception {
        Appendable a = new CharArrayWriter();
        CharSource cs = CharSource.wrap(STR_OF_CHAR_ARR);
        Assert.assertEquals(CHAR_ARR_LEN, cs.copyTo(a));
        Assert.assertArrayEquals(CHAR_ARR, ((CharArrayWriter) a).toCharArray());
    }

    @Test
    public void test_isEmpty() throws Exception {
        CharSource cs = CharSource.wrap(STR_OF_CHAR_ARR);
        Assert.assertFalse(cs.isEmpty());
    }
}
