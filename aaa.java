        public void addBook(String title, String author) {
            int id = books.size() + 1;
            Book book = new Book(id, title, author);
            books.add(book);
            saveBooksToFile();
            System.out.println("Book added successfully!");
        }

        public void addMember(String name) {
            int id = members.size() + 1;
            Member member = new Member(id, name);
            members.add(member);
            saveMembersToFile();
            System.out.println("Member added successfully!");
        }

        public void issueBook(int bookId, int memberId) {
            Book book = findBookById(bookId);
            Member member = findMemberById(memberId);

            if (book == null) {
                System.out.println("Book not found!");
                return;
            }

            if (member == null) {
                System.out.println("Member not found!");
                return;
            }

            if (book.isIssued()) {
                System.out.println("Book is already issued!");
                return;
            }

            book.setIssued(true);
            issuedBooks.put(bookId, memberId);
            saveBooksToFile();
            saveIssuedBooksToFile();
            System.out.println("Book issued successfully to member: " + member.getName());
        }

        public void returnBook(int bookId) {
            Book book = findBookById(bookId);

            if (book == null) {
                System.out.println("Book not found!");
                return;
            }

            if (!book.isIssued()) {
                System.out.println("This book is not issued to anyone!");
                return;
            }

            book.setIssued(false);
            issuedBooks.remove(bookId);
            saveBooksToFile();
            saveIssuedBooksToFile();
            System.out.println("Book returned successfully!");
        }

        public void viewBooks() {
            if (books.isEmpty()) {
                System.out.println("No books available in the library.");
                return;
            }

            for (Book book : books) {
                System.out.println(book);
            }
        }

        public void viewMembers() {
            if (members.isEmpty()) {
                System.out.println("No members registered.");
                return;
            }

            for (Member member : members) {
                System.out.println(member);
            }
        }

        public void viewIssuedBooks() {
            if (issuedBooks.isEmpty()) {
                System.out.println("No books are currently issued.");
                return;
            }

            for (Map.Entry<Integer, Integer> entry : issuedBooks.entrySet()) {
                Book book = findBookById(entry.getKey());
                Member member = findMemberById(entry.getValue());
                System.out.println("Book: " + book.getTitle() + " (ID: " + book.getId() + ") issued to Member: " + member.getName() + " (ID: " + member.getId() + ")");
            }
        }

        private Book findBookById(int id) {
            for (Book book : books) {
                if (book.getId() == id) {
                    return book;
                }
            }
            return null;
        }

        private Member findMemberById(int id) {
            for (Member member : members) {
                if (member.getId() == id) {
                    return member;
                }
            }
            return null;
        }

        private void saveBooksToFile() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKS_FILE))) {
                for (Book book : books) {
                    writer.write(book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.isIssued());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error saving books: " + e.getMessage());
            }
        }

        private void saveMembersToFile() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(MEMBERS_FILE))) {
                for (Member member : members) {
                    writer.write(member.getId() + "," + member.getName());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error saving members: " + e.getMessage());
            }
        }

        private void saveIssuedBooksToFile() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ISSUED_BOOKS_FILE))) {
                for (Map.Entry<Integer, Integer> entry : issuedBooks.entrySet()) {
                    writer.write(entry.getKey() + "," + entry.getValue());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error saving issued books: " + e.getMessage());
            }
        }

        private void loadBooksFromFile() {
            books.clear();
            File file = new File(BOOKS_FILE);
            if (!file.exists()) return;

            try (BufferedReader reader = new BufferedReader(new FileReader(BOOKS_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    int id = Integer.parseInt(parts[0]);
                    String title = parts[1];
                    String author = parts[2];
                    boolean isIssued = Boolean.parseBoolean(parts[3]);
                    Book book = new Book(id, title, author);
                    book.setIssued(isIssued);
                    books.add(book);
                }
            } catch (IOException e) {
                System.out.println("Error loading books: " + e.getMessage());
            }
        }

        private void loadMembersFromFile() {
            members.clear();
            File file = new File(MEMBERS_FILE);
            if (!file.exists()) return;

            try (BufferedReader reader = new BufferedReader(new FileReader(MEMBERS_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    members.add(new Member(id, name));
                }
            } catch (IOException e) {
                System.out.println("Error loading members: " + e.getMessage());
            }
        }

        private void loadIssuedBooksFromFile() {
            issuedBooks.clear();
            File file = new File(ISSUED_BOOKS_FILE);
            if (!file.exists()) return;

            try (BufferedReader reader = new BufferedReader(new FileReader(ISSUED_BOOKS_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    int bookId = Integer.parseInt(parts[0]);
                    int memberId = Integer.parseInt(parts[1]);
                    issuedBooks.put(bookId, memberId);
                }
            } catch (IOException e) {
                System.out.println("Error loading issued books: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. View Books");
            System.out.println("4. View Members");
            System.out.println("5. Issue Book");
            System.out.println("6. Return Book");
            System.out.println("7. View Issued Books");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();
                    library.addBook(title, author);
                    break;
                case 2:
                    System.out.print("Enter member name: ");
                    String name = scanner.nextLine();
                    library.addMember(name);
                    break;
                case 3:
                    library.viewBooks();
                    break;
                case 4:
                    library.viewMembers();
                    break;
                case 5:
                    System.out.print("Enter book ID to issue: ");
                    int bookId = scanner.nextInt();
                    System.out.print("Enter member ID to issue to: ");
                    int memberId = scanner.nextInt();
                    library.issueBook(bookId, memberId);
                    break;
                case 6:
                    System.out.print("Enter book ID to return: ");
                    int returnBookId = scanner.nextInt();
                    library.returnBook(returnBookId);
                    break;
                case 7:
                    library.viewIssuedBooks();
                    break;
                case 8:
                    System.out.println("Exiting. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
