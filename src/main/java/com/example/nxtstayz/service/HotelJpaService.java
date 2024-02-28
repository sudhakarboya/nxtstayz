/*
 *
 * You can use the following import statements
 * 
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.ArrayList;
 * import java.util.List;
 * 
 */

// Write your code here
package com.example.nxtstayz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Service;

import com.example.nxtstayz.model.*;
import com.example.nxtstayz.repository.*;
import java.util.*;

@Service
public class HotelJpaService implements HotelRepository {
    @Autowired
    private HotelJpaRepository hotelJpaRepository;

    @Autowired
    private RoomJpaRepository roomJpaRepository;

    @Override
    public ArrayList<Hotel> getHotels() {
        return (ArrayList<Hotel>) hotelJpaRepository.findAll();
    }

    @Override
    public Hotel getHotelById(int hotelId) {
        try {
            return hotelJpaRepository.findById(hotelId).get();

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Hotel addHotel(Hotel hotel) {

        return hotelJpaRepository.save(hotel);

    }

    @Override
    public Hotel updateHotel(int hotelId, Hotel hotel) {
        try {
            Hotel existHotel = hotelJpaRepository.findById(hotelId).get();
            if (hotel.getHotelName() != null) {
                existHotel.setHotelName(hotel.getHotelName());
            }
            if (hotel.getLocation() != null) {
                existHotel.setLocation(hotel.getLocation());
            }
            if (hotel.getRating() != 0) {
                existHotel.setRating(hotel.getRating());
            }
            return hotelJpaRepository.save(existHotel);

        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteHotel(int hotelId) {
        try {
            roomJpaRepository.deleteById(hotelId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);

    }

    @Override
    public List<Room> getHotelRooms(int hotelId) {
        try {
            Hotel hotel = hotelJpaRepository.findById(hotelId).get();
            List<Room> hotelRooms = roomJpaRepository.findByHotel(hotel);
            return hotelRooms;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}