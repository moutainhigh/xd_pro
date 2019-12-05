/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.xiaodou.thrift.model;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-07-26")
public class DefaultModel implements org.apache.thrift.TBase<DefaultModel, DefaultModel._Fields>, java.io.Serializable, Cloneable, Comparable<DefaultModel> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("DefaultModel");

  private static final org.apache.thrift.protocol.TField MODEL_FIELD_DESC = new org.apache.thrift.protocol.TField("model", org.apache.thrift.protocol.TType.STRING, (short)1);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new DefaultModelStandardSchemeFactory());
    schemes.put(TupleScheme.class, new DefaultModelTupleSchemeFactory());
  }

  public String model; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    MODEL((short)1, "model");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // MODEL
          return MODEL;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.MODEL, new org.apache.thrift.meta_data.FieldMetaData("model", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(DefaultModel.class, metaDataMap);
  }

  public DefaultModel() {
  }

  public DefaultModel(
    String model)
  {
    this();
    this.model = model;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public DefaultModel(DefaultModel other) {
    if (other.isSetModel()) {
      this.model = other.model;
    }
  }

  public DefaultModel deepCopy() {
    return new DefaultModel(this);
  }

  @Override
  public void clear() {
    this.model = null;
  }

  public String getModel() {
    return this.model;
  }

  public DefaultModel setModel(String model) {
    this.model = model;
    return this;
  }

  public void unsetModel() {
    this.model = null;
  }

  /** Returns true if field model is set (has been assigned a value) and false otherwise */
  public boolean isSetModel() {
    return this.model != null;
  }

  public void setModelIsSet(boolean value) {
    if (!value) {
      this.model = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case MODEL:
      if (value == null) {
        unsetModel();
      } else {
        setModel((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case MODEL:
      return getModel();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case MODEL:
      return isSetModel();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof DefaultModel)
      return this.equals((DefaultModel)that);
    return false;
  }

  public boolean equals(DefaultModel that) {
    if (that == null)
      return false;

    boolean this_present_model = true && this.isSetModel();
    boolean that_present_model = true && that.isSetModel();
    if (this_present_model || that_present_model) {
      if (!(this_present_model && that_present_model))
        return false;
      if (!this.model.equals(that.model))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_model = true && (isSetModel());
    list.add(present_model);
    if (present_model)
      list.add(model);

    return list.hashCode();
  }

  @Override
  public int compareTo(DefaultModel other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetModel()).compareTo(other.isSetModel());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetModel()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.model, other.model);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("DefaultModel(");
    boolean first = true;

    sb.append("model:");
    if (this.model == null) {
      sb.append("null");
    } else {
      sb.append(this.model);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class DefaultModelStandardSchemeFactory implements SchemeFactory {
    public DefaultModelStandardScheme getScheme() {
      return new DefaultModelStandardScheme();
    }
  }

  private static class DefaultModelStandardScheme extends StandardScheme<DefaultModel> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, DefaultModel struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // MODEL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.model = iprot.readString();
              struct.setModelIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, DefaultModel struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.model != null) {
        oprot.writeFieldBegin(MODEL_FIELD_DESC);
        oprot.writeString(struct.model);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class DefaultModelTupleSchemeFactory implements SchemeFactory {
    public DefaultModelTupleScheme getScheme() {
      return new DefaultModelTupleScheme();
    }
  }

  private static class DefaultModelTupleScheme extends TupleScheme<DefaultModel> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, DefaultModel struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetModel()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetModel()) {
        oprot.writeString(struct.model);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, DefaultModel struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.model = iprot.readString();
        struct.setModelIsSet(true);
      }
    }
  }

}
