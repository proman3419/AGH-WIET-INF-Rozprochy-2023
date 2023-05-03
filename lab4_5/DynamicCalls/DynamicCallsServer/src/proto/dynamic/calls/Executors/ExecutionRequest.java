// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ExecutionService.proto

package dynamic.calls.Executors;

/**
 * Protobuf type {@code dynamic.calls.Executors.ExecutionRequest}
 */
public  final class ExecutionRequest extends
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
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private ExecutionRequest(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            jarLocation_ = s;
            break;
          }
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();

            className_ = s;
            break;
          }
          case 26: {
            java.lang.String s = input.readStringRequireUtf8();

            methodName_ = s;
            break;
          }
          case 34: {
            java.lang.String s = input.readStringRequireUtf8();

            data_ = s;
            break;
          }
          default: {
            if (!parseUnknownFieldProto3(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
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
  private volatile java.lang.Object jarLocation_;
  /**
   * <code>string jarLocation = 1;</code>
   */
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
   */
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
  private volatile java.lang.Object className_;
  /**
   * <code>string className = 2;</code>
   */
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
   */
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
  private volatile java.lang.Object methodName_;
  /**
   * <code>string methodName = 3;</code>
   */
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
   */
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
  private volatile java.lang.Object data_;
  /**
   * <code>string data = 4;</code>
   */
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
   */
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
    if (!getJarLocationBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, jarLocation_);
    }
    if (!getClassNameBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, className_);
    }
    if (!getMethodNameBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, methodName_);
    }
    if (!getDataBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 4, data_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getJarLocationBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, jarLocation_);
    }
    if (!getClassNameBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, className_);
    }
    if (!getMethodNameBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, methodName_);
    }
    if (!getDataBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, data_);
    }
    size += unknownFields.getSerializedSize();
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

    boolean result = true;
    result = result && getJarLocation()
        .equals(other.getJarLocation());
    result = result && getClassName()
        .equals(other.getClassName());
    result = result && getMethodName()
        .equals(other.getMethodName());
    result = result && getData()
        .equals(other.getData());
    result = result && unknownFields.equals(other.unknownFields);
    return result;
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
    hash = (29 * hash) + unknownFields.hashCode();
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
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
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
      result.jarLocation_ = jarLocation_;
      result.className_ = className_;
      result.methodName_ = methodName_;
      result.data_ = data_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return (Builder) super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.addRepeatedField(field, value);
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
        onChanged();
      }
      if (!other.getClassName().isEmpty()) {
        className_ = other.className_;
        onChanged();
      }
      if (!other.getMethodName().isEmpty()) {
        methodName_ = other.methodName_;
        onChanged();
      }
      if (!other.getData().isEmpty()) {
        data_ = other.data_;
        onChanged();
      }
      this.mergeUnknownFields(other.unknownFields);
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
      dynamic.calls.Executors.ExecutionRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (dynamic.calls.Executors.ExecutionRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object jarLocation_ = "";
    /**
     * <code>string jarLocation = 1;</code>
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
     */
    public Builder setJarLocation(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      jarLocation_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string jarLocation = 1;</code>
     */
    public Builder clearJarLocation() {
      
      jarLocation_ = getDefaultInstance().getJarLocation();
      onChanged();
      return this;
    }
    /**
     * <code>string jarLocation = 1;</code>
     */
    public Builder setJarLocationBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      jarLocation_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object className_ = "";
    /**
     * <code>string className = 2;</code>
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
     */
    public Builder setClassName(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      className_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string className = 2;</code>
     */
    public Builder clearClassName() {
      
      className_ = getDefaultInstance().getClassName();
      onChanged();
      return this;
    }
    /**
     * <code>string className = 2;</code>
     */
    public Builder setClassNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      className_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object methodName_ = "";
    /**
     * <code>string methodName = 3;</code>
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
     */
    public Builder setMethodName(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      methodName_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string methodName = 3;</code>
     */
    public Builder clearMethodName() {
      
      methodName_ = getDefaultInstance().getMethodName();
      onChanged();
      return this;
    }
    /**
     * <code>string methodName = 3;</code>
     */
    public Builder setMethodNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      methodName_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object data_ = "";
    /**
     * <code>string data = 4;</code>
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
     */
    public Builder setData(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      data_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string data = 4;</code>
     */
    public Builder clearData() {
      
      data_ = getDefaultInstance().getData();
      onChanged();
      return this;
    }
    /**
     * <code>string data = 4;</code>
     */
    public Builder setDataBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      data_ = value;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFieldsProto3(unknownFields);
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
      return new ExecutionRequest(input, extensionRegistry);
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
