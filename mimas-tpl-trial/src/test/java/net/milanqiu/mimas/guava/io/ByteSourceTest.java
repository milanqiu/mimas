package net.milanqiu.mimas.guava.io;

import com.google.common.collect.ImmutableList;
import com.google.common.hash.Hashing;
import com.google.common.io.*;
import com.google.common.primitives.Bytes;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static net.milanqiu.mimas.guava.io.GuavaIoTestUtils.*;

/**
 * Creation Date: 2014-10-29
 * @author Milan Qiu
 */
public class ByteSourceTest {

    @Test
    public void test_wrap() throws Exception {
        ByteSource bs = ByteSource.wrap(ALL_BYTE_VALUES);
        Assert.assertArrayEquals(ALL_BYTE_VALUES, bs.read());
    }

    @Test
    public void test_concat() throws Exception {
        // ByteSource concat(Iterable<? extends ByteSource> sources)
        {
            ByteSource bs = ByteSource.concat(ImmutableList.of(ByteSource.wrap(ALL_BYTE_VALUES), ByteSource.wrap(ALL_BYTE_VALUES)));
            Assert.assertArrayEquals(Bytes.concat(ALL_BYTE_VALUES, ALL_BYTE_VALUES), bs.read());
        }

        // ByteSource concat(ByteSource... sources)
        {
            ByteSource bs = ByteSource.concat(ByteSource.wrap(ALL_BYTE_VALUES), ByteSource.wrap(ALL_BYTE_VALUES));
            Assert.assertArrayEquals(Bytes.concat(ALL_BYTE_VALUES, ALL_BYTE_VALUES), bs.read());
        }
    }

    @Test
    public void test_slice() throws Exception {
        ByteSource bs = ByteSource.wrap(ALL_BYTE_VALUES);
        ByteSource slicedBs = bs.slice(50, 100);
        Assert.assertArrayEquals(Arrays.copyOfRange(ALL_BYTE_VALUES, 50, 150), slicedBs.read());
        Assert.assertArrayEquals(ALL_BYTE_VALUES, bs.read());
    }

    @Test
    public void test_openStream() throws Exception {
        ByteSource bs = ByteSource.wrap(ALL_BYTE_VALUES);
        InputStream is = bs.openStream();
        Assert.assertArrayEquals(ALL_BYTE_VALUES, ByteStreams.toByteArray(is));
    }

    @Test
    public void test_openBufferedStream() throws Exception {
        ByteSource bs = ByteSource.wrap(ALL_BYTE_VALUES);
        InputStream is = bs.openBufferedStream();
        Assert.assertArrayEquals(ALL_BYTE_VALUES, ByteStreams.toByteArray(is));
    }

    @Test
    public void test_read() throws Exception {
        // byte[] read()
        {
            ByteSource bs = ByteSource.wrap(ALL_BYTE_VALUES);
            Assert.assertArrayEquals(ALL_BYTE_VALUES, bs.read());
        }

        // T read(ByteProcessor<T> processor)
        {
            ByteSource bs = ByteSource.wrap(ALL_BYTE_VALUES);
            Assert.assertEquals("-128", bs.read(new ByteProcessor<String>() {
                int sum = 0;
                @Override
                public boolean processBytes(byte[] bytes, int i, int i2) throws IOException {
                    for (int index = i; index < i2; index++)
                        sum += bytes[index];
                    return true;
                }
                @Override
                public String getResult() {
                    return String.valueOf(sum);
                }
            }));
        }
    }

    @Test
    public void test_copyTo() throws Exception {
        // long copyTo(OutputStream output)
        {
            OutputStream os = new ByteArrayOutputStream();
            ByteSource bs = ByteSource.wrap(ALL_BYTE_VALUES);
            Assert.assertEquals(ALL_BYTE_VALUES_LEN, bs.copyTo(os));
            Assert.assertArrayEquals(ALL_BYTE_VALUES, ((ByteArrayOutputStream) os).toByteArray());
        }

        // long copyTo(ByteSink sink)
        {
            final OutputStream os = new ByteArrayOutputStream();
            ByteSink bsk = new ByteSink() {
                @Override
                public OutputStream openStream() throws IOException {
                    return os;
                }
            };
            ByteSource bs = ByteSource.wrap(ALL_BYTE_VALUES);
            Assert.assertEquals(ALL_BYTE_VALUES_LEN, bs.copyTo(bsk));
            Assert.assertArrayEquals(ALL_BYTE_VALUES, ((ByteArrayOutputStream) os).toByteArray());
        }
    }

    @Test
    public void test_size() throws Exception {
        ByteSource bs = ByteSource.wrap(ALL_BYTE_VALUES);
        Assert.assertEquals(ALL_BYTE_VALUES_LEN, bs.size());
    }

    @Test
    public void test_isEmpty() throws Exception {
        ByteSource bs = ByteSource.wrap(ALL_BYTE_VALUES);
        Assert.assertFalse(bs.isEmpty());
        Assert.assertTrue(ByteSource.empty().isEmpty());
    }

    @Test
    public void test_contentEquals() throws Exception {
        ByteSource bs = ByteSource.wrap(ALL_BYTE_VALUES);
        ByteSource bs2 = ByteSource.wrap(ALL_BYTE_VALUES);
        ByteSource bs3 = ByteSource.wrap(Arrays.copyOfRange(ALL_BYTE_VALUES, 0, ALL_BYTE_VALUES_LEN-1));
        Assert.assertTrue(bs.contentEquals(bs2));
        Assert.assertFalse(bs.contentEquals(bs3));
    }

    @Test
    public void test_hash() throws Exception {
        ByteSource bs = ByteSource.wrap(ALL_BYTE_VALUES);
        Assert.assertEquals("03f9522e6aa992641525359b6c67cb55", bs.hash(Hashing.md5()).toString());
    }

    @Test
    public void test_asCharSource() throws Exception {
        ByteSource bs = ByteSource.wrap(UNICODE_CHAR_VALUES_STR.getBytes(StandardCharsets.UTF_8));
        CharSource cs = bs.asCharSource(StandardCharsets.UTF_8);
        Assert.assertEquals(UNICODE_CHAR_VALUES_STR, cs.read());
    }

    @Test
    public void test_empty() throws Exception {
        ByteSource bs = ByteSource.empty();
        Assert.assertEquals(0, bs.read().length);
    }
}
