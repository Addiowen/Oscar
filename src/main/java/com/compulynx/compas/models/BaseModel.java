package com.compulynx.compas.models;

import javax.persistence.*;

/**
 * 
 * @author Cyrus
 *
 */

@MappedSuperclass
public abstract class BaseModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	protected BaseModel() {
		id=null;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id =id;
	}
}
