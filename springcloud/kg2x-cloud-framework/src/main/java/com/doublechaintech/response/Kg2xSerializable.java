package com.doublechaintech.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public interface Kg2xSerializable extends Serializable {

  @JsonProperty("@class")
  default String getClazzName() {
    return this.getClass().getName();
  }
}
