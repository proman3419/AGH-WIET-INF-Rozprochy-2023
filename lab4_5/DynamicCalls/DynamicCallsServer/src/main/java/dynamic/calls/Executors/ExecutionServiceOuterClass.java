// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ExecutionService.proto

package dynamic.calls.Executors;

public final class ExecutionServiceOuterClass {
  private ExecutionServiceOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_dynamic_calls_Executors_ExecutionRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_dynamic_calls_Executors_ExecutionRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_dynamic_calls_Executors_ExecutionResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_dynamic_calls_Executors_ExecutionResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\026ExecutionService.proto\022\027dynamic.calls." +
      "Executors\"\\\n\020ExecutionRequest\022\023\n\013jarLoca" +
      "tion\030\001 \001(\t\022\021\n\tclassName\030\002 \001(\t\022\022\n\nmethodN" +
      "ame\030\003 \001(\t\022\014\n\004data\030\004 \001(\t\"0\n\021ExecutionResp" +
      "onse\022\r\n\005error\030\001 \001(\t\022\014\n\004data\030\002 \001(\t2t\n\020Exe" +
      "cutionService\022`\n\007execute\022).dynamic.calls" +
      ".Executors.ExecutionRequest\032*.dynamic.ca" +
      "lls.Executors.ExecutionResponseB\027P\001\252\002\022Dy" +
      "namicCallsClientb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_dynamic_calls_Executors_ExecutionRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_dynamic_calls_Executors_ExecutionRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_dynamic_calls_Executors_ExecutionRequest_descriptor,
        new java.lang.String[] { "JarLocation", "ClassName", "MethodName", "Data", });
    internal_static_dynamic_calls_Executors_ExecutionResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_dynamic_calls_Executors_ExecutionResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_dynamic_calls_Executors_ExecutionResponse_descriptor,
        new java.lang.String[] { "Error", "Data", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
