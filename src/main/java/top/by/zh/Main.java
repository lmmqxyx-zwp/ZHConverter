package top.by.zh;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: Main.java</p>
 * <p>Description: 转换主函数</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: www.iceos.top</p>
 *
 * ----------------------------------------
 * ----------------------------------------
 * @author zwp
 * @date 2018年10月27日
 *
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {
        try {
            String dir = new String();
            List<String> suffixNames = new ArrayList<String>();
            boolean debug = false;
            // 帮助手册
            if (args.length == 0 || args.length > 3) {
                new Main().help();
                return ;
            } else {
                if (args.length == 1) {
                    new Main().help();
                    return ;
                }
                if (args.length == 2 || args.length == 3) {
                    dir = args[0];
                    String args2 = args[1];
                    String[] args2s = args2.split(",");
                    for (String s : args2s) {
                        suffixNames.add(s);
                    }
                }

                if (args.length == 3) {
                    debug = Boolean.valueOf(args[2]);
                }

            }
            // 需要简繁转换的目录
            // E:\tmp\test

            ChangeSimpleToTraditional.changeFileFromSimpleChineseToTradionalWithRootPath(dir, suffixNames, debug);

            System.out.println("INFO：SUCCESS");
        } catch (Exception e) {
            System.err.println("ERROR：FAIL");
        }
    }

    private void help() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("| 欢迎使用简繁转换工具(help)");
        System.out.println("| 1.必须参数(是)：转换文件路径dir");
        System.out.println("| 2.必须参数(是)：待转换的文件后缀名(用','隔开)");
        System.out.println("| 3.必须参数(否)：是否显示详细日志(true | false)");
        System.out.println("| 4.调用示例：java -jar ZHConverter.jar dir .jsp,.txt,... true");
        System.out.println("| 5.调用注意：dir可以是单个文件也可以是一个目录");
        System.out.println("----------------------------------------------------------------");
    }

}

