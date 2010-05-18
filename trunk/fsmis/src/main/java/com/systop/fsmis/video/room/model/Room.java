package com.systop.fsmis.video.room.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

@Entity
@Table(name = "rooms", uniqueConstraints = {})
@SuppressWarnings("serial")
public class Room extends BaseModel {

	/**
	 * 房间name
	 */
	private String name;

	/**
	 * 成员数量
	 */
	private Integer membersCount;

	/**
	 * 房主的用户ID
	 */
	private Integer master;
	/**
	 * 房主姓名
	 */
	private String masterName;

	/**
	 * 房间成员，用,隔开的多个用户ID
	 */
	private String members;

	/**
	 * 等待队列，用,隔开的多个用户ID
	 */
	private String waitings;

	private String remark;

	private Date createTime;
	/** 会议记录 */
	private String meetingRecord;

	@Id
	@GeneratedValue(generator = "assigned")
	@GenericGenerator(name = "assigned", strategy = "assigned")
	@Column(unique = true, name = "roomName")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMembersCount() {
		return membersCount;
	}

	@Column(name = "members_count")
	public void setMembersCount(Integer membersCount) {

		this.membersCount = membersCount;
	}

	public Integer getMaster() {
		return master;
	}

	public void setMaster(Integer master) {
		this.master = master;
	}

	public String getMembers() {
		return members;
	}

	public void setMembers(String members) {
		this.members = members;
	}

	@Column(name = "waitings", columnDefinition = "varchar(2000)")
	public String getWaitings() {
		return waitings;
	}

	public void setWaitings(String waitings) {
		this.waitings = waitings;
	}

	@Column(name = "remark", columnDefinition = "varchar(2000)")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Transient
	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	@Column(name = "create_Time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "meeting_Record", columnDefinition = "varchar(2000)")
	public String getMeetingRecord() {
		return meetingRecord;
	}

	public void setMeetingRecord(String meetingRecord) {
		this.meetingRecord = meetingRecord;
	}

}
