package datashare.rd;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Record {
  public static void main(String[] args) throws Exception {
    String seedFile = null;
    String netSta = null;
    String gifFile = null;

    if ((args.length) > 2) {
      seedFile = args[0];
      netSta = args[1];
      gifFile = args[2];
    } else {
      throw new Exception("参数不够！");
    }

    BufferedReader inlist = new BufferedReader(new FileReader("dir.cnf"));

    for (int i = 0; i < 3; i++) { // 跳过前3行,取得bin目录的path
      inlist.readLine();
    }
    String line = inlist.readLine(); // bin path
    if(line == null || line.length() == 0) {
      throw new Exception("dir.cnf有错误!");
    }
    
    if(!line.endsWith(File.separator)) {
      line += "/rec.list";
    } else {
      line += "rec.list";
    }
    File file = new File(line); //记录jpg文件等信息的文件
    if (!file.exists()) { //该文件不存在，则创建
      file.createNewFile();
    }
    
    BufferedRandomAccessFile raf = new BufferedRandomAccessFile(file, "rw");
    raf.seek(raf.length());
    //文件的一行，参数用,分割
    //第一个是seedFile,第二个是netSta,第三个是gifFile
    String content = new StringBuffer(seedFile).append(",").append(netSta)
        .append(",").append(gifFile).append("\n").toString();
    try {     
      raf.write(content.getBytes(), 0, content.getBytes().length);
    } finally {      
      raf.close();
    }
  }
}
