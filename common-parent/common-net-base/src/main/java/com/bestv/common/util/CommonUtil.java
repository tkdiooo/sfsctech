//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.util;

import com.bestv.common.dto.Node;
import com.bestv.common.lang.enums.BaseEnum;
import com.bestv.common.lang.enums.Brace;
import java.io.File;
import java.io.FileFilter;
import java.net.InetAddress;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

public final class CommonUtil {
    private static final Map<Class<? extends BaseEnum>, Map<String, BaseEnum>> BASE_ENUM_CACHE = new ConcurrentHashMap();

    public CommonUtil() {
    }

    public static String getCurrentClassName() {
//        return ((<undefinedtype>)(new Object() {
//            String getContainerClassName() {
//                String className = this.getClass().getName();
//                return className.substring(0, className.lastIndexOf(36));
//            }
//        })).getContainerClassName();
        return "";
    }

    public static List<Class> getClassesUnderPackage(String packageName) {
        return getClassesUnderPackage(packageName, Collections.singletonList(new ClassFilter() {
            public boolean reject(Class clazz) {
                return false;
            }
        }));
    }

    public static List<Class> getClassesUnderPackage(String packageName, List<ClassFilter> classFilters) {
        String packageUrl = packageName.replace(".", "/");
        ArrayList classes = new ArrayList();

        try {
            Enumeration urls = Thread.currentThread().getContextClassLoader().getResources(packageUrl);

            while(true) {
                label61:
                while(true) {
                    URL url;
                    do {
                        if (!urls.hasMoreElements()) {
                            return classes;
                        }

                        url = (URL)urls.nextElement();
                    } while(url == null);

                    String protocol = url.getProtocol();
                    if (StringUtils.equals(protocol, "file")) {
                        String packagePath = url.getPath().replaceAll("%20", "");
                        addClass(classes, packagePath, packageName, classFilters);
                    } else if (StringUtils.equals(protocol, "jar")) {
                        JarURLConnection jarURLConnection = (JarURLConnection)url.openConnection();
                        if (jarURLConnection != null) {
                            JarFile jarFile = jarURLConnection.getJarFile();
                            Enumeration jarEntryEnumeration = jarFile.entries();

                            while(true) {
                                String jarEntryName;
                                do {
                                    if (!jarEntryEnumeration.hasMoreElements()) {
                                        continue label61;
                                    }

                                    JarEntry jarEntry = (JarEntry)jarEntryEnumeration.nextElement();
                                    jarEntryName = jarEntry.getName();
                                } while(!jarEntryName.endsWith(".class"));

                                String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(46)).replaceAll("/", ".");
                                Class clazz = Class.forName(className);
                                boolean reject = false;
                                Iterator var15 = classFilters.iterator();

                                while(var15.hasNext()) {
                                    ClassFilter classFilter = (ClassFilter)var15.next();
                                    if (classFilter != null && classFilter.reject(clazz)) {
                                        reject = true;
                                        break;
                                    }
                                }

                                if (!reject) {
                                    classes.add(clazz);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception var17) {
            throw new RuntimeException(var17);
        }
    }

    public static String convert2IPAdress(InetAddress inetAddress) {
        StringBuffer stringBuffer = new StringBuffer();
        byte[] var2 = inetAddress.getAddress();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            int value = b & 255;
            value = (value + 256) % 256;
            stringBuffer.append(value).append(".");
        }

        return stringBuffer.substring(0, stringBuffer.length() - 1);
    }

    public static String buildUrl(String ipAddress, int port) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(ipAddress).append(":").append(port);
        return stringBuffer.toString();
    }

    public static <T extends BaseEnum> T getByCode(String code, Class<? extends T> enumClass) {
        Map<String, BaseEnum> classEnumsMap = (Map)BASE_ENUM_CACHE.get(enumClass);
        if (classEnumsMap == null) {
             classEnumsMap = new HashMap();
            BaseEnum[] classEnums = (BaseEnum[])enumClass.getEnumConstants();
            if (classEnums != null && classEnums.length > 0) {
                BaseEnum[] var4 = classEnums;
                int var5 = classEnums.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    BaseEnum baseEnum = var4[var6];
                    classEnumsMap.put(baseEnum.getCode(), baseEnum);
                }
            }

            classEnumsMap = MapUtils.unmodifiableMap(classEnumsMap);
            BASE_ENUM_CACHE.putIfAbsent(enumClass, classEnumsMap);
        }

        return (T)classEnumsMap.get(code);
    }

    public static String nodeToJsonString(Node node, Map<String, Object> cacheMap, boolean needFormat) {
        return nodeToJsonStringWithOutBrace(node, cacheMap, needFormat);
    }

    private static String nodeToJsonStringWithOutBrace(Node node, Map<String, Object> cacheMap, boolean needFormat) {
        boolean isNodeArray = node.isNodeArray();
        boolean isFatherNodeArray = node.getFatherNode() != null && node.getFatherNode().isNodeArray();
        StringBuilder stringBuilder = new StringBuilder();
        if (StringUtils.isNotBlank(node.getNodeName()) && !isFatherNodeArray) {
            stringBuilder.append("\"").append(node.getNodeName()).append("\":");
        }

        if (!node.isChildNodesExists()) {
            if (node.getValue() == null) {
                throw new RuntimeException("非法的节点, 该节点没有子节点也没有值");
            } else {
                String transString;
                if (node.isNeedFormat()) {
                    transString = "{" + cacheMap.size() + "}";
                    cacheMap.put(transString, node.getValue());
                } else {
                    transString = "\"" + node.getValue() + "\"";
                }

                return stringBuilder.append(transString).toString();
            }
        } else {
            if (isNodeArray) {
                stringBuilder.append("[");
            } else {
                stringBuilder.append(outputBrace(Brace.LEFT_BRACE, needFormat));
            }

            if (node.getValue() != null) {
                stringBuilder.append("\"@type\":\"").append(node.getValue()).append("\",");
            }

            Iterator var6 = node.getChildNodes().iterator();

            while(var6.hasNext()) {
                Entry<String, Node> entry = (Entry)var6.next();
                stringBuilder.append(nodeToJsonStringWithOutBrace((Node)entry.getValue(), cacheMap, needFormat)).append(",");
            }

            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            if (isNodeArray) {
                stringBuilder.append("]");
            } else {
                stringBuilder.append(outputBrace(Brace.RIGHT_BRACE, needFormat));
            }

            return stringBuilder.toString().replace("''", "");
        }
    }

    private static String outputBrace(Brace brace, boolean needFormat) {
        return needFormat ? "'" + brace.getValue() + "'" : brace.getValue();
    }

    private static void addClass(List<Class> classes, String packagePath, String packageName, List<ClassFilter> classFilters) {
        if (classes == null) {
            throw new RuntimeException("class集合为空!");
        } else {
            File[] files = (new File(packagePath)).listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    return pathname.isFile() && pathname.getName().endsWith(".class") || pathname.isDirectory();
                }
            });
            if (files != null) {
                File[] var5 = files;
                int var6 = files.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    File file = var5[var7];
                    String fileName = file.getName();
                    String className;
                    if (!file.isFile()) {
                        className = packagePath + "/" + fileName;
                        String subPackageName = fileName;
                        if (StringUtils.isNotBlank(packageName)) {
                            subPackageName = packageName + "." + fileName;
                        }

                        addClass(classes, className, subPackageName, classFilters);
                    } else {
                        className = fileName.substring(0, fileName.lastIndexOf(46));
                        if (StringUtils.isNotBlank(packageName)) {
                            className = packageName + "." + className;
                        }

                        try {
                            Class clazz = Class.forName(className);
                            boolean reject = false;
                            Iterator var13 = classFilters.iterator();

                            while(var13.hasNext()) {
                                ClassFilter classFilter = (ClassFilter)var13.next();
                                if (classFilter != null && classFilter.reject(clazz)) {
                                    reject = true;
                                    break;
                                }
                            }

                            if (!reject) {
                                classes.add(clazz);
                            }
                        } catch (ClassNotFoundException var15) {
                            throw new RuntimeException(var15);
                        }
                    }
                }

            }
        }
    }
}
