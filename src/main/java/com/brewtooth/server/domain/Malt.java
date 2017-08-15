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
@Table(name = "malt")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Malt implements Ingredient {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, length = 1000)
	private String name;

	@Column(name = "grain", nullable = false, length = 100)
	@Enumerated(EnumType.STRING)
	private GrainType grain;

	@Column(name = "yield", nullable = false, precision = 8, scale = 2)
	private Double yield;

	@Column(name = "ebc", nullable = false, precision = 8, scale = 2)
	private Double ebc;

	@Column(name = "description", length = 10000)
	private String description;

	@Column(name = "url", length = 4096)
	private String url;

	public Malt() {
	}

	public Malt(String name, GrainType grain, Double yield, Double ebc) {
		this.name = name;
		this.grain = grain;
		this.yield = yield;
		this.ebc = ebc;
	}

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

	public Double getEbc() {
		return ebc;
	}

	public void setEbc(Double ebc) {
		this.ebc = ebc;
	}

	public Double getYield() {
		return yield;
	}

	public void setYield(Double yield) {
		this.yield = yield;
	}

	public GrainType getGrain() {
		return grain;
	}

	public void setGrain(GrainType grain) {
		this.grain = grain;
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
		details.put("grain", this.grain);
		details.put("yield", this.yield);

		return details;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		Malt malt = (Malt) o;

		return new EqualsBuilder().append(name, malt.name).append(grain, malt.grain).append(yield, malt.yield).append(ebc, malt.ebc).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(name).append(grain).append(yield).append(ebc).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("name", name).append("grain", grain).append("yield", yield).append("ebc", ebc).toString();
	}
}
