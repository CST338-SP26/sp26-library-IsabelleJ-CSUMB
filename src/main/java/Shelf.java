import Utilities.Code;

import java.util.HashMap;
import java.util.Objects;

public class Shelf {

    public static final int SHELF_NUMBER_ = 0;
    public static final int SUBJECT_ = 1;
    private HashMap<Book, Integer> books;
    private int shelfNumber;
    private String subject;

    public Shelf() {

    }

    public Shelf(int num, String subject) {
        this.shelfNumber = num;
        this.subject = subject;
        this.books = new HashMap<Book, Integer>();
    }

    public Code addBook(Book aBook) {
        if (books.containsKey(aBook)) {
            books.put(aBook, (books.get(aBook) + 1));
            return Code.SUCCESS;
        } else {
            if (aBook.getSubject() == subject) {
                books.put(aBook, 1);
                return Code.SUCCESS;
            } else {
                return Code.SHELF_SUBJECT_MISMATCH_ERROR;
            }
        }
    }

    public int getBookCount(Book aBook) {
        return 0;
    }

    public HashMap<Book, Integer> getBooks() {
        return books;
    }

    public int getShelfNumber() {
        return shelfNumber;
    }

    public String getSubject() {
        return subject;
    }


    public String listBooks() {
        return null;
    }

    public Code removeBook(Book aBook) {
        return null;
    }

    public void setBooks(HashMap<Book, Integer> books) {
        this.books = books;
    }

    public void setShelfNumber(int shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(shelfNumber);
        sb.append(" : ");
        sb.append(subject);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Shelf shelf = (Shelf) o;
        return shelfNumber == shelf.shelfNumber && Objects.equals(subject, shelf.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shelfNumber, subject);
    }
}
