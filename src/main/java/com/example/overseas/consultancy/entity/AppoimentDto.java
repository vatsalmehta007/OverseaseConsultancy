package com.example.overseas.consultancy.entity;

public class AppoimentDto 
{
	private String description;
	private String status;
	public String startTime;
	public String endTime;
	private Integer consultant_Id;
	private Integer student_Id;
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getConsultant_Id() {
		return consultant_Id;
	}
	public void setConsultant_Id(Integer consultant_Id) {
		this.consultant_Id = consultant_Id;
	}
	public Integer getStudent_Id() {
		return student_Id;
	}
	public void setStudent_Id(Integer student_Id) {
		this.student_Id = student_Id;
	}
}
