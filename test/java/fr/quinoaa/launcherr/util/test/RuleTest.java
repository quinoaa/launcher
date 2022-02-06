/*
 * MIT License
 *
 * Copyright (c) 2022 quinoaa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package fr.quinoaa.launcherr.util.test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import fr.quinoaa.launcherr.util.OsUtil;
import fr.quinoaa.launcherr.util.RuleUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class RuleTest {
    String json = "[{action:\"allow\"}]";
    @Test
    public void testEmpty(){
        String result = RuleUtil.getAction(new Gson().fromJson(json, JsonArray.class));

        Assertions.assertEquals("allow", result);
    }

    String json2 = "[{action:\"allow\",features:{test:false}}]";
    @Test
    public void testFeatureNotEqual(){
        Map<String, Boolean> map = new HashMap<>();

        map.put("test", true);

        String result = RuleUtil.getAction(new Gson().fromJson(json2, JsonArray.class), map);
        Assertions.assertNull(result);

        //Assertions.assertNull(result);
    }
    String json3 = "[{action:\"allow\",features:{test:false}}]";
    @Test
    public void testFeatureEqual(){
        Map<String, Boolean> map = new HashMap<>();

        map.put("test", false);

        String result = RuleUtil.getAction(new Gson().fromJson(json3, JsonArray.class), map);
        Assertions.assertEquals("allow", result);
    }
    String json4= "[{action:\"allow\",os:{name:"+ OsUtil.getOs() +"}}]";
    @Test
    public void testOs(){
        String result = RuleUtil.getAction(new Gson().fromJson(json4, JsonArray.class));
        Assertions.assertEquals("allow", result);
    }
    String json5 = "[{action:\"allow\",os:{name:\"\"}}]";
    @Test
    public void testNotOs(){
        String result = RuleUtil.getAction(new Gson().fromJson(json3, JsonArray.class));
        Assertions.assertNull(result);
    }
    String json6 = "[{action:a}, {action:b}, {action:c}, {action:d}]";
    @Test
    public void testMultiple(){
        String result = RuleUtil.getAction(new Gson().fromJson(json6, JsonArray.class));
        Assertions.assertEquals("d", result);
    }
}
