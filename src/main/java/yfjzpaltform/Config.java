package yfjzpaltform;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 刘琪
 * @class_name: Config
 * @Description:
 * @QQ: 1018628825@qq.com
 * @tel: 15685413726
 * @date: 2018-09-07 14:40
 */
public class Config {

    //这个是要拷贝的文件列表，多个文件使用逗号分开
    static String[] files = {
            "E:\\YFJZ\\YFJZ_PLATFORM\\yfjz_platform\\yfjz_platform\\target\\yfjzplatform\\WEB-INF\\classes\\io\\yfjz\\controller\\medical\\TVacFactoryController.class",
    };
    //每次的更新说明  增量包发布说明内容，直接写入说明文件
    static String introduction ="平台 --- 疫苗生产厂家修改不了";
    //需要改为自己的保存路径
    static String base = "E:\\toolFile\\";
    static String selfbase = base + "廖欢【平台系统】";
    static String jira = "YFJZN241";
    static String version = "SVN21";
    static String updateFile = base + "更新说明.txt";
    static String basePath = base + "yfjzplatform\\";
    //预防接种的项目物理路径target所在的路径
    //需要改为自己的保存路径
    static String replaceSecond = "E:\\\\YFJZ\\\\YFJZ_PLATFORM\\\\yfjz_platform\\\\yfjz_platform\\\\target\\\\yfjzplatform\\\\";
    //拷贝到历史接种记录里
    static String zipBasePath = base + "tmp.zip";
    static String lastVersionPath = selfbase + "--" + jira + "--" + version + "--" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".zip";


    public static void main(String[] args) throws Exception {
        PackageUtils.createDirtories();
    }
}
