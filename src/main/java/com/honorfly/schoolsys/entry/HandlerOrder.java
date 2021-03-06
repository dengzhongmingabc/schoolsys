package com.honorfly.schoolsys.entry;

import com.alibaba.fastjson.JSONArray;
import com.honorfly.schoolsys.utils.dao.EntityObj;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "handler_order")
@TypeDefs({
		@TypeDef(name = "string-array", typeClass = StringArrayType.class),
		@TypeDef(name = "int-array", typeClass = IntArrayType.class),
		@TypeDef(name = "json", typeClass = JsonStringType.class),
		@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class HandlerOrder extends EntityObj {
	@Column
	private String orderNo;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "studentId", insertable = false, updatable = false)
	private MarketStudent marketStudent;
	@Column
	private Long studentId;
	@Column
	private int orderType;//1:报名，2：续费，3：补费，4：转课，5：退费，6：资料，7：积分
	@Type(type = "jsonb")
	@Column(columnDefinition = "jsonb")
	private JSONArray orderContent;
	@Column
	private int orderMoney;//订单应收的钱  单位为分
	@Column
	private int getOrderMoneyReality;//实收费用 单位为分

	@Column
	private int oweUp;//欠款费用 单位为分

	@Column
	private int studentAccRecord;//学员账户记录

	@Column
	private String remark;//说明

	@Column
	private Boolean forbidden=false;

	public Boolean getForbidden() {
		return forbidden;
	}

	public int getOweUp() {
		return oweUp;
	}

	public void setOweUp(int oweUp) {
		this.oweUp = oweUp;
	}

	public void setForbidden(Boolean forbidden) {
		this.forbidden = forbidden;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public MarketStudent getMarketStudent() {
		return marketStudent;
	}

	public void setMarketStudent(MarketStudent marketStudent) {
		this.marketStudent = marketStudent;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public JSONArray getOrderContent() {
		return orderContent;
	}

	public void setOrderContent(JSONArray orderContent) {
		this.orderContent = orderContent;
	}

	public int getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(int orderMoney) {
		this.orderMoney = orderMoney;
	}

	public int getGetOrderMoneyReality() {
		return getOrderMoneyReality;
	}

	public void setGetOrderMoneyReality(int getOrderMoneyReality) {
		this.getOrderMoneyReality = getOrderMoneyReality;
	}

	public int getStudentAccRecord() {
		return studentAccRecord;
	}

	public void setStudentAccRecord(int studentAccRecord) {
		this.studentAccRecord = studentAccRecord;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
