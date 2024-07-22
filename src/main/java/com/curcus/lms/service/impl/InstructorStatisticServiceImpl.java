package com.curcus.lms.service.impl;

import java.util.Map;

import com.curcus.lms.model.response.CourseResponse;
import com.curcus.lms.service.InstructorStatisticService;

public class InstructorStatisticServiceImpl implements InstructorStatisticService {

    @Override
    public Long getTotalrevenue(Long instructorId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTotalrevenue'");
    }

    @Override
    public Long getTotalCourses(Long instructorId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTotalCourses'");
    }

    @Override
    public CourseResponse GetTheMostPurchasedCourse(Long instructorId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'GetTheMostPurchasedCourse'");
    }

    @Override
    public Map<Long, Long> getRevenueStatisticsForYears(Long instructorId, int numberyear) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRevenueStatisticsForYears'");
    }

    @Override
    public Map<Long, Long> getTotalCoursesForYears(Long instructorId, int numberyear) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTotalCoursesForYears'");
    }

}
