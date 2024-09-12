package com.cinema;

import java.util.ArrayList;

/**
 * Clase que representa una sala de cine.
 */
public class Cinema {

    private Seat[][] seats;


    /**
     * Construye una sala de cine. Se le pasa como dato un arreglo cuyo tamaño
     * es la cantidad de filas y los enteros que tiene son el número de butacas en cada fila.
     */
    public Cinema(int[] rows) {
        seats = new Seat[rows.length][];
        initSeats(rows);
    }

    /**
     * Inicializa las butacas de la sala de cine.
     *
     * @param rows arreglo que contiene la cantidad de butacas en cada fila
     */
    private void initSeats(int[] rows) {
        for (int i = 0; i < rows.length; i++) {
            seats[i] = new Seat[rows[i]];
        }
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                seats[i][j] = new Seat(i, j);
            }
        }
    }

    /**
     * Cuenta la cantidad de seats disponibles en el cine.
     */
    public int countAvailableSeats() {
        int totalSeats = 0;
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j].isAvailable()) {
                    totalSeats++;
                }
            }
        }
        return totalSeats;
    }

    /**
     * Busca la primera butaca libre dentro de una fila o null si no encuentra.
     */
    public Seat findFirstAvailableSeatInRow(int row) {
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j].isAvailable() && seats[i][j].getRow() == row) {
                    return seats[i][j];
                }
            }
        }
        return null;
    }

    /**
     * Busca la primera butaca libre o null si no encuentra.
     */
    public Seat findFirstAvailableSeat() {
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j].isAvailable()) {
                    return seats[i][j];
                }
            }
        }
        return null;
    }

    /**
     * Busca las N butacas libres consecutivas en una fila. Si no hay, retorna null.
     *
     * @param row    fila en la que buscará las butacas.
     * @param amount el número de butacas necesarias (N).
     * @return La primer butaca de la serie de N butacas, si no hay retorna null.
     */
    public Seat getAvailableSeatsInRow(int row, int amount) {
        int freeSeats = 0;
        Seat firstFreeSeat = null;
        for (int j = 0; j < seats[row].length; j++) {
            if (seats[row][j].isAvailable() && freeSeats == 0) {
                freeSeats += 1;
                firstFreeSeat = seats[row][j];
            } else if (seats[row][j].isAvailable() && freeSeats >= 1) {
                freeSeats += 1;
            } else {
                freeSeats = 0;
            }
            if (freeSeats == amount) {
                return firstFreeSeat;
            }
        }
        return null;
    }

    /**
     * Busca en toda la sala N butacas libres consecutivas. Si las encuentra
     * retorna la primer butaca de la serie, si no retorna null.
     *
     * @param amount el número de butacas pedidas.
     */
    public Seat getAvailableSeats(int amount) {
        for (int i = 0; i < seats.length; i++) {
            if (getAvailableSeatsInRow(i,amount)!= null){
                return getAvailableSeatsInRow(i,amount);
            }
        }
        return null;
    }

    /**
     * Marca como ocupadas la cantidad de butacas empezando por la que se le pasa.
     *
     * @param seat   butaca inicial de la serie.
     * @param amount la cantidad de butacas a reservar.
     */
    public void takeSeats(Seat seat, int amount) {
        int finals = 0;
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (amount > seats[i].length) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                else {
                    if (seats[i][j] == seat) {
                        for (int k = j; k < j + amount && k < seats[i].length; k++) {
                            if (seats[i][k].isAvailable()) {
                                seats[i][k].takeSeat();
                            }
                            finals += 1;
                        }
                    }
                    if (finals == amount) {
                        return;
                    }
                }
            }
        }
    }
    /**
     * Libera la cantidad de butacas consecutivas empezando por la que se le pasa.
     *
     * @param seat   butaca inicial de la serie.
     * @param amount la cantidad de butacas a liberar.
     */
    public void releaseSeats(Seat seat, int amount) {
        boolean seatFound = false;

        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j] == seat) {
                    seatFound = true;


                    if (j + amount > seats[i].length) {
                        throw new ArrayIndexOutOfBoundsException();
                    }


                    int finals = 0;
                    for (int k = j; k < j + amount; k++) {
                        if (!seats[i][k].isAvailable()) {
                            seats[i][k].releaseSeat();
                            finals++;
                        }
                    }

                    if (finals == amount) {
                        return;
                    }
                }
            }
        }
    }
}
