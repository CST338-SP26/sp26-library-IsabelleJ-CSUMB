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
        if (!books.containsKey(aBook)) {
            return -1;
        }
        return books.get(aBook).intValue();
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
        StringBuilder sb = new StringBuilder();
        int num = 0;
        Book[] allBooks = (Book[]) books.keySet().toArray();
        for(int i = 0; i < allBooks.length; i++) {
            num += getBookCount(allBooks[i]);
        }
        sb.append(num);
        sb.append(" books on shelf: ");
        sb.append(this.toString());
        sb.append(books.toString());
        return sb.toString();
    }

    public Code removeBook(Book aBook) {
        if (books.containsKey(aBook) && books.get(aBook) > 0) {
            books.put(aBook, books.get(aBook) - 1);
            System.out.println(aBook.getTitle() + " successfully removed from shelf " + subject);
            return Code.SUCCESS;
        } else if (books.containsKey(aBook) && books.get(aBook) == 0) {
            System.out.println("No copies of " + aBook.getTitle() + "remain on shelf " + subject);
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        } else {
            System.out.println(aBook.getTitle() + "is not on shelf " + subject);
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }
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
