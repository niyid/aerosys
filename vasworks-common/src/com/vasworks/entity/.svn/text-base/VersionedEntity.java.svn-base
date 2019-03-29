package com.vasworks.entity;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * VersionedEntity extends the {@link MySqlBaseEntity} class by adding "version" field. The field is annotated with @Version, ensuring Hibernate uses proper
 * data record versioning and reporting conflicts.
 * 
 * @author developer
 */
@MappedSuperclass
public class VersionedEntity extends MySqlBaseEntity {

	private static final long serialVersionUID = -3683125211329823418L;
	private int version = 0;

	/**
	 * Get record version
	 * @return record version
	 */
	@Version
	public int getVersion() {
		return version;
	}

	/** 
	 * Set version
	 * @param version
	 */
	public void setVersion(int version) {
		this.version = version;
	}
}
