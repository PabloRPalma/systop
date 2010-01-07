package com.systop.fsmis.assessment.webapp;

/**
 * 
 */
import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.core.webapp.upload.UpLoadUtil;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.assessment.AssessMentConstants;
import com.systop.fsmis.assessment.service.AssessmentAttachManager;
import com.systop.fsmis.assessment.service.AssessmentManager;
import com.systop.fsmis.model.Assessment;
import com.systop.fsmis.model.AssessmentAttach;

/**
 * 
 * @author ShangHua
 *
 */
@SuppressWarnings({ "serial", "unchecked" })
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AsseAttachAction extends ExtJsCrudAction<AssessmentAttach,AssessmentAttachManager> {

	/** 
	 * 风险评估Id 
	 */
	private Integer assessmentId;
	
	/**
	 * 风险评估Manager
	 */
	@Autowired
	private AssessmentManager assessmentManager;
	
	/**
	 * 上传的风险评估文件
	 */
	private File evalRisk;

	/**
	  * 上传的风险评估文件名称
	  */
	private String evalRiskFileName;
	
	/**
	 * 错误提示信息
	 */
	private String errorMsg;	


	/**
	 * 显示风险评估附件列表
	 */
	@Override
	public String index(){
		if (assessmentId != null ){
			Page page = PageUtil.getPage(getPageNo(), getPageSize());
			String hql = "from AssessmentAttach attach where attach.assessment.id = ?";
			getManager().pageQuery(page, hql, assessmentId);
			items = page.getData();
			restorePageData(page);
		}
		return INDEX;
	}
	
	/**
	 * 上传风险评估附件
	 * @return
	 */	
	public String upload(){
		try {
	      if (evalRisk != null) {
	    	//检查文件大小是否符合
	    	if(evalRisk.length() > AssessMentConstants.UPLOAD_ALLOWED_FILE_SIZE){
	    		errorMsg = "上传文件太大！";
	    		return INPUT;
	    	}
	    	//检查文件类型是否符合
	    	String extension = evalRiskFileName.substring(evalRiskFileName.indexOf(".") + 1);
	    	boolean flag = false;
	    	for(String extAllowed:AssessMentConstants.UPLOAD_ALLOWED_FILE_TYPES){
	    		if(StringUtils.equalsIgnoreCase(extension, extAllowed)){	    			
	    			flag = true;
	    			break;
	    		}
	    	}	    	
	    	if(!flag){
	    		errorMsg = "未选择Word文档！";
	    		return INPUT;
	    	}	    		    	
	        String fileRelativePath = null;
	        //保存文件不重命名
	        fileRelativePath = UpLoadUtil.doUpload(evalRisk, evalRiskFileName, FsConstants.ASSESSMENT_ATT_FOLDER,
	            getServletContext(), true);	      
	        // 相对路径，可用于下载地址
	        AssessmentAttach asseAttach = new AssessmentAttach();
	        Assessment assessment = assessmentManager.get(assessmentId);
	        asseAttach.setCreator(getModel().getCreator());
	        asseAttach.setPath(fileRelativePath);
	        asseAttach.setTitle(evalRiskFileName);
	        asseAttach.setAssessment(assessment);
	        getManager().save(asseAttach);
	      }	      
	    } catch (Exception e) {
	      errorMsg = "文件上传失败！";
	      return INPUT;
	    }		    
		return SUCCESS;		
	}
	
	
	/**
	 * 重写父类的remove方法
	 */
	@Override
	public String remove(){
		  AssessmentAttach asseAttach = getManager().get(getModel().getId());
			if(asseAttach != null){
				String path = asseAttach.getPath();
				if(StringUtils.isNotBlank(path)){
					getManager().removeAttach(asseAttach, getRealPath(asseAttach.getPath()));
					return SUCCESS;
				}
			}			
		return super.remove();
	}

	public Integer getAssessmentId() {
		return assessmentId;
	}

	public void setAssessmentId(Integer assessmentId) {
		this.assessmentId = assessmentId;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	/**
	 * @return the evalRisk
	 */
	public File getEvalRisk() {
		return evalRisk;
	}

	/**
	 * @param evalRisk the evalRisk to set
	 */
	public void setEvalRisk(File evalRisk) {
		this.evalRisk = evalRisk;
	}

	/**
	 * @return the evalRiskFileName
	 */
	public String getEvalRiskFileName() {
		return evalRiskFileName;
	}

	/**
	 * @param evalRiskFileName the evalRiskFileName to set
	 */
	public void setEvalRiskFileName(String evalRiskFileName) {
		this.evalRiskFileName = evalRiskFileName;
	}	
}
