package com.videorental;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class CustomerTest {
    public static final String NAME = "NAME_NOT_IMPORTANT";
    public static final String TITLE = "TITLE_NOT_IMPORTANT";
    Customer customer = new Customer(NAME);

    @Test
    public void returnNewCustomer() {
        assertThat(customer).isNotNull();
    }

    @Test
    public void statementForNoRental() {
        assertThat(customer.statement()).isEqualTo("Rental Record for NAME_NOT_IMPORTANT\n" +
                "Amount owed is 0.0\n" +
                "You earned 0 frequent renter pointers");
    }

    private static Rental createRentalFor(int daysRented, int priceCode) {
        Movie movie = new Movie(TITLE, priceCode);
        Rental rental = new Rental(movie, daysRented);
        return rental;
    }

    @Test
    public void statementForRegularMovieRentalLessThan3Days(){
        customer.addRental(createRentalFor(2, Movie.REGULAR));

        assertThat(customer.statement()).isEqualTo("Rental Record for NAME_NOT_IMPORTANT\n" +
                "\t2.0(TITLE_NOT_IMPORTANT)\n" +
                "Amount owed is 2.0\n" +
                "You earned 1 frequent renter pointers");
    }

    @Test
    public void statementForRegularMovieRentalMoreThan2Days(){
        customer.addRental(createRentalFor(3, Movie.REGULAR));

        assertThat(customer.statement()).isEqualTo("Rental Record for NAME_NOT_IMPORTANT\n" +
                "\t3.5(TITLE_NOT_IMPORTANT)\n" +
                "Amount owed is 3.5\n" +
                "You earned 1 frequent renter pointers");
    }

    @Test
    public void statementForNewReleaseMovie() {
        customer.addRental(createRentalFor(1, Movie.NEW_RELEASE));

        assertThat(customer.statement()).isEqualTo("Rental Record for NAME_NOT_IMPORTANT\n" +
                "\t3.0(TITLE_NOT_IMPORTANT)\n" +
                "Amount owed is 3.0\n" +
                "You earned 1 frequent renter pointers");
    }

    @Test
    public void statementForChildrenMovieRentalMoreThan3Days(){
        customer.addRental(createRentalFor(4, Movie.CHILDRENS));

        assertThat(customer.statement()).isEqualTo("Rental Record for NAME_NOT_IMPORTANT\n" +
                "\t3.0(TITLE_NOT_IMPORTANT)\n" +
                "Amount owed is 3.0\n" +
                "You earned 1 frequent renter pointers");
    }

    @Test
    public void statementForChildrenMovieRentalLessThan4Days(){
        customer.addRental(createRentalFor(3, Movie.CHILDRENS));

        assertThat(customer.statement()).isEqualTo("Rental Record for NAME_NOT_IMPORTANT\n" +
                "\t1.5(TITLE_NOT_IMPORTANT)\n" +
                "Amount owed is 1.5\n" +
                "You earned 1 frequent renter pointers");
    }

    @Test
    public void statementForNewReleaseMovieRentalMoreThan1Days(){
        customer.addRental(createRentalFor(2, Movie.NEW_RELEASE));

        assertThat(customer.statement()).isEqualTo("Rental Record for NAME_NOT_IMPORTANT\n" +
                "\t6.0(TITLE_NOT_IMPORTANT)\n" +
                "Amount owed is 6.0\n" +
                "You earned 2 frequent renter pointers");
    }

    @Test
    public void statementForFewMovieRental(){
        customer.addRental(createRentalFor(1, Movie.REGULAR));
        customer.addRental(createRentalFor(4, Movie.NEW_RELEASE));
        customer.addRental(createRentalFor(4, Movie.CHILDRENS));

        assertThat(customer.statement()).isEqualTo("Rental Record for NAME_NOT_IMPORTANT\n" +
                "\t2.0(TITLE_NOT_IMPORTANT)\n" +
                "\t12.0(TITLE_NOT_IMPORTANT)\n" +
                "\t3.0(TITLE_NOT_IMPORTANT)\n" +
                "Amount owed is 17.0\n" +
                "You earned 4 frequent renter pointers");
    }

    @Test
    public void setPriceCodeOfMovie(){
        Movie movie = new Movie(TITLE, Movie.NEW_RELEASE);
        movie.setPriceCode(2);
        Rental rental = new Rental(movie, 3);
        customer.addRental(rental);

        assertThat(customer.statement()).isEqualTo("Rental Record for NAME_NOT_IMPORTANT\n" +
                "\t1.5(TITLE_NOT_IMPORTANT)\n" +
                "Amount owed is 1.5\n" +
                "You earned 1 frequent renter pointers");
    }
}
