package com.im.kiosk.service;

import com.im.dashboard.entity.SpotInfo;
import com.im.dashboard.repository.SpotInfoRepository;
import com.im.kiosk.dto.TicketButtonCreateReq;
import com.im.kiosk.dto.TicketButtonUpdateReq;
import com.im.kiosk.entity.TicketLayoutInfo;
import com.im.kiosk.repository.TicketLayoutInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketLayoutInfoService {

    private final TicketLayoutInfoRepository ticketLayoutInfoRepository;
    private final SpotInfoRepository spotInfoRepository;

    public List<TicketLayoutInfo> getButtonsByDeptId(int deptId) {
        return ticketLayoutInfoRepository.findByDeptIdOrderByButtonPosition(deptId);
    }

    @Transactional
    public void updateButtonSettings(List<TicketButtonUpdateReq> buttonUpdates) {
        for (TicketButtonUpdateReq updateRequest : buttonUpdates) {
            TicketLayoutInfo button = ticketLayoutInfoRepository.findById(updateRequest.getTicketButtonId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid button ID"));

            button.setButtonPosition(updateRequest.getPosition());
            button.setVisible(updateRequest.isVisible());
        }
    }

    // 버튼 생성 로직
    @Transactional
    public TicketLayoutInfo createButton(TicketButtonCreateReq createReq) {
        // SpotInfo 엔티티를 조회하여 외래 키를 설정
        SpotInfo spotInfo = spotInfoRepository.findById(createReq.getDeptId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid department ID"));

        // TicketLayoutInfo 엔티티를 생성하여 저장
        TicketLayoutInfo newButton = TicketLayoutInfo.builder()
                .deptId(createReq.getDeptId())
                .buttonName(createReq.getButtonName())
                .buttonPosition(createReq.getButtonPosition())
                .visible(createReq.isVisible())
                .updatedBy(createReq.getUpdatedBy())
                .build();

        return ticketLayoutInfoRepository.save(newButton);
    }

    // 버튼 삭제 로직
    @Transactional
    public void deleteButton(int ticketButtonId) {
        TicketLayoutInfo button = ticketLayoutInfoRepository.findById(ticketButtonId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid button ID"));
        ticketLayoutInfoRepository.delete(button);
    }
}
