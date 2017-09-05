package com.sfsctech.common.util;

import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * Class ArrayListUtil
 *
 * @author 张麒 2016年3月31日
 * @version Description：
 */
public class ListUtil extends CollectionUtils {

    /**
     * 将字符串Set转换成String
     *
     * @param collection --字符串集合
     * @param separate   --分隔符
     * @return String
     */
    public static String toString(Collection<String> collection, String separate) {
        StringBuilder buffer = new StringBuilder();
        if (!isEmpty(collection)) {
            collection.forEach(s -> {
                if (buffer.length() > 0) {
                    buffer.append(separate);
                    buffer.append(s);
                } else {
                    buffer.append(s);
                }
            });
        }
        return buffer.toString();
    }


    @SafeVarargs
    public static <T> List<T> toList(T... a) {
        List<T> list = new ArrayList<>();
        Collections.addAll(list, a);
        return list;
    }

    /**
     * map的value转list对象
     *
     * @param map Map
     * @param <K> Key
     * @param <V> Bean
     * @return List<code><</code>V<code>></code>
     */
    public static <K, V> List<V> toList(Map<K, V> map) {
        List<V> list = new ArrayList<>();
        map.forEach((key, value) -> list.add(value));
        return list;
    }

    /**
     * 复制转换List
     *
     * @param cls     Class
     * @param dataSet DataSet
     * @return List<code><</code>T<code>></code>
     */
    public static <S, T> List<T> copyConvert(Class<T> cls, List<S> dataSet) {
        List<T> list = new ArrayList<>();
        T target;
        for (S s : dataSet) {
            try {
                target = cls.newInstance();
                BeanUtil.copyPropertiesNotEmpty(target, s);
                list.add(target);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static <T> T getBeanByList(String PropertyName, Object PropertyValue, List<T> dataSet) {
        for (T t : dataSet) {
            if (BeanUtil.getPropertyValue(t, PropertyName).equals(PropertyValue)) {
                return t;
            }
        }
        return null;
    }

    /**
     * List比较是否相同
     *
     * @param c1 List
     * @param c2 List
     * @return Boolean
     */
    public static boolean equals(List c1, List c2) {
        boolean bool = true;
        if (null != c1 && null != c2) {
            if (c1.size() != c2.size())
                bool = false;
            else
                for (int i = 0; i < c1.size(); i++) {
                    if (!c1.get(i).getClass().equals(c2.get(i).getClass())) {
                        bool = false;
                        break;
                    } else if (!c1.get(i).equals(c2.get(i))) {
                        bool = false;
                        break;
                    }
                }
        } else {
            bool = false;
        }
        return bool;
    }

    /**
     * 集合排序
     *
     * @param list         集合
     * @param PropertyName 排序的属性名称
     * @param order        true：asc、false：desc
     */
    public static <T> void sort(List<T> list, final String PropertyName, final boolean order) {
        list.sort((arg0, arg1) -> {
            Object o1 = BeanUtil.getPropertyValue(arg0, PropertyName);
            Object o2 = BeanUtil.getPropertyValue(arg1, PropertyName);
            return compareTo(o1, o2, order);
        });
    }

    public static int compareTo(Object o1, Object o2, boolean order) {
        if (null != o1 && null != o2) {
            if (o1 instanceof Byte && o2 instanceof Byte) {
                byte n1 = Byte.valueOf(o1.toString());
                byte n2 = Byte.valueOf(o2.toString());
                if (order)
                    return n1 > n2 ? 1 : n1 == n2 ? 0 : -1;
                else
                    return n2 > n1 ? 1 : n1 == n2 ? 0 : -1;
            } else if (o1 instanceof Short && o2 instanceof Short) {
                short n1 = Short.valueOf(o1.toString());
                short n2 = Short.valueOf(o2.toString());
                if (order)
                    return n1 > n2 ? 1 : n1 == n2 ? 0 : -1;
                else
                    return n2 > n1 ? 1 : n1 == n2 ? 0 : -1;
            } else if (o1 instanceof Float && o2 instanceof Float) {
                float n1 = Float.valueOf(o1.toString());
                float n2 = Float.valueOf(o2.toString());
                if (order)
                    return n1 > n2 ? 1 : n1 == n2 ? 0 : -1;
                else
                    return n2 > n1 ? 1 : n1 == n2 ? 0 : -1;
            } else if (o1 instanceof Double && o2 instanceof Double) {
                double n1 = Double.valueOf(o1.toString());
                double n2 = Double.valueOf(o2.toString());
                if (order)
                    return n1 > n2 ? 1 : n1 == n2 ? 0 : -1;
                else
                    return n2 > n1 ? 1 : n1 == n2 ? 0 : -1;
            } else if (o1 instanceof Long && o2 instanceof Long) {
                long n1 = Long.valueOf(o1.toString());
                long n2 = Long.valueOf(o2.toString());
                if (order)
                    return n1 > n2 ? 1 : n1 == n2 ? 0 : -1;
                else
                    return n2 > n1 ? 1 : n1 == n2 ? 0 : -1;
            } else if (o1 instanceof Integer && o2 instanceof Integer) {
                int n1 = Integer.valueOf(o1.toString());
                int n2 = Integer.valueOf(o2.toString());
                if (order)
                    return n1 > n2 ? 1 : n1 == n2 ? 0 : -1;
                else
                    return n2 > n1 ? 1 : n1 == n2 ? 0 : -1;
            } else if (o1 instanceof Character && o2 instanceof Character) {
                int n1 = o1.hashCode();
                int n2 = o2.hashCode();
                if (order)
                    return n1 > n2 ? 1 : n1 == n2 ? 0 : -1;
                else
                    return n2 > n1 ? 1 : n1 == n2 ? 0 : -1;
            } else if (o1 instanceof String && o2 instanceof String) {
                if (order)
                    return stringCompareTo(o1.toString(), o2.toString());
                else
                    return stringCompareTo(o2.toString(), o1.toString());
            }
        }
        return 0;
    }

    public static <T> boolean isContentNull(List<T> list) {
        if (list == null || list.isEmpty()) {
            return true;
        } else {
            for (T t : list) {
                if (StringUtil.isEmpty(t.toString()))
                    return true;
            }
            return false;
        }
    }

    private static int stringCompareTo(String s1, String s2) {
        char[] chars1 = s1.toCharArray();
        char[] chars2 = s2.toCharArray();

        for (int i = 0; i < chars1.length; i++) {
            if (chars1[i] > chars2[i]) return 1;
            else if (chars1[i] < chars2[i]) return -1;
        }
        return 0;
    }

}
