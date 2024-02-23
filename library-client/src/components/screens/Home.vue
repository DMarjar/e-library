<template>
  <div class="p-4">
    <h1>Libros</h1>
    <CreateBookModal @book-created="getBooks" />
    <EditBookModal @book-updated="getBooks" :book="selectedBook" />
    <DateSearchModal @searchByDateRange="searchByDateRange" @searchByDescending="searchByDescending" />
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
          <b-form-input v-model="search" :placeholder="searchPlaceholderTranslator" aria-label="Buscar..."
            aria-describedby="search" @keyup.enter="findBookBy(searchBy)"></b-form-input>
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
      <b-col>
        <b-row>
          <b-col md="6" v-for="book in books" :key="book.id" class="mb-2">
            <b-card :title="book.title" id="card" ref="cards" v-bind:style="{ animationDelay: `${index * 0.1}s` }">
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
        </b-row>

      </b-col>
      <b-col>
        <div id="putDrop" v-show="showElement">
          <b-row>

            <b-col>
              <b-card title="Llena la información del libro">
                <b-form @submit.prevent="onSubmit" draggable="true" @dragstart="onDragStart">
                  <b-form-group label="Título" label-for="title">
                    <b-form-input id="title" type="text" v-model="title" required></b-form-input>
                  </b-form-group>
                  <b-form-group label="Autor" label-for="author">
                    <b-form-input id="author" type="text" v-model="author" required></b-form-input>
                  </b-form-group>
                  <b-form-group label="Género" label-for="genre">
                    <b-form-select class="form-select" id="genre" v-model="genre" :options="genreList" required>
                      <template #first>
                        <option :value="null" disabled>Seleccione un género</option>
                      </template>
                    </b-form-select>
                  </b-form-group>
                  <b-form-group label="Año" label-for="year">
                    <b-form-input id="year" type="number" placeholder="yyyy" v-model="year" required></b-form-input>
                  </b-form-group>
                  <b-form-group label="Mes" label-for="month">
                    <b-form-input id="month" type="number" placeholder="mm" v-model="month" required></b-form-input>
                  </b-form-group>
                  <b-form-group label="Día" label-for="day">
                    <b-form-input id="day" type="number" placeholder="dd" v-model="day" required></b-form-input>
                  </b-form-group>
                </b-form>
              </b-card>
            </b-col>

          </b-row>
          <b-row>
            <b-col>
              <div class="drop-zone, mt-3" @drop="onDrop($event)" @dragover.prevent @dragenter.prevent>
                <b-card id="ubicacionTexto" title="Suelta aquí los libros que quieras crear"></b-card>
              </div>
            </b-col>
          </b-row>
        </div>
      </b-col>

      <div class="overflow-auto">
        <b-pagination v-model="currentPage" :per-page="perPage" aria-controls="my-table"></b-pagination>

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
import genreList from "@/utils/GenreList";

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
      lastScrollPosition: 0,
      showElement: true,


      // Search
      search: "",
      searchBy: "title",

      // Paginator
      sortBy: "title",
      sortDesc: false,
      perPage: 5,
      currentPage: 1,

      // Book
      title: "",
      author: "",
      genre: "",
      year: "",
      month: "",
      day: "",

      // Genre list
      genreList,
    };
  },

  methods: {

    onSubmit() {

    },
    onDragStart(event) {
      event.dataTransfer.setData("text/plain", JSON.stringify({
        title: this.title,
        author: this.author,
        genre: this.genre,
        fullPublishDate: `${this.year}-${this.month}-${this.day}`,
      }));
    },
    onDrop(event) {
      event.preventDefault();
      const bookData = JSON.parse(event.dataTransfer.getData("text/plain"));
      this.saveBook(bookData);
    },
    async saveBook(book) {
      try {
        await bookService.saveBook(book);
        this.$emit("book-created");

        this.clearForm();
      } catch (error) {
        console.error(error);
      }
    },
    clearForm() {
      this.title = "";
      this.author = "";
      this.genre = "";
      this.year = "";
      this.month = "";
      this.day = "";
    },


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


    async onSubmit() {
      const fullPublishDate = `${this.year}-${this.month}-${this.day}`;
      const book = {
        title: this.title,
        author: this.author,
        genre: this.genre,
        fullPublishDate,
      };
      await bookService.saveBook(book);
      this.$emit("book-created");
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


    onScroll() {

      const currentScrollPosition = window.pageYOffset || document.documentElement.scrollTop;
      console.log(currentScrollPosition);

      if (Math.abs(currentScrollPosition - this.lastScrollPosition) < 60) {
        return;
      }
      this.showElement = currentScrollPosition < this.lastScrollPosition;
      //  
      this.lastScrollPosition = currentScrollPosition;
    },


  },

  mounted() {
    this.getBooks();
    this.animateCardsOnLoad = true;

    window.addEventListener("scroll", this.onScroll);





  },

  beforeDestroy() {
    window.removeEventListener("scroll", this.onScroll);


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

#card {
  height: 100%;
  width: 50%;
}

#putDrop {
  width: 50%;
  height: 50%;
  margin-left: 50%;
}

#ubicacionTexto {
  text-align: center;
}

#card {
  animation: zoomIn 1s;
}
</style>