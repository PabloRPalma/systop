package com.systop.fsmis.fscase.task.taskatt.webapp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import com.systop.cms.utils.PageUtil;
import com.systop.core.util.ReflectUtil;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.fscase.task.taskatt.service.TaskAttManager;
import com.systop.fsmis.model.TaskAtt;

@Controller
@SuppressWarnings( { "serial", "unchecked" })
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TaskAttAction extends ExtJsCrudAction<TaskAtt, TaskAttManager> {
  private Integer taskId;

  /**
   * 根据任务id得到任务附件实体实例方法
   * 
   * @return
   */

  public String getTaskAttsByTaskId() {
    Assert.notNull(taskId);
    page = PageUtil.getPage(getPageNo(), getPageSize());
    page = getManager().pageQuery(page, "from TaskAtt ta where ta.task.id = ?",
        taskId);
    List taskAtts = page.getData();
    List mapTaskAtts = new ArrayList(taskAtts.size());
    for (Iterator itr = taskAtts.iterator(); itr.hasNext();) {
      TaskAtt ta = (TaskAtt) itr.next();
      Map mapTaskAtt = ReflectUtil.toMap(ta, new String[] { "id", "path",
          "title", "remark" }, true);
      mapTaskAtt.put("taskTitle", ta.getTask().getTitle());
      mapTaskAtts.add(mapTaskAtt);
    }
    page.setData(mapTaskAtts);

    return JSON;
  }

  public Integer getTaskId() {
    return taskId;
  }

  public void setTaskId(Integer taskId) {
    this.taskId = taskId;
  }

}
