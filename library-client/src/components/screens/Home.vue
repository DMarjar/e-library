<template>
  <div>
    <h1>Libros</h1>
    <b-button variant="primary" v-b-modal.createBookModal>Crear libro</b-button>
    <CreateBookModal @book-created="getBooks" />
    <EditBookModal @book-updated="getBooks" :book="selectedBook" />
    <b-row>
      <b-card v-for="book in books" :key="book.id">
        <b-card-text>
          <b-row>
            <b-col>
              <h3>{{ book.title }}</h3>
            </b-col>
            <b-col>
              <p>{{ book.author }}</p>
            </b-col>
            <b-col>
              <p>{{ book.genre }}</p>
            </b-col>
            <b-col>
              <p>{{ book.year }}</p>
            </b-col>
            <b-col>
              <b-button variant="primary" @click="selectBook(book)" v-b-modal.editBookModal>Editar</b-button>
              <b-button @click="deleteBook(book)" variant="danger">Eliminar</b-button>
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
    </b-row>
  </div>
</template>

<script>
import Vue from "vue";
import bookService from "../../services/Books.js";
import CreateBookModal from "@/components/screens/CreateBookModal.vue";
import EditBookModal from "@/components/screens/EditBookModal.vue";

export default Vue.extend({
  name: "Home",
  components: {
    CreateBookModal,
    EditBookModal,
  },
  data() {
    return {
      // Books
      books: [],
      selectedBook: null,

      // Paginator
      sortBy: "title",
      sortDesc: false,
      perPage: 5,
      currentPage: 1,
    };
  },

  methods: {
    async getBooks() {
      console.log("Getting books")
      this.books = [];
      this.selectedBook = null;

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

    async deleteBook(book) {
      try {
        await bookService.deleteBook(book.id);
        await this.getBooks();
      } catch (error) {
        console.error(error);
      }
    },

    selectBook(book) {
      this.selectedBook = book;
    },
  },

  mounted() {
    this.getBooks();
  },
});

</script>

<style scoped>

</style>