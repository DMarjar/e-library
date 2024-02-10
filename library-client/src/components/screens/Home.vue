<template>
    <b-container fluid>
      <b-row class="mt-4">
        <b-col>
          <h1>Administracion de libros</h1>
        </b-col>
      </b-row>
      <b-row class="mt-4">
        <b-row>
          <b-col>
            <b-button id="creteBook" variant="primary" @click="showModalCreate = true">Crear libro</b-button>
            <b-modal v-model="showModalCreate" title="Creacion de libro" @hide="resetCreateForm">
              <b-form @submit.prevent="createBook">
                <b-form-group id="titulo" label="Titulo:" label-for="input-1">
                  <b-form-input id="input-1" v-model="newBook.title" required></b-form-input>
                </b-form-group>
                <b-form-group id="autor" label="Autor:" label-for="input-2">
                  <b-form-input id="input-2" v-model="newBook.author" required></b-form-input>
                </b-form-group>
                <b-form-group id="genero" label="Genero:" label-for="input-3">
                  <b-form-select id="input-3" v-model="newBook.genre" :options="options"></b-form-select>
                </b-form-group>
                <label for="año">Año de publicacion</label>
                <b-input-group class="mb-3">
                  <b-form-input id="añoPublicacion" v-model="newBook.year" type="text" placeholder="YYYY-MM-DD" autocomplete="off"></b-form-input>
                  <b-input-group-append>
                  </b-input-group-append>
                </b-input-group>
                <b-button type="submit" variant="primary">Crear</b-button>
              </b-form>
            </b-modal>
          </b-col>
        </b-row>
        <b-row>
          <b-card v-for="book in paginatedBooks" :key="book.id">
            <b-card-text>
              <b-row>
                <b-col>
                  <h3>{{ book.title }}</h3>
                </b-col>
                <b-col>
                  <b-button variant="primary" @click="showModalEdit = true; editBook(book)">Editar</b-button>
                  <b-button variant="danger" @click="deleteBook(book.id)">Eliminar</b-button>
                </b-col>
              </b-row>
              <b-row>
                <b-col>
                  <p>{{ book.author }}</p>
                </b-col>
              </b-row>
              <b-row>
                <b-col>
                  <p>{{ book.genre }}</p>
                </b-col>
              </b-row>
              <b-row>
                <b-col>
                  <p>{{ book.year }}</p>
                </b-col>
              </b-row>
            </b-card-text>
          </b-card>
        </b-row>
        <b-row>
          <b-pagination v-model="currentPage" :total-rows="totalBooks" :per-page="perPage" class="mt-3"></b-pagination>
        </b-row>
      </b-row>
    </b-container>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        showModalCreate: false,
        showModalEdit: false,
        newBook: {
          title: '',
          author: '',
          genre: null,
          year: ''
        },
        editedBook: {},
        options: [
          { value: null, text: 'Seleccione un genero' },
          { value: 'Accion', text: 'Accion' },
          { value: 'Aventura', text: 'Aventura' },
          { value: 'Comedia', text: 'Comedia' },
          { value: 'Drama', text: 'Drama' },
          { value: 'Fantasia', text: 'Fantasia' },
          { value: 'Horror', text: 'Horror' },
          { value: 'Misterio', text: 'Misterio' },
          { value: 'Romance', text: 'Romance' },
          { value: 'Ciencia Ficcion', text: 'Ciencia Ficcion' },
          { value: 'Suspenso', text: 'Suspenso' },
          { value: 'Terror', text: 'Terror' }
        ],
        books: [],
        currentPage: 1,
        perPage: 5
      }
    },
  
    mounted(){
      this.fetchBooks();
    },
  
    computed: {
      paginatedBooks() {
        const start = (this.currentPage - 1) * this.perPage;
        const end = start + this.perPage;
        return this.books.slice(start, end);
      },
      totalBooks() {
        return this.books.length;
      }
    },
  
    methods: {
      fetchBooks() {
        axios.get('http://localhost:8081/api/books/page')
          .then(response => {
            this.books = response.data.content;
          })
          .catch(error => {
            console.error('Error fetching books:', error);
          });
      },
      createBook() {
        axios.post('http://localhost:8081/api/books/book', this.newBook)
          .then(response => {
            console.log('Book created:', response.data);
            this.showModalCreate = false;
            this.resetCreateForm();
            this.fetchBooks(); 
          })
          .catch(error => {
            console.error('Error creating book:', error);
          });
      },
      editBook(book) {
        this.editedBook = { ...book };
      },
      updateBook() {
        axios.put(`http://localhost:8081/api/books/book/${this.editedBook.id}`, this.editedBook)
          .then(response => {
            console.log('Book updated:', response.data);
            this.showModalEdit = false;
            this.fetchBooks(); 
          })
          .catch(error => {
            console.error('Error updating book:', error);
          });
      },
      deleteBook(id) {
        axios.delete(`http://localhost:8081/api/books/book/${id}`)
          .then(response => {
            console.log('Book deleted:', id);
            this.fetchBooks(); 
          })
          .catch(error => {
            console.error('Error deleting book:', error);
          });
      },
      resetCreateForm() {
        this.newBook = {
          title: '',
          author: '',
          genre: null,
          year: ''
        };
      }
    }
  }
  </script>
  
  <style scoped>
  #genero {
      margin-bottom: 15px;
  }
  
  #titulo {
      margin-bottom: 15px;
  }
  
  #autor {
      margin-bottom: 15px;
  }
  </style>
  