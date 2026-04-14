package com.expansion.server.domain.commission.entity;

import com.expansion.server.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "request_posts")
@Getter
@NoArgsConstructor
public class RequestPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_post_id")
    private Long requestPostId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "budget_min", precision = 10, scale = 2)
    private BigDecimal budgetMin;

    @Column(name = "budget_max", precision = 10, scale = 2)
    private BigDecimal budgetMax;

    @Column
    private LocalDate deadline;

    @Column(nullable = false, length = 20)
    private String status;
    // OPEN / CLOSED

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public RequestPost(User client, String title, String description,
                       BigDecimal budgetMin, BigDecimal budgetMax, LocalDate deadline) {
        this.client = client;
        this.title = title;
        this.description = description;
        this.budgetMin = budgetMin;
        this.budgetMax = budgetMax;
        this.deadline = deadline;
        this.status = "OPEN";
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.status == null) this.status = "OPEN";
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void update(String title, String description,
                       BigDecimal budgetMin, BigDecimal budgetMax, LocalDate deadline) {
        if (title != null) this.title = title;
        if (description != null) this.description = description;
        if (budgetMin != null) this.budgetMin = budgetMin;
        if (budgetMax != null) this.budgetMax = budgetMax;
        if (deadline != null) this.deadline = deadline;
    }

    public void close() {
        this.status = "CLOSED";
    }
}
