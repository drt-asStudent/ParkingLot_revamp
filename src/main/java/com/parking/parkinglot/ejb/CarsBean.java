package com.parking.parkinglot.ejb;

import jakarta.ejb.Stateless;
import jakarta.ejb.EJBException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import com.parking.parkinglot.common.CarDto;
import com.parking.parkinglot.entities.Car;
import com.parking.parkinglot.entities.User;

import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection; // Import necesar

@Stateless
public class CarsBean {
    private static final Logger LOG = Logger.getLogger(CarsBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    public List<CarDto> findAllCars() {
        LOG.info("findAllCars");
        try {
            TypedQuery<Car> typedQuery = entityManager.createQuery("SELECT c FROM Car c", Car.class);
            List<Car> cars = typedQuery.getResultList();
            return copyCarsToDto(cars);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    private List<CarDto> copyCarsToDto(List<Car> cars) {
        List<CarDto> carDtos = new ArrayList<>();
        for (Car car : cars) {
            CarDto dto = new CarDto(
                    car.getOwner().getUsername(),
                    car.getLicensePlate(),
                    car.getId(),
                    car.getParkingSpot()
            );
            carDtos.add(dto);
        }
        return carDtos;
    }

    public void createCar(String licensePlate, String parkingSpot, Long userId) {
        LOG.info("createCar");

        Car car = new Car();
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);

        User user = entityManager.find(User.class, userId);
        user.getCars().add(car);
        car.setOwner(user);

        entityManager.persist(car);
    }

    public CarDto findById(Long carId) {
        LOG.info("findById");

        Car car = entityManager.find(Car.class, carId);

        if (car == null) {
            return null;
        }

        return new CarDto(
                car.getOwner().getUsername(),
                car.getLicensePlate(),
                car.getId(),
                car.getParkingSpot()
        );
    }

    // --- METODA NOUĂ PENTRU ȘTERGERE ---
    public void deleteCarsByIds(Collection<Long> carIds) {
        LOG.info("deleteCarsByIds");

        for (Long carId : carIds) {
            Car car = entityManager.find(Car.class, carId);
            if (car != null) {
                entityManager.remove(car);
            }
        }
    }
    public void updateCar(Long carId, String licensePlate, String parkingSpot, Long userId) {
        LOG.info("updateCar");

        Car car = entityManager.find(Car.class, carId);
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);

        // 1. Scoatem mașina de la vechiul proprietar
        User oldUser = car.getOwner();
        oldUser.getCars().remove(car);

        // 2. Adăugăm mașina la noul proprietar
        User user = entityManager.find(User.class, userId);
        user.getCars().add(car);
        car.setOwner(user);

    }
}