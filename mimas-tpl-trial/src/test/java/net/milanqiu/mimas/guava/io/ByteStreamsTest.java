package net.milanqiu.mimas.guava.io;

import com.google.common.io.ByteProcessor;
import com.google.common.io.ByteStreams;
import net.milanqiu.mimas.collect.ArrayUtils;
import net.milanqiu.mimas.instrumentation.DebugUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;

import static net.milanqiu.mimas.guava.io.GuavaIoTestUtils.*;

/**
 * Creation Date: 2014-10-29
 * @author Milan Qiu
 */
public class ByteStreamsTest {

    @Test
    public void test_toByteArray() throws Exception {
        InputStream is = new ByteArrayInputStream(ALL_BYTE_VALUES);
        Assert.assertArrayEquals(ALL_BYTE_VALUES, ByteStreams.toByteArray(is));
    }

    @Test
    public void test_copy() throws Exception {
        InputStream is = new ByteArrayInputStream(ALL_BYTE_VALUES);
        OutputStream os = new ByteArrayOutputStream();
        Assert.assertEquals(ALL_BYTE_VALUES_LEN, ByteStreams.copy(is, os));
        Assert.assertArrayEquals(ALL_BYTE_VALUES, ((ByteArrayOutputStream) os).toByteArray());
    }

    @Test
    public void test_readFully() throws Exception {
        // void readFully(InputStream in, byte[] b)
        {
            InputStream is = new ByteArrayInputStream(ALL_BYTE_VALUES);
            byte[] byteArray = new byte[100];
            ByteStreams.readFully(is, byteArray);
            Assert.assertArrayEquals(Arrays.copyOfRange(ALL_BYTE_VALUES, 0, 100), byteArray);
        }

        // void readFully(InputStream in, byte[] b, int off, int len)
        {
            InputStream is = new ByteArrayInputStream(ALL_BYTE_VALUES);
            byte[] byteArray = new byte[100];
            ByteStreams.readFully(is, byteArray, 20, 50);
            Assert.assertArrayEquals(ArrayUtils.duplicate((byte) 0, 20),         Arrays.copyOfRange(byteArray, 0, 20));
            Assert.assertArrayEquals(Arrays.copyOfRange(ALL_BYTE_VALUES, 0, 50), Arrays.copyOfRange(byteArray, 20, 70));
            Assert.assertArrayEquals(ArrayUtils.duplicate((byte) 0, 30),         Arrays.copyOfRange(byteArray, 70, 100));

            is.reset();
            try {
                ByteStreams.readFully(is, new byte[60], 20, 50);
                DebugUtils.neverGoesHere();
            } catch (Exception e) {
                Assert.assertTrue(e instanceof IndexOutOfBoundsException);
            }
        }
    }

    @Test
    public void test_skipFully() throws Exception {
        InputStream is = new ByteArrayInputStream(ALL_BYTE_VALUES);
        ByteStreams.skipFully(is, 100);
        Assert.assertArrayEquals(Arrays.copyOfRange(ALL_BYTE_VALUES, 100, ALL_BYTE_VALUES_LEN), ByteStreams.toByteArray(is));
    }

    @Test
    public void test_nullOutputStream() throws Exception {
        OutputStream os =  ByteStreams.nullOutputStream();
        os.write(ALL_BYTE_VALUES);
    }

    @Test
    public void test_limit() throws Exception {
        InputStream is = new ByteArrayInputStream(ALL_BYTE_VALUES);
        InputStream limitedIs = ByteStreams.limit(is, 100);
        Assert.assertArrayEquals(Arrays.copyOfRange(ALL_BYTE_VALUES, 0, 100), ByteStreams.toByteArray(limitedIs));
        Assert.assertArrayEquals(Arrays.copyOfRange(ALL_BYTE_VALUES, 100, ALL_BYTE_VALUES_LEN), ByteStreams.toByteArray(is));
    }

    @Test
    public void test_readBytes() throws Exception {
        InputStream is = new ByteArrayInputStream(ALL_BYTE_VALUES);
        Assert.assertEquals("-128", ByteStreams.readBytes(is, new ByteProcessor<String>() {
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
