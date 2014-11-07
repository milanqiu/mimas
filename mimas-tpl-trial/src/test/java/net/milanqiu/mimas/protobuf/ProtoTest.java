package net.milanqiu.mimas.protobuf;

import com.google.protobuf.UninitializedMessageException;
import net.milanqiu.mimas.instrumentation.DebugUtils;
import net.milanqiu.mimas.lang.LangUtils;
import net.milanqiu.mimas.protobuf.generated.PersonProto.Person;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-6-18
 * @author Milan Qiu
 */
public class ProtoTest {

    /**
     * Check if the specified person is equal to default instance.
     * @param p the person to be checked
     */
    private void checkPersonIsDefault(Person p) {
        Assert.assertFalse(p.isInitialized());
        Assert.assertFalse(p.hasId());
        Assert.assertTrue(LangUtils.isDefault(p.getId()));
        Assert.assertFalse(p.hasName());
        Assert.assertTrue(p.getName().isEmpty());
    }

    /**
     * Check if the specified person builder is equal to default instance.
     * @param pb the person to be checked
     */
    private void checkPersonBuilderIsDefault(Person.Builder pb) {
        Assert.assertFalse(pb.isInitialized());
        Assert.assertFalse(pb.hasId());
        Assert.assertTrue(LangUtils.isDefault(pb.getId()));
        Assert.assertFalse(pb.hasName());
        Assert.assertTrue(pb.getName().isEmpty());
    }

    /**
     * Check if the specified person is equal to standard instance #0.
     * @param p the person to be checked
     */
    private void checkPersonIsSI0(Person p) {
        Assert.assertTrue(p.isInitialized());
        Assert.assertTrue(p.hasId());
        Assert.assertEquals(INT_0, p.getId());
        Assert.assertTrue(p.hasName());
        Assert.assertEquals(STR_0, p.getName());
    }

    /**
     * Check if the specified person builder is equal to standard instance #0.
     * @param pb the person builder to be checked
     */
    private void checkPersonBuilderIsSI0(Person.Builder pb) {
        Assert.assertTrue(pb.isInitialized());
        Assert.assertTrue(pb.hasId());
        Assert.assertEquals(INT_0, pb.getId());
        Assert.assertTrue(pb.hasName());
        Assert.assertEquals(STR_0, pb.getName());
    }

    /**
     * Check if the specified person is equal to standard instance #1.
     * @param p the person to be checked
     */
    private void checkPersonIsSI1(Person p) {
        Assert.assertTrue(p.isInitialized());
        Assert.assertTrue(p.hasId());
        Assert.assertEquals(INT_1, p.getId());
        Assert.assertTrue(p.hasName());
        Assert.assertEquals(STR_1, p.getName());
    }

    /**
     * Check if the specified person builder is equal to standard instance #1.
     * @param pb the person builder to be checked
     */
    private void checkPersonBuilderIsSI1(Person.Builder pb) {
        Assert.assertTrue(pb.isInitialized());
        Assert.assertTrue(pb.hasId());
        Assert.assertEquals(INT_1, pb.getId());
        Assert.assertTrue(pb.hasName());
        Assert.assertEquals(STR_1, pb.getName());
    }

    /**
     * Create a new person equals to standard instance #0.
     * @return the new person object
     */
    private Person newPersonSI0() {
        return newPersonBuilderSI0().build();
    }

    /**
     * Create a new person builder equals to standard instance #0.
     * @return the new person builder object
     */
    private Person.Builder newPersonBuilderSI0() {
        Person.Builder pb = Person.newBuilder();
        pb.setId(INT_0).setName(STR_0);
        return pb;
    }

    /**
     * Create a new person equals to standard instance #1.
     * @return the new person object
     */
    private Person newPersonSI1() {
        return newPersonBuilderSI1().build();
    }

    /**
     * Create a new person builder equals to standard instance #1.
     * @return the new person builder object
     */
    private Person.Builder newPersonBuilderSI1() {
        Person.Builder pb = Person.newBuilder();
        pb.setId(INT_1).setName(STR_1);
        return pb;
    }

    @Test
    public void test_getDefaultInstance() throws Exception {
        Person p = Person.getDefaultInstance();
        checkPersonIsDefault(p);
    }

    @Test
    public void test_newBuilder_build() throws Exception {
        Person.Builder pb = Person.newBuilder();
        pb.setId(INT_0).setName(STR_0);
        checkPersonBuilderIsSI0(pb);
        Person p = pb.build();
        checkPersonIsSI0(p);

        Person.Builder pb2 = Person.newBuilder();
        try {
            pb2.build();
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof UninitializedMessageException);
        }

        Person.Builder pb3 = Person.newBuilder(p);
        checkPersonBuilderIsSI0(pb3);
        Person p3 = pb3.build();
        checkPersonIsSI0(p3);
    }

    @Test
    public void test_toString() throws Exception {
        Person p = newPersonSI0();
        // Person.toString() is overrided
        Assert.assertEquals(
                "id: " + INT_0 + "\n" +
                "name: \"" + STR_0 + "\"\n",
                p.toString());

        Person.Builder pb = newPersonBuilderSI0();
        // Person.Builder.toString() is default
        Assert.assertTrue(LangUtils.hasDefaultToString(pb));
    }

    @Test
    public void test_clear() throws Exception {
        Person.Builder pb = newPersonBuilderSI0();
        pb.clear();
        checkPersonBuilderIsDefault(pb);
    }

    @Test
    public void test_ClearProperties() throws Exception {
        Person.Builder pb = newPersonBuilderSI0();
        pb.clearName();
        Assert.assertFalse(pb.isInitialized());
        Assert.assertTrue(pb.hasId());
        Assert.assertEquals(INT_0, pb.getId());
        Assert.assertFalse(pb.hasName());
        Assert.assertTrue(pb.getName().isEmpty());
    }

    @Test
    public void test_mergeFrom() throws Exception {
        Person.Builder pb = Person.newBuilder();

        Person pSI0 = newPersonSI0();
        pb.mergeFrom(pSI0);
        checkPersonBuilderIsSI0(pb);

        Person pSI1 = newPersonSI1();
        pb.mergeFrom(pSI1);
        checkPersonBuilderIsSI1(pb);
    }

    @Test
    public void test_SerializationAndDeserialization() throws Exception {
        {
            // style 1
            Person p = newPersonSI0();
            byte[] ba = p.toByteArray();
            Person pDeserialized = Person.parseFrom(ba);
            checkPersonIsSI0(pDeserialized);
        }

        {
            // style 2
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Person p = newPersonSI0();
            p.writeTo(baos);
            Person pDeserialized = Person.parseFrom(new ByteArrayInputStream(baos.toByteArray()));
            checkPersonIsSI0(pDeserialized);
        }
    }
}
