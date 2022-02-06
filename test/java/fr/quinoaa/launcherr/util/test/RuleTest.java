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
