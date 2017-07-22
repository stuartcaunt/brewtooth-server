package com.brewtooth.server.domain;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "hop")
public class Hop {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, length = 1000)
	private String name;

	@Column(name = "alpha_acid", nullable = false)
	private float alphaAcid;

	@Column(name = "is_pellet", nullable = false)
	private boolean isPellet;

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

	public float getAlphaAcid() {
		return alphaAcid;
	}

	public void setAlphaAcid(float alphaAcid) {
		this.alphaAcid = alphaAcid;
	}

	public boolean isPellet() {
		return isPellet;
	}

	public void setPellet(boolean pellet) {
		isPellet = pellet;
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
