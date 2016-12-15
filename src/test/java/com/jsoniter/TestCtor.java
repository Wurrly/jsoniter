package com.jsoniter;

import junit.framework.TestCase;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TestCtor extends TestCase {

    public static class OneArgCtor {
        String field1;

        public OneArgCtor(String param1) {
            field1 = param1;
        }
    }

    public void test_one_argument() throws IOException {
        Jsoniter.registerExtension(new EmptyExtension() {
            @Override
            public CustomizedConstructor getConstructor(Class clazz) {
                if (clazz == OneArgCtor.class) {
                    return new CustomizedConstructor() {{
                        parameters = (List) Arrays.asList(new Binding() {{
                            fromNames = new String[]{"param1"};
                            name="field1";
                            valueType = String.class;
                        }});
                    }};
                }
                return null;
            }
        });
        Jsoniter iter = Jsoniter.parse("{'param1': 'hello'}".replace('\'', '"'));
        OneArgCtor obj = iter.read(OneArgCtor.class);
        assertEquals("hello", obj.field1);
    }
}