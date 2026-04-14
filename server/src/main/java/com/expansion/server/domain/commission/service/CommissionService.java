package com.expansion.server.domain.commission.service;

import com.expansion.server.domain.commission.dto.*;
import com.expansion.server.domain.commission.entity.Commission;
import com.expansion.server.domain.commission.entity.CommissionFile;
import com.expansion.server.domain.commission.repository.CommissionFileRepository;
import com.expansion.server.domain.commission.repository.CommissionRepository;
import com.expansion.server.domain.user.entity.Profile;
import com.expansion.server.domain.user.entity.User;
import com.expansion.server.domain.user.repository.ProfileRepository;
import com.expansion.server.domain.user.repository.UserRepository;
import com.expansion.server.global.exception.CustomException;
import com.expansion.server.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommissionService {

    private final CommissionRepository commissionRepository;
    private final CommissionFileRepository commissionFileRepository;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    @Transactional
    public CommissionResponse createCommission(Long clientId, CommissionCreateRequest request) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        User artist = userRepository.findById(request.getArtistId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Commission commission = Commission.builder()
                .commissionType(request.getCommissionType())
                .client(client)
                .artist(artist)
                .serviceId(request.getServiceId())
                .requestPostId(request.getRequestPostId())
                .applicationId(request.getApplicationId())
                .agreedPrice(request.getAgreedPrice())
                .agreedDeadline(request.getAgreedDeadline())
                .status("IN_PROGRESS")
                .build();

        commissionRepository.save(commission);

        Profile clientProfile = profileRepository.findByUser_UserId(clientId).orElse(null);
        Profile artistProfile = profileRepository.findByUser_UserId(artist.getUserId()).orElse(null);

        return CommissionResponse.of(commission, clientProfile, artistProfile);
    }

    public CommissionResponse getCommission(Long commissionId, Long currentUserId) {
        Commission commission = commissionRepository.findById(commissionId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMISSION_NOT_FOUND));

        boolean isClient = commission.getClient().getUserId().equals(currentUserId);
        boolean isArtist = commission.getArtist().getUserId().equals(currentUserId);

        if (!isClient && !isArtist) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        Profile clientProfile = profileRepository.findByUser_UserId(commission.getClient().getUserId()).orElse(null);
        Profile artistProfile = profileRepository.findByUser_UserId(commission.getArtist().getUserId()).orElse(null);

        return CommissionResponse.of(commission, clientProfile, artistProfile);
    }

    public Page<CommissionSummary> getMyCommissionsAsClient(Long userId, Pageable pageable) {
        return commissionRepository.findByClient_UserId(userId, pageable)
                .map(c -> {
                    Profile clientProfile = profileRepository.findByUser_UserId(c.getClient().getUserId()).orElse(null);
                    Profile artistProfile = profileRepository.findByUser_UserId(c.getArtist().getUserId()).orElse(null);
                    return CommissionSummary.of(c, clientProfile, artistProfile);
                });
    }

    public Page<CommissionSummary> getMyCommissionsAsArtist(Long userId, Pageable pageable) {
        return commissionRepository.findByArtist_UserId(userId, pageable)
                .map(c -> {
                    Profile clientProfile = profileRepository.findByUser_UserId(c.getClient().getUserId()).orElse(null);
                    Profile artistProfile = profileRepository.findByUser_UserId(c.getArtist().getUserId()).orElse(null);
                    return CommissionSummary.of(c, clientProfile, artistProfile);
                });
    }

    @Transactional
    public CommissionResponse updateStatus(Long userId, Long commissionId, CommissionUpdateRequest request) {
        Commission commission = commissionRepository.findById(commissionId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMISSION_NOT_FOUND));

        boolean isClient = commission.getClient().getUserId().equals(userId);
        boolean isArtist = commission.getArtist().getUserId().equals(userId);

        if (!isClient && !isArtist) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        commission.updateStatus(request.getStatus());

        Profile clientProfile = profileRepository.findByUser_UserId(commission.getClient().getUserId()).orElse(null);
        Profile artistProfile = profileRepository.findByUser_UserId(commission.getArtist().getUserId()).orElse(null);

        return CommissionResponse.of(commission, clientProfile, artistProfile);
    }

    @Transactional
    public CommissionResponse uploadFile(Long uploaderId, Long commissionId,
                                         String fileType, String fileUrl,
                                         String fileName, Long fileSize) {
        Commission commission = commissionRepository.findById(commissionId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMISSION_NOT_FOUND));

        boolean isClient = commission.getClient().getUserId().equals(uploaderId);
        boolean isArtist = commission.getArtist().getUserId().equals(uploaderId);

        if (!isClient && !isArtist) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        User uploader = userRepository.findById(uploaderId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        CommissionFile file = CommissionFile.builder()
                .commission(commission)
                .uploader(uploader)
                .fileType(fileType)
                .fileUrl(fileUrl)
                .fileName(fileName)
                .fileSize(fileSize)
                .isPublic(false)
                .build();

        commissionFileRepository.save(file);

        Profile clientProfile = profileRepository.findByUser_UserId(commission.getClient().getUserId()).orElse(null);
        Profile artistProfile = profileRepository.findByUser_UserId(commission.getArtist().getUserId()).orElse(null);

        return CommissionResponse.of(commission, clientProfile, artistProfile);
    }

    @Transactional
    public void cancelCommission(Long userId, Long commissionId) {
        Commission commission = commissionRepository.findById(commissionId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMISSION_NOT_FOUND));

        boolean isClient = commission.getClient().getUserId().equals(userId);
        boolean isArtist = commission.getArtist().getUserId().equals(userId);

        if (!isClient && !isArtist) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        if ("COMPLETED".equals(commission.getStatus())) {
            throw new CustomException(ErrorCode.INVALID_COMMISSION_STATUS);
        }

        commission.cancel();
    }
}
