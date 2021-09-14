package com.doublechain.bank.geolocation;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

public class Geolocation {

  public static final String LONGITUDE_PROPERTY = "longitude";
  public static final String LATITUDE_PROPERTY = "latitude";

  @DecimalMin("-180")
  @DecimalMax("180")
  private BigDecimal longitude;

  @DecimalMin("-90")
  @DecimalMax("90")
  private BigDecimal latitude;

  public BigDecimal getLongitude() {
    return this.longitude;
  }

  public void setLongitude(BigDecimal longitude) {
    this.longitude = longitude;
  }

  public Geolocation updateLongitude(BigDecimal longitude) {
    setLongitude(longitude);
    return this;
  }

  public BigDecimal getLatitude() {
    return this.latitude;
  }

  public void setLatitude(BigDecimal latitude) {
    this.latitude = latitude;
  }

  public Geolocation updateLatitude(BigDecimal latitude) {
    setLatitude(latitude);
    return this;
  }
}
