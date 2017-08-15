package com.brewtooth.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "yeast")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Yeast implements Ingredient {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(name = "manufacturer", nullable = false, length = 1000)
	private String manufacturer;

	@Column(name = "name", nullable = false, length = 1000)
	private String name;

	@Column(name = "attenuation", nullable = false, precision = 8, scale = 2)
	private Double attenuation;

	@Column(name = "description", length = 10000)
	private String description;

	@Column(name = "url", length = 4096)
	private String url;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Double getAttenuation() {
		return attenuation;
	}

	public void setAttenuation(Double attenuation) {
		this.attenuation = attenuation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public Map<String, Object> getDetails() {
		Map<String, Object> details = new LinkedHashMap<>();
		details.put("name", this.name);
		details.put("manufacturer", this.manufacturer);

		return details;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		Yeast yeast = (Yeast) o;

		return new EqualsBuilder().append(name, yeast.name).append(attenuation, yeast.attenuation).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(name).append(attenuation).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("name", name).append("attenuation", attenuation).toString();
	}
}
