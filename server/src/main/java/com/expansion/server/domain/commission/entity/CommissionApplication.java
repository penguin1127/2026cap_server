package com.expansion.server.domain.commission.entity;

import com.expansion.server.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "commission_applications",
        uniqueConstraints = @UniqueConstraint(columnNames = {"request_post_id", "artist_id"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class CommissionApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long applicationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_post_id", nullable = false)
    private RequestPost requestPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private User artist;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(name = "proposed_price", precision = 10, scale = 2)
    private BigDecimal proposedPrice;

    @Column(nullable = false, length = 20)
    @Builder.Default
    private String status = "PENDING";  // PENDING / ACCEPTED / REJECTED

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public void accept() {
        this.status = "ACCEPTED";
    }

    public void reject() {
        this.status = "REJECTED";
    }
}
