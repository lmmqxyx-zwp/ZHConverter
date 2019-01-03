package top.by.zh;

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

    /**
     // 可转换的文件类型
     .jsp
     .js
     .html
     .java
     .inc
     .ftl
     .sql
     // 文件类型定义 @{link ChangeSimpleToTraditional#traverseFolder2}
     */
    public static void main(String[] args) {
        // 需要简繁转换的目录
        ChangeSimpleToTraditional.changeFileFromSimpleChineseToTradionalWithRootPath(dir);
    }

}

