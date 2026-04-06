import Utilities.Code;

import java.util.HashMap;
import java.util.Objects;

/**
 * @title Shelf.java
 * @abstract part of Library project dealing with logic representing bookshelves in a library.
 * @author Isabelle Johnson
 * @version 1.0.0
 * @Since 4/6/26
 **/

public class Shelf {

    public static final int SHELF_NUMBER_ = 0;
    public static final int SUBJECT_ = 1;
    private HashMap<Book, Integer> books;
    private int shelfNumber;
    private String subject;

    public Shelf() {

        this(0, "");
    }

    public Shelf(int num, String subject) {
        this.shelfNumber = num;
        this.subject = subject;
        this.books = new HashMap<Book, Integer>();
    }

    /**
     * Checks to see if another instance of aBook (same title) is in the books HashMap. If true adds 1 to the value to
     * the key of aBook, otherwise makes new key-value pair with 1 as the integer value.
     *
     * @param aBook book to be added to the shelf
     * @return code depending on if the book was successfully added or not
     */
    public Code addBook(Book aBook) {
        if (books.containsKey(aBook) && this != null) {
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

    /**
     * returns the integer value associated with aBook as a key in books HashMap.
     * @param aBook a book to get its count of on the shelf
     * @return number of instances of aBook on the shelf, returns -1 if there is no key for aBook in books
     */
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


    /**
     * parses through each key in books HashMap using keySet().toArray() to put in usable array and to parse through
     * each key to get its count to determine overall book count. Then parses through each indivdual key and prints its
     * Book class String and count.
     *
     * @return string listing the shelf information and all the books and quantities of books on the shelf
     */
    public String listBooks() {
        StringBuilder sb = new StringBuilder();
        int num = 0;
        Object[] allBooks =  books.keySet().toArray();
        for(int i = 0; i < allBooks.length; i++) {
            num += getBookCount((Book) allBooks[i]);
        }
        sb.append(num);
        sb.append(" books on shelf: ");
        sb.append(this.toString());
        for (int i = 0; i < allBooks.length; i++) {
            sb.append(allBooks[i].toString());
            sb.append(" ");
            sb.append(getBookCount((Book) allBooks[i]));
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * First checks that the key of aBook is on the shelf and there is at least 1 of those books, then removes 1 book
     * from that key. Also has checks resulting in invalid returns for cases where there are 0 books on the shelf with
     * a key and when the book it not a key in the books HashMap.
     *
     * @param aBook book to remove from the shelf
     * @return returns code used to show if a book was successfully removed or not.
     */
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
