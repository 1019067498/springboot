package com.example.demo.service.impl;

import com.example.demo.service.SecurityToken;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Transient;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @package: com.example.demo.service.impl
 * @author: QuJiaQi
 * @date: 2020/9/18 11:29
 */
public class AuditDomain implements SecurityToken {

    public static final String FIELD_CREATION_DATE = "creationDate";
    public static final String FIELD_CREATED_BY = "createdBy";
    public static final String FIELD_LAST_UPDATE_DATE = "lastUpdateDate";
    public static final String FIELD_LAST_UPDATED_BY = "lastUpdatedBy";
    public static final String FIELD_OBJECT_VERSION_NUMBER = "objectVersionNumber";
    public static final String FIELD_TABLE_ID = "tableId";
    @ApiModelProperty(
            hidden = true
    )
    private Date creationDate;
    @ApiModelProperty(
            hidden = true
    )
    private Long createdBy;
    @ApiModelProperty(
            hidden = true
    )
    private Date lastUpdateDate;
    @ApiModelProperty(
            hidden = true
    )
    private Long lastUpdatedBy;
    @ApiModelProperty(
            hidden = true
    )
    private Long objectVersionNumber;
    @Transient
    @ApiModelProperty(
            hidden = true
    )
    private String tableId;
    @Transient
    @JsonInclude(Include.NON_EMPTY)
    @ApiModelProperty(
            hidden = true
    )
    private Map<String, Map<String, String>> _tls;
    @Transient
    @JsonInclude(Include.NON_NULL)
    @ApiModelProperty(
            hidden = true
    )
    private String _token;
    @Transient
    @JsonInclude(Include.NON_EMPTY)
    @ApiModelProperty(
            hidden = true
    )
    private Map<String, Object> _innerMap;
    @Transient
    @JsonInclude(Include.NON_NULL)
    @ApiModelProperty(
            hidden = true
    )
    private AuditDomain.RecordStatus _status;
    @Transient
    @JsonInclude(Include.NON_EMPTY)
    @ApiModelProperty(
            hidden = true
    )
    private Map<String, Object> flex = new HashMap(32);

    public AuditDomain() {
    }

    public AuditDomain set_innerMap(Map<String, Object> _innerMap) {
        this._innerMap = _innerMap;
        return this;
    }

    @JsonAnySetter
    public void set_innerMap(String key, Object value) {
        if (this._innerMap == null) {
            this._innerMap = new HashMap();
        }

        this._innerMap.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> get_innerMap() {
        return this._innerMap;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastUpdateDate() {
        return this.lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Long getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Long getObjectVersionNumber() {
        return this.objectVersionNumber;
    }

    public void setObjectVersionNumber(Long objectVersionNumber) {
        this.objectVersionNumber = objectVersionNumber;
    }

    @JsonInclude(Include.NON_NULL)
    public String getTableId() {
        return this.tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public Map<String, Map<String, String>> get_tls() {
        return this._tls;
    }

    public AuditDomain set_tls(Map<String, Map<String, String>> _tls) {
        this._tls = _tls;
        return this;
    }

    @Override
    public String get_token() {
        return this._token;
    }

    @Override
    public void set_token(String _token) {
        this._token = _token;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

    public String toJSONString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public AuditDomain.RecordStatus get_status() {
        return this._status;
    }

    public AuditDomain set_status(AuditDomain.RecordStatus _status) {
        this._status = _status;
        return this;
    }

    public Map<String, Object> getFlex() {
        return this.flex;
    }

    public AuditDomain setFlex(Map<String, Object> flex) {
        this.flex = flex;
        return this;
    }

    public static enum RecordStatus {
        create,
        update,
        delete;

        private RecordStatus() {
        }
    }
}
