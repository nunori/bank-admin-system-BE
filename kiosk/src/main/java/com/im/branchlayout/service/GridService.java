package com.im.branchlayout.service;

import com.im.branchlayout.dto.GridSizeUpdateRes;
import com.im.branchlayout.entity.GridInfo;
import com.im.branchlayout.repository.GridRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class GridService {
    private final GridRepository gridRepository;

    public GridSizeUpdateRes updateGridSize(Integer deptId, Integer floorNumber, Integer width, Integer height) {
        // deptId와 floorNumber 유효성 검사 추가
        if (deptId == null || floorNumber == null) {
            throw new IllegalArgumentException("부서ID와 층 번호는 필수입니다.");
        }

        // 그리드 크기 유효성 검사
        validateGridSize(width, height);

        // 기존 그리드 조회 또는 새로 생성
        GridInfo gridInfo = gridRepository.findByDeptIdAndFloorNumber(deptId, floorNumber)
                .orElse(GridInfo.builder()
                        .deptId(deptId)
                        .floorNumber(floorNumber)
                        .gridWidth(width)
                        .gridHeight(height)
                        .build());

        // 값 업데이트
        gridInfo.updateGridSize(width, height);

        // 저장
        GridInfo savedGrid = gridRepository.save(gridInfo);

        // GridSizeRes.from() 메서드를 사용하여 응답 생성
        return GridSizeUpdateRes.from(savedGrid);
    }

    private void validateGridSize(Integer width, Integer height) {
        if (width == null || height == null) {
            throw new IllegalArgumentException("가로/세로 크기는 필수입니다.");
        }

        if (width < 5 || width > 100 ||
                height < 5 || height > 100) {
            throw new IllegalArgumentException("그리드 크기는 5에서 30 사이여야 합니다.");
        }
    }

    // 부서별 모든 층의 그리드 정보 조회
    public List<GridSizeUpdateRes> getAllGridSizes(Integer deptId) {
        if (deptId == null) {
            throw new IllegalArgumentException("부서 ID는 필수입니다.");
        }

        List<GridInfo> gridInfos = gridRepository.findAllByDeptId(deptId);

        // 조회된 결과가 없을 경우 빈 리스트 반환
        if (gridInfos.isEmpty()) {
            log.info("부서 ID {}에 대한 그리드 정보가 없습니다.", deptId);
            return Collections.emptyList();
        }

        // GridInfo 엔티티를 GridSizeRes DTO로 변환
        return gridInfos.stream()
                .map(GridSizeUpdateRes::from)
                .collect(Collectors.toList());
    }

    public GridSizeUpdateRes getCurrentGridSize(Integer deptId, Integer floorNumber) {
        // deptId와 floorNumber 유효성 검사 추가
        if (deptId == null || floorNumber == null) {
            throw new IllegalArgumentException("부서ID와 층 번호는 필수입니다.");
        }

        GridInfo gridInfo = gridRepository.findByDeptIdAndFloorNumber(deptId, floorNumber)
                .orElseGet(() -> {
                    // 기본값으로 새 그리드 생성
                    GridInfo newGrid = GridInfo.builder()
                            .deptId(deptId)
                            .floorNumber(floorNumber)
                            .gridWidth(10) // 기본 가로 크기
                            .gridHeight(10) // 기본 세로 크기
                            .build();
                    return gridRepository.save(newGrid);
                });

        // GridSizeRes.from() 메서드를 사용하여 응답 생성
        return GridSizeUpdateRes.from(gridInfo);
    }
}