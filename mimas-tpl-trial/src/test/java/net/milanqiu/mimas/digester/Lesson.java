package net.milanqiu.mimas.digester;

import com.google.common.base.Preconditions;

import java.math.BigDecimal;

/**
 * Creation Date: 2018-09-07
 * @author Milan Qiu
 */
public class Lesson {

    private int id;
    private String name;
    private BigDecimal passRate;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public BigDecimal getPassRate() {
        return passRate;
    }
    public void setPassRate(String passRatePercentStr) {
        Preconditions.checkArgument(passRatePercentStr.endsWith("%"));
        //noinspection BigDecimalMethodWithoutRoundingCalled
        this.passRate = new BigDecimal(passRatePercentStr.substring(0, passRatePercentStr.length()-1)).divide(new BigDecimal(100));
    }
}
