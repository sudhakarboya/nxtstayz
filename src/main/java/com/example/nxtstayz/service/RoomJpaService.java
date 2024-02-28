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
public class RoomJpaService implements RoomRepository {
    @Autowired
    private HotelJpaRepository hotelJpaRepository;

    @Autowired
    private RoomJpaRepository roomJpaRepository;

    @Override
    public ArrayList<Room> getRooms() {
        return (ArrayList<Room>) roomJpaRepository.findAll();
    }

    @Override
    public Room getRoomById(int roomId) {
        try {
            return roomJpaRepository.findById(roomId).get();

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Room addRoom(Room room) {
        int hotelId = room.getHotel().getHotelId();
        try {
            Hotel hotel = hotelJpaRepository.findById(hotelId).get();
            room.setHotel(hotel);
            return roomJpaRepository.save(room);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public Room updateRoom(int roomId, Room room) {
        try {
            Room existRoom = roomJpaRepository.findById(roomId).get();
            if (room.getRoomNumber() != null) {
                existRoom.setRoomNumber(room.getRoomNumber());
            }
            if (room.getRoomType() != null) {
                existRoom.setRoomType(room.getRoomType());
            }
            if (room.getPrice() != 0.0) {
                existRoom.setPrice(room.getPrice());
            }
            if (room.getHotel() != null) {
                int hotelId = room.getHotel().getHotelId();
                Hotel hotel = hotelJpaRepository.findById(hotelId).get();
                existRoom.setHotel(hotel);

            }
            return roomJpaRepository.save(existRoom);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteRoom(int roomId) {
        try {
            roomJpaRepository.deleteById(roomId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);

    }

    @Override
    public Hotel getRoomHotel(int roomId) {
        try {
            Room room = roomJpaRepository.findById(roomId).get();
            return room.getHotel();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
