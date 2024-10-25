package com.TripPlan.TripPlanProject.controller;

import com.TripPlan.TripPlanProject.dto.DetailResponseDTO;
import com.TripPlan.TripPlanProject.dto.PlanlistResponseDTO;
import com.TripPlan.TripPlanProject.dto.UserResponseDTO;
import com.TripPlan.TripPlanProject.model.Planlist;
import com.TripPlan.TripPlanProject.model.Tripplandetail;
import com.TripPlan.TripPlanProject.service.JwtTokenProvider;
import com.TripPlan.TripPlanProject.service.PlanlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/plan")
public class PlanController {

    private final PlanlistService planlistService;
    private final JwtTokenProvider jwtTokenProvider;

    public PlanController(PlanlistService planlistService, JwtTokenProvider jwtTokenProvider) {
        this.planlistService = planlistService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 일정 생성 api
    @PostMapping("/plancreate")
    public ResponseEntity<?> createPlan(@RequestHeader("Authorization") String token, @RequestBody PlanlistResponseDTO planRequest) {
        try{
            String jwtToken = token.substring(7);

            if (!jwtTokenProvider.validateToken(jwtToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
            }

            String userId = jwtTokenProvider.getUsername(jwtToken);

            planRequest.setUserId(userId);
            UserResponseDTO response = planlistService.createPlan(planRequest.toEntity());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    // 세부 일정 생성 api
    @PostMapping("/plandetailcreate")
    public ResponseEntity<?> getCreateplandetail(@RequestHeader("Authorization") String token, @RequestBody DetailResponseDTO planRequest) {
        try{
            String jwtToken = token.substring(7);

            if (!jwtTokenProvider.validateToken(jwtToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
            }

            if (planRequest.getPlannum() == null || planRequest.getPlannum().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Plan number is missing");
            }

            UserResponseDTO response = planlistService.addDetail(planRequest.toEntity());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    // 일정 리스트 목록 확인 api
    @GetMapping("/planlist")
    public ResponseEntity<?> getPlanlists(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);

        if (!jwtTokenProvider.validateToken(jwtToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
        }

        try {
            String userId = jwtTokenProvider.getUsername(jwtToken);
            List<Planlist> planlists = planlistService.getPlanlistsByUserId(userId);
            List<PlanlistResponseDTO> response = planlists.stream()
                    .map(planlist -> new PlanlistResponseDTO(
                            planlist.getPlannum(),
                            planlist.getUserId(),
                            planlist.getStartDate(),
                            planlist.getEndDate(),
                            planlist.getTripTotalDate(),
                            planlist.getTripRegion(),
                            planlist.getTripRoute(),
                            planlist.isTripOpen()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server Error: " + e.getMessage());
        }
    }

    // 세부 일정 목록 확인 api
    @GetMapping("/plandetaillist")
    public ResponseEntity<?> getPlanDetails(
            @RequestHeader("Authorization") String token,
            @RequestParam String plannum) {
        String jwtToken = token.substring(7);

        if (!jwtTokenProvider.validateToken(jwtToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
        }

        try {
            List<Tripplandetail> details = planlistService.getPlandetailByPlannum(plannum);
            if (details.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No details found for the provided plan number");
            }

            List<DetailResponseDTO> response = details.stream()
                    .map(detail -> new DetailResponseDTO(
                            detail.getPlannum(),
                            detail.getTripdate(),
                            detail.getDestination(),
                            detail.getVector(),
                            detail.getVisit(),
                            detail.getMemo()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
