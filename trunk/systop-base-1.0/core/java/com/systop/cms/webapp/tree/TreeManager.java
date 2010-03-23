package com.systop.cms.webapp.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.systop.cms.model.Content;

/**
 * 对Tree进行操作
 * @author Administrator
 * 
 */
public class TreeManager {

  /**
   * 创建Content
   * @param folders
   * @return
   */
  public Content buildTree(Set<Content> parents) {
    Content root = new Content();
    root.setChildren(parents);
    root.setTitle("All");

    return root;
  }
  
  /**
   * 添加子目录
   * @param child
   */
  public void addChild(Content child) {
    Content parent = new Content();
    parent.getChildren().add(child);
    child.setParent(parent);
  }

 /**
  * 删除子目录
  * @param child
  * @return
  */
 public boolean removeChild(Content child) {
   Content parent = new Content();
   return parent.getChildren().remove(child);
 }
 
 /**
  * 清空子目录
  */
 public void clearChildren() {
   Content parent = new Content();
   parent.getChildren().clear();
 }

  /**
   * 用递归方式显示所有子节点
   * @param folder
   * @return
   */
  public List showAllChildrenFolders(Content folder) {
    Set<Content> childrens = folder.getChildren();
    List list = new ArrayList();
    for (Content theFolder : childrens) {
      showAllChildrenFolders(theFolder);
      list.add(theFolder);
    }
    return list;
  }
}
