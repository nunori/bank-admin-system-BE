package com.im.branchlayout.service;

import com.im.branchlayout.dto.FloorCreateReq;
import com.im.branchlayout.dto.FloorGetRes;
import com.im.branchlayout.dto.FloorUpdateReq;
import com.im.branchlayout.entity.FloorInfo;
import com.im.branchlayout.repository.FloorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FloorService {

    private final FloorRepository floorRepository;

    public List<FloorInfo> getFloorsByDeptId(Integer deptId) {
        return floorRepository.findByDeptId(deptId);
    }

    public FloorInfo addFloor(FloorCreateReq request) {
        boolean exists = floorRepository.existsByDeptIdAndFloorNumber(
                request.getDeptId(),
                request.getFloorNumber()
        );

        if (exists) {
            throw new IllegalArgumentException("중복된 층 번호입니다: " + request.getFloorNumber());
        }
        FloorInfo floorInfo = FloorInfo.builder()
                .deptId(request.getDeptId())
                .floorNumber(request.getFloorNumber())
                .floorName(request.getFloorName())
                .build();

        return floorRepository.save(floorInfo);
    }

    public FloorInfo updateFloor(FloorUpdateReq request) {
        FloorInfo floorInfo = floorRepository.findByDeptId(request.getDeptId())
                .stream()
                .filter(floor -> floor.getFloorNumber().equals(request.getFloorNumber()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Floor not found"));

        floorInfo.setFloorName(request.getFloorName());
        return floorRepository.save(floorInfo);
    }

    public void deleteFloor(Integer deptId, Integer floorNumber) {
        FloorInfo floorInfo = floorRepository.findByDeptId(deptId)
                .stream()
                .filter(floor -> floor.getFloorNumber().equals(floorNumber))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Floor not found"));

        floorRepository.delete(floorInfo);
    }
}
