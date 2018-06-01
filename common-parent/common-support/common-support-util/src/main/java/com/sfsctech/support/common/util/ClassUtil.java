package com.sfsctech.support.common.util;

import com.sfsctech.core.base.constants.LabelConstants;
import org.apache.commons.lang3.ClassUtils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Class ClassUtil
 *
 * @author 张麒 2016/5/27.
 * @version Description:
 */
public class ClassUtil extends ClassUtils {

    public static Object convert(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof String) {
            return value.toString();
        } else if (value instanceof Long) {
            return Long.valueOf(value.toString());
        } else if (value instanceof Integer) {
            return Integer.valueOf(value.toString());
        } else if (value instanceof Double) {
            return Double.valueOf(value.toString());
        } else if (value instanceof Float) {
            return Float.valueOf(value.toString());
        } else if (value instanceof Short) {
            return Short.valueOf(value.toString());
        } else if (value instanceof Boolean) {
            return Boolean.valueOf(value.toString());
        } else {
            return value;
        }
    }

    public static <T> Object convertType(Object value, Class<T> cls) {
        if (value == null) {
            return null;
        } else if (cls == String.class) {
            return value.toString();
        } else if (cls == Long.class) {
            return Long.valueOf(value.toString());
        } else if (cls == Integer.class) {
            return Integer.valueOf(value.toString());
        } else if (cls == Double.class) {
            return Double.valueOf(value.toString());
        } else if (cls == Float.class) {
            return Float.valueOf(value.toString());
        } else if (cls == Short.class) {
            return Short.valueOf(value.toString());
        } else if (cls == Boolean.class) {
            return Boolean.valueOf(value.toString());
        } else {
            return value;
        }
    }

    public static Set<Class<?>> getClassesByEndsWith(final String basePackage, final String endsWith) {
        return getClasses(basePackage, endsWith, null);
    }

    public static Set<Class<?>> getClassesByAnnotation(final String basePackage, final Class<Annotation> annotation) {
        return getClasses(basePackage, null, annotation);
    }

    public static Set<Class<?>> getClasses(final String basePackage) {
        return getClasses(basePackage, null, null);
    }

    /**
     * 从包package中获取所有的Class
     *
     * @param basePackage
     * @return
     */
    private static Set<Class<?>> getClasses(final String basePackage, String endsWith, Class<Annotation> annotation) {
        // 第一个class类的集合
        Set<Class<?>> classes = new LinkedHashSet<>();
        if (StringUtil.isBlank(basePackage)) {
            return classes;
        }
        for (String packageName : basePackage.split(LabelConstants.COMMA)) {
            String packageDirName = packageName.trim().replace(LabelConstants.PERIOD.charAt(0), LabelConstants.FORWARD_SLASH.charAt(0));
            // 定义一个枚举的集合 并进行循环来处理这个目录下的things
            Enumeration<URL> dirs;
            try {
                dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
                // 循环迭代下去
                while (dirs.hasMoreElements()) {
                    // 获取下一个元素
                    URL url = dirs.nextElement();
                    // 得到协议的名称
                    String protocol = url.getProtocol();
                    // 如果是以文件的形式保存在服务器上
                    if ("file".equals(protocol)) {
                        // 获取包的物理路径
                        String filePath = URLDecoder.decode(url.getFile(), LabelConstants.UTF8);
                        // 以文件的方式扫描整个包下的文件 并添加到集合中
                        findAndAddClassesInPackageByFile(packageName, filePath, classes, endsWith, annotation);
                    }
                    // 如果是jar包文件
                    else if ("jar".equals(protocol)) {
                        // 定义一个JarFile
                        JarFile jar;
                        try {
                            // 获取jar
                            jar = ((JarURLConnection) url.openConnection()).getJarFile();
                            // 从此jar包 得到一个枚举类
                            Enumeration<JarEntry> entries = jar.entries();
                            // 同样的进行循环迭代
                            while (entries.hasMoreElements()) {
                                // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                                JarEntry entry = entries.nextElement();
                                String name = entry.getName();
                                // 如果是以/开头的
                                if (name.charAt(0) == '/') {
                                    // 获取后面的字符串
                                    name = name.substring(1);
                                }
                                // 如果前半部分和定义的包名相同
                                if (name.startsWith(packageDirName)) {
                                    int index = name.lastIndexOf(LabelConstants.FORWARD_SLASH.charAt(0));
                                    // 如果以"/"结尾 是一个包
                                    if (index != -1) {
                                        // 获取包名 把"/"替换成"."
                                        packageName = name.substring(0, index).replace(LabelConstants.FORWARD_SLASH.charAt(0), LabelConstants.PERIOD.charAt(0));
                                        // 如果是一个.class文件 而且不是目录
                                        if (name.endsWith(".class") && !entry.isDirectory()) {
                                            // 去掉后面的".class" 获取真正的类名
                                            String className = name.substring(packageName.length() + 1, name.length() - 6);
                                            try {
                                                // 所有Class
                                                if (annotation == null && StringUtil.isBlank(endsWith)) {
                                                    classes.add(Class.forName(packageName + '.' + className));
                                                }
                                                // 包结尾匹配
                                                else if (StringUtil.isNotBlank(endsWith) && packageName.contains(LabelConstants.PERIOD + endsWith)) {
                                                    classes.add(Class.forName(packageName + '.' + className));
                                                }
                                                // 注解匹配
                                                else if (null != annotation) {
                                                    Class cls = Class.forName(packageName + '.' + className);
                                                    if (cls.isAnnotationPresent(annotation)) {
                                                        classes.add(cls);
                                                    }
                                                }
                                            } catch (ClassNotFoundException e) {
                                                // log.error("添加用户自定义视图类错误 找不到此类的.class文件");
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (IOException e) {
                            // log.error("在扫描用户定义视图时从jar包获取文件出错");
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return classes;
    }

    /**
     * 以文件的形式来获取包下的所有Class
     */
    private static void findAndAddClassesInPackageByFile(String packageName, String filePath, Set<Class<?>> classes, String endsWith, Class<Annotation> annotation) {
        // 获取此包的目录 建立一个File
        File dir = new File(filePath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            // log.warn("用户定义包名 " + packageName + " 下没有任何文件");
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
        File[] dirfiles = dir.listFiles(file -> file.isDirectory() || file.getName().endsWith(".class"));
        // 循环所有文件
        if (null != dirfiles) {
            for (File file : dirfiles) {
                // 如果是目录 则继续扫描
                if (file.isDirectory()) {
                    findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), classes, endsWith, annotation);
                } else {
                    // 去掉后面的.class 只留下类名
                    String className = file.getName().substring(0, file.getName().length() - 6);
                    try {
                        // 所有Class
                        if (annotation == null && StringUtil.isBlank(endsWith)) {
                            classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
                        }
                        // 包结尾匹配
                        else if (StringUtil.isNotBlank(endsWith) && filePath.contains(LabelConstants.BACK_SLASH + endsWith)) {
                            classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
                        }
                        // 注解匹配
                        else if (null != annotation) {
                            Class cls = Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className);
                            if (cls.isAnnotationPresent(annotation)) {
                                classes.add(cls);
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        // log.error("添加用户自定义视图类错误 找不到此类的.class文件");
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    private static final Map<Class<?>, Class<?>> primitiveMap = new HashMap<>(9);

    static {
        primitiveMap.put(String.class, String.class);
        primitiveMap.put(Boolean.class, boolean.class);
        primitiveMap.put(Byte.class, byte.class);
        primitiveMap.put(Character.class, char.class);
        primitiveMap.put(Double.class, double.class);
        primitiveMap.put(Float.class, float.class);
        primitiveMap.put(Integer.class, int.class);
        primitiveMap.put(Long.class, long.class);
        primitiveMap.put(Short.class, short.class);
        primitiveMap.put(Date.class, Date.class);
    }

    /**
     * 判断基本类型
     */
    public static boolean isPrimitive(Class<?> clazz) {
        return primitiveMap.containsKey(clazz) || clazz.isPrimitive();
    }

    /**
     * 获取方法返回值类型
     */
    public static Class<?> getElementType(Class<?> tartget, String fieldName) {
        Class<?> elementTypeClass;
        try {
            Type type = tartget.getDeclaredField(fieldName).getGenericType();
            ParameterizedType t = (ParameterizedType) type;
            String classStr = t.getActualTypeArguments()[0].toString().replace("class ", "");
            elementTypeClass = Thread.currentThread().getContextClassLoader().loadClass(classStr);
        } catch (ClassNotFoundException | NoSuchFieldException | SecurityException e) {
            throw new RuntimeException("get fieldName[" + fieldName + "] error", e);
        }
        return elementTypeClass;
    }
}
