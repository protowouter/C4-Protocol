/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Wouter Timmermans
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package nl.woutertimmermans.connect4.protocol.thrift;

import org.apache.thrift.EncodingUtils;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;

import javax.annotation.Generated;
import java.util.*;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-1-8")
public class C4Session implements org.apache.thrift.TBase<C4Session, C4Session._Fields>, java.io.Serializable, Cloneable, Comparable<C4Session> {
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;

  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SESSION_ID, new org.apache.thrift.meta_data.FieldMetaData("session_id", org.apache.thrift.TFieldRequirementType.DEFAULT,
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PLAYER, new org.apache.thrift.meta_data.FieldMetaData("player", org.apache.thrift.TFieldRequirementType.DEFAULT,
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, C4Player.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(C4Session.class, metaDataMap);
  }
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("C4Session");
  private static final org.apache.thrift.protocol.TField SESSION_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("session_id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField PLAYER_FIELD_DESC = new org.apache.thrift.protocol.TField("player", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new C4SessionStandardSchemeFactory());
    schemes.put(TupleScheme.class, new C4SessionTupleSchemeFactory());
  }
  // isset id assignments
  private static final int __SESSION_ID_ISSET_ID = 0;
  public int session_id; // required
  public C4Player player; // required
  private byte __isset_bitfield = 0;
  public C4Session() {
  }

  public C4Session(
    int session_id,
    C4Player player)
  {
    this();
    this.session_id = session_id;
    setSession_idIsSet(true);
    this.player = player;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public C4Session(C4Session other) {
    __isset_bitfield = other.__isset_bitfield;
    this.session_id = other.session_id;
    if (other.isSetPlayer()) {
      this.player = new C4Player(other.player);
    }
  }

  public C4Session deepCopy() {
    return new C4Session(this);
  }

  @Override
  public void clear() {
    setSession_idIsSet(false);
    this.session_id = 0;
    this.player = null;
  }

  public int getSession_id() {
    return this.session_id;
  }

  public C4Session setSession_id(int session_id) {
    this.session_id = session_id;
    setSession_idIsSet(true);
    return this;
  }

  public void unsetSession_id() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SESSION_ID_ISSET_ID);
  }

  /** Returns true if field session_id is set (has been assigned a value) and false otherwise */
  public boolean isSetSession_id() {
    return EncodingUtils.testBit(__isset_bitfield, __SESSION_ID_ISSET_ID);
  }

  public void setSession_idIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SESSION_ID_ISSET_ID, value);
  }

  public C4Player getPlayer() {
    return this.player;
  }

  public C4Session setPlayer(C4Player player) {
    this.player = player;
    return this;
  }

  public void unsetPlayer() {
    this.player = null;
  }

  /** Returns true if field player is set (has been assigned a value) and false otherwise */
  public boolean isSetPlayer() {
    return this.player != null;
  }

  public void setPlayerIsSet(boolean value) {
    if (!value) {
      this.player = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SESSION_ID:
      if (value == null) {
        unsetSession_id();
      } else {
        setSession_id((Integer)value);
      }
      break;

    case PLAYER:
      if (value == null) {
        unsetPlayer();
      } else {
        setPlayer((C4Player)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SESSION_ID:
      return Integer.valueOf(getSession_id());

    case PLAYER:
      return getPlayer();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SESSION_ID:
      return isSetSession_id();
    case PLAYER:
      return isSetPlayer();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof C4Session)
      return this.equals((C4Session)that);
    return false;
  }

  public boolean equals(C4Session that) {
    if (that == null)
      return false;

    boolean this_present_session_id = true;
    boolean that_present_session_id = true;
    if (this_present_session_id || that_present_session_id) {
      if (!(this_present_session_id && that_present_session_id))
        return false;
      if (this.session_id != that.session_id)
        return false;
    }

    boolean this_present_player = true && this.isSetPlayer();
    boolean that_present_player = true && that.isSetPlayer();
    if (this_present_player || that_present_player) {
      if (!(this_present_player && that_present_player))
        return false;
      if (!this.player.equals(that.player))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_session_id = true;
    list.add(present_session_id);
    if (present_session_id)
      list.add(session_id);

    boolean present_player = true && (isSetPlayer());
    list.add(present_player);
    if (present_player)
      list.add(player);

    return list.hashCode();
  }

  @Override
  public int compareTo(C4Session other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetSession_id()).compareTo(other.isSetSession_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSession_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.session_id, other.session_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPlayer()).compareTo(other.isSetPlayer());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPlayer()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.player, other.player);
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
    StringBuilder sb = new StringBuilder("C4Session(");
    boolean first = true;

    sb.append("session_id:");
    sb.append(this.session_id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("player:");
    if (this.player == null) {
      sb.append("null");
    } else {
      sb.append(this.player);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (player != null) {
      player.validate();
    }
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  /**
   * The set of fields this struct contains, along with convenience methods for finding and manipulating them.
   */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SESSION_ID((short) 1, "session_id"),
    PLAYER((short) 2, "player");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch (fieldId) {
        case 1: // SESSION_ID
          return SESSION_ID;
        case 2: // PLAYER
          return PLAYER;
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

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  private static class C4SessionStandardSchemeFactory implements SchemeFactory {
    public C4SessionStandardScheme getScheme() {
      return new C4SessionStandardScheme();
    }
  }

  private static class C4SessionStandardScheme extends StandardScheme<C4Session> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, C4Session struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SESSION_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.session_id = iprot.readI32();
              struct.setSession_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PLAYER
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.player = new C4Player();
              struct.player.read(iprot);
              struct.setPlayerIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, C4Session struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(SESSION_ID_FIELD_DESC);
      oprot.writeI32(struct.session_id);
      oprot.writeFieldEnd();
      if (struct.player != null) {
        oprot.writeFieldBegin(PLAYER_FIELD_DESC);
        struct.player.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class C4SessionTupleSchemeFactory implements SchemeFactory {
    public C4SessionTupleScheme getScheme() {
      return new C4SessionTupleScheme();
    }
  }

  private static class C4SessionTupleScheme extends TupleScheme<C4Session> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, C4Session struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetSession_id()) {
        optionals.set(0);
      }
      if (struct.isSetPlayer()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetSession_id()) {
        oprot.writeI32(struct.session_id);
      }
      if (struct.isSetPlayer()) {
        struct.player.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, C4Session struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.session_id = iprot.readI32();
        struct.setSession_idIsSet(true);
      }
      if (incoming.get(1)) {
        struct.player = new C4Player();
        struct.player.read(iprot);
        struct.setPlayerIsSet(true);
      }
    }
  }

}
