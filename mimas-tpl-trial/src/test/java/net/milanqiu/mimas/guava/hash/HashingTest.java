package net.milanqiu.mimas.guava.hash;

import com.google.common.collect.ImmutableList;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import net.milanqiu.mimas.config.MimasTplTrialProjectConfig;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-12-22
 * @author Milan Qiu
 */
public class HashingTest {

    @Test
    public void test_md5() throws Exception {
        Assert.assertEquals("ab56b4d92b40713acc5af89985d4b786",
                Hashing.md5().hashString("abcde", StandardCharsets.UTF_8).toString());
    }

    @Test
    public void test_sha1() throws Exception {
        Assert.assertEquals("03de6c570bfe24bfc328ccd7ca46b76eadaf4334",
                Hashing.sha1().hashString("abcde", StandardCharsets.UTF_8).toString());
    }

    @Test
    public void test_crc32() throws Exception {
        Assert.assertEquals(0x8587d865,
                Hashing.crc32().hashString("abcde", StandardCharsets.UTF_8).asInt());
    }

    @Test
    public void test_goodFastHash() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 256; i++) {
            sb.append(Hashing.goodFastHash(i).hashString(STR_0, StandardCharsets.UTF_8).toString())
              .append(System.lineSeparator());
        }
        MimasTplTrialProjectConfig.getSingleton().writeFileInTestOutDirUsingUtf8(sb);

        Assert.assertEquals(Hashing.goodFastHash(32).hashString(STR_0, StandardCharsets.UTF_8),
                            Hashing.goodFastHash(32).hashString(STR_0, StandardCharsets.UTF_8));
    }

    @Test
    public void test_combineOrdered_combineUnordered() throws Exception {
        HashCode hashCode1 = Hashing.md5().hashString(STR_0, StandardCharsets.UTF_8);
        HashCode hashCode2 = Hashing.md5().hashString(STR_1, StandardCharsets.UTF_8);
        HashCode hashCode3 = Hashing.md5().hashString(STR_2, StandardCharsets.UTF_8);

        Assert.assertEquals(Hashing.combineOrdered(ImmutableList.of(hashCode1, hashCode2, hashCode3)),
                            Hashing.combineOrdered(ImmutableList.of(hashCode1, hashCode2, hashCode3)));
        Assert.assertEquals(Hashing.combineUnordered(ImmutableList.of(hashCode1, hashCode2, hashCode3)),
                            Hashing.combineUnordered(ImmutableList.of(hashCode1, hashCode2, hashCode3)));

        Assert.assertNotEquals(Hashing.combineOrdered(ImmutableList.of(hashCode1, hashCode2, hashCode3)),
                               Hashing.combineOrdered(ImmutableList.of(hashCode3, hashCode2, hashCode1)));
        Assert.assertEquals(Hashing.combineUnordered(ImmutableList.of(hashCode1, hashCode2, hashCode3)),
                            Hashing.combineUnordered(ImmutableList.of(hashCode3, hashCode2, hashCode1)));
    }
}
