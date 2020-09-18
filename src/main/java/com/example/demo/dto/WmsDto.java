package com.example.demo.dto;

import com.example.demo.service.impl.AuditDomain;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * 公共属性类
 * @author Beason
 *
 */
@Data
public class WmsDto extends AuditDomain {
	public static final String FIELD_ID = "id";
	public static final String FIELD_ORG_ID = "orgId";
	public static final String FIELD_OWNER_ID = "ownerId";
	public static final String FIELD_WAREHOUSE_ID = "warehouseId";
	public static final String FIELD_WHAREA_ID = "whareaId";
	public static final String FIELD_WHAREA_CODE = "whareaCode";
	public static final String FIELD_LOCATION_ID = "locationId";
	public static final String FIELD_CID_ID = "cidId";
	public static final String FIELD_CID_CODE = "cidCode";
	public static final String FIELD_GOODS_ID = "goodsId";
	public static final String FIELD_BATCH_ID = "batchId";
	public static final String FIELD_BILL_TYPE = "billType";
	public static final String FIELD_BILL_TYPE_ID = "billTypeId";
	public static final String FIELD_BILL_ID = "billId";
	public static final String FIELD_BILL_CODE = "billCode";
	public static final String FIELD_SOURCE_BILL_CODE = "sourceBillCode";
	public static final String FIELD_RELATED_LINE_ID = "relatedLineId";
	public static final String FIELD_DETAIL_ID = "detailId";
	public static final String FIELD_CUSTOMER_ID = "customerId";
	public static final String FIELD_ENABLED_FLAG = "enabledFlag";
	public static final String FIELD_REMARK = "remark";
	public static final String FIELD_ATTRIBUTE_CATEGORY= "attributeCategory";
	public static final String FIELD_ATTRIBUTE1 = "attribute1";
	public static final String FIELD_ATTRIBUTE2 = "attribute2";
	public static final String FIELD_ATTRIBUTE3 = "attribute3";
	public static final String FIELD_ATTRIBUTE4 = "attribute4";
	public static final String FIELD_ATTRIBUTE5 = "attribute5";
	public static final String FIELD_ATTRIBUTE6 = "attribute6";
	public static final String FIELD_ATTRIBUTE7 = "attribute7";
	public static final String FIELD_ATTRIBUTE8 = "attribute8";
	public static final String FIELD_ATTRIBUTE9 = "attribute9";
	public static final String FIELD_ATTRIBUTE10 = "attribute10";
	public static final String FIELD_ATTRIBUTE11 = "attribute11";
	public static final String FIELD_ATTRIBUTE12 = "attribute12";
	public static final String FIELD_ATTRIBUTE13 = "attribute13";
	public static final String FIELD_ATTRIBUTE14 = "attribute14";
	public static final String FIELD_ATTRIBUTE15 = "attribute15";
	public static final String FIELD_TENANT_ID = "tenantId";

	/**
     * 表ID，主键，供其他表做外键.
     */
	@Id
	@GeneratedValue
	@ApiModelProperty(value = "表ID，主键，供其他表做外键")
	private Long id;

	@ApiModelProperty(value = "租户ID,hpfm_tenant.tenant_id")
	private Long tenantId;

	//备注
	@ApiModelProperty(value = "备注说明",hidden = true)
	private String remark;

	//记录行状态
	@ApiModelProperty(value = "启用标识",hidden = true)
	private Long enabledFlag;

	//创建人
	@ApiModelProperty(value = "创建人",hidden = true)
	private Long createdBy;


	//单据头ID
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(hidden = true)
	@Transient
	private Long billId;

	//单据行ID
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(hidden = true)
	@Transient
	private Long detailId;

	@ApiModelProperty(value = "仓库id", hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private Long warehouseId;

	//仓库编码
	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String warehouseCode;

	//仓库名称
	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String warehouseName;

	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private Long ownerId;

	//货主名称
	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String ownerName;

	//货主编码
	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String ownerCode;

	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private Long orgId;

	//组织编码
	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String orgCode;

	//组织名称
	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String orgName;

	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private Long whareaId;

    //库区编码
	@ApiModelProperty(hidden = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String whareaCode;

	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private Long batchId;

	//批次编码
	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String batchCode;

	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private Long locationId;

	//库位编码
	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String locationCode;

	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private Long carrierId;

	//承运商编码
	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String carrierCode;

	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private Long cidId;

	//托盘编码
	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String cidCode;

	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private Long serialId;

	//序列号编码
	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String serialNumber;

	//库区名称
	@ApiModelProperty(hidden = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String whareaName;

	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private Long vendorId;

	//供应商编码
	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String vendorCode;

	//供应商名称
	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String vendorName;

	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private Long customerId;

	//客户编码
	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String customerCode;

	//客户名称
	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String customerName;

	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private Long goodsId;

	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String sku;

	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String goodsName;

	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private Long uomId;

	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String uomCode;

	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String uomName;

	@ApiModelProperty(hidden = true)
	private Date creationDate;

	@ApiModelProperty(hidden = true)
	private Date lastUpdateDate;

	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private Date startDate;

	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private Date endDate;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private List<Long> ids;

	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private List<String> codes;

	@Transient
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<Long> billIds;

	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private List<String> processMessageList;

	@Transient
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(hidden = true)
	private List<String> statusList;

	@ApiModelProperty(hidden = true)
	@Transient
	private Long pageId;

	@ApiModelProperty(hidden = true)
	@Transient
	private String pageCode;

	@ApiModelProperty(hidden = true)
	@Transient
	private String deviceCode;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(hidden = true)
	@Transient
	private String errorMessage;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(hidden = true)
	@Transient
	private Boolean successFlag;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private Long interfaceId;
	//
	// 可访问权限字段
	// ------------------------------------------------------------------------------
	//可访问库存组织
	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private List<Long> orgIds;

	//可访问仓库
	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private List<Long> warehouseIds;

	//可访问库区
	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private List<Long> whareaIds;

	//可访问货主
	@ApiModelProperty(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private List<Long> ownerIds;

	//
	// 弹性域字段
	// ------------------------------------------------------------------------------

	@ApiModelProperty(hidden = true)
	private String attributeCategory;
	@ApiModelProperty(hidden = true)
	private String attribute1;
	@ApiModelProperty(hidden = true)
	private String attribute2;
	@ApiModelProperty(hidden = true)
	private String attribute3;
	@ApiModelProperty(hidden = true)
	private String attribute4;
	@ApiModelProperty(hidden = true)
	private String attribute5;
	@ApiModelProperty(hidden = true)
	private String attribute6;
	@ApiModelProperty(hidden = true)
	private String attribute7;
	@ApiModelProperty(hidden = true)
	private String attribute8;
	@ApiModelProperty(hidden = true)
	private String attribute9;
	@ApiModelProperty(hidden = true)
	private String attribute10;
	@ApiModelProperty(hidden = true)
	private String attribute11;
	@ApiModelProperty(hidden = true)
	private String attribute12;
	@ApiModelProperty(hidden = true)
	private String attribute13;
	@ApiModelProperty(hidden = true)
	private String attribute14;
	@ApiModelProperty(hidden = true)
	private String attribute15;

}
