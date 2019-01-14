package yfjznew;

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
//            "E:\\20181015\\yfjz\\yfjz\\target\\yfjz\\WEB-INF\\page\\child\\tchildinfo.html",
//            "E:\\20181015\\yfjz\\yfjz\\target\\yfjz\\statics\\js\\child\\tchildinfo.js"
            "E:\\20181015\\yfjz\\yfjz\\target\\yfjz\\statics\\js\\queue\\observation.js",
            "E:\\20181015\\yfjz\\yfjz\\target\\yfjz\\WEB-INF\\classes\\sqlMapper\\child\\TChildInoculateDao.xml",
    };
    //每次的更新说明  增量包发布说明内容，直接写入说明文件
    static String introduction ="留观机查询历史接种记录显示undefined";


    static String base = "E:\\toolFile\\";
    static String selfbase = base + "廖欢【门诊系统】";
    static String jira = "YFJZN683";
    static String version = "SVN619";
    static String updateFile = base + "更新说明.txt";
    static String basePath = base + "yfjz\\";
    //预防接种的项目物理路径target所在的路径
    //需要改为自己的保存路径
    static String replaceSecond = "E:\\\\20181015\\\\yfjz\\\\yfjz\\\\target\\\\yfjz\\\\";
    //拷贝到历史接种记录里
    static String zipBasePath = base + "tmp.zip";
    static String lastVersionPath = selfbase + "--" + jira + "--" + version + "--" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".zip";

    public static void main(String[] args) throws Exception {
        PackageUtils.createDirtories();
    }
//测试一下
    //http://localhost:8882/yfjz/webService/updateFullData?pId=52242401&orgId=5224240101
}
