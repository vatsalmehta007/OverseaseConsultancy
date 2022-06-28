package com.example.overseas.consultancy.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AppoimentEntity 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime created_on;
	private LocalDateTime modified_on;
	private String description;
	private String status;
	public String startTime;
	public String endTime;
	private Integer consultant_Id;
	private Integer student_Id;
	
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getCreated_on() {
		return created_on;
	}
	public void setCreated_on(LocalDateTime created_on) {
		this.created_on = created_on;
	}
	public LocalDateTime getModified_on() {
		return modified_on;
	}
	public void setModified_on(LocalDateTime modified_on) {
		this.modified_on = modified_on;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
