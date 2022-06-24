import com.sfsctech.support.common.security.EncrypterTool;
import com.sfsctech.support.common.util.FileUtil;
import com.sfsctech.support.common.util.StringUtil;
import org.dom4j.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class LoadFile
 *
 * @author 张麒 2022-4-1.
 * @version Description:
 */
public class LoadFile {
    public static void main(String[] args) {
        System.out.println(EncrypterTool.encrypt(EncrypterTool.Security.Des3ECBHex, "transwarp"));
        File test = new File("D:\\测试.log");
        File fs_sms = new File("D:\\fs_sms-update.txt");
        File fs_sms_cn = new File("D:\\fs_sms-update中文.txt");
        File fs_sms_batch = new File("D:\\fs_sms_batch-update.txt");
        File fs_sms_select = new File("D:\\fs_sms-select.txt");
        File fs_sms_batch_select = new File("D:\\fs_sms_batch-select.txt");
        try {
            List<String> list = FileUtil.readLines(test, "utf-8");
            Map<Integer, String> count = new HashMap<>();
            String update_fs_sms = "--update sfsc_sms.fs_sms fs set fs.status = '%s',fs.deny_remarks = '%s',fs.IS_FINISHED = '1',fs.FINISHED_DATE = sysdate where fs.sms_no = '%s' and fs.status = '2'";
            String update_fs_sms_batch = "--update sfsc_sms.fs_sms_batch fsb set fsb.status='%s',fsb.is_finished='1',fsb.finished_date=sysdate,is_queue='0' where fsb.batch_no=(select fs.batch_no from sfsc_sms.fs_sms fs where fs.sms_no = '%s') and fsb.status = '2'";
//            String select_fs_sms = "select * from sfsc_sms.fs_sms fs where fs.sms_no = '%s'";
//            String select_fs_sms_batch = "select * from  sfsc_sms.fs_sms_batch fsb where fsb.batch_no=(select fs.batch_no from sfsc_sms.fs_sms fs where fs.sms_no = '%s')";
//            String sql3 = "select b.biz_type_name,count(*) from sfsc_sms.fs_sms_biz_type b,sfsc_sms.fs_sms_batch fsb,sfsc_sms.fs_sms fs\n" +
//                    "where b.biz_type=fsb.biz_type and fsb.batch_no=fs.batch_no and(";
//                        " fs.sms_no in ('28432199','28432175') or\n" +
//                        " fs.sms_no in ('28432116','28432102')\n" +
//                        " )\n" +
//                        "group by b.biz_type_name";
//            String sql = "select fs.* from sfsc_sms.fs_sms fs where fs.status = '2' and (";
            String sql1 = "select * from sfsc_sms.fs_sms fs where ";
            String sql2 = "select * from  sfsc_sms.fs_sms_batch fsb where fsb.batch_no in (select fs.batch_no from sfsc_sms.fs_sms fs where ";
            Map<String, String> updateMap = new HashMap();
            Map<String, String> checkMap = new HashMap();
            Set<String> set = new HashSet<>();
            StringBuilder sb = new StringBuilder("fs.sms_no in (");
            int errorCount = 0, checkCount = 0;
            for (int i = 0; i < list.size(); i++) {
                String f = list.get(i);
                if (f.indexOf("<?xml version=\"1.0\" encoding=\"UTF-8\"?><response><error>0</error><message></message></response>") <= 0) {
                    String xml = f.substring(f.indexOf("after= ") + "after= ".length());
                    checkCount++;
                    try {
                        Document document = DocumentHelper.parseText(xml);
                        Iterator iterator = document.getRootElement().elementIterator();
                        boolean bool = false;
                        Map<String, String> map = new HashMap<>();
                        int c = 0, b = 0;
                        while (iterator.hasNext()) {
                            Element node = (Element) iterator.next();
                            if (node.getName().equals("message")) {
                                c++;
                                String seqid = null, state = null;
                                Iterator nodeIterator = node.elementIterator();
                                while (nodeIterator.hasNext()) {
                                    Element message = (Element) nodeIterator.next();
                                    if (message.getName().equals("seqid")) {
                                        if (StringUtil.isNotBlank(message.getText())) {
                                            seqid = message.getText();
                                        } else {
                                            bool = true;
                                            b++;
                                        }
                                    }
                                    if (message.getName().equals("state")) {
                                        state = message.getText();
                                    }
                                }
                                if (StringUtil.isNotBlank(seqid)) {
                                    map.put(seqid, state);
                                    errorCount++;
                                    set.add(seqid);
                                }
                            }
                        }
                        if (bool) {
                            System.out.println("行数：" + i + 1 + ",当前行有" + c + "个message,其中有" + b + "个空的seqid,需update的数据有" + map.size() + "条");
                            if (map.size() != c - b) {
                                System.out.println(map);
                            }
                            updateMap.putAll(map);
                        } else {
                            map.forEach((key, value) -> {
                                if (!checkMap.containsKey(key)) {
                                    checkMap.put(key, value);
                                }
                            });
                        }
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
//                    System.out.println(xml);
                }
//                if (StringUtil.isNotBlank(f) && f.indexOf("消息id=") > 0) {
//                    String no = f.substring(f.indexOf("消息id=") + "消息id=".length(), f.indexOf("|状态="));
//                    if (no.length() > 6) {
//                        String code = f.substring(f.indexOf("状态=") + "状态=".length(), f.indexOf("|报告时间="));
//                        set.put(no, code);
//                    }
//                } else
//                    count.put(i, f);
            }
            AtomicInteger i = new AtomicInteger();
            updateMap.forEach((no, code) -> {
                if (no.length() > 6) {
                    try {
                        String status = "";
                        if ("DELIVRD".equals(code)) {
                            status = "3";
                            code = "";
//                            FileUtil.writeStringToFile(fs_sms, String.format(update_fs_sms, status, code, no) + ";\r\n", "utf-8", true);
                        } else {
                            status = "4";
                            code = "运营商返回错误编码：" + code;
//                            FileUtil.writeStringToFile(fs_sms_cn, String.format(update_fs_sms, status, code, no) + ";\r\n", "utf-8", true);
                        }
                        FileUtil.writeStringToFile(fs_sms, String.format(update_fs_sms, status, code, no) + ";\r\n", "utf-8", true);
                        FileUtil.writeStringToFile(fs_sms_batch, String.format(update_fs_sms_batch, status, no) + ";\r\n", "utf-8", true);
//                        FileUtil.writeStringToFile(fs_sms_select, String.format(select_fs_sms, no) + ";\r\n", "utf-8", true);
//                        FileUtil.writeStringToFile(fs_sms_batch_select, String.format(select_fs_sms_batch, no) + ";\r\n", "utf-8", true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else
                    System.out.println(no);
                i.getAndIncrement();
            });
            i.set(0);
            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            StringBuilder sb3 = new StringBuilder();
            StringBuilder sb4 = new StringBuilder();
            updateMap.forEach((no, code) -> {
                if (i.get() < 8000) {
                    sb.append("'" + no + "'");
                    if (i.get() != 0 && i.get() % 100 == 0) {
                        sb.append(") or fs.sms_no in (");
                    } else {
                        sb.append(",");
                    }
                } else if (i.get() >= 8000 && i.get() < 16000) {
                    sb1.append("'" + no + "'");
                    if (i.get() != 0 && i.get() % 100 == 0) {
                        sb1.append(") or fs.sms_no in (");
                    } else {
                        sb1.append(",");
                    }

                } else if (i.get() >= 8000 * 2 && i.get() < 8000 * 3) {
                    sb2.append("'" + no + "'");
                    if (i.get() != 0 && i.get() % 100 == 0) {
                        sb2.append(") or fs.sms_no in (");
                    } else {
                        sb2.append(",");
                    }

                } else if (i.get() >= 8000 * 3 && i.get() < 8000 * 4) {
                    sb3.append("'" + no + "'");
                    if (i.get() != 0 && i.get() % 100 == 0) {
                        sb3.append(") or fs.sms_no in (");
                    } else {
                        sb3.append(",");
                    }

                } else {
                    sb4.append("'" + no + "'");
                    if (i.get() != 0 && i.get() % 100 == 0) {
                        sb4.append(") or fs.sms_no in (");
                    } else {
                        sb4.append(",");
                    }
                }
                i.getAndIncrement();
            });
//            count.forEach((key, value) -> {
//                System.out.println(key + "::" + value);
//            });

            updateMap.forEach((key, value) -> checkMap.remove(key));
            System.out.println("有效数据行数：" + checkCount);
            System.out.println("sms_no总数(未去重)：" + errorCount);
            System.out.println("sms_no总数(去重)：" + set.size());
            System.out.println("需Update的sms_no数量：" + updateMap.size());
            System.out.println("正确的sms_no数量：" + checkMap.size());
            System.out.println("数据计算：" + (updateMap.size() + checkMap.size()));

//            System.out.println(sql1 + sb + ")");
//            System.out.println(sql1 + " fs.sms_no in (" + sb1 + ")");
//            System.out.println(sql1 + " fs.sms_no in (" + sb2 + ")");
//            System.out.println(sql1 + " fs.sms_no in (" + sb3 + ")");
//            System.out.println(sql2 + sb + "))");
//            System.out.println(sql2 + " fs.sms_no in (" + sb1 + "))");
//            System.out.println(sql2 + " fs.sms_no in (" + sb2 + "))");
//            System.out.println(sql2 + " fs.sms_no in (" + sb3 + "))");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
