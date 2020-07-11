package com.honorfly.schoolsys.entry;

import com.alibaba.fastjson.JSONObject;
import com.honorfly.schoolsys.utils.dao.EntityObj;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Entity
@Table(name = "teach_course")
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class TeachCourse extends EntityObj {

	@Column
	private String name;

	@Column
	private long type;

	@Column
	private int teachType;//授课方式1：一对一 2，班级


	@Type(type = "json")
	@Column(columnDefinition = "json" )
	private List<School> schools = new ArrayList<School>();

	@Type(type = "json")
	@Column(columnDefinition = "json" )
	private JSONObject payModel;//收费模式 1,按课时，2：时间，3：季度


	public int getTeachType() {
		return teachType;
	}

	public void setTeachType(int teachType) {
		this.teachType = teachType;
	}

	public List<School> getSchools() {
		return schools;
	}

	public void setSchools(List<School> schools) {
		this.schools = schools;
	}

	/**商品相关信息 *//*
	@Type(type = "json")
	@Column(columnDefinition = "json" )
	private List<School> cargoModelList;


	@Type( type = "json" )
	@Column(columnDefinition = "json")
	private JsonNode properties;

	public JsonNode getProperties() {
		return properties;
	}

	public void setProperties(JsonNode properties) {
		this.properties = properties;
	}

	public List<School> getCargoModelList() {
		return cargoModelList;
	}

	public void setCargoModelList(List<School> cargoModelList) {
		this.cargoModelList = cargoModelList;
	}*/



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
	}

	public JSONObject getPayModel() {
		return payModel;
	}

	public void setPayModel(JSONObject payModel) {
		this.payModel = payModel;
	}
}
