// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ExecutionService.proto

package dynamic.calls.Executors;

/**
 * Protobuf type {@code dynamic.calls.Executors.ExecutionRequest}
 */
public final class ExecutionRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:dynamic.calls.Executors.ExecutionRequest)
    ExecutionRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ExecutionRequest.newBuilder() to construct.
  private ExecutionRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ExecutionRequest() {
    jarLocation_ = "";
    className_ = "";
    methodName_ = "";
    data_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new ExecutionRequest();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return dynamic.calls.Executors.ExecutionServiceOuterClass.internal_static_dynamic_calls_Executors_ExecutionRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return dynamic.calls.Executors.ExecutionServiceOuterClass.internal_static_dynamic_calls_Executors_ExecutionRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            dynamic.calls.Executors.ExecutionRequest.class, dynamic.calls.Executors.ExecutionRequest.Builder.class);
  }

  public static final int JARLOCATION_FIELD_NUMBER = 1;
  @SuppressWarnings("serial")
  private volatile java.lang.Object jarLocation_ = "";
  /**
   * <code>string jarLocation = 1;</code>
   * @return The jarLocation.
   */
  @java.lang.Override
  public java.lang.String getJarLocation() {
    java.lang.Object ref = jarLocation_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      jarLocation_ = s;
      return s;
    }
  }
  /**
   * <code>string jarLocation = 1;</code>
   * @return The bytes for jarLocation.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getJarLocationBytes() {
    java.lang.Object ref = jarLocation_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      jarLocation_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int CLASSNAME_FIELD_NUMBER = 2;
  @SuppressWarnings("serial")
  private volatile java.lang.Object className_ = "";
  /**
   * <code>string className = 2;</code>
   * @return The className.
   */
  @java.lang.Override
  public java.lang.String getClassName() {
    java.lang.Object ref = className_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      className_ = s;
      return s;
    }
  }
  /**
   * <code>string className = 2;</code>
   * @return The bytes for className.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getClassNameBytes() {
    java.lang.Object ref = className_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      className_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int METHODNAME_FIELD_NUMBER = 3;
  @SuppressWarnings("serial")
  private volatile java.lang.Object methodName_ = "";
  /**
   * <code>string methodName = 3;</code>
   * @return The methodName.
   */
  @java.lang.Override
  public java.lang.String getMethodName() {
    java.lang.Object ref = methodName_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      methodName_ = s;
      return s;
    }
  }
  /**
   * <code>string methodName = 3;</code>
   * @return The bytes for methodName.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getMethodNameBytes() {
    java.lang.Object ref = methodName_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      methodName_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int DATA_FIELD_NUMBER = 4;
  @SuppressWarnings("serial")
  private volatile java.lang.Object data_ = "";
  /**
   * <code>string data = 4;</code>
   * @return The data.
   */
  @java.lang.Override
  public java.lang.String getData() {
    java.lang.Object ref = data_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      data_ = s;
      return s;
    }
  }
  /**
   * <code>string data = 4;</code>
   * @return The bytes for data.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getDataBytes() {
    java.lang.Object ref = data_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      data_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(jarLocation_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, jarLocation_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(className_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, className_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(methodName_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, methodName_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(data_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 4, data_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(jarLocation_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, jarLocation_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(className_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, className_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(methodName_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, methodName_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(data_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, data_);
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof dynamic.calls.Executors.ExecutionRequest)) {
      return super.equals(obj);
    }
    dynamic.calls.Executors.ExecutionRequest other = (dynamic.calls.Executors.ExecutionRequest) obj;

    if (!getJarLocation()
        .equals(other.getJarLocation())) return false;
    if (!getClassName()
        .equals(other.getClassName())) return false;
    if (!getMethodName()
        .equals(other.getMethodName())) return false;
    if (!getData()
        .equals(other.getData())) return false;
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + JARLOCATION_FIELD_NUMBER;
    hash = (53 * hash) + getJarLocation().hashCode();
    hash = (37 * hash) + CLASSNAME_FIELD_NUMBER;
    hash = (53 * hash) + getClassName().hashCode();
    hash = (37 * hash) + METHODNAME_FIELD_NUMBER;
    hash = (53 * hash) + getMethodName().hashCode();
    hash = (37 * hash) + DATA_FIELD_NUMBER;
    hash = (53 * hash) + getData().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static dynamic.calls.Executors.ExecutionRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static dynamic.calls.Executors.ExecutionRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static dynamic.calls.Executors.ExecutionRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static dynamic.calls.Executors.ExecutionRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static dynamic.calls.Executors.ExecutionRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static dynamic.calls.Executors.ExecutionRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static dynamic.calls.Executors.ExecutionRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static dynamic.calls.Executors.ExecutionRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static dynamic.calls.Executors.ExecutionRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static dynamic.calls.Executors.ExecutionRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static dynamic.calls.Executors.ExecutionRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static dynamic.calls.Executors.ExecutionRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(dynamic.calls.Executors.ExecutionRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code dynamic.calls.Executors.ExecutionRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:dynamic.calls.Executors.ExecutionRequest)
      dynamic.calls.Executors.ExecutionRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return dynamic.calls.Executors.ExecutionServiceOuterClass.internal_static_dynamic_calls_Executors_ExecutionRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return dynamic.calls.Executors.ExecutionServiceOuterClass.internal_static_dynamic_calls_Executors_ExecutionRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              dynamic.calls.Executors.ExecutionRequest.class, dynamic.calls.Executors.ExecutionRequest.Builder.class);
    }

    // Construct using dynamic.calls.Executors.ExecutionRequest.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      jarLocation_ = "";
      className_ = "";
      methodName_ = "";
      data_ = "";
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return dynamic.calls.Executors.ExecutionServiceOuterClass.internal_static_dynamic_calls_Executors_ExecutionRequest_descriptor;
    }

    @java.lang.Override
    public dynamic.calls.Executors.ExecutionRequest getDefaultInstanceForType() {
      return dynamic.calls.Executors.ExecutionRequest.getDefaultInstance();
    }

    @java.lang.Override
    public dynamic.calls.Executors.ExecutionRequest build() {
      dynamic.calls.Executors.ExecutionRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public dynamic.calls.Executors.ExecutionRequest buildPartial() {
      dynamic.calls.Executors.ExecutionRequest result = new dynamic.calls.Executors.ExecutionRequest(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(dynamic.calls.Executors.ExecutionRequest result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.jarLocation_ = jarLocation_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.className_ = className_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.methodName_ = methodName_;
      }
      if (((from_bitField0_ & 0x00000008) != 0)) {
        result.data_ = data_;
      }
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof dynamic.calls.Executors.ExecutionRequest) {
        return mergeFrom((dynamic.calls.Executors.ExecutionRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(dynamic.calls.Executors.ExecutionRequest other) {
      if (other == dynamic.calls.Executors.ExecutionRequest.getDefaultInstance()) return this;
      if (!other.getJarLocation().isEmpty()) {
        jarLocation_ = other.jarLocation_;
        bitField0_ |= 0x00000001;
        onChanged();
      }
      if (!other.getClassName().isEmpty()) {
        className_ = other.className_;
        bitField0_ |= 0x00000002;
        onChanged();
      }
      if (!other.getMethodName().isEmpty()) {
        methodName_ = other.methodName_;
        bitField0_ |= 0x00000004;
        onChanged();
      }
      if (!other.getData().isEmpty()) {
        data_ = other.data_;
        bitField0_ |= 0x00000008;
        onChanged();
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10: {
              jarLocation_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000001;
              break;
            } // case 10
            case 18: {
              className_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000002;
              break;
            } // case 18
            case 26: {
              methodName_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000004;
              break;
            } // case 26
            case 34: {
              data_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000008;
              break;
            } // case 34
            default: {
              if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                done = true; // was an endgroup tag
              }
              break;
            } // default:
          } // switch (tag)
        } // while (!done)
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.unwrapIOException();
      } finally {
        onChanged();
      } // finally
      return this;
    }
    private int bitField0_;

    private java.lang.Object jarLocation_ = "";
    /**
     * <code>string jarLocation = 1;</code>
     * @return The jarLocation.
     */
    public java.lang.String getJarLocation() {
      java.lang.Object ref = jarLocation_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        jarLocation_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string jarLocation = 1;</code>
     * @return The bytes for jarLocation.
     */
    public com.google.protobuf.ByteString
        getJarLocationBytes() {
      java.lang.Object ref = jarLocation_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        jarLocation_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string jarLocation = 1;</code>
     * @param value The jarLocation to set.
     * @return This builder for chaining.
     */
    public Builder setJarLocation(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      jarLocation_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>string jarLocation = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearJarLocation() {
      jarLocation_ = getDefaultInstance().getJarLocation();
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }
    /**
     * <code>string jarLocation = 1;</code>
     * @param value The bytes for jarLocation to set.
     * @return This builder for chaining.
     */
    public Builder setJarLocationBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      jarLocation_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }

    private java.lang.Object className_ = "";
    /**
     * <code>string className = 2;</code>
     * @return The className.
     */
    public java.lang.String getClassName() {
      java.lang.Object ref = className_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        className_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string className = 2;</code>
     * @return The bytes for className.
     */
    public com.google.protobuf.ByteString
        getClassNameBytes() {
      java.lang.Object ref = className_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        className_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string className = 2;</code>
     * @param value The className to set.
     * @return This builder for chaining.
     */
    public Builder setClassName(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      className_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <code>string className = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearClassName() {
      className_ = getDefaultInstance().getClassName();
      bitField0_ = (bitField0_ & ~0x00000002);
      onChanged();
      return this;
    }
    /**
     * <code>string className = 2;</code>
     * @param value The bytes for className to set.
     * @return This builder for chaining.
     */
    public Builder setClassNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      className_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }

    private java.lang.Object methodName_ = "";
    /**
     * <code>string methodName = 3;</code>
     * @return The methodName.
     */
    public java.lang.String getMethodName() {
      java.lang.Object ref = methodName_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        methodName_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string methodName = 3;</code>
     * @return The bytes for methodName.
     */
    public com.google.protobuf.ByteString
        getMethodNameBytes() {
      java.lang.Object ref = methodName_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        methodName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string methodName = 3;</code>
     * @param value The methodName to set.
     * @return This builder for chaining.
     */
    public Builder setMethodName(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      methodName_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <code>string methodName = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearMethodName() {
      methodName_ = getDefaultInstance().getMethodName();
      bitField0_ = (bitField0_ & ~0x00000004);
      onChanged();
      return this;
    }
    /**
     * <code>string methodName = 3;</code>
     * @param value The bytes for methodName to set.
     * @return This builder for chaining.
     */
    public Builder setMethodNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      methodName_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }

    private java.lang.Object data_ = "";
    /**
     * <code>string data = 4;</code>
     * @return The data.
     */
    public java.lang.String getData() {
      java.lang.Object ref = data_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        data_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string data = 4;</code>
     * @return The bytes for data.
     */
    public com.google.protobuf.ByteString
        getDataBytes() {
      java.lang.Object ref = data_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        data_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string data = 4;</code>
     * @param value The data to set.
     * @return This builder for chaining.
     */
    public Builder setData(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      data_ = value;
      bitField0_ |= 0x00000008;
      onChanged();
      return this;
    }
    /**
     * <code>string data = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearData() {
      data_ = getDefaultInstance().getData();
      bitField0_ = (bitField0_ & ~0x00000008);
      onChanged();
      return this;
    }
    /**
     * <code>string data = 4;</code>
     * @param value The bytes for data to set.
     * @return This builder for chaining.
     */
    public Builder setDataBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      data_ = value;
      bitField0_ |= 0x00000008;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:dynamic.calls.Executors.ExecutionRequest)
  }

  // @@protoc_insertion_point(class_scope:dynamic.calls.Executors.ExecutionRequest)
  private static final dynamic.calls.Executors.ExecutionRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new dynamic.calls.Executors.ExecutionRequest();
  }

  public static dynamic.calls.Executors.ExecutionRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ExecutionRequest>
      PARSER = new com.google.protobuf.AbstractParser<ExecutionRequest>() {
    @java.lang.Override
    public ExecutionRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      Builder builder = newBuilder();
      try {
        builder.mergeFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(builder.buildPartial());
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(e)
            .setUnfinishedMessage(builder.buildPartial());
      }
      return builder.buildPartial();
    }
  };

  public static com.google.protobuf.Parser<ExecutionRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ExecutionRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public dynamic.calls.Executors.ExecutionRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

