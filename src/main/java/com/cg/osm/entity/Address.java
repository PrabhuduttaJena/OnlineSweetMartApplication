package com.cg.osm.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public class Address {
	@NotNull(message = "City name cannot be null")
	private String city;
	@NotNull(message = "Contact Number cannot be null")
	@Size(min = 10, max = 15)
	private String contactNo;
	@Length(min = 6, max = 6)
	private String zip;

	public Address() {
	}

	public Address(@NotNull(message = "City name cannot be null") String city,
			@NotNull(message = "Contact Number cannot be null") @Size(min = 10, max = 15) String contactNo,
			@Length(min = 6, max = 6) String zip) {
		this.city = city;
		this.contactNo = contactNo;
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getcontactNo() {
		return contactNo;
	}

	public void setcontactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getzip() {
		return zip;
	}

	public void setzip(String zip) {
		this.zip = zip;
	}

	@Override
	public String toString() {
		return "Address [city=" + city + ", contactNo=" + contactNo + ", zip=" + zip + "]";
	}

}// closing class