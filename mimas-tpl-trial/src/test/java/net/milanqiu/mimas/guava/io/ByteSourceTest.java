package net.milanqiu.mimas.guava.io;

import com.google.common.hash.Hashing;
import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharSource;
import com.google.common.primitives.Bytes;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static net.milanqiu.mimas.guava.io.GuavaIoTestUtils.*;

/**
 * <p>Creation Date: 2014-10-29
 * @author Milan Qiu
 */
public class ByteSourceTest {

    @Test
    public void test_wrap() throws Exception {
        ByteSource bs = ByteSource.wrap(BYTE_ARR);
        Assert.assertArrayEquals(BYTE_ARR, bs.read());
    }

    @Test
    public void test_concat() throws Exception {
        ByteSource bs = ByteSource.concat(ByteSource.wrap(BYTE_ARR), ByteSource.wrap(BYTE_ARR));
        Assert.assertArrayEquals(Bytes.concat(BYTE_ARR, BYTE_ARR), bs.read());
    }

    @Test
    public void test_slice() throws Exception {
        ByteSource bs = ByteSource.wrap(BYTE_ARR);
        bs = bs.slice(1, 3);
        Assert.assertArrayEquals(Arrays.copyOfRange(BYTE_ARR, 1, 4), bs.read());
    }

    @Test
    public void test_openStream() throws Exception {
        ByteSource bs = ByteSource.wrap(BYTE_ARR);
        InputStream is = bs.openStream();
        Assert.assertArrayEquals(BYTE_ARR, ByteStreams.toByteArray(is));
    }

    @Test
    public void test_openBufferedStream() throws Exception {
        ByteSource bs = ByteSource.wrap(BYTE_ARR);
        InputStream is = bs.openBufferedStream();
        Assert.assertArrayEquals(BYTE_ARR, ByteStreams.toByteArray(is));
    }

    @Test
    public void test_read() throws Exception {
        ByteSource bs = ByteSource.wrap(BYTE_ARR);
        Assert.assertArrayEquals(BYTE_ARR, bs.read());
    }

    @Test
    public void test_copyTo() throws Exception {
        OutputStream bos = new ByteArrayOutputStream();
        ByteSource bs = ByteSource.wrap(BYTE_ARR);
        Assert.assertEquals(BYTE_ARR_LEN, bs.copyTo(bos));
        Assert.assertArrayEquals(BYTE_ARR, ((ByteArrayOutputStream) bos).toByteArray());
    }

    @Test
    public void test_size() throws Exception {
        ByteSource bs = ByteSource.wrap(BYTE_ARR);
        Assert.assertEquals(BYTE_ARR_LEN, bs.size());
    }

    @Test
    public void test_isEmpty() throws Exception {
        ByteSource bs = ByteSource.wrap(BYTE_ARR);
        Assert.assertFalse(bs.isEmpty());
    }

    @Test
    public void test_contentEquals() throws Exception {
        ByteSource bs = ByteSource.wrap(BYTE_ARR);
        ByteSource bs2 = ByteSource.wrap(BYTE_ARR);
        ByteSource bs3 = ByteSource.wrap(Arrays.copyOfRange(BYTE_ARR, 0, BYTE_ARR_LEN-1));
        Assert.assertTrue(bs.contentEquals(bs2));
        Assert.assertFalse(bs.contentEquals(bs3));
    }

    @Test
    public void test_hash() throws Exception {
        ByteSource bs = ByteSource.wrap(BYTE_ARR);
        Assert.assertEquals("c7f5e428ba8ea38eb9681cc411674316", bs.hash(Hashing.md5()).toString());
    }

    @Test
    public void test_asCharSource() throws Exception {
        ByteSource bs = ByteSource.wrap(STR_OF_CHAR_ARR.getBytes(StandardCharsets.UTF_8));
        CharSource cs = bs.asCharSource(StandardCharsets.UTF_8);
        Assert.assertEquals(STR_OF_CHAR_ARR, cs.read());
    }
}
