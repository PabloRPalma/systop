package com.systop.fsmis.assessment.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.assessment.AssessMentConstants;
import com.systop.fsmis.model.Assessment;
import com.systop.fsmis.model.CheckResult;
import com.systop.fsmis.model.FsCase;

/**
 * 风险评估信息管理类
 * 
 * @author ShangHua
 * 
 */
@Service
public class AssessmentManager extends BaseGenericsManager<Assessment> {

	/**
	 * 得到所有事件标题
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String[] getAllTitles() {
  	String[] titles = null;
    List<FsCase> fsCases = this.getDao().query("from FsCase fs");
    if (CollectionUtils.isNotEmpty(fsCases)) {
      int arraySize = fsCases.size();
      titles = new String[arraySize];
      // 遍历各条记录
      for (int i = 0; i < arraySize; i++) {
        FsCase fsCase = (FsCase) fsCases.get(i);
        // 取得事件标题放入标题数组中
        titles[i] = (String) fsCase.getTitle();
      }
    }
    return titles;
  }

	/**
	 * 保存风险评估审核信息
	 * @param assessment 风险评估实体类
	 * @param checkResult 公共审核类
	 */
	@Transactional
	public void auditSave(CheckResult checkResult) {
		CheckResult cResult = new CheckResult();
		//判断审核是否通过
		if (checkResult.getAssessment() != null) {
			//如果选择"是",则更新风险评估表的评估状态为已审核("2"),否则为未审核("1")。
			if (checkResult.getIsAgree().equals(FsConstants.Y)) {
				checkResult.getAssessment().setState(AssessMentConstants.AUDITING_PASSED_STATE);
			} else {
				checkResult.getAssessment().setState(AssessMentConstants.AUDITING_REJECT_STATE);
			}
			super.save(checkResult.getAssessment());
			cResult.setAssessment(checkResult.getAssessment());
			cResult.setChecker(checkResult.getChecker());
			cResult.setCheckTime(new Date());
			cResult.setIsAgree(checkResult.getIsAgree());
			cResult.setResult(checkResult.getResult());
			getDao().save(cResult);
		}
	}
	
	/**
	 * 提取风险评估审核记录表中最新的信息
	 * @param assessmentId 风险评估Id
	 * @return
	 */
  @SuppressWarnings("unchecked")
  public CheckResult getCheckResult(Integer assessmentId) {
    List<CheckResult> checkResults = this.getDao()
         .query("from CheckResult cr where cr.assessment.id = ? " +
         		"order by cr.checkTime desc, cr.isAgree desc", assessmentId);
    return (CollectionUtils.isNotEmpty(checkResults)) ? 
    		    (CheckResult) checkResults.get(0) : null;
  }
	
  /**
   * 删除风险评估对应的审核数据
   * @param assessment 风险评估对象
   */
  @Transactional
  public void delCheckResults(Assessment assessment) {
		Set<CheckResult> checkResults = assessment.getCheckResults();
  	if (CollectionUtils.isNotEmpty(checkResults)) {
  		for (CheckResult checkResult : checkResults) {
  				getDao().evict(checkResult);
  				getDao().delete(CheckResult.class, checkResult.getId());
  	  }
    }
  }
}