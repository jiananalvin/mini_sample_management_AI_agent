package com.miniproject.slims.sample;

import jakarta.persistence.*;
import java.time.Instant;

// Those are JPA (Jakarta Persistence API) annotations. JPA is the contract for ORM in Java.
// Hibernate is an Object-Relational Mapping (ORM) tool for Java applications.
// It maps Java classes to database tables and Java data types to SQL data types, automating database interactions.

//  Index a column if it is used frequently in queries. Indexes make reads faster (avoids full table scans), at the cost of: a bit more storage & slightly slower writes (INSERT/UPDATE)
@Entity
@Table(name = "samples", indexes = {
        @Index(name = "idx_samples_status", columnList = "status"),
        @Index(name = "idx_samples_type", columnList = "type")
})
public class Sample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="sample_code", nullable = false, unique = true, length = 64)
    private String sampleCode;

    @Column(nullable = false, length = 64)  // 64 chars
    private String type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private SampleStatus status;  

    @Column(nullable = false)
    private Instant collectedAt;  // The exact timestamp on the global timeline, 2026-01-11T16:42:30.123Z

    @Column(length = 500)
    private String comment;

    protected Sample() {}

    public Sample(String sampleCode, String type, SampleStatus status, Instant collectedAt, String comment) {
        this.sampleCode = sampleCode;
        this.type = type;
        this.status = status;
        this.collectedAt = collectedAt;
        this.comment = comment;
    }

    public Long getId() { return id; }
    public String getSampleCode() { return sampleCode; }
    public String getType() { return type; }
    public SampleStatus getStatus() { return status; }
    public Instant getCollectedAt() { return collectedAt; }
    public String getComment() { return comment; }

    public void setType(String type) { this.type = type; }
    public void setStatus(SampleStatus status) { this.status = status; }
    public void setCollectedAt(Instant collectedAt) { this.collectedAt = collectedAt; }
    public void setComment(String comment) { this.comment = comment; }
}
