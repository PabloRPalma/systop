package datashare.metadata.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.core.ApplicationException;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;

import datashare.metadata.model.Metadata;
import datashare.metadata.service.MetadataManager;

/**
 * 元数据服务管理Action
 * @author DU
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MetadataAction extends DefaultCrudAction<Metadata, MetadataManager> {
  
  /**
   * json数据返回结果
   */
  private String josnResult;
  
  /**
   * 要保存的元数据文件
   */
  private File meta;
  
  /**
   * 元数据文件名称
   */
  private String metaFileName;
  
  /**
   * json数据
   */
  private Metadata metadata;
    
  @SuppressWarnings("unchecked")
  private List<Node> metaList = new ArrayList();
  
  /**
   * 查询所有的元数据
   */
  public String index(){
    StringBuffer hql = new StringBuffer("from Metadata m ");
    List<String> args = new ArrayList<String>();
    if(StringUtils.isNotBlank(getModel().getCzOrQz())){
      hql.append("where m.czOrQz = ?");
      args.add(getModel().getCzOrQz());
    }
    List<Metadata> items = getManager().query(hql.toString(), args.toArray());
    getRequest().setAttribute("items", items);
    return INDEX;
  }
  
  /**
   * 保存元数据
   * 元数据文件必须是.xml格式的文件
   * 使用SAXReader解析元数据文件后，保存在数据库中
   */
  @Override
  public String save() {
    if (StringUtils.isNotEmpty(metaFileName) && meta != null) {
      String ext = metaFileName.substring(metaFileName.lastIndexOf("."));
      logger.debug("文件后缀名：" + ext);
      if(!".xml".equalsIgnoreCase(ext)) {
        addActionError("文件类型错误，只能是XML格式的文件。");
      } else {
        logger.debug("元数据文件:{}", meta.getAbsolutePath());         
        logger.debug("元数据文件存在:{}", meta.exists());
        SAXReader sax = new SAXReader();
        Document doc;
        try {
          doc = sax.read(meta);
          getModel().setFileContent(doc.asXML());
          super.save();
        } catch (DocumentException e) {
          e.printStackTrace();
          }
        }
      }
    return SUCCESS;
  }
  
  /**
   * 查看元数据
   * 读取数据库中的元数据信息
   * 读取config.xml文件节点对应的属性
   * 解析元数据文件节点对应的属性
   */
  @SuppressWarnings("unchecked")
  public String queryMetadataInfo() {
    Metadata mdata = getManager().getMatadataInfo(getModel().getType());
    if (mdata != null) {
      XMLWriter outFile = null;
      try {        
        //logger.debug("元数据文件内容：{}", mdata.getFileContent());
        Document outerdoc = DocumentHelper.parseText(mdata.getFileContent());  
        String folderPath = getServletContext().getRealPath("/metafile/");
        File folder = new File(folderPath);
        if (!folder.exists()) {
          folder.mkdirs();
        }        
        outFile = new XMLWriter(new FileOutputStream(folderPath + File.separator + mdata.getMetaSet()+ ".xml"));
        outFile.write(outerdoc);
        Document doc = readDoc(folderPath + File.separator + mdata.getMetaSet()+ ".xml");
        Element root = doc.getRootElement();
        //读取元数据文件对应的节点信息
        Map infoMap = getMetaFileInfo(folderPath + File.separator + "config.xml");
       //遍历元数据文件所有的子节点
        viewAllElement(root);
        String mContent = mdata.getFileContent();
        for (Node node : metaList) {
          String info = node.getName();
          if(infoMap.get(node.getName()) != null) {
            info = infoMap.get(node.getName()).toString();
          }
          if (StringUtils.isBlank(node.getText())) {
            mContent = mContent.replace("</"+node.getName()+">", "<br/>").replace("<"+node.getName()+">", 
                "<b>" + info + "<br/>" + "</b>").replace(" ", "&nbsp;");
          } else {
            mContent = mContent.replace("</"+node.getName()+">", "<br/>").replace("<"+node.getName()+">", 
                "<b>" + info + ": " + "</b>").replace(" ", "&nbsp;");
          }
        }        
        getResponse().setCharacterEncoding("UTF-8");
        getResponse().setContentType("text/html");
        render(getResponse(), "<font size='2'>"+mContent+"</font>", "text/html");
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          if (outFile != null) {
            outFile.close();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }      
    } else {
      render(getResponse(), "暂无数据", "text/html");
    }    
    return null;
  }
  
  /**
   * 读取元数据文件对应的节点信息
   * 将元数据文件节点名字和节点文字信息以Map的数据结构保存
   * @param filePath 元数据文件
   */
  @SuppressWarnings("unchecked")
  private Map getMetaFileInfo(String filePath) {
    Document document = readDoc(filePath);
    List nodeList = document.selectNodes("//item");
    Iterator iter = nodeList.iterator();
    Map fileInfoMap = new HashMap();
    while(iter.hasNext()){
      Element element=(Element)iter.next();
      Iterator itemNameIter=element.elementIterator("itemName");
      Iterator aliasIter=element.elementIterator("itemAlias1");
      Element itemNameElement = null;
      Element aliasElement = null;
      while(itemNameIter.hasNext()){
        itemNameElement=(Element)itemNameIter.next();
      }
      while(aliasIter.hasNext()){
        aliasElement=(Element)aliasIter.next();
      }
      //logger.debug(itemNameElement.getText() + " : " + aliasElement.getText());
      fileInfoMap.put(itemNameElement.getText(), aliasElement.getText());
    }
    
    return fileInfoMap;
  }
  
  /**
   * 下载元数据
   */
  public String downloadMetadataInfo() {
    Metadata mdata = getManager().getMatadataInfo(getModel().getType());
    if (mdata != null) {
      getResponse().setCharacterEncoding("UTF-8");
      getResponse().setContentType("application/x-download");
      String fileName = "metadata";
      try {
        fileName = URLEncoder.encode(mdata.getMetaSet(),"UTF-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
      getResponse().addHeader("Content-Disposition", 
          "attachment;filename=\"" + fileName + ".xml\"");
      render(getResponse(), mdata.getFileContent(), "text/xml");
    } else {
      render(getResponse(), "暂无数据", "text/html");
    }    
    return null;
  }
  
  /**
   * 删除元数据
   */
  public String deleteMetadata() {
    try {
      metadata = getManager().getMatadataInfo(getModel().getType());
      if(metadata != null) {
        getManager().remove(metadata);
        josnResult = "delok";
      } else {
        josnResult = "nometa";
      }
    } catch(Exception e) {
      e.printStackTrace();
      josnResult = "delerr";
    }
    return "delResult";
  }
  
  /**
   * 取得元数据
   */
  @SuppressWarnings("unchecked")
  public String getMetadataInfo() {
    metadata = getManager().getMatadataInfo(getModel().getType());
    metadata.setFileContent(null);
    return "metaResult";
  }
  
  /**
   * 取得document对象
   * @param filename 文件名
   */
  private Document readDoc(String filename) {
    SAXReader reader = new SAXReader();
    try {
      return reader.read(new FileInputStream(new File(filename)));
    } catch (DocumentException e) {
      e.printStackTrace();
      throw new ApplicationException(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      throw new ApplicationException(e.getMessage());
    }
  }
  
  /**
   * 遍历所有节点
   * @param element
   */
  @SuppressWarnings("unchecked")
  public void viewAllElement(Element element) {
    for ( int i = 0, size = element.nodeCount(); i < size; i++ ) {
      Node node = element.node(i);
      if ( node instanceof Element ) {
        viewAllElement( (Element) node );
        //logger.info("节点名称：" + node.getName() + " 节点内容：" + node.getText());
        metaList.add(node);
      } 
    }
  } 
  
  /**
   * @return the josnResult
   */
  public String getJosnResult() {
    return josnResult;
  }

  /**
   * @param josnResult the josnResult to set
   */
  public void setJosnResult(String josnResult) {
    this.josnResult = josnResult;
  }

  /**
   * @return the metadata
   */
  public Metadata getMetadata() {
    return metadata;
  }

  /**
   * @param metadata the metadata to set
   */
  public void setMetadata(Metadata metadata) {
    this.metadata = metadata;
  }

  public String getMetaFileName() {
    return metaFileName;
  }

  public void setMetaFileName(String metaFileName) {
    this.metaFileName = metaFileName;
  }

  public File getMeta() {
    return meta;
  }

  public void setMeta(File meta) {
    this.meta = meta;
  }

}
