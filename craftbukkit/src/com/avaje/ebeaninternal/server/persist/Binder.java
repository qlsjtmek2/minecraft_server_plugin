// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist;

import java.sql.Timestamp;
import java.sql.Date;
import java.sql.Time;
import java.math.BigDecimal;
import com.avaje.ebeaninternal.server.type.ScalarType;
import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.server.core.Message;
import java.sql.CallableStatement;
import java.util.List;
import com.avaje.ebeaninternal.api.BindParams;
import java.sql.SQLException;
import java.util.ArrayList;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.type.TypeManager;
import java.util.logging.Logger;

public class Binder
{
    private static final Logger logger;
    private final TypeManager typeManager;
    
    public Binder(final TypeManager typeManager) {
        this.typeManager = typeManager;
    }
    
    public void bind(final BindValues bindValues, final DataBind dataBind, final StringBuilder bindBuf) throws SQLException {
        String logPrefix = "";
        final ArrayList<BindValues.Value> list = bindValues.values();
        for (int i = 0; i < list.size(); ++i) {
            final BindValues.Value bindValue = list.get(i);
            if (bindValue.isComment()) {
                if (bindBuf != null) {
                    bindBuf.append(bindValue.getName());
                    if (logPrefix.equals("")) {
                        logPrefix = ", ";
                    }
                }
            }
            else {
                final Object val = bindValue.getValue();
                final int dt = bindValue.getDbType();
                this.bindObject(dataBind, val, dt);
                if (bindBuf != null) {
                    bindBuf.append(logPrefix);
                    if (logPrefix.equals("")) {
                        logPrefix = ", ";
                    }
                    bindBuf.append(bindValue.getName());
                    bindBuf.append("=");
                    if (this.isLob(dt)) {
                        bindBuf.append("[LOB]");
                    }
                    else {
                        bindBuf.append(String.valueOf(val));
                    }
                }
            }
        }
    }
    
    public String bind(final BindParams bindParams, final DataBind dataBind) throws SQLException {
        final StringBuilder bindLog = new StringBuilder();
        this.bind(bindParams, dataBind, bindLog);
        return bindLog.toString();
    }
    
    public void bind(final BindParams bindParams, final DataBind dataBind, final StringBuilder bindLog) throws SQLException {
        this.bind(bindParams.positionedParameters(), dataBind, bindLog);
    }
    
    public void bind(final List<BindParams.Param> list, final DataBind dataBind, final StringBuilder bindLog) throws SQLException {
        CallableStatement cstmt = null;
        if (dataBind.getPstmt() instanceof CallableStatement) {
            cstmt = (CallableStatement)dataBind.getPstmt();
        }
        Object value = null;
        try {
            for (int i = 0; i < list.size(); ++i) {
                final BindParams.Param param = list.get(i);
                if (param.isOutParam() && cstmt != null) {
                    cstmt.registerOutParameter(dataBind.nextPos(), param.getType());
                    if (param.isInParam()) {
                        dataBind.decrementPos();
                    }
                }
                if (param.isInParam()) {
                    value = param.getInValue();
                    if (bindLog != null) {
                        if (param.isEncryptionKey()) {
                            bindLog.append("****");
                        }
                        else {
                            bindLog.append(value);
                        }
                        bindLog.append(", ");
                    }
                    if (value == null) {
                        this.bindObject(dataBind, null, param.getType());
                    }
                    else {
                        this.bindObject(dataBind, value);
                    }
                }
            }
        }
        catch (SQLException ex) {
            Binder.logger.warning(Message.msg("fetch.bind.error", "" + (dataBind.currentPos() - 1), value));
            throw ex;
        }
    }
    
    public void bindObject(final DataBind dataBind, Object value) throws SQLException {
        if (value == null) {
            this.bindObject(dataBind, null, 1111);
        }
        else {
            final ScalarType<?> type = this.typeManager.getScalarType(value.getClass());
            if (type == null) {
                final String msg = "No ScalarType registered for " + value.getClass();
                throw new PersistenceException(msg);
            }
            if (!type.isJdbcNative()) {
                value = type.toJdbcType(value);
            }
            final int dbType = type.getJdbcType();
            this.bindObject(dataBind, value, dbType);
        }
    }
    
    public void bindObject(final DataBind dataBind, final Object data, final int dbType) throws SQLException {
        if (data == null) {
            dataBind.setNull(dbType);
            return;
        }
        switch (dbType) {
            case -1: {
                this.bindLongVarChar(dataBind, data);
                break;
            }
            case -4: {
                this.bindLongVarBinary(dataBind, data);
                break;
            }
            case 2005: {
                this.bindClob(dataBind, data);
                break;
            }
            case 2004: {
                this.bindBlob(dataBind, data);
                break;
            }
            default: {
                this.bindSimpleData(dataBind, dbType, data);
                break;
            }
        }
    }
    
    private void bindSimpleData(final DataBind b, final int dataType, final Object data) throws SQLException {
        try {
            switch (dataType) {
                case 16: {
                    final Boolean bo = (Boolean)data;
                    b.setBoolean(bo);
                    break;
                }
                case -7: {
                    final Boolean bitBool = (Boolean)data;
                    b.setBoolean(bitBool);
                    break;
                }
                case 12: {
                    b.setString((String)data);
                    break;
                }
                case 1: {
                    b.setString(data.toString());
                    break;
                }
                case -6: {
                    b.setByte((byte)data);
                    break;
                }
                case 5: {
                    b.setShort((short)data);
                    break;
                }
                case 4: {
                    b.setInt((int)data);
                    break;
                }
                case -5: {
                    b.setLong((long)data);
                    break;
                }
                case 7: {
                    b.setFloat((float)data);
                    break;
                }
                case 6: {
                    b.setDouble((double)data);
                    break;
                }
                case 8: {
                    b.setDouble((double)data);
                    break;
                }
                case 2: {
                    b.setBigDecimal((BigDecimal)data);
                    break;
                }
                case 3: {
                    b.setBigDecimal((BigDecimal)data);
                    break;
                }
                case 92: {
                    b.setTime((Time)data);
                    break;
                }
                case 91: {
                    b.setDate((Date)data);
                    break;
                }
                case 93: {
                    b.setTimestamp((Timestamp)data);
                    break;
                }
                case -2: {
                    b.setBytes((byte[])data);
                    break;
                }
                case -3: {
                    b.setBytes((byte[])data);
                    break;
                }
                case 1111: {
                    b.setObject(data);
                    break;
                }
                case 2000: {
                    b.setObject(data);
                    break;
                }
                default: {
                    final String msg = Message.msg("persist.bind.datatype", "" + dataType, "" + b.currentPos());
                    throw new SQLException(msg);
                }
            }
        }
        catch (Exception e) {
            String dataClass = "Data is null?";
            if (data != null) {
                dataClass = data.getClass().getName();
            }
            String m = "Error with property[" + b.currentPos() + "] dt[" + dataType + "]";
            m = m + "data[" + data + "][" + dataClass + "]";
            throw new PersistenceException(m, e);
        }
    }
    
    private void bindLongVarChar(final DataBind b, final Object data) throws SQLException {
        final String sd = (String)data;
        b.setClob(sd);
    }
    
    private void bindLongVarBinary(final DataBind b, final Object data) throws SQLException {
        final byte[] bytes = (byte[])data;
        b.setBlob(bytes);
    }
    
    private void bindClob(final DataBind b, final Object data) throws SQLException {
        final String sd = (String)data;
        b.setClob(sd);
    }
    
    private void bindBlob(final DataBind b, final Object data) throws SQLException {
        final byte[] bytes = (byte[])data;
        b.setBlob(bytes);
    }
    
    private boolean isLob(final int dbType) {
        switch (dbType) {
            case 2005: {
                return true;
            }
            case -1: {
                return true;
            }
            case 2004: {
                return true;
            }
            case -4: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    static {
        logger = Logger.getLogger(Binder.class.getName());
    }
}
