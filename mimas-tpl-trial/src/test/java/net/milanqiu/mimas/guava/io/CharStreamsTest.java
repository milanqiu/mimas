package net.milanqiu.mimas.guava.io;

import com.google.common.io.CharStreams;
import com.google.common.io.LineProcessor;
import net.milanqiu.mimas.collect.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

import static net.milanqiu.mimas.guava.io.GuavaIoTestUtils.*;

/**
 * Creation Date: 2014-10-29
 * @author Milan Qiu
 */
public class CharStreamsTest {

    @Test
    public void test_toString() throws Exception {
        Readable r = new CharArrayReader(ALL_CHAR_VALUES);
        Assert.assertEquals(ALL_CHAR_VALUES_STR, CharStreams.toString(r));
    }

    @Test
    public void test_readLines() throws Exception {
        // List<String> readLines(Readable r)
        {
            Readable r = new StringReader(MULTILINE_STR);
            Assert.assertEquals(MULTILINE_LIST, CharStreams.readLines(r));
        }

        // T readLines(Readable readable, LineProcessor<T> processor)
        {
            Readable r = new StringReader(MULTILINE_STR);
            Assert.assertEquals(CollectionUtils.getSumLength(MULTILINE_LIST),
                    (int) CharStreams.readLines(r, new LineProcessor<Integer>() {
                        int sumLength = 0;
                        @Override
                        public boolean processLine(String s) throws IOException {
                            sumLength += s.length();
                            return true;
                        }
                        @Override
                        public Integer getResult() {
                            return sumLength;
                        }
                    }));
        }
    }

    @Test
    public void test_copy() throws Exception {
        Readable r = new CharArrayReader(ALL_CHAR_VALUES);
        Appendable a = new CharArrayWriter();
        Assert.assertEquals(ALL_CHAR_VALUES_LEN, CharStreams.copy(r, a));
        Assert.assertArrayEquals(ALL_CHAR_VALUES, ((CharArrayWriter) a).toCharArray());
    }

    @Test
    public void test_skipFully() throws Exception {
        Reader r = new CharArrayReader(ALL_CHAR_VALUES);
        CharStreams.skipFully(r, 100);
        Assert.assertEquals(String.valueOf(ALL_CHAR_VALUES, 100, ALL_CHAR_VALUES_LEN-100), CharStreams.toString(r));
    }

    @Test
    public void test_nullWriter() throws Exception {
        Writer w = CharStreams.nullWriter();
        w.write(ALL_CHAR_VALUES_STR);
    }
}
