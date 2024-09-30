// automatically generated by the FlatBuffers compiler, do not modify

package io.lionweb.serialization.flatbuffers.gen;

import com.google.flatbuffers.BaseVector;
import com.google.flatbuffers.Constants;
import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@SuppressWarnings("unused")
public final class FBLanguage extends Table {
  public static void ValidateVersion() {
    Constants.FLATBUFFERS_24_3_25();
  }

  public static FBLanguage getRootAsFBLanguage(ByteBuffer _bb) {
    return getRootAsFBLanguage(_bb, new FBLanguage());
  }

  public static FBLanguage getRootAsFBLanguage(ByteBuffer _bb, FBLanguage obj) {
    _bb.order(ByteOrder.LITTLE_ENDIAN);
    return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb));
  }

  public void __init(int _i, ByteBuffer _bb) {
    __reset(_i, _bb);
  }

  public FBLanguage __assign(int _i, ByteBuffer _bb) {
    __init(_i, _bb);
    return this;
  }

  public String key() {
    int o = __offset(4);
    return o != 0 ? __string(o + bb_pos) : null;
  }

  public ByteBuffer keyAsByteBuffer() {
    return __vector_as_bytebuffer(4, 1);
  }

  public ByteBuffer keyInByteBuffer(ByteBuffer _bb) {
    return __vector_in_bytebuffer(_bb, 4, 1);
  }

  public String version() {
    int o = __offset(6);
    return o != 0 ? __string(o + bb_pos) : null;
  }

  public ByteBuffer versionAsByteBuffer() {
    return __vector_as_bytebuffer(6, 1);
  }

  public ByteBuffer versionInByteBuffer(ByteBuffer _bb) {
    return __vector_in_bytebuffer(_bb, 6, 1);
  }

  public static int createFBLanguage(FlatBufferBuilder builder, int keyOffset, int versionOffset) {
    builder.startTable(2);
    FBLanguage.addVersion(builder, versionOffset);
    FBLanguage.addKey(builder, keyOffset);
    return FBLanguage.endFBLanguage(builder);
  }

  public static void startFBLanguage(FlatBufferBuilder builder) {
    builder.startTable(2);
  }

  public static void addKey(FlatBufferBuilder builder, int keyOffset) {
    builder.addOffset(0, keyOffset, 0);
  }

  public static void addVersion(FlatBufferBuilder builder, int versionOffset) {
    builder.addOffset(1, versionOffset, 0);
  }

  public static int endFBLanguage(FlatBufferBuilder builder) {
    int o = builder.endTable();
    return o;
  }

  public static final class Vector extends BaseVector {
    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) {
      __reset(_vector, _element_size, _bb);
      return this;
    }

    public FBLanguage get(int j) {
      return get(new FBLanguage(), j);
    }

    public FBLanguage get(FBLanguage obj, int j) {
      return obj.__assign(__indirect(__element(j), bb), bb);
    }
  }
}
