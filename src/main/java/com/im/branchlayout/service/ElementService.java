package com.im.branchlayout.service;

import com.im.branchlayout.dto.*;
import com.im.branchlayout.entity.ElementInfo;
import com.im.branchlayout.enums.ElementType;
import com.im.branchlayout.repository.ElementRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ElementService {

    private final ElementRepository elementRepository;

    public List<ElementGetRes> getElementsByDeptAndFloor(Integer deptId, Integer floorNumber) {
        List<ElementInfo> elements = elementRepository.findByDeptIdAndFloorNumber(deptId, floorNumber);

        return elements.stream()
                .map(element -> ElementGetRes.builder()
                        .elementId(element.getElementId())
                        .elementName(element.getElementName())
                        .elementType(element.getElementType())
                        .elementTypeDescription(element.getElementTypeDescription())
                        .elementGridX(element.getElementGridX())
                        .elementGridY(element.getElementGridY())
                        .elementWidth(element.getElementWidth())
                        .elementHeight(element.getElementHeight())
                        .elementColor(element.getElementColor())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public ElementCreateRes createElement(ElementCreateReq request) {
        ElementInfo newElement = ElementInfo.builder()
                .deptId(request.getDeptId())
                .floorNumber(request.getFloorNumber())
                .elementName(request.getElementName())
                .elementType(ElementType.valueOf(request.getElementType().toLowerCase())) // Enum 변환
                .elementGridX(request.getElementGridX())
                .elementGridY(request.getElementGridY())
                .elementWidth(request.getElementWidth())
                .elementHeight(request.getElementHeight())
                .elementColor(request.getElementColor())
                .build();

        // 데이터베이스에 엔티티 저장
        ElementInfo savedElement = elementRepository.save(newElement);

        // 엔티티를 바로 사용하여 DTO 생성
        return new ElementCreateRes(savedElement);
    }

    public void deleteElement(Integer elementId) {
        ElementInfo element = elementRepository.findById(elementId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid element ID: " + elementId));
        elementRepository.delete(element);
    }

    @Transactional
    public ElementUpdateRes updateElement(ElementUpdateReq request) {
        Integer elementId = request.getElementId();
        ElementInfo element = elementRepository.findById(elementId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid element ID: " + request.getElementId()));

        element.setElementName(request.getElementName());
        element.setElementType(ElementType.valueOf(request.getElementType().toLowerCase()));
        element.setElementGridX(request.getElementGridX());
        element.setElementGridY(request.getElementGridY());
        element.setElementWidth(request.getElementWidth());
        element.setElementHeight(request.getElementHeight());
        element.setElementColor(request.getElementColor());

        elementRepository.save(element);

        return ElementUpdateRes.builder()
                .elementId(element.getElementId())
                .floorNumber(element.getFloorNumber())
                .elementName(element.getElementName())
                .elementType(element.getElementType())
                .elementGridX(element.getElementGridX())
                .elementGridY(element.getElementGridY())
                .elementWidth(element.getElementWidth())
                .elementHeight(element.getElementHeight())
                .elementColor(element.getElementColor())
                .build();
    }

    public Integer getWindowCount(Integer deptId, Integer floorNumber) {
        return elementRepository.countWindowsByDeptIdAndFloorNumber(deptId, floorNumber);
    }
}