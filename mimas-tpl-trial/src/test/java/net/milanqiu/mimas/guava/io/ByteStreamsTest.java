package net.milanqiu.mimas.guava.io;

import com.google.common.io.ByteStreams;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import static net.milanqiu.mimas.guava.io.GuavaIoTestUtils.*;

/**
 * <p>Creation Date: 2014-10-29
 * @author Milan Qiu
 */
public class ByteStreamsTest {

    @Test
    public void test_toByteArray() throws Exception {
        InputStream is = new ByteArrayInputStream(BYTE_ARR);
        Assert.assertArrayEquals(BYTE_ARR, ByteStreams.toByteArray(is));
    }

    @Test
    public void test_copy() throws Exception {
        InputStream is = new ByteArrayInputStream(BYTE_ARR);
        OutputStream os = new ByteArrayOutputStream();
        Assert.assertEquals(BYTE_ARR_LEN, ByteStreams.copy(is, os));
        Assert.assertArrayEquals(BYTE_ARR, ((ByteArrayOutputStream) os).toByteArray());
    }

    @Test
    public void test_readFully() throws Exception {
        InputStream is = new ByteArrayInputStream(BYTE_ARR);
        byte[] byteArr = new byte[BYTE_ARR_LEN-2];
        ByteStreams.readFully(is, byteArr);
        Assert.assertArrayEquals(Arrays.copyOfRange(BYTE_ARR, 0, BYTE_ARR_LEN - 2), byteArr);
    }

    @Test
    public void test_skipFully() throws Exception {
        InputStream is = new ByteArrayInputStream(BYTE_ARR);
        ByteStreams.skipFully(is, 2);
        Assert.assertArrayEquals(Arrays.copyOfRange(BYTE_ARR, 2, BYTE_ARR_LEN), ByteStreams.toByteArray(is));
    }

    @Test
    public void test_nullOutputStream() throws Exception {
        OutputStream os =  ByteStreams.nullOutputStream();
        os.write(BYTE_ARR);
    }
}
