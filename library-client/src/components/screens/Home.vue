<template>
  <div>
    <h1>Libros</h1>

    <b-row>
      <b-col>
        <b-button variant="primary" @click="showModalAdd = true">Añadir libro</b-button>
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
    






    <b-card v-for="book in books" :key="book.id">
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
    <div class="overflow-auto">
      <b-pagination
          v-model="currentPage"
          :per-page="perPage"
          aria-controls="my-table"
      ></b-pagination>

      <p class="mt-3">Página actual: {{ currentPage }}</p>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import bookService from "../../services/Books.js";

export default Vue.extend({
  name: "Home",
  data() {
    return {
      sortBy: "title",
      sortDesc: false,
      perPage: 5,
      currentPage: 1,
      books: [],
      showModalCreate: false,
      showModalEdit: false,
      newBook: {
        title: "",
        author: "",
        genre: "",
        year: "",
      },
      options: [
        { value: null, text: 'Seleccione un genero' },
        { value: 'Accion', text: 'Accion' },
        { value: "FANTASY", text: "Fantasia" },
        { value: "SCIENCE_FICTION", text: "Ciencia Ficcion" },
        { value: "HORROR", text: "Terror" },
        { value: "ROMANCE", text: "Romance" },
        { value: "ADVENTURE", text: "Aventura" },
        { value: "MYSTERY", text: "Misterio" },
        { value: "THRILLER", text: "Thriller" },
        { value: "HISTORY", text: "Historia" },
        { value: "BIOGRAPHY", text: "Biografia" },
        { value: "AUTOBIOGRAPHY", text: "Autobiografia" },
        { value: "POETRY", text: "Poesia" },
        { value: "DRAMA", text: "Drama" },
        { value: "COMEDY", text: "Comedia" },
        { value: "ACTION", text: "Accion" },
        { value: "MANGA", text: "Manga" },
        { value: "COMIC", text: "Comic" },
        { value: "COOKBOOK", text: "Recetario" },
        { value: "DICTIONARY", text: "Diccionario" },
        { value: "ENCYCLOPEDIA", text: "Enciclopedia" },
        { value: "TEXTBOOK", text: "Libro de texto" },
        { value: "JOURNAL", text: "Revista" },
        { value: "MAGAZINE", text: "Periodico" },
        { value: "OTHER", text: "Otro" },
      ],
    };
  },

  methods: {
    async getBooks() {
      try {
        const data = await bookService.getBooksPaginated(
            parseInt(this.currentPage) - 1,
            parseInt(this.perPage),
            this.sortBy
        );
        this.books = data.content;
      } catch (error) {
        console.error(error);
      }
    },
    async createBook() {
      try {
        await bookService.saveBook(this.newBook);
        this.showModalCreate = false;
        this.getBooks();
      } catch (error) {
        console.error(error);
      }
    },

    async deleteBook(id) {
      try {
        await bookService.deleteBook(id);
        this.getBooks();
      } catch (error) {
        console.error(error);
      }
    },

    async editBook(book) {
      this.newBook = { ...book };
    },

    goToRoute(route) {
      this.$router.push(route);
    },
  },

  mounted() {
    this.getBooks();
  },
});

</script>

<style scoped>

</style>