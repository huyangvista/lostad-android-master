package com.lostad.applib.util;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 实体操作类
 */
public class BeanUtil {
    /**
     * 两个实体之间赋值
     *
     * @param dist
     * @param src
     * @param copyNull
     */
    public static void copyProperties(Object dist, Object src, boolean copyNull) {
        Class classDist = dist.getClass();
        Class classSrc = src.getClass();


        Field fields[] = classSrc.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field filed = fields[i];
            filed.setAccessible(true);//

            try {
                Object srcValue = filed.get(src);
                String fieldName = filed.getName();
                //复制
                //Field distField = classDist.getField(fieldName);//public feld
                Field distField = classDist.getDeclaredField(fieldName);//all field
                distField.setAccessible(true);
                if (srcValue == null) {
                    if (copyNull) {
                        distField.set(dist, null);//强制赋值给dist
                    }
                } else {
                    distField.set(dist, srcValue);//强制赋值给dist
                }

            } catch (Exception e) {
            }
        }
    }

    /**
     * 对象之间字段赋值【包括父类】
     *
     * @param to   复制后的对象
     * @param from 原对象
     * @return
     */
    public static void copyField(Object to, Object from) {
        List<Field[]> listTo = ReflectUtil.getFieldsAll(to.getClass());
        List<Field[]> listFrom = ReflectUtil.getFieldsAll(from.getClass());
        for (int i = listTo.size() - 1; i >= 0; i--) {
            Field[] fst = listTo.get(i);
            for (int j = fst.length - 1; j >= 0; j--) {
                Field ft = fst[j];
                fieldwork:
                for (int k = 0; k < listFrom.size(); k++) {
                    Field[] fsf = listFrom.get(k);
                    for (int m = fsf.length - 1; m >= 0; m--) {
                        Field ff = fsf[m];
                        if (ft.getName().equals(ff.getName()) && ft.getType().equals(ff.getType())) {
                            try {
                                ft.set(to, ff.get(from));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            continue fieldwork;
                        }
                    }
                }
            }
        }
    }

    /**
     * 快速键值对
     *
     * @param o
     * @return
     */
    public static Map<String, Object> markMap(Object... o) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < o.length; i += 2) {
            map.put(o[i].toString(), o[i + 1]);
        }
        return map;
    }

    /**
     * 序列化为 Map 从 Bean
     *
     * @param bean
     * @return
     */
    public static Map<String, Object> parseMap(Object bean) {
        Map<String, Object> map = new HashMap<>();
        List<Field[]> list = ReflectUtil.getFieldsAll(bean.getClass());
        for (int i = list.size() - 1; i >= 0; i--) {
            Field[] fs = list.get(i);
            for (int j = fs.length - 1; j >= 0; j--) {
                try {
                    Field f = fs[j];
                    Object val = f.get(bean);
                    map.put(f.getName(), val);
                } catch (Exception e) {
                    continue;
                }
            }
        }
        return map;
    }

    /**
     * 序列化为 Bean 从 Map
     *
     * @param bean
     * @param map
     */
    public static void parseBean(Object bean, Map<String, Object> map) {
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> en = it.next();
            String key = en.getKey();
            Object value = en.getValue();
            ReflectUtil.setFieldValueAll(bean, key, value);
        }
    }

    //Demo
    public static void main(String[] args) {
        //map => bean
        Map<String, Object> map = markMap("id", "123", "sex", "女");
        M m = new M();
        System.out.println(((P) m).id + " " + m.sex);
        BeanUtil.parseBean(m, map);
        System.out.println(((P) m).id + " " + m.sex);
        System.out.println();

        //bean => map
        map = BeanUtil.parseMap(m);
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> en = it.next();
            String key = en.getKey();
            Object value = en.getValue();
            System.out.println(key + ":" + value);
        }
        System.out.println();

        //字段赋值
        G g = new G();
        System.out.println(g.id + " " + g.sex);
        BeanUtil.copyField(g, m);
        System.out.println(g.id + " " + g.sex);
        System.out.println();

    }

    private static class P {
        private String id = "0";
    }

    private static class M extends P {
        private String sex = "m";
    }

    private static class G {
        private String id = "1";
        private String sex = "g";
    }
}