package com.expansion.server.domain.asset.entity;

import com.expansion.server.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "asset_purchases",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "asset_id"})
)
@Getter
@NoArgsConstructor
public class AssetPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Long purchaseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "price_paid", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePaid;

    @Column(nullable = false, length = 20)
    private String status;
    // ACTIVE / REFUNDED

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public AssetPurchase(User user, Asset asset, Long paymentId, BigDecimal pricePaid) {
        this.user = user;
        this.asset = asset;
        this.paymentId = paymentId;
        this.pricePaid = pricePaid != null ? pricePaid : BigDecimal.ZERO;
        this.status = "ACTIVE";
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.status == null) this.status = "ACTIVE";
        if (this.pricePaid == null) this.pricePaid = BigDecimal.ZERO;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void refund() {
        this.status = "REFUNDED";
    }
}
