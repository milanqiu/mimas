package net.milanqiu.mimas.guava.hash;

import com.google.common.hash.Funnel;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.common.hash.PrimitiveSink;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-12-29
 * @author Milan Qiu
 */
public class HasherTest {

    private Hasher md5Hasher;

    @Before
    public void setUp() throws Exception {
        md5Hasher = Hashing.md5().newHasher();
    }

    @Test
    public void test_putString_putByte_putBytes_putObject_hash() throws Exception {
        Funnel<Object> funnelPutE = new Funnel<Object>() {
            @Override
            public void funnel(Object from, PrimitiveSink into) {
                into.putByte((byte)'e');
            }
        };

        // equivalent to md5("abcde")
        Assert.assertEquals("ab56b4d92b40713acc5af89985d4b786",
                md5Hasher.putString("a", StandardCharsets.UTF_8)
                         .putByte((byte)'b')
                         .putBytes(new byte[]{'c', 'd'})
                         .putObject(OBJ_0, funnelPutE)
                         .hash().toString());
    }
}
