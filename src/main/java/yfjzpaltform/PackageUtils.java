package yfjzpaltform;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * class_name: ConfigUtils
 *
 * @author 刘琪
 * @Description:
 * @QQ: 1018628825@qq.com
 * @tel: 15685413726
 * @date: 2018-06-09 12:46
 */
public class PackageUtils {

    private static final byte[] BUFFER = new byte[2048]; ;
    public static void createDirtories() throws Exception {
        File dir = new File(Config.basePath);
        deleteAllFilesOfDir(dir);// 优先删除文件夹，然后创建
        //创建文件路径后，拷贝文件
        String[] files = Config.files;
        for (int i = 0; i <files.length; i++) {
            String filepath = Config.basePath+files[i];
            createDir(Config.basePath.replaceAll("\\\\","\\\\\\\\"), Config.replaceSecond,filepath);
        }
        File introductionFile = new File(Config.updateFile);
        if (introductionFile.exists()){
            introductionFile.delete();
        }
        if (!introductionFile.exists()){
            introductionFile.createNewFile();
        }
        OutputStreamWriter pw = new OutputStreamWriter(new FileOutputStream(introductionFile),"GBK");
        pw.write(Config.introduction);
        pw.close();//关闭流
        System.out.println("保存文件成功,文件已经保存到路径"+ Config.basePath+"目录下");

        //压缩文件
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String zipName= Config.zipBasePath;
        File file = new File(zipName);
        if (!file.exists()){
            file.createNewFile();
        }
        ZipUtils.toZip(Config.basePath,new FileOutputStream(file),true);
        ZipOutputStream append = new ZipOutputStream(new FileOutputStream(Config.lastVersionPath));
        ZipUtils.appendFileToZip(introductionFile.getName(),new ZipFile(zipName),append);

        //弄完以后，删除临时文件
        file.delete();
        System.out.println("保存文件成功");

    }


    /**
     * @method_name: createDir
     * @describe:
     * @param: replaceFisrt 需要替换为空字符串的参数  需要保存的更新包存放的路径
     * @param: replaceSecond 需要替换为空字符串的参数 项目源码所在的物理路径
     * @param: destDirName 需要拷贝的文件的物理路径
     * @return: boolean
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/6/4  23:55
     **/
    public static boolean createDir(String replaceFisrt,String replaceSecond,String destDirName) throws Exception {
        String tmpPath = destDirName;
        File sourceFile = new File(tmpPath.replaceAll(replaceFisrt,""));
        File file = new File(destDirName);
        String parentPath = file.getParent();
        destDirName = parentPath.replaceAll(replaceSecond,"");
        //创建需要拷贝的文件
        File dir = new File(destDirName);
        if (dir.exists()) {
            //该目录已经存在的情况下，直接拷贝文件，不再创建文件夹
            saveFiles(sourceFile,destDirName);
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {// 结尾是否以"/"结束
            destDirName = destDirName + File.separator;
        }
        if (dir.mkdirs()) {// 创建目标目录
            saveFiles(sourceFile,destDirName);
            return true;
        } else {
            System.out.println("创建目录失败！");
            return false;
        }
    }

    private static void saveFiles(File sourceFile, String destDirName) throws Exception{
        //拷贝文件到更新文件夹中
        List<String> list =getAllFile(sourceFile.getAbsolutePath(),false);
        FileChannel inChannel=null;
        FileChannel outChannel=null;
        for (int i = 0; i < list.size(); i++) {
            File tmpFile = new File(list.get(i));
            String destFile = destDirName+File.separator+tmpFile.getName();
            inChannel =new FileInputStream(list.get(i)).getChannel();
            outChannel=new FileOutputStream(destFile).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        }
        inChannel.close();
        outChannel.close();
    }

    /**
     * @method_name: getAllFile
     * @describe: 获取文件夹下的文件，次方法在本程序中就是为了获取编译后的class 出现后缀$1,$2,$3....class的多个文件下的拷贝
     * @param: isAddDirectory 默认false，不递归子目录
     * @return: java.util.List<java.lang.String>
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/6/4  23:08
     **/
    private static List<String> getAllFile(String directoryPath,boolean isAddDirectory) {
        List<String> list = new ArrayList<>();
        File baseFile = new File(directoryPath);
        String fileName = baseFile.getName();
        File tmpFile = new File(baseFile.getParent());
        if (tmpFile.isFile() || !tmpFile.exists()) {
            return list;
        }
        //判断文件是否是.class类型的文件，如果是，则需要做模糊匹配
        boolean flag1 =fileName.endsWith(".class");
        if (!flag1){
            File[] files = tmpFile.listFiles();
            for (File file : files) {
                //判断除了.class做模糊匹配，其他的文件做完全匹配
                boolean flag2 = file.getName().equals(fileName);
                if (flag2){
                    list.add(file.getAbsolutePath());
                }
            }
        }else{
            fileName = fileName.replaceAll(".class","");
            File[] files = tmpFile.listFiles();
            for (File file : files) {
                boolean flag = file.getName().startsWith(fileName);
                if (flag){
                    list.add(file.getAbsolutePath());
                }
                //			if (file.isDirectory()) {
                //如果是目录，暂时不考虑
                //				if(isAddDirectory){
                //					list.add(file.getAbsolutePath());
                //				}
                //				list.addAll(getAllFile(file.getAbsolutePath(),isAddDirectory));
                //			} else {
                //				list.add(file.getAbsolutePath());
                //			}
            }
        }
        return list;
    }

    /**
     * @method_name: deleteAllFilesOfDir
     * @describe: 优先删除文件夹，然后创建
     * @param:
     * @return: void
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/6/4  23:56
     **/
    private static void deleteAllFilesOfDir(File path) {
        if (!path.exists())
            return;
        if (path.isFile()) {
            path.delete();
            return;
        }
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteAllFilesOfDir(files[i]);
        }
        path.delete();
    }
}
