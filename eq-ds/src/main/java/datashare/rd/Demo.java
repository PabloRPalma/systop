package datashare.rd;


  import java.io.File;

  public class Demo {
    public static void main(String[] args) throws Exception {
      File file = new File("/home/sam/桌面/rec.list"); //记录jpg文件等信息的文件
      if (!file.exists()) { //该文件不存在，则创建
        file.createNewFile();
      }
      
     BufferedRandomAccessFile braf = new BufferedRandomAccessFile(file, "rw");
      

      //文件的一行，参数用,分割
      //第一个是seedFile,第二个是netSta,第三个是gifFile
      String content = new StringBuffer("SEED_FILE").append(",").append("STATION2")
          .append(",").append("SOME_JPG").append("\n").toString();
      try {
        braf.seek(file.length());
        braf.write(content.getBytes());
        //raf.skipBytes((int) file.length()); //在文件最后追加一行
        //raf.write(content.getBytes(), 0, content.getBytes().length);
      } finally {      
        braf.close();
      }
    }
  }


