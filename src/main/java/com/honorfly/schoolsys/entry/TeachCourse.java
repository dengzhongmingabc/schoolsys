package com.honorfly.schoolsys.entry;

import com.alibaba.fastjson.JSONObject;
import com.honorfly.schoolsys.utils.dao.EntityObj;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Entity
@Table(name = "teach_course")
@TypeDefs({
        @TypeDef(name = "string-array", typeClass = StringArrayType.class),
        @TypeDef(name = "int-array", typeClass = IntArrayType.class),
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class TeachCourse extends EntityObj {

    @Column
    private String name;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "typeId", insertable = false, updatable = false)
    private TeachCourseType type;
    @Column
    private Long typeId;
    @Column
    private Integer teachType = 1;//授课方式1：一对一 2，班级



    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "teach_course_School",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "school_id")
    )
    private List<School> schools = new ArrayList<School>();

    @Column
    private Integer selectType;//1：全部校区，2：指定校区

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private JSONObject payModel;//收费模式 1,按课时，2：时间，3：季度

	@Column
	private Boolean forbidden=false;

    public Integer getSelectType() {
        return selectType;
    }

    public void setSelectType(Integer selectType) {
        this.selectType = selectType;
    }

    public Boolean getForbidden() {
		return forbidden;
	}

	public void setForbidden(Boolean forbidden) {
		this.forbidden = forbidden;
	}


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

    /**
     * 商品相关信息
     *//*
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

    public TeachCourseType getType() {
        return type;
    }

    public void setType(TeachCourseType type) {
        this.type = type;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public JSONObject getPayModel() {
        return payModel;
    }

    public void setPayModel(JSONObject payModel) {
        this.payModel = payModel;
    }
}
