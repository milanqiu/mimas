package net.milanqiu.mimas.guava.io;

import com.google.common.collect.ImmutableList;
import com.google.common.io.CharSink;
import com.google.common.io.CharSource;
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
public class CharSourceTest {

    @Test
    public void test_wrap() throws Exception {
        CharSource cs = CharSource.wrap(ALL_CHAR_VALUES_STR);
        Assert.assertEquals(ALL_CHAR_VALUES_STR, cs.read());
    }

    @Test
    public void test_concat() throws Exception {
        // CharSource concat(CharSource... sources)
        {
            CharSource cs = CharSource.concat(CharSource.wrap(ALL_CHAR_VALUES_STR), CharSource.wrap(ALL_CHAR_VALUES_STR));
            Assert.assertEquals(ALL_CHAR_VALUES_STR+ALL_CHAR_VALUES_STR, cs.read());
        }

        // CharSource concat(Iterable<? extends CharSource> sources)
        {
            CharSource cs = CharSource.concat(ImmutableList.of(CharSource.wrap(ALL_CHAR_VALUES_STR), CharSource.wrap(ALL_CHAR_VALUES_STR)));
            Assert.assertEquals(ALL_CHAR_VALUES_STR+ALL_CHAR_VALUES_STR, cs.read());
        }
    }

    @Test
    public void test_openStream() throws Exception {
        CharSource cs = CharSource.wrap(ALL_CHAR_VALUES_STR);
        Reader r = cs.openStream();
        Assert.assertEquals(ALL_CHAR_VALUES_STR, CharStreams.toString(r));
    }

    @Test
    public void test_openBufferedStream() throws Exception {
        CharSource cs = CharSource.wrap(ALL_CHAR_VALUES_STR);
        BufferedReader br = cs.openBufferedStream();
        Assert.assertEquals(ALL_CHAR_VALUES_STR, CharStreams.toString(br));
    }

    @Test
    public void test_read() throws Exception {
        CharSource cs = CharSource.wrap(ALL_CHAR_VALUES_STR);
        Assert.assertEquals(ALL_CHAR_VALUES_STR, cs.read());
    }

    @Test
    public void test_readLines() throws Exception {
        // ImmutableList<String> readLines()
        {
            CharSource cs = CharSource.wrap(MULTILINE_STR);
            Assert.assertEquals(MULTILINE_LIST, cs.readLines());
        }

        // T readLines(LineProcessor<T> processor)
        {
            CharSource cs = CharSource.wrap(MULTILINE_STR);
            Assert.assertEquals(CollectionUtils.getSumLength(MULTILINE_LIST),
                    (int) cs.readLines(new LineProcessor<Integer>() {
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
    public void test_readFirstLine() throws Exception {
        CharSource cs = CharSource.wrap(MULTILINE_STR);
        Assert.assertEquals(MULTILINE_LIST.get(0), cs.readFirstLine());
    }

    @Test
    public void test_copyTo() throws Exception {
        // long copyTo(Appendable appendable)
        {
            Appendable a = new CharArrayWriter();
            CharSource cs = CharSource.wrap(ALL_CHAR_VALUES_STR);
            Assert.assertEquals(ALL_CHAR_VALUES_LEN, cs.copyTo(a));
            Assert.assertArrayEquals(ALL_CHAR_VALUES, ((CharArrayWriter) a).toCharArray());
        }

        // long copyTo(CharSink sink)
        {
            final Writer w = new CharArrayWriter();
            CharSink csk = new CharSink() {
                @Override
                public Writer openStream() throws IOException {
                    return w;
                }
            };
            CharSource cs = CharSource.wrap(ALL_CHAR_VALUES_STR);
            Assert.assertEquals(ALL_CHAR_VALUES_LEN, cs.copyTo(csk));
            Assert.assertArrayEquals(ALL_CHAR_VALUES, ((CharArrayWriter) w).toCharArray());
        }
    }

    @Test
    public void test_isEmpty() throws Exception {
        CharSource cs = CharSource.wrap(ALL_CHAR_VALUES_STR);
        Assert.assertFalse(cs.isEmpty());
        Assert.assertTrue(CharSource.empty().isEmpty());
    }

    @Test
    public void test_empty() throws Exception {
        CharSource cs = CharSource.empty();
        Assert.assertEquals(0, cs.read().length());
    }
}
