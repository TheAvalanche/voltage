package org.kartishev.voltage.domain;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.function.Function;
import java.util.function.Predicate;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
	private Long id;

	@Version
	@Column(name = "version", nullable = false)
	private Long version;

	@Column(name = "created", nullable = false)
	private ZonedDateTime created = ZonedDateTime.now();

	@Column(name = "updated", nullable = false)
	private ZonedDateTime updated = ZonedDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @PreUpdate
    @PrePersist
    public void prePersist() {
        this.setUpdated(ZonedDateTime.now());
    }

    @SuppressWarnings("unchecked")
	public <T, F> T apply(Function<F, T> function) {
		return function.apply((F) this);
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> boolean satisfies(Predicate<T> specification) {
		return specification.test((T) this);
	}


}


