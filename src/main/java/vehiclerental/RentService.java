package vehiclerental;

import java.time.LocalTime;
import java.util.*;

public class RentService {

    private Set<User> users = new HashSet<>();
    private Set<Rentable> rentables = new HashSet<>();
    private Map<Rentable, User> actualRenting = new TreeMap<>(Comparator.comparing(Rentable::getRentingTime));

    public Set<User> getUsers() {
        return users;
    }

    public Set<Rentable> getRentables() {
        return rentables;
    }

    public Map<Rentable, User> getActualRenting() {
        return actualRenting;
    }

    public void registerUser(User user) {
        if (users.stream().map(User::getUserName).toList().contains(user.getUserName())) {
            throw new UserNameIsAlreadyTakenException("Username is taken!");
        } else {
            users.add(user);
        }
    }

    public void addRentable(Rentable rentable) {
        rentables.add(rentable);
    }

    public void rent(User user, Rentable rentable, LocalTime time) {
        if (rentable.getRentingTime() == null && user.getBalance() >= rentable.calculateSumPrice(180)) {
            rentable.rent(time);
            actualRenting.put(rentable, user);
        } else {
            throw new IllegalStateException("Can not rent!");
        }
    }

    public void closeRent(Rentable rentable, int minutes) {
        if (actualRenting.containsKey(rentable)) {
            actualRenting.get(rentable).minusBalance(rentable.calculateSumPrice(minutes));
            actualRenting.remove(rentable);
            rentable.closeRent();
        } else {
            throw new IllegalStateException("Rentable not found!");
        }
    }

    public static void main(String[] args) {
        System.out.println(LocalTime.of(3, 0).isAfter(LocalTime.of(3, 0)));
    }
}
