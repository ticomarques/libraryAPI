# Library API

### Project
The library API is based on a library, which you can perform all CRUD operations on entity Livros (books), and the book has an Autor (Author).

There is a relationship between Book and autor.


### Relationship
The book has an author. (1->1)

The Author can have multiple books. (1->n)


### JPA
**Transactions**: transient -> managed -> persisted (commited).