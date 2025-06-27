package com.carrentalsystem.app.repository;

import com.carrentalsystem.app.entity.Booking;
import com.carrentalsystem.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {
    List<Booking> findByUser_Id(Integer userId);

    List<Booking> findAllByUser_Id(Integer userId);

    Integer user(User user);

}
