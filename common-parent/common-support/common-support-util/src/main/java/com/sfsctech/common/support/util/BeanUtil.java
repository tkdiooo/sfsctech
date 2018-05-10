/**
 *
 */
package com.sfsctech.common.support.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Class BeanUtil
 *
 * @author 张麒 2016年3月28日
 * @version Description：对象工具类
 */
public class BeanUtil extends BeanUtils {

    private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    /**
     * 根据属性名称获取值
     */
    public static Object getPropertyValue(final Object obj, String PropertyName) {
        AssertUtil.notNull(obj, "object不能为空");
        AssertUtil.hasText(PropertyName, "PropertyName");
        try {
            return invokeGetterMethod(obj, PropertyName);
        } catch (Exception e) {
            throw ThrowableUtil.convertExceptionToUnchecked(e);
        }
    }

    /**
     * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问.
     * 如向上转型到Object仍无法找到, 返回null.
     */
    public static Field getAccessibleField(final Object obj, final String fieldName) {
        AssertUtil.notNull(obj, "object不能为空");
        AssertUtil.hasText(fieldName, "fieldName");
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                // NOSONAR
                // Field不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    /**
     * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问. 如向上转型到Object仍无法找到, 返回null.
     * <p>
     * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)
     */
    public static Method getAccessibleMethod(final Object obj, final String methodName, final Class<?>... parameterTypes) {
        AssertUtil.notNull(obj, "object不能为空");
        AssertUtil.isNotBlank(methodName, "methodName不能为空");

        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Method method = superClass.getDeclaredMethod(methodName, parameterTypes);
                method.setAccessible(true);
                return method;
            } catch (NoSuchMethodException e) {
                // NOSONAR
                // Method不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    /**
     * 执行某个对象标注了某个Annotation的方法，如果没有返回空
     */
    public static Object invokeAnnotation(final Object obj, Class<? extends Annotation> annotationClass) {
        AssertUtil.notNull(obj);
        AssertUtil.notNull(annotationClass);
        Method[] methods = obj.getClass().getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotationClass) && null != method.getAnnotation(annotationClass)) {
                try {
                    method.invoke(obj);
                } catch (Exception e) {
                    throw ThrowableUtil.convertExceptionToUnchecked(e);
                }
            }
        }
        return null;
    }

    /**
     * 批量执行对象多个Annotation方法
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> invokeAnnotationsBatch(final Object obj, Class<? extends Annotation>... annotationClasses) {
        AssertUtil.notNull(obj);
        if (ArrayUtil.isEmpty(annotationClasses)) {
            throw new IllegalArgumentException("annotationClasses is empty");
        }
        Map<String, Object> result = new HashMap<>();
        Method[] methods = obj.getClass().getMethods();
        for (Method method : methods) {
            for (Class<? extends Annotation> annotationClass : annotationClasses) {
                if (method.isAnnotationPresent(annotationClass) && null != method.getAnnotation(annotationClass)) {
                    try {
                        result.put(method.getName(), method.invoke(obj));
                        break;
                    } catch (Exception e) {
                        throw ThrowableUtil.convertExceptionToUnchecked(e);
                    }
                }
            }
        }
        return result;
    }

    /**
     * 直接调用对象方法, 无视private/protected修饰符. 用于一次性调用的情况.
     */
    public static Object invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes, final Object[] args) {
        Method method = getAccessibleMethod(obj, methodName, parameterTypes);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
        }

        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw ThrowableUtil.convertExceptionToUnchecked(e);
        }
    }

    /**
     * 调用Getter方法.
     */
    public static Object invokeGetterMethod(Object obj, String propertyName) {
        String getterMethodName = "get" + StringUtil.capitalize(propertyName);
        return invokeMethod(obj, getterMethodName, new Class[]{}, new Object[]{});
    }

    /**
     * 调用Setter方法.使用value的Class来查找Setter方法.
     */
    public static void invokeSetterMethod(Object obj, String propertyName, Object value) {
        invokeSetterMethod(obj, propertyName, value, null);
    }

    /**
     * 调用Setter方法.
     *
     * @param obj          Object
     * @param propertyName Property Name
     * @param params       params
     * @param propertyType 属性类型,为空时使用value的Class替代.
     */
    public static void invokeSetterMethod(Object obj, String propertyName, Object params, Class<?> propertyType) {
        AssertUtil.notNull(obj, "object不能为空");
        AssertUtil.isNotBlank(propertyName, "propertyName不能为空");
        Class<?> type = propertyType != null ? propertyType : params.getClass();
        String setterMethodName = "set" + StringUtil.capitalize(propertyName);
        invokeMethod(obj, setterMethodName, new Class[]{type}, new Object[]{params});
    }

    /**
     * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
     */
    public static Object getFieldValue(final Object obj, final String fieldName) {
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
        }

        Object result = null;
        try {
            result = field.get(obj);
        } catch (IllegalAccessException e) {
            logger.error("不可能抛出的异常{}", e.getMessage());
        }
        return result;
    }

    /**
     * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
     */
    public static void setFieldValue(final Object obj, final String fieldName, final Object value) {
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
        }

        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            logger.error("不可能抛出的异常:{}", e.getMessage());
        }
    }

    /**
     * 通过反射, 获得Class定义中声明的父类的泛型参数的类型. 如无法找到, 返回Object.class.
     *
     * @param clazz The class to introspect
     * @return the first generic declaration, or Object.class if cannot be determined
     */
    public static Class<?> getSuperClassGenricType(final Class<?> clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 通过反射, 获得Class定义中声明的父类的泛型参数的类型. 如无法找到, 返回Object.class.
     *
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic ddeclaration,start from 0.
     * @return the index generic declaration, or Object.class if cannot be determined
     */
    public static Class<?> getSuperClassGenricType(final Class<?> clazz, final int index) {
        AssertUtil.notNull(clazz, "clazz不能为空");
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
            return Object.class;
        }

        return (Class<?>) params[index];
    }

    /**
     * 提取集合中的对象的属性(通过getter函数), 组合成List&lt;?&gt;.
     *
     * @param collection   来源集合.
     * @param propertyName 要提取的属性名.
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> extractElementToList(final Collection<?> collection, final String propertyName, Class<T> cls) {
        List<T> list = new ArrayList<>();

        try {
            for (Object obj : collection) {
                list.add((T) PropertyUtils.getProperty(obj, propertyName));
            }
        } catch (Exception e) {
            logger.error("[" + cls.getSimpleName() + "] " + e.getMessage());
            throw ThrowableUtil.convertExceptionToUnchecked(e);
        }
        return list;
    }

    /**
     * 提取集合中的对象的属性(通过getter函数), 组合成由分割符分隔的字符串.
     *
     * @param collection   来源集合.
     * @param propertyName 要提取的属性名.
     * @param separator    分隔符.
     * @return Json Result
     */
    public static String extractElementToString(final Collection<?> collection, final String propertyName, Class<?> cls, final String separator) {
        List<?> list = extractElementToList(collection, propertyName, cls);
        return StringUtil.join(list, separator);
    }

    /**
     * 转换字符串到相应类型.
     *
     * @param value 待转换的字符串
     * @param clazz 转换目标类型
     */
    public static Object convertStringToObject(String value, Class<?> clazz) {
        try {
            return ConvertUtils.convert(value, clazz);
        } catch (Exception e) {
            throw ThrowableUtil.convertExceptionToUnchecked(e);
        }
    }

    /**
     * 复制实体Bean
     *
     * @param source 数据Bean
     * @param target 目标Bean
     */
    public static <T> void copyPropertiesNotEmpty(T target, T source) {
        try {
            Class l = source.getClass();
            Class v = target.getClass();
            Method[] methods = l.getMethods();
            Map<String, Object> params = new HashMap<>();
            String methodName, key;
            Object value;
            for (Method method1 : methods) {
                methodName = method1.getName();
                if (!methodName.equals("getClass") && methodName.startsWith("get") && !Modifier.isStatic(method1.getModifiers())) {
                    value = method1.invoke(source);
                    if (null != value) {
                        if (!(value instanceof String)) {
                            params.put(methodName.substring(3), value);
                        } else if (!value.equals("")) {
                            params.put(methodName.substring(3), value);
                        }
                    }
                }
            }
            methods = v.getMethods();
            for (Method method : methods) {
                methodName = method.getName();
                if (methodName.startsWith("set")) {
                    key = methodName.substring(3);
                    if (params.containsKey(key) && null != params.get(key)) {
                        method.invoke(target, params.get(key));
                    }
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static <T, S> T copyPropertiesNotEmpty(Class<T> cls, S source) {
        try {
            T target = cls.newInstance();
            copyPropertiesNotEmpty(target, source);
            return target;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * the beanCopierMap
     */
    private static final ConcurrentMap<String, BeanCopier> beanCopierMap = new ConcurrentHashMap<>();


    public static <T, S> T copyBeanForCglib(S source, Class<T> target) {
        T ret = null;
        if (source != null) {
            try {
                ret = target.newInstance();
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("create class[" + target.getName() + "] instance error", e);
            }
            copyBeanForCglib(source, ret);
        }
        return ret;
    }

    public static <T, S> void copyBeanForCglib(S source, T target) {
        if (source != null) {
            BeanCopier copier = getBeanCopier(source.getClass(), target.getClass());
            copier.copy(source, target, new DeepCopyConverter(target.getClass()));
        }
    }

    public static <T, S> List<T> copyListForCglib(List<S> source, Class<T> target) {
        if (ListUtil.isNotEmpty(source)) {
            List<T> list = new ArrayList<>();
            BeanCopier copier = getBeanCopier(source.get(0).getClass(), target);
            DeepCopyConverter converter = new DeepCopyConverter(target);
            source.forEach(s -> {
                try {
                    T t = target.newInstance();
                    copier.copy(s, t, converter);
                    list.add(t);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return list;
        }
        return null;
    }


    /**
     * 获取BeanCopier
     */
    private static BeanCopier getBeanCopier(Class<?> source, Class<?> target) {
        String beanCopierKey = generateBeanKey(source, target);
        if (beanCopierMap.containsKey(beanCopierKey)) {
            return beanCopierMap.get(beanCopierKey);
        } else {
            BeanCopier beanCopier = BeanCopier.create(source, target, true);
            beanCopierMap.putIfAbsent(beanCopierKey, beanCopier);
        }
        return beanCopierMap.get(beanCopierKey);
    }

    /**
     * 生成两个类的key
     */
    private static String generateBeanKey(Class<?> source, Class<?> target) {
        return source.getName() + "@" + target.getName();
    }

    private static class DeepCopyConverter implements Converter {

        /**
         * The Target.
         */
        private Class<?> target;

        /**
         * Instantiates a new Deep copy converter.
         *
         * @param target the target
         */
        public DeepCopyConverter(Class<?> target) {
            this.target = target;
        }

        @Override
        public Object convert(Object value, Class targetClazz, Object methodName) {
            if (value instanceof List) {
                List values = (List) value;
                List retList = new ArrayList<>(values.size());
                for (final Object source : values) {
                    String tempFieldName = methodName.toString().replace("set", "");
                    String fieldName = tempFieldName.substring(0, 1).toLowerCase() + tempFieldName.substring(1);
                    Class clazz = ClassUtil.getElementType(target, fieldName);
                    retList.add(BeanUtil.copyBeanForCglib(source, clazz));
                }
                return retList;
            } else if (value instanceof Map) {
                // TODO 暂时用不到，后续有需要再补充
            } else if (!ClassUtil.isPrimitive(targetClazz)) {
                return BeanUtil.copyBeanForCglib(value, targetClazz);
            }
            return value;
        }
    }
}
