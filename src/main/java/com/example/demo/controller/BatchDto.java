package com.example.demo.controller;

import com.example.demo.dto.WmsDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApiModel("批次")
@Data
public class BatchDto extends WmsDto {
    @ApiModelProperty(value = "批次id")
    private Long batchId;
    @ApiModelProperty(value = "批次代码")
    private String batchCode;
    @ApiModelProperty(value = "组织id")
    private Long orgId;
    @ApiModelProperty(value = "组织code")
    private String orgCode;
    @ApiModelProperty(value = "货主id")
    private Long ownerId;
    @ApiModelProperty(value = "货主code")
    private String ownerCode;
    @ApiModelProperty(value = "供应商id")
    private Long vendorId;
    @ApiModelProperty(value = "供应商code")
    private String vendorCode;
    @ApiModelProperty(value = "供应商批号")
    private String vendorBatch;
    @ApiModelProperty(value = "物料id")
    private Long goodsId;
    @ApiModelProperty(value = "物料编码")
    private String sku;
    @ApiModelProperty(value = "批次状态")
    private String batchStatus;
    @ApiModelProperty(value = "批次规则")
    private String batchRulecode;
    @ApiModelProperty(value = "生产日期")
    private Date produceDate;
    @ApiModelProperty(value = "有效日期")
    private Date expireDate;
    @ApiModelProperty(value = "下次质检日期")
    private Date nextCheckDate;
    @ApiModelProperty(value = "启用标识")
    private Long enabledFlag;
    @ApiModelProperty(value = "有效期从")
    private Date startDate;
    @ApiModelProperty(value = "有效期至")
    private Date endDate;
    @ApiModelProperty(value = "现有量")
    private BigDecimal qty;
    @ApiModelProperty(value = "可用量")
    private BigDecimal validQty;
    @ApiModelProperty(value = "批次状态")
    private String batchStatusMeaning;
    @ApiModelProperty(value = "批次id")
    private List<Long> batchIds;
    @ApiModelProperty(value = "物料id")
    private List<Long> goodsIds;


    public BatchDto() {
    }

    public BatchDto(String batchCode) {
        this.batchCode = batchCode;
    }

    public BatchDto(String batchCode, String sku) {
        this.batchCode = batchCode;
        this.sku = sku;
    }

}
