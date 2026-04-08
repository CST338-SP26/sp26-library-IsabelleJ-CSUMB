import Utilities.Code;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Library {

    public static final int LENDING_LIMIT = 0;
    private HashMap<Book, Integer> books;
    private int libraryCard;
    private String name;
    private List<Reader> readers;
    private HashMap<String, Shelf> shelves;

    public Library(String name) {

    }

    public Code addBook(Book aBook) {
        return null;
    }

    private Code addBookToShelf(Book aBook, Shelf aShelf) {
        return null;
    }

    public Code addReader(Reader aReader) {
        return null;
    }

    public Code addShelf(Shelf aShelf) {
        return null;
    }

    public Code addShelf(String aString) {
        return null;
    }

    public Code checkOutBook(Reader aReader, Book Abook) {
        return null;
    }

    public LocalDate convertDate(String dateString, Code aCode) {
        return null;
    }

    public int convertInt(String aString, Code aCode) {
        return 0;
    }

    private Code errorCode(int num) {
        return null;
    }

    public Book getBookByISBN(String isbn) {
        return null;
    }

    public int getLibraryCardNumber() {
        return this.libraryCard;
    }

    public String getName(){
        return this.name;
    }

    public String getReaderByCard(int cardNum) {
        return null;
    }

    public Shelf getShelf(String shelf) {
        return null;
    }

    public Shelf getShelf(int shelfNum) {
        return null;
    }

    public Code init(String aString) {
        return null;
    }

    private Code initBooks(int num, Scanner aScanner) {
        return null;
    }

    public Code initReader(int num, Scanner aScanner) {
        return null;
    }

    public Code initShelves(int num, Scanner aScanner) {
        return null;
    }

    public int listBooks() {
        return 0;
    }

    public int listReaders() {
        return 0;
    }

    public int listReaders(boolean aBool) {
        return 0;
    }

    public int listShelves(boolean aBool) {
        return 0;
    }

    public int listShelves() {
        return 0;
    }

    public Code removeReader(Reader aReader) {
        return null;
    }



}
