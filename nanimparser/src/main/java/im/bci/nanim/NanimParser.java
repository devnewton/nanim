/*
 * Copyright (c) 2012 devnewton <devnewton@bci.im>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'devnewton <devnewton@bci.im>' nor the names of
 *   its contributors may be used to endorse or promote products derived
 *   from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package im.bci.nanim;

public final class NanimParser {
  private NanimParser() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public enum PixelFormat
      implements com.google.protobuf.ProtocolMessageEnum {
    RGB(0, 1),
    RGBA(1, 2),
    ;
    
    
    public final int getNumber() { return value; }
    
    public static PixelFormat valueOf(int value) {
      switch (value) {
        case 1: return RGB;
        case 2: return RGBA;
        default: return null;
      }
    }
    
    public static com.google.protobuf.Internal.EnumLiteMap<PixelFormat>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static com.google.protobuf.Internal.EnumLiteMap<PixelFormat>
        internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<PixelFormat>() {
            public PixelFormat findValueByNumber(int number) {
              return PixelFormat.valueOf(number)
    ;        }
          };
    
    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      return getDescriptor().getValues().get(index);
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return im.bci.nanim.NanimParser.getDescriptor().getEnumTypes().get(0);
    }
    
    private static final PixelFormat[] VALUES = {
      RGB, RGBA, 
    };
    public static PixelFormat valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      return VALUES[desc.getIndex()];
    }
    private final int index;
    private final int value;
    private PixelFormat(int index, int value) {
      this.index = index;
      this.value = value;
    }
    
    static {
      im.bci.nanim.NanimParser.getDescriptor();
    }
  }
  
  public static final class Frame extends
      com.google.protobuf.GeneratedMessage.ExtendableMessage<
        Frame> {
    // Use Frame.newBuilder() to construct.
    private Frame() {}
    
    private static final Frame defaultInstance = new Frame();
    public static Frame getDefaultInstance() {
      return defaultInstance;
    }
    
    public Frame getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return im.bci.nanim.NanimParser.internal_static_im_bci_nanim_Frame_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return im.bci.nanim.NanimParser.internal_static_im_bci_nanim_Frame_fieldAccessorTable;
    }
    
    // required string imageName = 1;
    public static final int IMAGENAME_FIELD_NUMBER = 1;
    private boolean hasImageName;
    private java.lang.String imageName_ = "";
    public boolean hasImageName() { return hasImageName; }
    public java.lang.String getImageName() { return imageName_; }
    
    // required int32 duration = 2;
    public static final int DURATION_FIELD_NUMBER = 2;
    private boolean hasDuration;
    private int duration_ = 0;
    public boolean hasDuration() { return hasDuration; }
    public int getDuration() { return duration_; }
    
    // required float u1 = 3;
    public static final int U1_FIELD_NUMBER = 3;
    private boolean hasU1;
    private float u1_ = 0F;
    public boolean hasU1() { return hasU1; }
    public float getU1() { return u1_; }
    
    // required float v1 = 4;
    public static final int V1_FIELD_NUMBER = 4;
    private boolean hasV1;
    private float v1_ = 0F;
    public boolean hasV1() { return hasV1; }
    public float getV1() { return v1_; }
    
    // required float u2 = 5;
    public static final int U2_FIELD_NUMBER = 5;
    private boolean hasU2;
    private float u2_ = 0F;
    public boolean hasU2() { return hasU2; }
    public float getU2() { return u2_; }
    
    // required float v2 = 6;
    public static final int V2_FIELD_NUMBER = 6;
    private boolean hasV2;
    private float v2_ = 0F;
    public boolean hasV2() { return hasV2; }
    public float getV2() { return v2_; }
    
    public final boolean isInitialized() {
      if (!hasImageName) return false;
      if (!hasDuration) return false;
      if (!hasU1) return false;
      if (!hasV1) return false;
      if (!hasU2) return false;
      if (!hasV2) return false;
      if (!extensionsAreInitialized()) return false;
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      com.google.protobuf.GeneratedMessage.ExtendableMessage
        .ExtensionWriter extensionWriter = newExtensionWriter();
      if (hasImageName()) {
        output.writeString(1, getImageName());
      }
      if (hasDuration()) {
        output.writeInt32(2, getDuration());
      }
      if (hasU1()) {
        output.writeFloat(3, getU1());
      }
      if (hasV1()) {
        output.writeFloat(4, getV1());
      }
      if (hasU2()) {
        output.writeFloat(5, getU2());
      }
      if (hasV2()) {
        output.writeFloat(6, getV2());
      }
      extensionWriter.writeUntil(536870912, output);
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (hasImageName()) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(1, getImageName());
      }
      if (hasDuration()) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(2, getDuration());
      }
      if (hasU1()) {
        size += com.google.protobuf.CodedOutputStream
          .computeFloatSize(3, getU1());
      }
      if (hasV1()) {
        size += com.google.protobuf.CodedOutputStream
          .computeFloatSize(4, getV1());
      }
      if (hasU2()) {
        size += com.google.protobuf.CodedOutputStream
          .computeFloatSize(5, getU2());
      }
      if (hasV2()) {
        size += com.google.protobuf.CodedOutputStream
          .computeFloatSize(6, getV2());
      }
      size += extensionsSerializedSize();
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    public static im.bci.nanim.NanimParser.Frame parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static im.bci.nanim.NanimParser.Frame parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static im.bci.nanim.NanimParser.Frame parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static im.bci.nanim.NanimParser.Frame parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static im.bci.nanim.NanimParser.Frame parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static im.bci.nanim.NanimParser.Frame parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static im.bci.nanim.NanimParser.Frame parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeDelimitedFrom(input).buildParsed();
    }
    public static im.bci.nanim.NanimParser.Frame parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeDelimitedFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static im.bci.nanim.NanimParser.Frame parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static im.bci.nanim.NanimParser.Frame parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(im.bci.nanim.NanimParser.Frame prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.ExtendableBuilder<
          im.bci.nanim.NanimParser.Frame, Builder> {
      private im.bci.nanim.NanimParser.Frame result;
      
      // Construct using im.bci.nanim.NanimParser.Frame.newBuilder()
      private Builder() {}
      
      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new im.bci.nanim.NanimParser.Frame();
        return builder;
      }
      
      protected im.bci.nanim.NanimParser.Frame internalGetResult() {
        return result;
      }
      
      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException(
            "Cannot call clear() after build().");
        }
        result = new im.bci.nanim.NanimParser.Frame();
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(result);
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return im.bci.nanim.NanimParser.Frame.getDescriptor();
      }
      
      public im.bci.nanim.NanimParser.Frame getDefaultInstanceForType() {
        return im.bci.nanim.NanimParser.Frame.getDefaultInstance();
      }
      
      public boolean isInitialized() {
        return result.isInitialized();
      }
      public im.bci.nanim.NanimParser.Frame build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }
      
      private im.bci.nanim.NanimParser.Frame buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }
      
      public im.bci.nanim.NanimParser.Frame buildPartial() {
        if (result == null) {
          throw new IllegalStateException(
            "build() has already been called on this Builder.");
        }
        im.bci.nanim.NanimParser.Frame returnMe = result;
        result = null;
        return returnMe;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof im.bci.nanim.NanimParser.Frame) {
          return mergeFrom((im.bci.nanim.NanimParser.Frame)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(im.bci.nanim.NanimParser.Frame other) {
        if (other == im.bci.nanim.NanimParser.Frame.getDefaultInstance()) return this;
        if (other.hasImageName()) {
          setImageName(other.getImageName());
        }
        if (other.hasDuration()) {
          setDuration(other.getDuration());
        }
        if (other.hasU1()) {
          setU1(other.getU1());
        }
        if (other.hasV1()) {
          setV1(other.getV1());
        }
        if (other.hasU2()) {
          setU2(other.getU2());
        }
        if (other.hasV2()) {
          setV2(other.getV2());
        }
        this.mergeExtensionFields(other);
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10: {
              setImageName(input.readString());
              break;
            }
            case 16: {
              setDuration(input.readInt32());
              break;
            }
            case 29: {
              setU1(input.readFloat());
              break;
            }
            case 37: {
              setV1(input.readFloat());
              break;
            }
            case 45: {
              setU2(input.readFloat());
              break;
            }
            case 53: {
              setV2(input.readFloat());
              break;
            }
          }
        }
      }
      
      
      // required string imageName = 1;
      public boolean hasImageName() {
        return result.hasImageName();
      }
      public java.lang.String getImageName() {
        return result.getImageName();
      }
      public Builder setImageName(java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  result.hasImageName = true;
        result.imageName_ = value;
        return this;
      }
      public Builder clearImageName() {
        result.hasImageName = false;
        result.imageName_ = getDefaultInstance().getImageName();
        return this;
      }
      
      // required int32 duration = 2;
      public boolean hasDuration() {
        return result.hasDuration();
      }
      public int getDuration() {
        return result.getDuration();
      }
      public Builder setDuration(int value) {
        result.hasDuration = true;
        result.duration_ = value;
        return this;
      }
      public Builder clearDuration() {
        result.hasDuration = false;
        result.duration_ = 0;
        return this;
      }
      
      // required float u1 = 3;
      public boolean hasU1() {
        return result.hasU1();
      }
      public float getU1() {
        return result.getU1();
      }
      public Builder setU1(float value) {
        result.hasU1 = true;
        result.u1_ = value;
        return this;
      }
      public Builder clearU1() {
        result.hasU1 = false;
        result.u1_ = 0F;
        return this;
      }
      
      // required float v1 = 4;
      public boolean hasV1() {
        return result.hasV1();
      }
      public float getV1() {
        return result.getV1();
      }
      public Builder setV1(float value) {
        result.hasV1 = true;
        result.v1_ = value;
        return this;
      }
      public Builder clearV1() {
        result.hasV1 = false;
        result.v1_ = 0F;
        return this;
      }
      
      // required float u2 = 5;
      public boolean hasU2() {
        return result.hasU2();
      }
      public float getU2() {
        return result.getU2();
      }
      public Builder setU2(float value) {
        result.hasU2 = true;
        result.u2_ = value;
        return this;
      }
      public Builder clearU2() {
        result.hasU2 = false;
        result.u2_ = 0F;
        return this;
      }
      
      // required float v2 = 6;
      public boolean hasV2() {
        return result.hasV2();
      }
      public float getV2() {
        return result.getV2();
      }
      public Builder setV2(float value) {
        result.hasV2 = true;
        result.v2_ = value;
        return this;
      }
      public Builder clearV2() {
        result.hasV2 = false;
        result.v2_ = 0F;
        return this;
      }
    }
    
    static {
      im.bci.nanim.NanimParser.getDescriptor();
    }
    
    static {
      im.bci.nanim.NanimParser.internalForceInit();
    }
  }
  
  public static final class Animation extends
      com.google.protobuf.GeneratedMessage.ExtendableMessage<
        Animation> {
    // Use Animation.newBuilder() to construct.
    private Animation() {}
    
    private static final Animation defaultInstance = new Animation();
    public static Animation getDefaultInstance() {
      return defaultInstance;
    }
    
    public Animation getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return im.bci.nanim.NanimParser.internal_static_im_bci_nanim_Animation_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return im.bci.nanim.NanimParser.internal_static_im_bci_nanim_Animation_fieldAccessorTable;
    }
    
    // required string name = 1;
    public static final int NAME_FIELD_NUMBER = 1;
    private boolean hasName;
    private java.lang.String name_ = "";
    public boolean hasName() { return hasName; }
    public java.lang.String getName() { return name_; }
    
    // repeated .im.bci.nanim.Frame frames = 2;
    public static final int FRAMES_FIELD_NUMBER = 2;
    private java.util.List<im.bci.nanim.NanimParser.Frame> frames_ =
      java.util.Collections.emptyList();
    public java.util.List<im.bci.nanim.NanimParser.Frame> getFramesList() {
      return frames_;
    }
    public int getFramesCount() { return frames_.size(); }
    public im.bci.nanim.NanimParser.Frame getFrames(int index) {
      return frames_.get(index);
    }
    
    public final boolean isInitialized() {
      if (!hasName) return false;
      for (im.bci.nanim.NanimParser.Frame element : getFramesList()) {
        if (!element.isInitialized()) return false;
      }
      if (!extensionsAreInitialized()) return false;
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      com.google.protobuf.GeneratedMessage.ExtendableMessage
        .ExtensionWriter extensionWriter = newExtensionWriter();
      if (hasName()) {
        output.writeString(1, getName());
      }
      for (im.bci.nanim.NanimParser.Frame element : getFramesList()) {
        output.writeMessage(2, element);
      }
      extensionWriter.writeUntil(536870912, output);
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (hasName()) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(1, getName());
      }
      for (im.bci.nanim.NanimParser.Frame element : getFramesList()) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(2, element);
      }
      size += extensionsSerializedSize();
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    public static im.bci.nanim.NanimParser.Animation parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static im.bci.nanim.NanimParser.Animation parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static im.bci.nanim.NanimParser.Animation parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static im.bci.nanim.NanimParser.Animation parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static im.bci.nanim.NanimParser.Animation parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static im.bci.nanim.NanimParser.Animation parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static im.bci.nanim.NanimParser.Animation parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeDelimitedFrom(input).buildParsed();
    }
    public static im.bci.nanim.NanimParser.Animation parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeDelimitedFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static im.bci.nanim.NanimParser.Animation parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static im.bci.nanim.NanimParser.Animation parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(im.bci.nanim.NanimParser.Animation prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.ExtendableBuilder<
          im.bci.nanim.NanimParser.Animation, Builder> {
      private im.bci.nanim.NanimParser.Animation result;
      
      // Construct using im.bci.nanim.NanimParser.Animation.newBuilder()
      private Builder() {}
      
      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new im.bci.nanim.NanimParser.Animation();
        return builder;
      }
      
      protected im.bci.nanim.NanimParser.Animation internalGetResult() {
        return result;
      }
      
      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException(
            "Cannot call clear() after build().");
        }
        result = new im.bci.nanim.NanimParser.Animation();
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(result);
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return im.bci.nanim.NanimParser.Animation.getDescriptor();
      }
      
      public im.bci.nanim.NanimParser.Animation getDefaultInstanceForType() {
        return im.bci.nanim.NanimParser.Animation.getDefaultInstance();
      }
      
      public boolean isInitialized() {
        return result.isInitialized();
      }
      public im.bci.nanim.NanimParser.Animation build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }
      
      private im.bci.nanim.NanimParser.Animation buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }
      
      public im.bci.nanim.NanimParser.Animation buildPartial() {
        if (result == null) {
          throw new IllegalStateException(
            "build() has already been called on this Builder.");
        }
        if (result.frames_ != java.util.Collections.EMPTY_LIST) {
          result.frames_ =
            java.util.Collections.unmodifiableList(result.frames_);
        }
        im.bci.nanim.NanimParser.Animation returnMe = result;
        result = null;
        return returnMe;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof im.bci.nanim.NanimParser.Animation) {
          return mergeFrom((im.bci.nanim.NanimParser.Animation)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(im.bci.nanim.NanimParser.Animation other) {
        if (other == im.bci.nanim.NanimParser.Animation.getDefaultInstance()) return this;
        if (other.hasName()) {
          setName(other.getName());
        }
        if (!other.frames_.isEmpty()) {
          if (result.frames_.isEmpty()) {
            result.frames_ = new java.util.ArrayList<im.bci.nanim.NanimParser.Frame>();
          }
          result.frames_.addAll(other.frames_);
        }
        this.mergeExtensionFields(other);
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10: {
              setName(input.readString());
              break;
            }
            case 18: {
              im.bci.nanim.NanimParser.Frame.Builder subBuilder = im.bci.nanim.NanimParser.Frame.newBuilder();
              input.readMessage(subBuilder, extensionRegistry);
              addFrames(subBuilder.buildPartial());
              break;
            }
          }
        }
      }
      
      
      // required string name = 1;
      public boolean hasName() {
        return result.hasName();
      }
      public java.lang.String getName() {
        return result.getName();
      }
      public Builder setName(java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  result.hasName = true;
        result.name_ = value;
        return this;
      }
      public Builder clearName() {
        result.hasName = false;
        result.name_ = getDefaultInstance().getName();
        return this;
      }
      
      // repeated .im.bci.nanim.Frame frames = 2;
      public java.util.List<im.bci.nanim.NanimParser.Frame> getFramesList() {
        return java.util.Collections.unmodifiableList(result.frames_);
      }
      public int getFramesCount() {
        return result.getFramesCount();
      }
      public im.bci.nanim.NanimParser.Frame getFrames(int index) {
        return result.getFrames(index);
      }
      public Builder setFrames(int index, im.bci.nanim.NanimParser.Frame value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.frames_.set(index, value);
        return this;
      }
      public Builder setFrames(int index, im.bci.nanim.NanimParser.Frame.Builder builderForValue) {
        result.frames_.set(index, builderForValue.build());
        return this;
      }
      public Builder addFrames(im.bci.nanim.NanimParser.Frame value) {
        if (value == null) {
          throw new NullPointerException();
        }
        if (result.frames_.isEmpty()) {
          result.frames_ = new java.util.ArrayList<im.bci.nanim.NanimParser.Frame>();
        }
        result.frames_.add(value);
        return this;
      }
      public Builder addFrames(im.bci.nanim.NanimParser.Frame.Builder builderForValue) {
        if (result.frames_.isEmpty()) {
          result.frames_ = new java.util.ArrayList<im.bci.nanim.NanimParser.Frame>();
        }
        result.frames_.add(builderForValue.build());
        return this;
      }
      public Builder addAllFrames(
          java.lang.Iterable<? extends im.bci.nanim.NanimParser.Frame> values) {
        if (result.frames_.isEmpty()) {
          result.frames_ = new java.util.ArrayList<im.bci.nanim.NanimParser.Frame>();
        }
        super.addAll(values, result.frames_);
        return this;
      }
      public Builder clearFrames() {
        result.frames_ = java.util.Collections.emptyList();
        return this;
      }
    }
    
    static {
      im.bci.nanim.NanimParser.getDescriptor();
    }
    
    static {
      im.bci.nanim.NanimParser.internalForceInit();
    }
  }
  
  public static final class Image extends
      com.google.protobuf.GeneratedMessage.ExtendableMessage<
        Image> {
    // Use Image.newBuilder() to construct.
    private Image() {}
    
    private static final Image defaultInstance = new Image();
    public static Image getDefaultInstance() {
      return defaultInstance;
    }
    
    public Image getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return im.bci.nanim.NanimParser.internal_static_im_bci_nanim_Image_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return im.bci.nanim.NanimParser.internal_static_im_bci_nanim_Image_fieldAccessorTable;
    }
    
    // required string name = 1;
    public static final int NAME_FIELD_NUMBER = 1;
    private boolean hasName;
    private java.lang.String name_ = "";
    public boolean hasName() { return hasName; }
    public java.lang.String getName() { return name_; }
    
    // required int32 width = 2;
    public static final int WIDTH_FIELD_NUMBER = 2;
    private boolean hasWidth;
    private int width_ = 0;
    public boolean hasWidth() { return hasWidth; }
    public int getWidth() { return width_; }
    
    // required int32 height = 3;
    public static final int HEIGHT_FIELD_NUMBER = 3;
    private boolean hasHeight;
    private int height_ = 0;
    public boolean hasHeight() { return hasHeight; }
    public int getHeight() { return height_; }
    
    // required .im.bci.nanim.PixelFormat format = 4;
    public static final int FORMAT_FIELD_NUMBER = 4;
    private boolean hasFormat;
    private im.bci.nanim.NanimParser.PixelFormat format_ = im.bci.nanim.NanimParser.PixelFormat.RGB;
    public boolean hasFormat() { return hasFormat; }
    public im.bci.nanim.NanimParser.PixelFormat getFormat() { return format_; }
    
    // required bytes pixels = 5;
    public static final int PIXELS_FIELD_NUMBER = 5;
    private boolean hasPixels;
    private com.google.protobuf.ByteString pixels_ = com.google.protobuf.ByteString.EMPTY;
    public boolean hasPixels() { return hasPixels; }
    public com.google.protobuf.ByteString getPixels() { return pixels_; }
    
    public final boolean isInitialized() {
      if (!hasName) return false;
      if (!hasWidth) return false;
      if (!hasHeight) return false;
      if (!hasFormat) return false;
      if (!hasPixels) return false;
      if (!extensionsAreInitialized()) return false;
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      com.google.protobuf.GeneratedMessage.ExtendableMessage
        .ExtensionWriter extensionWriter = newExtensionWriter();
      if (hasName()) {
        output.writeString(1, getName());
      }
      if (hasWidth()) {
        output.writeInt32(2, getWidth());
      }
      if (hasHeight()) {
        output.writeInt32(3, getHeight());
      }
      if (hasFormat()) {
        output.writeEnum(4, getFormat().getNumber());
      }
      if (hasPixels()) {
        output.writeBytes(5, getPixels());
      }
      extensionWriter.writeUntil(536870912, output);
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (hasName()) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(1, getName());
      }
      if (hasWidth()) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(2, getWidth());
      }
      if (hasHeight()) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(3, getHeight());
      }
      if (hasFormat()) {
        size += com.google.protobuf.CodedOutputStream
          .computeEnumSize(4, getFormat().getNumber());
      }
      if (hasPixels()) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(5, getPixels());
      }
      size += extensionsSerializedSize();
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    public static im.bci.nanim.NanimParser.Image parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static im.bci.nanim.NanimParser.Image parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static im.bci.nanim.NanimParser.Image parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static im.bci.nanim.NanimParser.Image parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static im.bci.nanim.NanimParser.Image parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static im.bci.nanim.NanimParser.Image parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static im.bci.nanim.NanimParser.Image parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeDelimitedFrom(input).buildParsed();
    }
    public static im.bci.nanim.NanimParser.Image parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeDelimitedFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static im.bci.nanim.NanimParser.Image parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static im.bci.nanim.NanimParser.Image parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(im.bci.nanim.NanimParser.Image prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.ExtendableBuilder<
          im.bci.nanim.NanimParser.Image, Builder> {
      private im.bci.nanim.NanimParser.Image result;
      
      // Construct using im.bci.nanim.NanimParser.Image.newBuilder()
      private Builder() {}
      
      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new im.bci.nanim.NanimParser.Image();
        return builder;
      }
      
      protected im.bci.nanim.NanimParser.Image internalGetResult() {
        return result;
      }
      
      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException(
            "Cannot call clear() after build().");
        }
        result = new im.bci.nanim.NanimParser.Image();
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(result);
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return im.bci.nanim.NanimParser.Image.getDescriptor();
      }
      
      public im.bci.nanim.NanimParser.Image getDefaultInstanceForType() {
        return im.bci.nanim.NanimParser.Image.getDefaultInstance();
      }
      
      public boolean isInitialized() {
        return result.isInitialized();
      }
      public im.bci.nanim.NanimParser.Image build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }
      
      private im.bci.nanim.NanimParser.Image buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }
      
      public im.bci.nanim.NanimParser.Image buildPartial() {
        if (result == null) {
          throw new IllegalStateException(
            "build() has already been called on this Builder.");
        }
        im.bci.nanim.NanimParser.Image returnMe = result;
        result = null;
        return returnMe;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof im.bci.nanim.NanimParser.Image) {
          return mergeFrom((im.bci.nanim.NanimParser.Image)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(im.bci.nanim.NanimParser.Image other) {
        if (other == im.bci.nanim.NanimParser.Image.getDefaultInstance()) return this;
        if (other.hasName()) {
          setName(other.getName());
        }
        if (other.hasWidth()) {
          setWidth(other.getWidth());
        }
        if (other.hasHeight()) {
          setHeight(other.getHeight());
        }
        if (other.hasFormat()) {
          setFormat(other.getFormat());
        }
        if (other.hasPixels()) {
          setPixels(other.getPixels());
        }
        this.mergeExtensionFields(other);
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10: {
              setName(input.readString());
              break;
            }
            case 16: {
              setWidth(input.readInt32());
              break;
            }
            case 24: {
              setHeight(input.readInt32());
              break;
            }
            case 32: {
              int rawValue = input.readEnum();
              im.bci.nanim.NanimParser.PixelFormat value = im.bci.nanim.NanimParser.PixelFormat.valueOf(rawValue);
              if (value == null) {
                unknownFields.mergeVarintField(4, rawValue);
              } else {
                setFormat(value);
              }
              break;
            }
            case 42: {
              setPixels(input.readBytes());
              break;
            }
          }
        }
      }
      
      
      // required string name = 1;
      public boolean hasName() {
        return result.hasName();
      }
      public java.lang.String getName() {
        return result.getName();
      }
      public Builder setName(java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  result.hasName = true;
        result.name_ = value;
        return this;
      }
      public Builder clearName() {
        result.hasName = false;
        result.name_ = getDefaultInstance().getName();
        return this;
      }
      
      // required int32 width = 2;
      public boolean hasWidth() {
        return result.hasWidth();
      }
      public int getWidth() {
        return result.getWidth();
      }
      public Builder setWidth(int value) {
        result.hasWidth = true;
        result.width_ = value;
        return this;
      }
      public Builder clearWidth() {
        result.hasWidth = false;
        result.width_ = 0;
        return this;
      }
      
      // required int32 height = 3;
      public boolean hasHeight() {
        return result.hasHeight();
      }
      public int getHeight() {
        return result.getHeight();
      }
      public Builder setHeight(int value) {
        result.hasHeight = true;
        result.height_ = value;
        return this;
      }
      public Builder clearHeight() {
        result.hasHeight = false;
        result.height_ = 0;
        return this;
      }
      
      // required .im.bci.nanim.PixelFormat format = 4;
      public boolean hasFormat() {
        return result.hasFormat();
      }
      public im.bci.nanim.NanimParser.PixelFormat getFormat() {
        return result.getFormat();
      }
      public Builder setFormat(im.bci.nanim.NanimParser.PixelFormat value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasFormat = true;
        result.format_ = value;
        return this;
      }
      public Builder clearFormat() {
        result.hasFormat = false;
        result.format_ = im.bci.nanim.NanimParser.PixelFormat.RGB;
        return this;
      }
      
      // required bytes pixels = 5;
      public boolean hasPixels() {
        return result.hasPixels();
      }
      public com.google.protobuf.ByteString getPixels() {
        return result.getPixels();
      }
      public Builder setPixels(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  result.hasPixels = true;
        result.pixels_ = value;
        return this;
      }
      public Builder clearPixels() {
        result.hasPixels = false;
        result.pixels_ = getDefaultInstance().getPixels();
        return this;
      }
    }
    
    static {
      im.bci.nanim.NanimParser.getDescriptor();
    }
    
    static {
      im.bci.nanim.NanimParser.internalForceInit();
    }
  }
  
  public static final class Nanim extends
      com.google.protobuf.GeneratedMessage.ExtendableMessage<
        Nanim> {
    // Use Nanim.newBuilder() to construct.
    private Nanim() {}
    
    private static final Nanim defaultInstance = new Nanim();
    public static Nanim getDefaultInstance() {
      return defaultInstance;
    }
    
    public Nanim getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return im.bci.nanim.NanimParser.internal_static_im_bci_nanim_Nanim_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return im.bci.nanim.NanimParser.internal_static_im_bci_nanim_Nanim_fieldAccessorTable;
    }
    
    // repeated .im.bci.nanim.Image images = 1;
    public static final int IMAGES_FIELD_NUMBER = 1;
    private java.util.List<im.bci.nanim.NanimParser.Image> images_ =
      java.util.Collections.emptyList();
    public java.util.List<im.bci.nanim.NanimParser.Image> getImagesList() {
      return images_;
    }
    public int getImagesCount() { return images_.size(); }
    public im.bci.nanim.NanimParser.Image getImages(int index) {
      return images_.get(index);
    }
    
    // repeated .im.bci.nanim.Animation animations = 2;
    public static final int ANIMATIONS_FIELD_NUMBER = 2;
    private java.util.List<im.bci.nanim.NanimParser.Animation> animations_ =
      java.util.Collections.emptyList();
    public java.util.List<im.bci.nanim.NanimParser.Animation> getAnimationsList() {
      return animations_;
    }
    public int getAnimationsCount() { return animations_.size(); }
    public im.bci.nanim.NanimParser.Animation getAnimations(int index) {
      return animations_.get(index);
    }
    
    // optional string author = 3;
    public static final int AUTHOR_FIELD_NUMBER = 3;
    private boolean hasAuthor;
    private java.lang.String author_ = "";
    public boolean hasAuthor() { return hasAuthor; }
    public java.lang.String getAuthor() { return author_; }
    
    // optional string license = 4;
    public static final int LICENSE_FIELD_NUMBER = 4;
    private boolean hasLicense;
    private java.lang.String license_ = "";
    public boolean hasLicense() { return hasLicense; }
    public java.lang.String getLicense() { return license_; }
    
    public final boolean isInitialized() {
      for (im.bci.nanim.NanimParser.Image element : getImagesList()) {
        if (!element.isInitialized()) return false;
      }
      for (im.bci.nanim.NanimParser.Animation element : getAnimationsList()) {
        if (!element.isInitialized()) return false;
      }
      if (!extensionsAreInitialized()) return false;
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      com.google.protobuf.GeneratedMessage.ExtendableMessage
        .ExtensionWriter extensionWriter = newExtensionWriter();
      for (im.bci.nanim.NanimParser.Image element : getImagesList()) {
        output.writeMessage(1, element);
      }
      for (im.bci.nanim.NanimParser.Animation element : getAnimationsList()) {
        output.writeMessage(2, element);
      }
      if (hasAuthor()) {
        output.writeString(3, getAuthor());
      }
      if (hasLicense()) {
        output.writeString(4, getLicense());
      }
      extensionWriter.writeUntil(536870912, output);
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      for (im.bci.nanim.NanimParser.Image element : getImagesList()) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(1, element);
      }
      for (im.bci.nanim.NanimParser.Animation element : getAnimationsList()) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(2, element);
      }
      if (hasAuthor()) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(3, getAuthor());
      }
      if (hasLicense()) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(4, getLicense());
      }
      size += extensionsSerializedSize();
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    public static im.bci.nanim.NanimParser.Nanim parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static im.bci.nanim.NanimParser.Nanim parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static im.bci.nanim.NanimParser.Nanim parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static im.bci.nanim.NanimParser.Nanim parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static im.bci.nanim.NanimParser.Nanim parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static im.bci.nanim.NanimParser.Nanim parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static im.bci.nanim.NanimParser.Nanim parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeDelimitedFrom(input).buildParsed();
    }
    public static im.bci.nanim.NanimParser.Nanim parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeDelimitedFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static im.bci.nanim.NanimParser.Nanim parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static im.bci.nanim.NanimParser.Nanim parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(im.bci.nanim.NanimParser.Nanim prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.ExtendableBuilder<
          im.bci.nanim.NanimParser.Nanim, Builder> {
      private im.bci.nanim.NanimParser.Nanim result;
      
      // Construct using im.bci.nanim.NanimParser.Nanim.newBuilder()
      private Builder() {}
      
      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new im.bci.nanim.NanimParser.Nanim();
        return builder;
      }
      
      protected im.bci.nanim.NanimParser.Nanim internalGetResult() {
        return result;
      }
      
      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException(
            "Cannot call clear() after build().");
        }
        result = new im.bci.nanim.NanimParser.Nanim();
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(result);
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return im.bci.nanim.NanimParser.Nanim.getDescriptor();
      }
      
      public im.bci.nanim.NanimParser.Nanim getDefaultInstanceForType() {
        return im.bci.nanim.NanimParser.Nanim.getDefaultInstance();
      }
      
      public boolean isInitialized() {
        return result.isInitialized();
      }
      public im.bci.nanim.NanimParser.Nanim build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }
      
      private im.bci.nanim.NanimParser.Nanim buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }
      
      public im.bci.nanim.NanimParser.Nanim buildPartial() {
        if (result == null) {
          throw new IllegalStateException(
            "build() has already been called on this Builder.");
        }
        if (result.images_ != java.util.Collections.EMPTY_LIST) {
          result.images_ =
            java.util.Collections.unmodifiableList(result.images_);
        }
        if (result.animations_ != java.util.Collections.EMPTY_LIST) {
          result.animations_ =
            java.util.Collections.unmodifiableList(result.animations_);
        }
        im.bci.nanim.NanimParser.Nanim returnMe = result;
        result = null;
        return returnMe;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof im.bci.nanim.NanimParser.Nanim) {
          return mergeFrom((im.bci.nanim.NanimParser.Nanim)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(im.bci.nanim.NanimParser.Nanim other) {
        if (other == im.bci.nanim.NanimParser.Nanim.getDefaultInstance()) return this;
        if (!other.images_.isEmpty()) {
          if (result.images_.isEmpty()) {
            result.images_ = new java.util.ArrayList<im.bci.nanim.NanimParser.Image>();
          }
          result.images_.addAll(other.images_);
        }
        if (!other.animations_.isEmpty()) {
          if (result.animations_.isEmpty()) {
            result.animations_ = new java.util.ArrayList<im.bci.nanim.NanimParser.Animation>();
          }
          result.animations_.addAll(other.animations_);
        }
        if (other.hasAuthor()) {
          setAuthor(other.getAuthor());
        }
        if (other.hasLicense()) {
          setLicense(other.getLicense());
        }
        this.mergeExtensionFields(other);
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10: {
              im.bci.nanim.NanimParser.Image.Builder subBuilder = im.bci.nanim.NanimParser.Image.newBuilder();
              input.readMessage(subBuilder, extensionRegistry);
              addImages(subBuilder.buildPartial());
              break;
            }
            case 18: {
              im.bci.nanim.NanimParser.Animation.Builder subBuilder = im.bci.nanim.NanimParser.Animation.newBuilder();
              input.readMessage(subBuilder, extensionRegistry);
              addAnimations(subBuilder.buildPartial());
              break;
            }
            case 26: {
              setAuthor(input.readString());
              break;
            }
            case 34: {
              setLicense(input.readString());
              break;
            }
          }
        }
      }
      
      
      // repeated .im.bci.nanim.Image images = 1;
      public java.util.List<im.bci.nanim.NanimParser.Image> getImagesList() {
        return java.util.Collections.unmodifiableList(result.images_);
      }
      public int getImagesCount() {
        return result.getImagesCount();
      }
      public im.bci.nanim.NanimParser.Image getImages(int index) {
        return result.getImages(index);
      }
      public Builder setImages(int index, im.bci.nanim.NanimParser.Image value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.images_.set(index, value);
        return this;
      }
      public Builder setImages(int index, im.bci.nanim.NanimParser.Image.Builder builderForValue) {
        result.images_.set(index, builderForValue.build());
        return this;
      }
      public Builder addImages(im.bci.nanim.NanimParser.Image value) {
        if (value == null) {
          throw new NullPointerException();
        }
        if (result.images_.isEmpty()) {
          result.images_ = new java.util.ArrayList<im.bci.nanim.NanimParser.Image>();
        }
        result.images_.add(value);
        return this;
      }
      public Builder addImages(im.bci.nanim.NanimParser.Image.Builder builderForValue) {
        if (result.images_.isEmpty()) {
          result.images_ = new java.util.ArrayList<im.bci.nanim.NanimParser.Image>();
        }
        result.images_.add(builderForValue.build());
        return this;
      }
      public Builder addAllImages(
          java.lang.Iterable<? extends im.bci.nanim.NanimParser.Image> values) {
        if (result.images_.isEmpty()) {
          result.images_ = new java.util.ArrayList<im.bci.nanim.NanimParser.Image>();
        }
        super.addAll(values, result.images_);
        return this;
      }
      public Builder clearImages() {
        result.images_ = java.util.Collections.emptyList();
        return this;
      }
      
      // repeated .im.bci.nanim.Animation animations = 2;
      public java.util.List<im.bci.nanim.NanimParser.Animation> getAnimationsList() {
        return java.util.Collections.unmodifiableList(result.animations_);
      }
      public int getAnimationsCount() {
        return result.getAnimationsCount();
      }
      public im.bci.nanim.NanimParser.Animation getAnimations(int index) {
        return result.getAnimations(index);
      }
      public Builder setAnimations(int index, im.bci.nanim.NanimParser.Animation value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.animations_.set(index, value);
        return this;
      }
      public Builder setAnimations(int index, im.bci.nanim.NanimParser.Animation.Builder builderForValue) {
        result.animations_.set(index, builderForValue.build());
        return this;
      }
      public Builder addAnimations(im.bci.nanim.NanimParser.Animation value) {
        if (value == null) {
          throw new NullPointerException();
        }
        if (result.animations_.isEmpty()) {
          result.animations_ = new java.util.ArrayList<im.bci.nanim.NanimParser.Animation>();
        }
        result.animations_.add(value);
        return this;
      }
      public Builder addAnimations(im.bci.nanim.NanimParser.Animation.Builder builderForValue) {
        if (result.animations_.isEmpty()) {
          result.animations_ = new java.util.ArrayList<im.bci.nanim.NanimParser.Animation>();
        }
        result.animations_.add(builderForValue.build());
        return this;
      }
      public Builder addAllAnimations(
          java.lang.Iterable<? extends im.bci.nanim.NanimParser.Animation> values) {
        if (result.animations_.isEmpty()) {
          result.animations_ = new java.util.ArrayList<im.bci.nanim.NanimParser.Animation>();
        }
        super.addAll(values, result.animations_);
        return this;
      }
      public Builder clearAnimations() {
        result.animations_ = java.util.Collections.emptyList();
        return this;
      }
      
      // optional string author = 3;
      public boolean hasAuthor() {
        return result.hasAuthor();
      }
      public java.lang.String getAuthor() {
        return result.getAuthor();
      }
      public Builder setAuthor(java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  result.hasAuthor = true;
        result.author_ = value;
        return this;
      }
      public Builder clearAuthor() {
        result.hasAuthor = false;
        result.author_ = getDefaultInstance().getAuthor();
        return this;
      }
      
      // optional string license = 4;
      public boolean hasLicense() {
        return result.hasLicense();
      }
      public java.lang.String getLicense() {
        return result.getLicense();
      }
      public Builder setLicense(java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  result.hasLicense = true;
        result.license_ = value;
        return this;
      }
      public Builder clearLicense() {
        result.hasLicense = false;
        result.license_ = getDefaultInstance().getLicense();
        return this;
      }
    }
    
    static {
      im.bci.nanim.NanimParser.getDescriptor();
    }
    
    static {
      im.bci.nanim.NanimParser.internalForceInit();
    }
  }
  
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_im_bci_nanim_Frame_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_im_bci_nanim_Frame_fieldAccessorTable;
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_im_bci_nanim_Animation_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_im_bci_nanim_Animation_fieldAccessorTable;
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_im_bci_nanim_Image_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_im_bci_nanim_Image_fieldAccessorTable;
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_im_bci_nanim_Nanim_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_im_bci_nanim_Nanim_fieldAccessorTable;
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\013nanim.proto\022\014im.bci.nanim\"g\n\005Frame\022\021\n\t" +
      "imageName\030\001 \002(\t\022\020\n\010duration\030\002 \002(\005\022\n\n\002u1\030" +
      "\003 \002(\002\022\n\n\002v1\030\004 \002(\002\022\n\n\002u2\030\005 \002(\002\022\n\n\002v2\030\006 \002(" +
      "\002*\t\010\350\007\020\200\200\200\200\002\"I\n\tAnimation\022\014\n\004name\030\001 \002(\t\022" +
      "#\n\006frames\030\002 \003(\0132\023.im.bci.nanim.Frame*\t\010\350" +
      "\007\020\200\200\200\200\002\"z\n\005Image\022\014\n\004name\030\001 \002(\t\022\r\n\005width\030" +
      "\002 \002(\005\022\016\n\006height\030\003 \002(\005\022)\n\006format\030\004 \002(\0162\031." +
      "im.bci.nanim.PixelFormat\022\016\n\006pixels\030\005 \002(\014" +
      "*\t\010\350\007\020\200\200\200\200\002\"\205\001\n\005Nanim\022#\n\006images\030\001 \003(\0132\023." +
      "im.bci.nanim.Image\022+\n\nanimations\030\002 \003(\0132\027",
      ".im.bci.nanim.Animation\022\016\n\006author\030\003 \001(\t\022" +
      "\017\n\007license\030\004 \001(\t*\t\010\350\007\020\200\200\200\200\002* \n\013PixelForm" +
      "at\022\007\n\003RGB\020\001\022\010\n\004RGBA\020\002B\rB\013NanimParser"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_im_bci_nanim_Frame_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_im_bci_nanim_Frame_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_im_bci_nanim_Frame_descriptor,
              new java.lang.String[] { "ImageName", "Duration", "U1", "V1", "U2", "V2", },
              im.bci.nanim.NanimParser.Frame.class,
              im.bci.nanim.NanimParser.Frame.Builder.class);
          internal_static_im_bci_nanim_Animation_descriptor =
            getDescriptor().getMessageTypes().get(1);
          internal_static_im_bci_nanim_Animation_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_im_bci_nanim_Animation_descriptor,
              new java.lang.String[] { "Name", "Frames", },
              im.bci.nanim.NanimParser.Animation.class,
              im.bci.nanim.NanimParser.Animation.Builder.class);
          internal_static_im_bci_nanim_Image_descriptor =
            getDescriptor().getMessageTypes().get(2);
          internal_static_im_bci_nanim_Image_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_im_bci_nanim_Image_descriptor,
              new java.lang.String[] { "Name", "Width", "Height", "Format", "Pixels", },
              im.bci.nanim.NanimParser.Image.class,
              im.bci.nanim.NanimParser.Image.Builder.class);
          internal_static_im_bci_nanim_Nanim_descriptor =
            getDescriptor().getMessageTypes().get(3);
          internal_static_im_bci_nanim_Nanim_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_im_bci_nanim_Nanim_descriptor,
              new java.lang.String[] { "Images", "Animations", "Author", "License", },
              im.bci.nanim.NanimParser.Nanim.class,
              im.bci.nanim.NanimParser.Nanim.Builder.class);
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }
  
  public static void internalForceInit() {}
}
