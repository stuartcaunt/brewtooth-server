package com.brewtooth.server.domain;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "malt")
public class Malt {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, length = 1000)
	private String name;

	@Column(name = "grain", nullable = false, length = 100)
	@Enumerated(EnumType.STRING)
	private GrainType grain;

	@Column(name = "yield", nullable = false)
	private float yield;

	@Column(name = "ebc", nullable = false)
	private float EBC;

	@Column(name = "description", length = 10000)
	private String description;

	@Column(name = "url", length = 4096)
	private String url;

	public Malt() {
	}

	public Malt(String name, GrainType grain, float yield, float EBC) {
		this.name = name;
		this.grain = grain;
		this.yield = yield;
		this.EBC = EBC;
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

	public float getEBC() {
		return EBC;
	}

	public void setEBC(float EBC) {
		this.EBC = EBC;
	}

	public float getYield() {
		return yield;
	}

	public void setYield(float yield) {
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
}
