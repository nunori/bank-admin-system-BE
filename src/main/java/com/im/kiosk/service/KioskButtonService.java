package com.im.kiosk.service;

import com.im.dashboard.entity.SpotInfo;
import com.im.dashboard.repository.SpotInfoRepository;
import com.im.kiosk.dto.KioskButtonCreateReq;
import com.im.kiosk.dto.KioskButtonRes;
import com.im.kiosk.dto.KioskButtonUpdateReq;
import com.im.kiosk.entity.KioskButtonInfo;
import com.im.kiosk.repository.KioskButtonRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KioskButtonService {

    private final KioskButtonRepository kioskButtonRepository;
    private final SpotInfoRepository spotInfoRepository;

    public List<KioskButtonRes> getButtonsByDeptId(int deptId) {
        List<KioskButtonInfo> buttons = kioskButtonRepository.findByDeptIdOrderByButtonPosition(deptId);
        return buttons.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateButtonSettings(List<KioskButtonUpdateReq> buttonUpdates) {
        for (KioskButtonUpdateReq updateRequest : buttonUpdates) {
            KioskButtonInfo button = kioskButtonRepository.findById(updateRequest.getButtonId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid button ID"));

            button.setButtonPosition(updateRequest.getPosition());
            button.setVisible(updateRequest.isVisible());
        }
    }

    @Transactional
    public KioskButtonRes createButton(KioskButtonCreateReq createReq) {
        SpotInfo spotInfo = spotInfoRepository.findById(createReq.getDeptId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid department ID"));

        KioskButtonInfo newButton = KioskButtonInfo.builder()
                .deptId(createReq.getDeptId())
                .buttonName(createReq.getButtonName())
                .buttonPosition(createReq.getButtonPosition())
                .visible(createReq.isVisible())
                .updatedBy(createReq.getUpdatedBy())
                .build();

        KioskButtonInfo savedButton = kioskButtonRepository.save(newButton);
        return convertToResponse(savedButton);
    }

    @Transactional
    public void deleteButton(int buttonId) {
        kioskButtonRepository.deleteById(buttonId);
    }

    private KioskButtonRes convertToResponse(KioskButtonInfo button) {
        return new KioskButtonRes(
                button.getButtonId(),
                button.getButtonName(),
                button.getButtonPosition(),
                button.isVisible()
        );
    }
}
