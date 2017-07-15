package com.brewtooth.server.domain;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "malt")
public class Malt {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = IDENTITY)
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
