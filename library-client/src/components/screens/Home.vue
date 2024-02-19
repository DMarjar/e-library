<template>
  <div class="p-4">
    <h1>Libros</h1>
    <CreateBookModal @book-created="getBooks"/>
    <EditBookModal @book-updated="getBooks" :book="selectedBook"/>
    <DateSearchModal @searchByDateRange="searchByDateRange" @searchByDescending="searchByDescending"/>
    <b-row class="mb-2">
      <b-col cols="8">
        <b-input-group>
          <b-input-group-prepend>
            <b-dropdown text="Buscar por" variant="outline-secondary">
              <b-dropdown-item @click="searchBy = 'title'">Título</b-dropdown-item>
              <b-dropdown-item @click="searchBy = 'author'">Autor</b-dropdown-item>
              <b-dropdown-item @click="searchBy = 'genre'">Género</b-dropdown-item>
            </b-dropdown>
          </b-input-group-prepend>
          <b-form-input
              v-model="search"
              :placeholder="searchPlaceholderTranslator"
              aria-label="Buscar..."
              aria-describedby="search"
              @keyup.enter="findBookBy(searchBy)"
          ></b-form-input>
          <b-input-group-append>
            <b-button @click="findBookBy(searchBy)">
              <b-icon icon="search" aria-hidden="true">
              </b-icon>
            </b-button>
          </b-input-group-append>
        </b-input-group>
      </b-col>
      <b-col cols="2">
        <b-button class="blocky" variant="success" v-b-modal.dateSearchModal>Fechas</b-button>
      </b-col>
      <b-col cols="2">
        <b-button class="blocky" variant="primary" v-b-modal.createBookModal>Registrar libro</b-button>
      </b-col>
    </b-row>
    <b-row>
      <b-col v-for="book in books" :key="book.id" cols="4" class="mb-2">
        <b-card :title="book.title">
          <b-card-text>
            <b-row>
              <b-col cols="12">
                <p>{{ book.author }}</p>
              </b-col>
              <b-col cols="12">
                <p>{{ book.genre }}</p>
              </b-col>
              <b-col cols="12">
                <p>{{ book.fullPublishDate }}</p>
              </b-col>
              <b-col cols="6">
                <b-button variant="primary" @click="selectBook(book)" v-b-modal.editBookModal>Editar</b-button>
              </b-col>
              <b-col cols="6">
                <b-button @click="deleteBook(book)" variant="danger">Eliminar</b-button>
              </b-col>
            </b-row>
          </b-card-text>
        </b-card>
      </b-col>

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
import DateSearchModal from "@/components/screens/DateSearchModal.vue";

export default Vue.extend({
  name: "Home",
  components: {
    CreateBookModal,
    EditBookModal,
    DateSearchModal,
  },
  data() {
    return {
      // Books
      books: [],
      selectedBook: null,

      // Search
      search: "",
      searchBy: "title",

      // Paginator
      sortBy: "title",
      sortDesc: false,
      perPage: 5,
      currentPage: 1,
    };
  },

  methods: {

    async searchByDateRange(dateRangeObject) {
      try {
        const data = await bookService.findBooksByDateRangePaginated(
            dateRangeObject.dateStart,
            dateRangeObject.dateEnd
        );
        this.books = data.content;
      } catch (error) {
        console.error(error);
      }
    },

    async searchByDescending() {
      try {
        const data = await bookService.findBooksByDescendingPaginated();
        this.books = data.content;
      } catch (error) {
        console.error(error);
      }
    },

    async getBooks() {
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

    async findBookBy(searchBy) {
      try {
        let data;
        switch (searchBy) {
          case "title":
            data = await bookService.findBooksByTitlePaginated(this.search);
            this.books = data.content;
            break;
          case "author":
            data = await bookService.findBooksByAuthorPaginated(this.search);
            this.books = data.content;
            break;
          case "genre":
            data = await bookService.findBooksByGenrePaginated(this.search);
            this.books = data.content;
            break;
          default:
            data = await bookService.findBooksByTitlePaginated(this.search);
            this.books = data.content;
        }
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

  computed: {
    searchPlaceholderTranslator() {
      this.search = "";
      switch (this.searchBy) {
        case "title":
          return "Título...";
        case "author":
          return "Autor...";
        case "genre":
          return "Género...";
        default:
          return "Título...";

      }
    },
  },
});

</script>

<style scoped>
.blocky {
  display: block;
  width: 100%;
}
</style>