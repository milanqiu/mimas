package net.milanqiu.mimas.guava.io;

import com.google.common.io.CharStreams;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

import static net.milanqiu.mimas.guava.io.GuavaIoTestUtils.*;

/**
 * <p>Creation Date: 2014-10-29
 * @author Milan Qiu
 */
public class CharStreamsTest {

    @Test
    public void test_toString() throws Exception {
        Readable r = new CharArrayReader(CHAR_ARR);
        Assert.assertEquals(STR_OF_CHAR_ARR, CharStreams.toString(r));
    }

    @Test
    public void test_readLines() throws Exception {
        Readable r = new StringReader(STR_MULTILINE);
        Assert.assertEquals(LIST_MULTILINE, CharStreams.readLines(r));
    }

    @Test
    public void test_copy() throws Exception {
        Readable r = new CharArrayReader(CHAR_ARR);
        Appendable a = new CharArrayWriter();
        Assert.assertEquals(CHAR_ARR_LEN, CharStreams.copy(r, a));
        Assert.assertArrayEquals(CHAR_ARR, ((CharArrayWriter) a).toCharArray());
    }

    @Test
    public void test_skipFully() throws Exception {
        Reader r = new CharArrayReader(CHAR_ARR);
        CharStreams.skipFully(r, 2);
        Assert.assertEquals(String.valueOf(CHAR_ARR, 2, CHAR_ARR_LEN-2), CharStreams.toString(r));
    }

    @Test
    public void test_nullWriter() throws Exception {
        Writer w = CharStreams.nullWriter();
        w.write(STR_OF_CHAR_ARR);
    }
}
