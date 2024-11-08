package com.im.kiosk.service;

import com.im.dashboard.entity.SpotInfo;
import com.im.dashboard.repository.SpotInfoRepository;
import com.im.kiosk.dto.TicketButtonCreateReq;
import com.im.kiosk.dto.TicketButtonRes;
import com.im.kiosk.dto.TicketButtonUpdateReq;
import com.im.kiosk.entity.TicketLayoutInfo;
import com.im.kiosk.repository.TicketLayoutInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketLayoutInfoService {

    private final TicketLayoutInfoRepository ticketLayoutInfoRepository;
    private final SpotInfoRepository spotInfoRepository;

    public List<TicketButtonRes> getButtonsByDeptId(int deptId) {
        List<TicketLayoutInfo> buttons = ticketLayoutInfoRepository.findByDeptIdOrderByButtonPosition(deptId);
        return buttons.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateButtonSettings(List<TicketButtonUpdateReq> buttonUpdates) {
        for (TicketButtonUpdateReq updateRequest : buttonUpdates) {
            TicketLayoutInfo button = ticketLayoutInfoRepository.findById(updateRequest.getTicketButtonId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid button ID"));

            button.setButtonPosition(updateRequest.getPosition());
            button.setVisible(updateRequest.isVisible());
            button.setButtonDescription(updateRequest.getButtonDescription());
        }
    }

    // 버튼 생성 로직
    @Transactional
    public TicketButtonRes createButton(TicketButtonCreateReq createReq) {
        SpotInfo spotInfo = spotInfoRepository.findById(createReq.getDeptId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid department ID"));

        TicketLayoutInfo newButton = TicketLayoutInfo.builder()
                .deptId(createReq.getDeptId())
                .buttonName(createReq.getButtonName())
                .buttonDescription(createReq.getButtonDescription())
                .buttonPosition(createReq.getButtonPosition())
                .visible(createReq.isVisible())
                .updatedBy(createReq.getUpdatedBy())
                .build();

        TicketLayoutInfo savedButton = ticketLayoutInfoRepository.save(newButton);
        return convertToResponse(savedButton);
    }

    // Helper method to convert Entity to DTO
    private TicketButtonRes convertToResponse(TicketLayoutInfo button) {
        return new TicketButtonRes(
                button.getTicketButtonId(),
                button.getButtonName(),
                button.getButtonDescription(),
                button.getButtonPosition(),
                button.isVisible()
        );
    }

    // 버튼 삭제 로직
    @Transactional
    public void deleteButton(int ticketButtonId) {
        TicketLayoutInfo button = ticketLayoutInfoRepository.findById(ticketButtonId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid button ID"));
        ticketLayoutInfoRepository.delete(button);
    }
}
