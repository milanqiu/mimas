package net.milanqiu.mimas.code.script;

import org.junit.Assert;
import org.junit.Test;

/**
 * Creation Date: 2016-07-26
 * @author Milan Qiu
 */
public class HtmlUtilsTest {

    @Test
    public void test_htmlSpaces() throws Exception {
        Assert.assertEquals("", HtmlUtils.htmlSpaces(-1));
        Assert.assertEquals("", HtmlUtils.htmlSpaces(0));
        Assert.assertEquals("&nbsp;", HtmlUtils.htmlSpaces(1));
        Assert.assertEquals("&nbsp;&nbsp;&nbsp;", HtmlUtils.htmlSpaces(3));
        Assert.assertEquals("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", HtmlUtils.htmlSpaces(5));
        Assert.assertEquals("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", HtmlUtils.htmlSpaces(8));
    }

    @Test
    public void test_htmlEscape() throws Exception {
        Assert.assertEquals("&#39;&lt;title&gt;&quot;Tom&nbsp;&amp;&nbsp;Jerry&quot;&lt;/title&gt;&#39;",
                HtmlUtils.htmlEscape("'<title>\"Tom & Jerry\"</title>'"));
        Assert.assertEquals("", HtmlUtils.htmlEscape(""));
    }

    @Test
    public void test_optionsOfIntRange() throws Exception {
        Assert.assertEquals("<option value=\"1\">1</option><option value=\"2\" selected>2</option><option value=\"3\">3</option>",
                HtmlUtils.optionsOfIntRange(1, 3, 2));
        Assert.assertEquals("<option value=\"1\">1</option><option value=\"2\">2</option><option value=\"3\">3</option>",
                HtmlUtils.optionsOfIntRange(1, 3, 0));
        Assert.assertEquals("", HtmlUtils.optionsOfIntRange(2, 1, 0));
    }
}
